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
import java.util.List;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ui.commands.AbstractBundleMakerHandler;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.internal.Activator;
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
public abstract class AbstractExportHandler extends AbstractBundleMakerHandler {

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
  protected abstract void export(IAdvancedArtifact advancedArtifact) throws Exception;

  /**
   * Executes the given exporter on a non-UI thread. The user gets feedback via ProgressMonitor
   * 
   * @param exporter
   * @param modularizedSystem
   * @param exporterContext
   * @throws Exception
   */
  protected void doExport(final IModuleExporter exporter, final IModularizedSystem modularizedSystem,
      final IModuleExporterContext exporterContext) throws Exception {

    final ModularizedSystemExporterAdapter adapter = new ModularizedSystemExporterAdapter(exporter);

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
    dialog.setMessage("Select an external folder");
    dialog.setText("External folder");
    // Path currPath = new Path(_entryText.getText());
    // dialog.setFilterPath(currPath.toOSString());
    String res = dialog.open();

    if (res != null) {
      File destination = Path.fromOSString(res).makeAbsolute().toFile();
      destination.mkdirs();

      return destination;
    }

    return null;

  }

}
