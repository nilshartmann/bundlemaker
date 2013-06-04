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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.osgi.internal.exporter.ManifestCreatorAdapter;
import org.bundlemaker.core.osgi.manifest.DefaultManifestCreator;
import org.bundlemaker.core.osgi.manifest.DefaultManifestPreferences;
import org.bundlemaker.core.osgi.manifest.IBundleManifestCreator;
import org.bundlemaker.core.osgi.manifest.IManifestPreferences;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;
import org.osgi.framework.BundleException;

/**
 * <p>
 * Abstract base class for all OSGi manifest aware exporter.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractManifestAwareExporter extends AbstractExporter {

  /** the current manifest contents */
  private ManifestContents                   _manifestContents;

  /** the bundle manifest creator */
  private IBundleManifestCreator             _creator;

  /** the template provider */
  private ITemplateProvider                  _templateProvider;

  /** the manifest preferences */
  private IManifestPreferences               _manifestPreferences;

  /** the list of interceptors */
  private List<IManifestContentsInterceptor> _interceptors;

  /** the internal manifest cache */
  private CycleAwareGenericCache             _manifestCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractManifestAwareExporter}.
   * </p>
   * 
   * @param templateProvider
   * @param bundleManifestCreator
   * @param manifestPreferences
   */
  protected AbstractManifestAwareExporter(ITemplateProvider templateProvider,
      IBundleManifestCreator bundleManifestCreator, IManifestPreferences manifestPreferences) {

    // null-safe initialize
    _templateProvider = templateProvider != null ? templateProvider : new NullTemplateProvider();
    _creator = bundleManifestCreator != null ? bundleManifestCreator : new DefaultManifestCreator();
    _manifestPreferences = manifestPreferences != null ? manifestPreferences : new DefaultManifestPreferences(false);

    //
    _manifestCache = new CycleAwareGenericCache();
    _interceptors = new ArrayList<IManifestContentsInterceptor>();
  }

  /**
   * <p>
   * Returns the {@link ITemplateProvider} for this exporter.
   * </p>
   * 
   * @return the {@link ITemplateProvider} for this exporter.
   */
  public final ITemplateProvider getTemplateProvider() {
    return _templateProvider;
  }

  /**
   * <p>
   * Returns the list of interceptors.
   * </p>
   * 
   * @return the list of interceptors.
   */
  public final List<IManifestContentsInterceptor> getInterceptors() {
    return _interceptors;
  }

  /**
   * <p>
   * Sets the manifest preferences.
   * </p>
   * 
   * @param manifestPreferences
   *          the manifest preferences.
   */
  public void setManifestPreferences(IManifestPreferences manifestPreferences) {
    Assert.isNotNull(manifestPreferences);

    _manifestPreferences = manifestPreferences;
  }

  /**
   * <p>
   * Returns the current manifest contents.
   * </p>
   * 
   * @return the current manifest contents.
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
    Dictionary<String, String> dictionary = null;
    try {
      dictionary = new Hashtable<String, String>();
      Properties properties = ManifestUtils.convertManifest(ManifestUtils.toManifest(_manifestContents));
      for (String propertyName : properties.stringPropertyNames()) {
        dictionary.put(propertyName, properties.getProperty(propertyName));
      }
      StateObjectFactory.defaultFactory.createBundleDescription(null, dictionary, "internal", 1);
    } catch (BundleException e) {
      // TODO
      System.out.println(dictionary);
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }
  }

  /**
   * <p>
   * Executes all registered interceptors for the given manifest contents
   * </p>
   * 
   * @param manifestContents
   *          the manifest contents
   * @param modularizedSystem
   *          the {@link IModularizedSystem}
   * @param module
   *          the {@link IModule}
   */
  private final void executeInterceptors(ManifestContents manifestContents, IModularizedSystem modularizedSystem,
      IModule module) {

    // iterate over all the interceptors
    for (IManifestContentsInterceptor interceptor : _interceptors) {

      // assert not null
      if (interceptor != null) {

        // call manipulateManifestContents
        interceptor.manipulateManifestContents(manifestContents, modularizedSystem, module);
      }
    }
  }

  /**
   * <p>
   * Cache that creates manifests for a given resource module. If the module is a fragment module, the host module will
   * be resolved first as the host manifest is needed to create the fragment manifest.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class CycleAwareGenericCache extends GenericCache<IModule, ManifestContents> {

    /** default serialVersionUID */
    private static final long    serialVersionUID = 1L;

    /** the host modules */
    private Set<IModule> _hostModules     = new HashSet<IModule>();

    /**
     * {@inheritDoc}
     */
    @Override
    protected ManifestContents create(IModule resourceModule) {

      // define the host manifest contents (as it is needed to create a fragments manifest correctly)
      ManifestContents hostManifestContents = null;

      // test if the resource module is a fragment
      if (ManifestUtils.isFragment(resourceModule)) {

        // find the host module
        IModule hostModule = (IModule) ManifestUtils.getFragmentHost(resourceModule);

        // check (should not be false here)
        if (!hostModule.equals(resourceModule)) {

          // check if the module has already been handled to prevent loops
          if (_hostModules.contains(hostModule)) {
            // throw RuntimeException
            throw new RuntimeException("CycleException " + _hostModules);
          } else {

            // add to handled modules list
            _hostModules.add(hostModule);

            // get the host manifest contents
            hostManifestContents = this.getOrCreate(hostModule);
          }
        }
      }

      // create the adapter
      ManifestCreatorAdapter adapter = new ManifestCreatorAdapter(getCurrentModularizedSystem(), resourceModule,
          getCurrentContext(), _templateProvider, hostManifestContents, _creator, _manifestPreferences);

      // create the manifest contents
      ManifestContents manifestContents = adapter.createManifest();

      //
      Assert.isNotNull(manifestContents,
          String.format("The method createManifest() of class '%s' returned 'null'.", adapter.getClass().getName()));

      // execute the interceptors
      executeInterceptors(manifestContents, getCurrentModularizedSystem(), resourceModule);

      // return the manifest contents
      return manifestContents;
    }

    /**
     * <p>
     * Clears the cycle set.
     * </p>
     */
    public void clearCycleSet() {
      _hostModules.clear();
    }
  }

  /**
   * <p>
   * Default implementation of an {@link ITemplateProvider} that returns no templates.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  class NullTemplateProvider implements ITemplateProvider {

    /**
     * {@inheritDoc}
     */
    @Override
    public ManifestContents getTemplate(IModule module, IModularizedSystem modularizedSystem,
        IModuleExporterContext context) {
      return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IResource> getAdditionalResources(IModule currentModule,
        IModularizedSystem currentModularizedSystem, IModuleExporterContext currentContext) {

      //
      return Collections.emptySet();
    }
  }
}
