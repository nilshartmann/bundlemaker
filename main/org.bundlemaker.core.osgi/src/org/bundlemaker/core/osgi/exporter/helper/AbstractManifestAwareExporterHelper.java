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
package org.bundlemaker.core.osgi.exporter.helper;

import java.io.File;
import java.io.IOException;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.utils.ManifestUtils;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractManifestAwareExporterHelper extends AbstractManifestTemplateBasedExporterHelper {

  /** - */
  private ManifestContents _manifestContents;

  /** - */
  private ManifestContents _originalManifestContents;

  /** - */
  private ManifestContents _hostManifestContents;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractManifestAwareExporterHelper}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @param templateRootDirectory
   */
  public AbstractManifestAwareExporterHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context, File templateRootDirectory, ManifestContents hostManifestContents) {
    super(modularizedSystem, module, context, templateRootDirectory);

    _hostManifestContents = hostManifestContents;
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractManifestAwareExporterHelper}.
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   */
  public AbstractManifestAwareExporterHelper(IModularizedSystem modularizedSystem, IResourceModule module,
      IModuleExporterContext context) {

    this(modularizedSystem, module, context, null, null);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final boolean isFragment() {
    return _hostManifestContents != null;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents getCurrentManifest() {
    Assert.isNotNull(_manifestContents, String.format("No manifest set. The method createManifest(IModularizedSystem, "
        + "IResourceModule, IModuleExporterContext) of class " + "'%s' has not been called yet.", this.getClass()
        .getName()));

    return _manifestContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents getOriginalManifest() {

    // the original manifest contents
    if (_originalManifestContents == null) {

      // the existing bundle manifest resource
      IResource existingManifestResource = getModule().getResource("META-INF/MANIFEST.MF", ContentType.BINARY);

      // create default manifest
      if (existingManifestResource == null) {
        _originalManifestContents = ManifestUtils.toManifestContents(BundleManifestFactory.createBundleManifest());
      }

      // the existing bundle manifest
      try {
        _originalManifestContents = ManifestUtils.readManifestContents(existingManifestResource);
      } catch (IOException exception) {
        exception.printStackTrace();
        _originalManifestContents = ManifestUtils.toManifestContents(BundleManifestFactory.createBundleManifest());

      }
    }

    //
    return _originalManifestContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param module
   * @param context
   * @return
   * @throws Exception
   */
  protected abstract ManifestContents createManifest();
}
