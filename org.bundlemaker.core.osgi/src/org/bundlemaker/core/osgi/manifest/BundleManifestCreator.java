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
package org.bundlemaker.core.osgi.manifest;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.util.ModuleExporterUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.osgi.exporter.ManifestConstants;
import org.bundlemaker.core.osgi.manifest.internal.CurrentModule;
import org.bundlemaker.core.osgi.manifest.internal.importresolver.ImportResolver;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Version;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.osgi.manifest.BundleSymbolicName;
import com.springsource.util.osgi.manifest.ExportPackage;
import com.springsource.util.osgi.manifest.ExportedPackage;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleManifestCreator {

  /** - */
  private BundleManifest  _newBundleManifest;

  /** - */
  private CurrentModule   _currentModule;

  /** - */
  private DependencyStyle _dependencyStyle = DependencyStyle.PREFER_IMPORT_PACKAGE;

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param manifestTemplate
   * @param modularizedSystem
   * @param context
   */
  public BundleManifestCreator(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      IModuleExporterContext context, ManifestContents manifestTemplate) {

    //
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(context);
    Assert.isNotNull(manifestTemplate);

    //
    _currentModule = new CurrentModule(modularizedSystem, resourceModule, context, manifestTemplate);
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
  public ManifestContents createManifest() throws CoreException {

    // the existing bundle manifest resource
    IResource existingManifestResource = _currentModule.getResourceModule().getResource("META-INF/MANIFEST.MF",
        ContentType.BINARY);

    // the existing bundle manifest
    ManifestContents existingManifest;
    try {
      existingManifest = ManifestUtils.readManifestContents(existingManifestResource);

      // return immediately if manifest already is a bundle manifest
      if (ManifestUtils.isBundleManifest(existingManifest)
          && !ModuleExporterUtils.requiresRepackaging(_currentModule.getResourceModule(), ContentType.BINARY)) {

        // return the existing manifest
        return existingManifest;
      }

    } catch (IOException e) {
      // TODO
      e.printStackTrace();
      throw new CoreException(new Status(IStatus.ERROR, "", ""));
    }

    // create a new bundle manifest
    _newBundleManifest = BundleManifestFactory.createBundleManifest();

    // set the header
    createBundleManifestVersion();
    createBundleName();
    createBundleSymbolicName();
    createBundleVersion();
    createImportPackageAndRequiredBundle();
    createExportPackage();

    // get the new manifest contents
    ManifestContents newManifestContents = ManifestUtils.toManifestContents(_newBundleManifest);

    // copy the original headers
    copyOriginalHeaders(newManifestContents, existingManifest);

    // copy the template headers
    copyTemplateHeaders(newManifestContents, _currentModule.getManifestTemplate());

    // return the result
    return newManifestContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @param useRequireBundle
   */
  public void setDependencyStyle(DependencyStyle dependencyStyle) {
    _dependencyStyle = dependencyStyle;
  }

  /**
   * <p>
   * </p>
   */
  protected void createBundleName() {

    // set the symbolic name
    _newBundleManifest.setBundleName(_currentModule.getResourceModule().getModuleIdentifier().getName());
  }

  protected void copyTemplateHeaders(ManifestContents newManifestContents, ManifestContents manifestTemplate) {

    // iterate over the exiting main attributes
    for (Entry<String, String> entry : manifestTemplate.getMainAttributes().entrySet()) {

      // copy if not ignored
      if (!ManifestConstants.TEMPLATE_HEADERS_NOT_TO_COPY.contains(entry.getKey())) {

        //
        newManifestContents.getMainAttributes().put(entry.getKey().toString(),
            manifestTemplate.getMainAttributes().get(entry.getKey().toString()));
      }
    }

  }

  protected void copyOriginalHeaders(ManifestContents newManifestContents, ManifestContents existingManifest) {

    // iterate over the exiting main attributes
    for (Entry<String, String> entry : existingManifest.getMainAttributes().entrySet()) {

      // copy if not ignored
      if (!ManifestConstants.ORIGINAL_HEADERS_NOT_TO_COPY.contains(entry.getKey())) {

        String key = entry.getKey().toString();
        String value = existingManifest.getMainAttributes().get(entry.getKey().toString());

        if (value != null && !value.trim().isEmpty()) {

          //
          newManifestContents.getMainAttributes().put(key.trim(), value.trim());
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   */
  protected void createBundleManifestVersion() {

    // set the bundle manifest version to '2'
    _newBundleManifest.setBundleManifestVersion(2);
  }

  /**
   * <p>
   * </p>
   */
  protected void createBundleSymbolicName() {

    // get the BundleSymbolicName 'header'
    BundleSymbolicName bundleSymbolicName = _newBundleManifest.getBundleSymbolicName();

    // set the symbolic name
    bundleSymbolicName.setSymbolicName(_currentModule.getResourceModule().getModuleIdentifier().getName());
  }

  /**
   * <p>
   * </p>
   */
  protected void createBundleVersion() {

    //
    Version version = null;

    try {

      //
      version = new Version(_currentModule.getResourceModule().getModuleIdentifier().getVersion());
    } catch (Exception e) {

      //
      version = new Version("1.0.0");
    }

    // get the BundleSymbolicName 'header'
    _newBundleManifest.setBundleVersion(version);
  }

  /**
   * <p>
   * </p>
   */
  protected void createImportPackageAndRequiredBundle() {

    //
    ImportResolver importResolver = new ImportResolver(_currentModule, _newBundleManifest.getImportPackage(),
        _newBundleManifest.getRequireBundle());

    //
    importResolver.setDependencyStyle(_dependencyStyle);

    //
    importResolver.addImportPackageAndRequiredBundle();
  }

  /**
   * <p>
   * </p>
   */
  protected void createExportPackage() {

    // get the export package 'header'
    ExportPackage exportPackage = _newBundleManifest.getExportPackage();

    // get the import package template
    String importPackageTemplateHeader = _currentModule.getManifestTemplate().getMainAttributes()
        .get(ManifestConstants.HEADER_IMPORT_TEMPLATE);

    List<HeaderDeclaration> exportPackageTemplates = ManifestUtils.parseManifestValue(importPackageTemplateHeader);

    // get all referenced package names
    Set<String> packageNames = _currentModule.getResourceModule().getContainedPackageNames();

    //
    for (String packageName : packageNames) {

      // create the 'ExportPackage' instance
      ExportedPackage exportedPackage = exportPackage.addExportedPackage(packageName);

      // get the template
      HeaderDeclaration exportPackageTemplate = ManifestUtils.findMostSpecificDeclaration(exportPackageTemplates,
          packageName);

      // assign the template values
      if (exportPackageTemplate != null) {

        // add the attributes
        exportedPackage.getAttributes().putAll(exportPackageTemplate.getAttributes());

        // add the directives
        exportedPackage.getDirectives().putAll(exportPackageTemplate.getDirectives());
      }
    }
  }
}
