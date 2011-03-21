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

import org.bundlemaker.core.osgi.manifest.ManifestUtils;
import org.eclipse.core.runtime.CoreException;

import com.springsource.bundlor.util.SimpleManifestContents;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public abstract class AbstractManifestTemplateBasedExporter extends AbstractTemplateDirectoryBasedExporter {

  /** the manifest template contents */
  private ManifestContents _manifestTemplateContents;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final ManifestContents getCurrentManifestTemplate() {
    return _manifestTemplateContents;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preExportModule() throws CoreException {

    //
    super.preExportModule();

    // get the template manifest
    _manifestTemplateContents = createManifestTemplate();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents createManifestTemplate() {

    //
    if (getCurrentTemplateRootDirectory() == null) {
      return createDefaultManifestTemplate();
    }

    File templateFile = getManifestTemplateFile();

    if (templateFile == null) {
      return createDefaultManifestTemplate();
    }

    ManifestContents templateManifestContents = ManifestUtils.readManifestContents(templateFile);

    //
    if (templateManifestContents != null) {
      return templateManifestContents;
    }

    // return the default manifest contents
    else {
      return createDefaultManifestTemplate();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected File getManifestTemplateFile() {

    //
    File rootDirectory = null;

    // step 1: does a module template directory exists (e.g.
    // '<root>/x.y.z_1.2.3/' or '<root>/x.y.z/')?
    if (hasCurrentModuleTemplateDirectory()) {

      // step 1a: get the current root directory
      rootDirectory = getCurrentModuleTemplateDirectory();

      // step 1b: try '<root>/<module-root>/x.y.z_1.2.3.template'
      File templateFile = new File(rootDirectory, getCurrentModule().getModuleIdentifier().toString() + ".template");

      // step 1c: try '<root>/<module-root>/x.y.z.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, getCurrentModule().getModuleIdentifier().getName() + ".template");
      }

      // step 1d: try '<root>/<module-root>/manifest.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, "manifest.template");
      }

      // step 1e: try '<root>/<module-root>/manifest.properties'
      // DON'T USE - JUST FOR BACKWARD COMPATIBILITY
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, "manifest.template");
      }

      //
      return templateFile.exists() ? templateFile : null;
    }

    // step 2: try the root template directory
    else {

      // step 1b: try '<root>/x.y.z_1.2.3.template'
      File templateFile = new File(getCurrentTemplateRootDirectory(), getCurrentModule().getModuleIdentifier()
          .toString() + ".template");

      // step 1c: try '<root>/x.y.z.template'
      if (!templateFile.exists()) {
        templateFile = new File(rootDirectory, getCurrentModule().getModuleIdentifier().getName() + ".template");
      }

      //
      return templateFile.exists() ? templateFile : null;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ManifestContents createDefaultManifestTemplate() {
    return new SimpleManifestContents();
  }
}
