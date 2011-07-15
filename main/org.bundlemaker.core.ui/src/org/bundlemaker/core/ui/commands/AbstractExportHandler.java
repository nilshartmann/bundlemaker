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
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractExportHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent
   * , java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

    IModularizedSystem modularizedSystem = null;

    try {
      for (IArtifact iArtifact : selectedArtifacts) {
        if (iArtifact instanceof IAdvancedArtifact) {
          IAdvancedArtifact advancedArtifact = (IAdvancedArtifact) iArtifact;
          if (modularizedSystem == null) {
            modularizedSystem = advancedArtifact.getModularizedSystem();
          } else if (!modularizedSystem.equals(advancedArtifact.getModularizedSystem())) {
            MessageDialog.openError(new Shell(), "Exporter", "Only one modularized system can be exported at a time");
            return;
          }
        }
      }

      if (modularizedSystem == null || selectedArtifacts.isEmpty()) {
        return;
      }

      exportAll(modularizedSystem, selectedArtifacts);

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
   *          The artifact that should be exported. Never null. All contained {@link IArtifact} objects must be long to
   *          the given modularizedSystem
   * @throws Exception
   */
  protected void exportAll(IModularizedSystem modularizedSystem, List<IArtifact> selectedArtifacts) throws Exception {

    File destination = getDestinationDirectory();
    if (destination == null) {
      return;
    }

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);

    // create the exporter
    System.out.println("export to " + destination);

    // create the adapter
    ModularizedSystemExporterAdapter adapter = createModularizedSystemExporterAdapter(selectedArtifacts);

    // do the export
    doExport(adapter, modularizedSystem, exporterContext);

    System.out.println("export done!");
  }

  protected ModularizedSystemExporterAdapter createModularizedSystemExporterAdapter(List<IArtifact> selectedArtifacts)
      throws Exception {
    // create module exporter
    IModuleExporter moduleExporter = createExporter();

    // create the adapter
    final ModularizedSystemExporterAdapter adapter = new ModularizedSystemExporterAdapter(moduleExporter);

    // set the module filter that filters selected artifacts
    adapter.setModuleFilter(createModuleFilter(selectedArtifacts));

    return adapter;
  }

  protected IQueryFilter<IModule> createModuleFilter(List<IArtifact> artifacts) {
    ListBasedModuleQueryFilter moduleFilter = new ListBasedModuleQueryFilter();

    for (IArtifact iArtifact : artifacts) {
      addModules(moduleFilter, iArtifact);
    }
    return moduleFilter;
  }

  private void addModules(ListBasedModuleQueryFilter moduleFilter, IArtifact artifact) {
    if (artifact.getType() == ArtifactType.Module) {
      moduleFilter.add(artifact);
      return;
    }

    Collection<IArtifact> children = artifact.getChildren();
    for (IArtifact iArtifact : children) {
      if (iArtifact instanceof IAdvancedArtifact) {
        addModules(moduleFilter, (IAdvancedArtifact) iArtifact);
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
      public void run(final IProgressMonitor monitor) throws InvocationTargetException {
        try {
          adapter.export(modularizedSystem, exporterContext, monitor);
        } catch (Exception ex) {
          throw new InvocationTargetException(ex);
        }
      }
    });
  }

  protected File getDestinationDirectory() {
    DirectoryDialog dialog = new DirectoryDialog(new Shell());
    dialog.setMessage("Select the export destination folder");
    dialog.setText("Export modules");
    String res = dialog.open();

    if (res != null) {
      File destination = Path.fromOSString(res).makeAbsolute().toFile();
      destination.mkdirs();

      return destination;
    }

    return null;

  }

  class ListBasedModuleQueryFilter implements IQueryFilter<IModule> {

    private final HashSet<String> _moduleNames = new HashSet<String>();

    @Override
    public boolean matches(IModule content) {
      String moduleName = content.getModuleIdentifier().getName() + "_" + content.getModuleIdentifier().getVersion();
      boolean contains = _moduleNames.contains(moduleName);
      return contains;
    }

    void add(IArtifact artifact) {
      _moduleNames.add(artifact.getName());
    }
  }

}
