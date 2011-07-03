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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bundlemaker.analysis.ui.handlers.AbstractArtifactBasedHandler;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.transformations.dsl.transformationDsl.TransformationModel;
import org.bundlemaker.core.transformations.dsl.ui.utils.TransformationDslUtils;
import org.bundlemaker.core.ui.editor.transformation.TransformationExecutor;
import org.bundlemaker.core.ui.internal.Activator;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

/**
 * This handler lets the user select a transformation script that is applied on a new IModularizedSystem
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformHandler extends AbstractArtifactBasedHandler {

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.commands.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent
   * , java.util.List)
   */
  @Override
  protected void execute(ExecutionEvent event, List<IArtifact> selectedArtifacts) throws Exception {
    for (IArtifact iArtifact : selectedArtifacts) {
      IAdvancedArtifact advancedArtifact = (IAdvancedArtifact) iArtifact;
      IModularizedSystem modularizedSystem = advancedArtifact.getModularizedSystem();

      // Ask the user for the script that should be run
      IFile selectedScript = selectTransformationScript(modularizedSystem.getBundleMakerProject());

      if (selectedScript != null) {
        String moduleName = getModuleName(selectedScript);

        // Apply the transformation script
        transform(modularizedSystem.getBundleMakerProject(), moduleName, selectedScript);
      }
    }
  }

  static String getModuleName(IFile transformationScript) {
    // TODO ask user for name of module
    String moduleName = transformationScript.getLocation().removeFileExtension().lastSegment();
    return moduleName;
  }

  /**
   * Applies the transformation script that is identified by the given uri
   * 
   * @param bundleMakerProject
   * @param moduleName
   *          the name of the new {@link IModularizedSystem}
   * @param uri
   * @throws Exception
   */
  static void transform(final IBundleMakerProject bundleMakerProject, final String moduleName, final IFile scriptFile)
      throws Exception {

    PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {
      public void run(final IProgressMonitor monitor) throws InvocationTargetException {
        try {
          // monitor.beginTask("Running " + scriptFile.getName(), 100);

          // Parse the transformation script
          TransformationModel model = TransformationDslUtils.parse(scriptFile.getLocationURI().toString());

          // Execute the script and apply the contained transformation
          final TransformationExecutor executor = new TransformationExecutor(createModularizedSystem(
              bundleMakerProject, moduleName), model);

          executor.apply(monitor);
        } catch (Exception ex) {
          throw new InvocationTargetException(ex);
        } finally {
          monitor.done();
        }
      }
    });

    // Refresh the project explorer to make sure new project is visible
    BundleMakerUiUtils.refreshProjectExplorer(bundleMakerProject);
  }

  /**
   * Asks the user to select a transformation script from the workspace.
   * 
   * <p>
   * The dialog that will be opened only displays <tt>.bmt</tt>-transformation scripts
   * 
   * @param project
   * @return the selected BMT-file or null if no file has been selected
   */
  private IFile selectTransformationScript(IBundleMakerProject project) {
    ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(new Shell(), new WorkbenchLabelProvider(),
        new WorkbenchContentProvider());
    dialog.setHelpAvailable(false);
    dialog.setTitle("Choose transformation script");
    dialog.setMessage("Choose a transformation script that will be applied on " + project.getProject().getName());

    // set the filter that filters bmt-files and their containing directories
    dialog.addFilter(new DslFileFilter());

    // set the validator that checks that only IFile instances can be selected
    dialog.setValidator(new ISelectionStatusValidator() {

      private IStatus fgErrorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "");

      private IStatus fgOKStatus    = new Status(IStatus.OK, Activator.PLUGIN_ID, "");

      @Override
      public IStatus validate(Object[] selection) {

        for (Object object : selection) {
          if (!(object instanceof IFile)) {
            return fgErrorStatus;
          }
        }
        return fgOKStatus;
      }
    });
    dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
    dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
    dialog.setAllowMultiple(false);

    // open the dialog and return the selected IFile
    if (dialog.open() == Window.OK) {
      Object firstResult = dialog.getFirstResult();
      if (firstResult == null) {
        return null;
      }
      IFile selectedScript = (IFile) firstResult;
      return selectedScript;
    }
    return null;
  }

  /**
   * (Re-)creates a {@link IModularizedSystem} with the specified name
   * 
   * @param bundleMakerProject
   * @param name
   * @return
   * @throws Exception
   */
  private static IModularizedSystem createModularizedSystem(IBundleMakerProject bundleMakerProject, String name)
      throws Exception {

    if (bundleMakerProject.hasModularizedSystemWorkingCopy(name)) {
      // Remote exisiting system
      bundleMakerProject.deleteModularizedSystemWorkingCopy(name);
    }
    return bundleMakerProject.createModularizedSystemWorkingCopy(name);
  }

  class DslFileFilter extends ViewerFilter {

    public boolean select(Viewer viewer, Object parent, Object element) {
      if (element instanceof IFile) {
        IPath fullPath = ((IFile) element).getFullPath();
        String ext = fullPath.getFileExtension();
        return "bmt".equals(ext);
      } else if (element instanceof IContainer) { // IProject, IFolder
        // ignore closed projects
        if (element instanceof IProject && !((IProject) element).isOpen())
          return false;
        try {
          IResource[] resources = ((IContainer) element).members();
          for (int i = 0; i < resources.length; i++) {
            // recursive! Only show containers that contain an archive
            if (select(viewer, parent, resources[i])) {
              return true;
            }
          }
        } catch (CoreException e) {
          reportError(Activator.PLUGIN_ID, e.toString(), e);
        }
      }
      return false;
    }

  }

}
