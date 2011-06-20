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

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ui.commands.AbstractArtifactBasedHandler;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.ui.internal.Activator;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
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

  private static final AllModuleQueryFilter theAllModuleQueryFilter = new AllModuleQueryFilter();

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent
   * , java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {

    IArtifact currentArtifact = null;

    try {
      for (IArtifact iArtifact : selectedArtifacts) {
        currentArtifact = iArtifact;
        if (iArtifact instanceof IAdvancedArtifact) {
          export((IAdvancedArtifact) iArtifact);
        }
      }
    } catch (Exception ex) {
      reportError(Activator.PLUGIN_ID, "Could not export " + currentArtifact.getName() + ": " + ex, ex);
      MessageDialog
          .openError(new Shell(), "Export failed", "Could not export " + currentArtifact.getName() + ": " + ex);
    }

  }

  /**
   * Export the given artifact
   * 
   * @param advancedArtifact
   *          The artifact that should be exported. Never null
   * @throws Exception
   */
  protected void export(IAdvancedArtifact advancedArtifact) throws Exception {
    IModularizedSystem modularizedSystem = advancedArtifact.getModularizedSystem();

    File destination = getDestinationDirectory();
    if (destination == null) {
      return;
    }

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), destination, modularizedSystem);

    // create the exporter
    System.out.println("export to " + destination);
    IModuleExporter moduleExporter = createExporter();

    // create the adapter
    final ModularizedSystemExporterAdapter adapter = new ModularizedSystemExporterAdapter(moduleExporter);
    adapter.setModuleFilter(createModuleFilter(advancedArtifact));

    // do the export
    doExport(adapter, modularizedSystem, exporterContext);

    System.out.println("export done!");
  }

  protected IQueryFilter<IModule> createModuleFilter(IAdvancedArtifact advancedArtifact) {
    if (advancedArtifact.getType() == ArtifactType.Root) {
      // export whole ModularizedSystem
      return theAllModuleQueryFilter;
    }

    ListBasedModuleQueryFilter moduleFilter = new ListBasedModuleQueryFilter();
    addModules(moduleFilter, advancedArtifact);

    return moduleFilter;
  }

  private void addModules(ListBasedModuleQueryFilter moduleFilter, IAdvancedArtifact advancedArtifact) {
    if (advancedArtifact.getType() == ArtifactType.Module) {
      moduleFilter.add(advancedArtifact);
      return;
    }

    Collection<IArtifact> children = advancedArtifact.getChildren();
    for (IArtifact iArtifact : children) {
      if (iArtifact instanceof IAdvancedArtifact) {
        addModules(moduleFilter, (IAdvancedArtifact) iArtifact);
      }
    }
  }

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
      // TODO versions ?
      return _moduleNames.contains(content.getModuleIdentifier().getName());
    }

    void add(IAdvancedArtifact artifact) {
      _moduleNames.add(artifact.getName());
    }
  }

  static class AllModuleQueryFilter implements IQueryFilter<IModule> {

    @Override
    public boolean matches(IModule content) {
      return true;
    }

  }

}
