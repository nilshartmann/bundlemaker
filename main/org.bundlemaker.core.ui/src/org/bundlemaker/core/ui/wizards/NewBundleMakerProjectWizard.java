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
package org.bundlemaker.core.ui.wizards;

import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.statushandlers.IStatusAdapterConstants;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * <p>
 * A project wizard that creates a new Bundlemaker project
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewBundleMakerProjectWizard extends Wizard implements INewWizard {

  /**
   * The project that has been created after the wizard has been successfully completed
   */
  private IProject   _newProject;

  private IWorkbench _workbench;

  public NewBundleMakerProjectWizard() {

  }

  @Override
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    _workbench = workbench;
    setNeedsProgressMonitor(true);
    setWindowTitle("New Bundlemaker Project");
    setDefaultPageImageDescriptor(UIImages.BUNDLEMAKER_ICON.getImageDescriptor());
  }

  NewBundleMakerProjectWizardCreationPage mainPage;

  @Override
  public void addPages() {
    super.addPages();

    // add bundlemaker page
    mainPage = new NewBundleMakerProjectWizardCreationPage();
    addPage(mainPage);
  }

  @Override
  public boolean performFinish() {
    createNewProject();

    if (_newProject == null) {
      return false;
    }

    // open the bundlemaker project description editor
    openProjectDescriptionEditor(_newProject);
    return true;

  }

  private IProject createNewProject() {
    if (_newProject != null) {
      return _newProject;
    }

    // get a project handle
    final IProject newProjectHandle = mainPage.getProjectHandle();

    // get a project descriptor
    URI location = null;
    if (!mainPage.useDefaults()) {
      location = mainPage.getLocationURI();
    }

    IWorkspace workspace = ResourcesPlugin.getWorkspace();
    final IProjectDescription description = workspace.newProjectDescription(newProjectHandle.getName());
    description.setLocationURI(location);

    // create the new project operation
    IRunnableWithProgress op = new IRunnableWithProgress() {
      public void run(IProgressMonitor monitor) throws InvocationTargetException {
        CreateProjectOperation op = new CreateProjectOperation(description, "Create new Bundlemaker project");
        try {
          // see bug
          // https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
          // directly execute the operation so that the undo state is
          // not preserved. Making this undoable resulted in too many
          // accidental file deletions.
          op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
        } catch (ExecutionException e) {
          throw new InvocationTargetException(e);
        }
      }
    };

    // run the new project creation operation
    try {
      getContainer().run(true, true, op);
    } catch (InterruptedException e) {
      return null;
    } catch (InvocationTargetException e) {
      Throwable t = e.getTargetException();
      if (t instanceof ExecutionException && t.getCause() instanceof CoreException) {
        CoreException cause = (CoreException) t.getCause();
        IStatus status;
        if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS) {
          status = BundleMakerUiUtils
              .newWarning(
                  format(
                      "The underlying file system is case insensitive. There is an existing project or directory that conflicts with '%s'",
                      newProjectHandle.getName()), cause);
        } else {
          status = BundleMakerUiUtils.newStatus(cause, "Problems while creating the project");
        }
        StatusAdapter statusAdapter = new StatusAdapter(status);
        statusAdapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, "Project creation problems");
        StatusManager.getManager().handle(status, StatusManager.BLOCK);
      } else {
        StatusAdapter statusAdapter = new StatusAdapter(BundleMakerUiUtils.newWarning(
            format("Internal error: %s", t.getMessage()), t));
        statusAdapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, "Project creation problems");
        StatusManager.getManager().handle(statusAdapter, StatusManager.LOG | StatusManager.BLOCK);
      }
      return null;
    }

    try {
      BundleMakerCore.addBundleMakerNature(newProjectHandle);
      IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(newProjectHandle,
          new NullProgressMonitor());
      IModifiableProjectDescription modifiableProjectDescription = bundleMakerProject
          .getModifiableProjectDescription();
      modifiableProjectDescription.setJre(mainPage.getSelectedJreId());
      modifiableProjectDescription.save();

    } catch (CoreException ex) {
      IStatus status = BundleMakerUiUtils.newStatus(ex, "Could not add Bundlemaker nature");
      StatusAdapter statusAdapter = new StatusAdapter(status);
      statusAdapter.setProperty(IStatusAdapterConstants.TITLE_PROPERTY, "Project creation problems");
      StatusManager.getManager().handle(status, StatusManager.BLOCK);
      return null;

    }

    _newProject = newProjectHandle;

    return _newProject;
  }

  /**
   * Opens the BundleMaker project description editor for the specified project
   * 
   * @param project
   */
  private void openProjectDescriptionEditor(IProject project) {
    IFile iFile = _newProject.getProject().getFile(BundleMakerCore.PROJECT_DESCRIPTION_PATH);

    IWorkbenchPage activePage = _workbench.getActiveWorkbenchWindow().getActivePage();

    IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(iFile.getName());
    try {
      activePage.openEditor(new FileEditorInput(iFile), desc.getId());
    } catch (Exception ex) {
      BundleMakerUiUtils.logError("Could not open editor for " + iFile.getProjectRelativePath(), ex);
    }

  }
}
