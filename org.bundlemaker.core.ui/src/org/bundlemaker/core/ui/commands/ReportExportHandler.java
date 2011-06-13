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
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ReportExportHandler extends AbstractExportHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.commands.AbstractExportHandler#export(org.bundlemaker.core.analysis.IAdvancedArtifact)
   */
  @Override
  protected void export(IAdvancedArtifact advancedArtifact) throws Exception {

    IModularizedSystem modularizedSystem = advancedArtifact.getModularizedSystem();

    File destination = getDestinationDirectory();
    if (destination == null) {
      return;
    }

    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(advancedArtifact
        .getModularizedSystem().getBundleMakerProject(), destination, modularizedSystem);
    System.out.println("exportToSimpleReport...");
    SimpleReportExporter exporter = new SimpleReportExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    System.out.println("exportToSimpleReport done!");

  }

}
