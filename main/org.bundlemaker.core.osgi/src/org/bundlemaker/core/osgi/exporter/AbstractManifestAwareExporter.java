/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.osgi.exporter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.exporter.helper.ManifestCreatorHelper;
import org.bundlemaker.core.osgi.internal.manifest.DroolsBasedBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.osgi.framework.BundleException;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractManifestAwareExporter extends AbstractExporter {

  /** - */
  private CycleAwareGenericCache             _manifestCache;

  /** - */
  private ManifestContents                   _manifestContents;

  /** - */
  private IBundleManifestCreator             _creator;

  /** the root directory for all templates */
  private File                               _templateRootDirectory;

  /** - */
  private IManifestPreferences               _manifestPreferences;

  /** - */
  private List<IManifestContentsInterceptor> _interceptors;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractManifestAwareExporter}.
   * </p>
   */
  public AbstractManifestAwareExporter() {

    // TODO
    _creator = new DroolsBasedBundleManifestCreator();

    //
    _manifestCache = new CycleAwareGenericCache();

    //
    _interceptors = new ArrayList<IManifestContentsInterceptor>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final List<IManifestContentsInterceptor> getInterceptors() {
    return _interceptors;
  }

  /**
   * <p>
   * </p>
   * 
   * @param templateRootDirectory
   */
  public final void setTemplateRootDirectory(File templateRootDirectory) {
    Assert.isNotNull(templateRootDirectory);
    Assert.isTrue(templateRootDirectory.isDirectory());

    _templateRootDirectory = templateRootDirectory;
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifestPreferences
   */
  public void setManifestPreferences(IManifestPreferences manifestPreferences) {
    Assert.isNotNull(manifestPreferences);

    _manifestPreferences = manifestPreferences;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ManifestContents getManifestContents() {
    return _manifestContents;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preExportModule() throws CoreException {

    // call super
    super.preExportModule();

    // get the manifest contents
    _manifestCache.clearCycleSet();
    _manifestContents = _manifestCache.getOrCreate(getCurrentModule());

    // check the manifest
    try {
      StateObjectFactory.defaultFactory.createBundleDescription(null,
          ManifestUtils.convertManifest(ManifestUtils.toManifest(_manifestContents)), "internal", 1);
    } catch (BundleException e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected File getCurrentModuleTemplateDirectory() {

    // TODO
    ManifestCreatorHelper helper = new ManifestCreatorHelper(getCurrentModularizedSystem(), getCurrentModule(),
        getCurrentContext(), _templateRootDirectory, null, _creator, _manifestPreferences);

    return helper.getModuleTemplateDirectory() != null ? helper.getModuleTemplateDirectory() : null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected boolean hasCurrentModuleTemplateDirectory() {

    // TODO
    ManifestCreatorHelper helper = new ManifestCreatorHelper(getCurrentModularizedSystem(), getCurrentModule(),
        getCurrentContext(), _templateRootDirectory, null, _creator, _manifestPreferences);

    return helper.getModuleTemplateDirectory() != null && helper.getModuleTemplateDirectory().exists();
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifestContents
   */
  private void executeInterceptors(ManifestContents manifestContents, IModularizedSystem modularizedSystem,
      IModule module) {

    //
    for (IManifestContentsInterceptor interceptor : _interceptors) {

      //
      interceptor.manipulateManifestContents(manifestContents, modularizedSystem, module);
    }
  }

  /**
   * @author P200329
   * 
   */
  private final class CycleAwareGenericCache extends GenericCache<IResourceModule, ManifestContents> {
    //
    private Set<IResourceModule> _hostModules = new HashSet<IResourceModule>();

    public void clearCycleSet() {
      _hostModules.clear();
    }

    @Override
    protected ManifestContents create(IResourceModule resourceModule) {

      //
      ManifestContents hostManifestContents = null;

      if (ManifestUtils.isFragment(resourceModule)) {

        //
        IResourceModule hostModule = (IResourceModule) ManifestUtils.getFragmentHost(resourceModule);

        if (!hostModule.equals(resourceModule)) {

          //
          if (_hostModules.contains(hostModule)) {
            // TODO
            throw new RuntimeException("CycleException " + _hostModules);
          } else {
            _hostModules.add(hostModule);
            hostManifestContents = this.getOrCreate(hostModule);
          }
        }
      }

      //
      ManifestCreatorHelper helper = new ManifestCreatorHelper(getCurrentModularizedSystem(), resourceModule,
          getCurrentContext(), _templateRootDirectory, hostManifestContents, _creator, _manifestPreferences);

      //
      ManifestContents manifestContents = helper.createManifest();

      //
      executeInterceptors(manifestContents, getCurrentModularizedSystem(), resourceModule);

      //
      Assert.isNotNull(manifestContents,
          String.format("The method create(IModule) of class " + "'%s' returned 'null'.", helper.getClass().getName()));

      //
      return manifestContents;
    }
  }
}
