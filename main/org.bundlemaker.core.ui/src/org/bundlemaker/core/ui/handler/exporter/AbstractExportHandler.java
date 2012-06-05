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
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.ui.handler.AbstractArtifactBasedHandler;
import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractExportHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent ,
   * java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    IModularizedSystem modularizedSystem = null;

    try {
      for (IBundleMakerArtifact iArtifact : selectedArtifacts) {
        if (modularizedSystem == null) {
          modularizedSystem = iArtifact.getModularizedSystem();
        } else if (!modularizedSystem.equals(iArtifact.getModularizedSystem())) {
          MessageDialog.openError(new Shell(), "Exporter", "Only one modularized system can be exported at a time");
          return;
        }
      }

      if (modularizedSystem == null || selectedArtifacts.isEmpty()) {
        return;
      }

      exportAll(
          HandlerUtil.getActiveShell(event),
          modularizedSystem, selectedArtifacts);

    } catch (Exception ex) {
      reportError(Activator.PLUGIN_ID, "Error during export: " + ex, ex);
      MessageDialog.openError(new Shell(), "Export failed", "Error during export: " + ex);
    }

  }

  /**
   * Export the given artifact
   * 
   * @param modularizedSystem
   *          The Modularized system that is parent of the selectedArtifacts
   * @param selectedArtifacts
   *          The artifact that should be exported. Never null. All contained {@link IBundleMakerArtifact} objects must
   *          be long to the given modularizedSystem
   * @throws Exception
   */
  protected void exportAll(Shell shell, IModularizedSystem modularizedSystem,
      List<IBundleMakerArtifact> selectedArtifacts)
      throws Exception {

    File destination = getDestinationDirectory();
    if (destination == null) {
      return;
    }

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);

    // create module exporter
    IModuleExporter moduleExporter = createExporter();

    // create the adapter
    ModularizedSystemExporterAdapter adapter = createModularizedSystemExporterAdapter(moduleExporter, selectedArtifacts);

    // do the export
    doExport(adapter, modularizedSystem, exporterContext);

    System.out.println("export done!");
  }

  protected ModularizedSystemExporterAdapter createModularizedSystemExporterAdapter(
      IModuleExporter moduleExporter,
      List<IBundleMakerArtifact> selectedArtifacts) throws Exception {

    // create the adapter
    final ModularizedSystemExporterAdapter adapter = new ModularizedSystemExporterAdapter(moduleExporter);

    // set the module filter that filters selected artifacts
    adapter.setModuleFilter(createModuleFilter(selectedArtifacts));

    return adapter;
  }

  protected IQueryFilter<IModule> createModuleFilter(List<IBundleMakerArtifact> artifacts) {
    ListBasedModuleQueryFilter moduleFilter = new ListBasedModuleQueryFilter();

    for (IBundleMakerArtifact iArtifact : artifacts) {
      addModules(moduleFilter, iArtifact);
    }
    return moduleFilter;
  }

  private void addModules(ListBasedModuleQueryFilter moduleFilter, IBundleMakerArtifact artifact) {
    if (artifact instanceof IModuleArtifact) {
      moduleFilter.add(artifact);
      return;
    }

    for (IBundleMakerArtifact iArtifact : artifact.getChildren()) {
      if (iArtifact instanceof IBundleMakerArtifact) {
        addModules(moduleFilter, iArtifact);
      }
    }
  }

  /**
   * Returns the actual {@link IModuleExporter} that should be used to export the selected modules.
   * 
   * @return
   * @throws Exception
   */
  protected abstract IModuleExporter createExporter() throws Exception;

  /**
   * Executes the given exporter on a non-UI thread. The user gets feedback via ProgressMonitor
   * 
   * @param exporter
   * @param modularizedSystem
   * @param exporterContext
   * @throws Exception
   */
  protected void doExport(final ModularizedSystemExporterAdapter adapter, final IModularizedSystem modularizedSystem,
      final IModuleExporterContext exporterContext) throws Exception {

    PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
      @Override
      public void run(final IProgressMonitor monitor) throws InvocationTargetException {
        try {
          doExport(monitor, adapter, modularizedSystem, exporterContext);
        } catch (Exception ex) {
          throw new InvocationTargetException(ex);
        }
      }
    });
  }

  protected void doExport(IProgressMonitor monitor, final ModularizedSystemExporterAdapter adapter,
      final IModularizedSystem modularizedSystem,
      final IModuleExporterContext exporterContext) throws Exception {
    adapter.export(modularizedSystem, exporterContext, monitor);
  }

  protected File getDestinationDirectory() {
    // DirectoryDialog dialog = new DirectoryDialog(new Shell());
    // dialog.setMessage("Select the export destination folder");
    // dialog.setText("Export modules");
    // String res = dialog.open();
    //
    // if (res != null) {
    // File destination = Path.fromOSString(res).makeAbsolute().toFile();
    // destination.mkdirs();
    //
    // return destination;
    // }
    //
    // return null;

    return ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toFile();
  }

  class ListBasedModuleQueryFilter implements IQueryFilter<IModule> {

    private final HashSet<String> _moduleNames = new HashSet<String>();

    @Override
    public boolean matches(IModule content) {
      String moduleName = content.getModuleIdentifier().getName() + "_" + content.getModuleIdentifier().getVersion();
      boolean contains = _moduleNames.contains(moduleName);
      return contains;
    }

    void add(IBundleMakerArtifact artifact) {
      _moduleNames.add(artifact.getName());
    }
  }

}
