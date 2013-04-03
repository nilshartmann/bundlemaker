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
package org.bundlemaker.core.ui.handler.exporter;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.osgi.exporter.pde.PdePluginProjectModuleExporter;
import org.bundlemaker.core.osgi.manifest.CustomManifestCreator;
import org.bundlemaker.core.osgi.manifest.DefaultManifestPreferences;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class PdePluginProjectExportHandler extends AbstractExportHandler {

  private CustomManifestCreator _customManifestCreator;

  @Override
  protected void exportAll(Shell shell, IModularizedSystem modularizedSystem,
      List<IBundleMakerArtifact> selectedArtifacts)
      throws Exception {

    PdePluginProjectExporterConfigurationDialog dialog = new PdePluginProjectExporterConfigurationDialog(shell);
    if (dialog.open() != Window.OK) {
      // cancel
      return;
    }

    File destination = dialog.getDestination();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);

    // create module exporter
    PdePluginProjectModuleExporter pdeExporter = (PdePluginProjectModuleExporter) createExporter();
    pdeExporter.setUseClassifcationForExportDestination(dialog.isUseClassificationInOutputPath());
    _customManifestCreator.setUseOptionalOnMissingImports(dialog.isUseOptionalResolutionOnMissingImports());
    // Manifest preferences
    DefaultManifestPreferences manifestPreferences = new DefaultManifestPreferences(false);
    manifestPreferences.setDependencyStyle(dialog.getDependencyStyle());

    pdeExporter.setManifestPreferences(manifestPreferences);

    // create the adapter
    ModularizedSystemExporterAdapter adapter = createModularizedSystemExporterAdapter(pdeExporter, selectedArtifacts);

    // do the export
    doExport(adapter, modularizedSystem, exporterContext);
  }

  @Override
  protected IModuleExporter createExporter() throws Exception {

    _customManifestCreator = new CustomManifestCreator();
    // Create the exporter instance
    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter(null, _customManifestCreator, null);

    return pdeExporter;

  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see org.bundlemaker.core.ui.handler.exporter.AbstractExportHandler#getDestinationDirectory()
  // */
  // @Override
  // protected File getDestinationDirectory() {
  // // TODO Auto-generated method stub
  // return new File("/Users/nils/develop/bundlemaker/export-target");
  // }

}
