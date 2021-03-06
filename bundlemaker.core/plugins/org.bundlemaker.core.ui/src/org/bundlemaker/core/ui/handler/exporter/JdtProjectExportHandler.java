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

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.jdt.exporter.JdtProjectExporter;
import org.bundlemaker.core.osgi.exporter.pde.PdePluginProjectModuleExporter;
import org.bundlemaker.core.osgi.manifest.CustomManifestCreator;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class JdtProjectExportHandler extends AbstractExportHandler {

  private CustomManifestCreator _customManifestCreator;

  @Override
  protected void exportAll(final Shell shell, final IModularizedSystem modularizedSystem,
      final List<IBundleMakerArtifact> selectedArtifacts)
      throws Exception {

    // create module exporter
    final JdtProjectExporter jdtProjectExporter = new JdtProjectExporter();

    // create the exporter context
    final DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
        modularizedSystem.getBundleMakerProject(), null, modularizedSystem);

    PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
      @Override
      public void run(final IProgressMonitor monitor) throws InvocationTargetException {
        try {

          final int modulesToExportCount = selectedArtifacts.size();

          SubMonitor subMonitor = SubMonitor.convert(monitor, "Exporting " + modularizedSystem.getName(),
              modulesToExportCount);

          for (IBundleMakerArtifact iBundleMakerArtifact : selectedArtifacts) {
            if (subMonitor.isCanceled()) {
              return;
            }

            IModuleArtifact module = (IModuleArtifact) iBundleMakerArtifact;
            final IModuleArtifact jdkModule = AnalysisModelQueries.getJreModuleArtifact(module);

            IModule associatedModule = module.getAssociatedModule();
            if (jdtProjectExporter.canExport(modularizedSystem, associatedModule, exporterContext)) {

              Collection<IDependency> dependenciesTo = module.getDependenciesTo();

              Set<IModuleArtifact> referencedModules = new HashSet<IModuleArtifact>();

              for (IDependency dependency : dependenciesTo) {
                IModuleArtifact refModule = dependency.getTo().getParent(IModuleArtifact.class);
                if (refModule != null && !module.equals(refModule) && !jdkModule.equals(refModule)) {
                  referencedModules.add(refModule);
                }
              }

              jdtProjectExporter.setReferencedModules(referencedModules);
              jdtProjectExporter.export(modularizedSystem, associatedModule, exporterContext, subMonitor.newChild(1));
            }
          }
        } catch (Exception ex) {
          throw new InvocationTargetException(ex);
        } finally {
          // close the ProgressMonitor
          if (monitor != null) {
            monitor.done();
          }
        }
      }
    });

    // PdePluginProjectModuleExporter pdeExporter = (PdePluginProjectModuleExporter) createExporter();
    // pdeExporter.setUseClassifcationForExportDestination(dialog.isUseClassificationInOutputPath());
    // _customManifestCreator.setUseOptionalOnMissingImports(dialog.isUseOptionalResolutionOnMissingImports());
    // // Manifest preferences
    // DefaultManifestPreferences manifestPreferences = new DefaultManifestPreferences(false);
    // manifestPreferences.setDependencyStyle(dialog.getDependencyStyle());
    //
    // pdeExporter.setManifestPreferences(manifestPreferences);

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
