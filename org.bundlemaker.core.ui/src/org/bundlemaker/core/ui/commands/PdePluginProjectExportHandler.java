/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.commands;

import java.io.File;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.osgi.pde.exporter.PdePluginProjectModuleExporter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class PdePluginProjectExportHandler extends AbstractExportHandler {

  /**
   * @param iArtifact
   */
  protected void export(IAdvancedArtifact iArtifact) throws Exception {
    IModularizedSystem modularizedSystem = iArtifact.getModularizedSystem();

    File destination = getDestinationDirectory();
    if (destination == null) {
      return;
    }

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);

    System.out.println("exportAsProjects to " + destination);
    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter();
    pdeExporter.setUseClassifcationForExportDestination(false);

    // do the export
    doExport(pdeExporter, modularizedSystem, exporterContext);

    System.out.println("exportAsProjects done!");

  }

}
