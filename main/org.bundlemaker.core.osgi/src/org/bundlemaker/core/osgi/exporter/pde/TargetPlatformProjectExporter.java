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
package org.bundlemaker.core.osgi.exporter.pde;

import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ITemplateProvider;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.util.Helper;
import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.osgi.internal.Activator;
import org.bundlemaker.core.osgi.utils.SystemBundleCopier;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.pde.core.target.ITargetDefinition;
import org.eclipse.pde.core.target.ITargetHandle;
import org.eclipse.pde.core.target.ITargetLocation;
import org.eclipse.pde.core.target.ITargetPlatformService;

@SuppressWarnings("restriction")
public class TargetPlatformProjectExporter extends ModularizedSystemExporterAdapter {

  /**
   * <p>
   * Creates a new instance of type {@link TargetPlatformProjectExporter}.
   * </p>
   */
  public TargetPlatformProjectExporter() {
    this(null);
  }

  /**
   * <p>
   * Creates a new instance of type {@link TargetPlatformProjectExporter}.
   * </p>
   * 
   */
  public TargetPlatformProjectExporter(ITemplateProvider templateProvider) {
    super(new JarFileBundleExporter(templateProvider, null, null));
  }

  @Override
  protected IModuleExporterContext preExportModules() throws Exception {

    // get a non-existing project name
    String projectName = Helper.getUniqueProjectName(String
        .format("%s.target", getCurrentModularizedSystem().getName()));

    // delete and create project
    IProject project = Helper.deleteAndCreateProject(projectName, null);

    //
    IFolder folder = project.getFolder("bundles");
    folder.create(true, true, null);

    IFile targetFile = project.getFile(projectName);

    ITargetPlatformService targetPlatformService = Activator.getTargetPlatformService();

    ITargetHandle targetHandle = targetPlatformService.getTarget(targetFile);

    ITargetDefinition targetDefinition = targetHandle.getTargetDefinition();
    targetDefinition.setName(projectName);

    ITargetLocation targetLocations = targetPlatformService.newDirectoryLocation("${project_loc:/" + projectName
        + "}/bundles");

    targetDefinition.setTargetLocations(new ITargetLocation[] { targetLocations });
    targetPlatformService.saveTargetDefinition(targetDefinition);

    SystemBundleCopier.copySystemBundle(folder.getRawLocation().toFile());

    return new DefaultModuleExporterContext(getCurrentContext().getBundleMakerProject(), folder.getRawLocation()
        .toFile(), getCurrentContext().getModularizedSystem());
  }
}
