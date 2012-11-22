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

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceStatus;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.ui.PreferenceConstants;
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
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

/**
 * <p>
 * A project wizard that creates a new Bundlemaker project
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class NewBundleMakerProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

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
    setDefaultPageImageDescriptor(BundleMakerImages.BUNDLEMAKER_ICON.getImageDescriptor());
  }

  NewBundleMakerProjectWizardCreationPage mainPage;

  private IConfigurationElement           _configurationElement;

  @Override
  public void addPages() {
    super.addPages();

    // add bundlemaker page
    mainPage = new NewBundleMakerProjectWizardCreationPage();
    addPage(mainPage);
  }

  /*
   * Stores the configuration element for the wizard. The config element will be used in <code>performFinish</code> to
   * set the result perspective.
   */
  @Override
  public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
    _configurationElement = cfig;
  }

  @Override
  public boolean performFinish() {
    createNewProject();

    if (_newProject == null) {
      return false;
    }

    // open associated BundleMaker perspective
    BasicNewProjectResourceWizard.updatePerspective(_configurationElement);

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
      @Override
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
      if (mainPage.isTransformationScriptSupportSelected()) {
        // hier: org.eclipse.jdt.ui.wizards.JavaCapabilityConfigurationPage.init(IJavaProject, IPath, IClasspathEntry[],
        // boolean)
        BundleMakerCore.addJavaNature(newProjectHandle);
        IJavaProject javaProject = JavaCore.create(newProjectHandle);
        IPath projectPath = new Path(newProjectHandle.getName()).makeAbsolute();
        IWorkspaceRoot root = newProjectHandle.getWorkspace().getRoot();

        IPath sourceFolderPath = projectPath.append("src");
        IPath binFolderPath = projectPath.append("bin");

        createFolder(root.getFolder(sourceFolderPath), true, true, null);
        createDerivedFolder(root.getFolder(binFolderPath), true, true, null);

        List<IClasspathEntry> cpEntries = new ArrayList<IClasspathEntry>();
        cpEntries.add(JavaCore.newSourceEntry(sourceFolderPath));
        cpEntries.add(JavaCore.newContainerEntry(BundleMakerCore.BUNDLEMAKER_CONTAINER_PATH));
        cpEntries.addAll(Arrays.asList(PreferenceConstants.getDefaultJRELibrary()));

        IClasspathEntry[] classpathEntries = cpEntries.toArray(new IClasspathEntry[0]);
        javaProject.setRawClasspath(classpathEntries, binFolderPath, null);
        javaProject.save(null, true);
        IClasspathEntry[] rawClasspath = javaProject.getRawClasspath();

        InputStream is = getClass().getResourceAsStream("TransformationScriptTemplate.txt");

        IFolder examplePackageFolder = createFolder(
            root.getFolder(sourceFolderPath.append("org/bundlemaker/example/transformation")), true, true, null);
        IFile sampleScript = examplePackageFolder.getFile("SampleTransformationScript.java");
        sampleScript.create(is, true, null);

      }
      IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(newProjectHandle,
          new NullProgressMonitor());
      IModifiableProjectDescription modifiableProjectDescription = bundleMakerProject.getModifiableProjectDescription();
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

  public static void createDerivedFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor)
      throws CoreException {
    if (!folder.exists()) {
      IContainer parent = folder.getParent();
      if (parent instanceof IFolder) {
        createDerivedFolder((IFolder) parent, force, local, null);
      }
      folder.create(force ? (IResource.FORCE | IResource.DERIVED) : IResource.DERIVED, local, monitor);
    }
  }

  /**
   * Creates a folder and all parent folders if not existing. Project must exist.
   * <code> org.eclipse.ui.dialogs.ContainerGenerator</code> is too heavy (creates a runnable)
   * 
   * @param folder
   *          the folder to create
   * @param force
   *          a flag controlling how to deal with resources that are not in sync with the local file system
   * @param local
   *          a flag controlling whether or not the folder will be local after the creation
   * @param monitor
   *          the progress monitor
   * @throws CoreException
   *           thrown if the creation failed
   */
  public static IFolder createFolder(IFolder folder, boolean force, boolean local, IProgressMonitor monitor)
      throws CoreException {
    if (!folder.exists()) {
      IContainer parent = folder.getParent();
      if (parent instanceof IFolder) {
        createFolder((IFolder) parent, force, local, null);
      }
      folder.create(force, local, monitor);
    }

    return folder;
  }

}
