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
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.osgi.exporter.bundle.JarFileBundleExporter;
import org.bundlemaker.core.osgi.manifest.CustomManifestCreator;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BinaryBundleExportHandler extends AbstractExportHandler {

  private static final String WORKSPACE_RESOURCE_KEY = BinaryBundleExportHandler.class.getName() + ".workspaceResource";

  @Override
  protected void exportAll(Shell shell, IModularizedSystem modularizedSystem,
      List<IBundleMakerArtifact> selectedArtifacts)
      throws Exception {

    BinaryBundleExporterConfigurationDialog dialog = new BinaryBundleExporterConfigurationDialog(shell);
    if (dialog.open() != Window.OK) {
      // cancel
      return;
    }

    File destination = dialog.getDestination();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);
    if (!dialog.isSaveToFileSystem()) {
      exporterContext.put(WORKSPACE_RESOURCE_KEY, dialog.getWorkspaceResource());
    }

    // create module exporter
    JarFileBundleExporter moduleExporter = (JarFileBundleExporter) createExporter();
    moduleExporter.setIncludeSources(dialog.isIncludeSources());
    moduleExporter.setCreateEclipseSourceBundle(dialog.isCreateEclipseSourceBundle());

    // create the adapter
    ModularizedSystemExporterAdapter adapter = createModularizedSystemExporterAdapter(moduleExporter, selectedArtifacts);

    // do the export
    doExport(adapter, modularizedSystem, exporterContext);

    System.out.println("export done to " + destination);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractExportHandler#doExport(org.eclipse.core.runtime.IProgressMonitor,
   * org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter, org.bundlemaker.core.modules.IModularizedSystem,
   * org.bundlemaker.core.exporter.IModuleExporterContext)
   */
  @Override
  protected void doExport(IProgressMonitor monitor, ModularizedSystemExporterAdapter adapter,
      IModularizedSystem modularizedSystem, IModuleExporterContext exporterContext) throws Exception {
    super.doExport(monitor, adapter, modularizedSystem, exporterContext);

    IResource workspaceResource = (IResource) exporterContext.getAttribute(WORKSPACE_RESOURCE_KEY);
    if (workspaceResource != null) {
      workspaceResource.refreshLocal(IResource.DEPTH_ONE, monitor);
    }
  }

  @Override
  protected IModuleExporter createExporter() throws Exception {

    JarFileBundleExporter jarFileBundleExporter = new JarFileBundleExporter(null, new CustomManifestCreator(), null);
    return jarFileBundleExporter;
  }
}
