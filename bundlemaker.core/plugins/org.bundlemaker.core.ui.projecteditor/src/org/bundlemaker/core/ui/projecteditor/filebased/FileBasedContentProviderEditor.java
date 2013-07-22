/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.filebased;

import java.util.List;

import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.project.filecontent.FileBasedProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.filebased.edit.EditFileBasedContentProviderDialog;
import org.bundlemaker.core.ui.projecteditor.filebased.edit.EditProjectPathDialog;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class FileBasedContentProviderEditor implements IProjectContentProviderEditor {

  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof FileBasedProjectContentProvider);
  }

  @Override
  public Object getRootElement(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
    // FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;
    //
    // return fileBasedContentProvider.getFileBasedContent();
  }

  @Override
  public List<? extends Object> getChildren(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) {

    return _fileBasedContentRenderer.getChildren(project, rootElement);

  }

  @Override
  public AnalyzeMode getAnalyzeMode(Object element) {
    return _fileBasedContentRenderer.getAnalyzeMode(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canChangeAnalyzeMode(org.bundlemaker
   * .core.projectdescription.IProjectContentProvider, java.lang.Object)
   */
  @Override
  public boolean canChangeAnalyzeMode(IProjectContentProvider projectContentProvider, Object element) {
    return (element instanceof FileBasedProjectContentProvider);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#setAnalyzeMode(org.bundlemaker.core
   * .projectdescription.IProjectContentProvider, java.lang.Object)
   */
  @Override
  public void setAnalyzeMode(IProjectContentProvider projectContentProvider, Object element, AnalyzeMode analyzeMode) {

    ((FileBasedProjectContentProvider) projectContentProvider).setAnalyzeMode(analyzeMode);
  }

  @Override
  public Image getImage(Object element) {
    return _fileBasedContentRenderer.getImage(element);

  }

  @Override
  public String getLabel(Object element) {
    return _fileBasedContentRenderer.getLabel(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canEdit(java.lang.Object)
   */
  @Override
  public boolean canEdit(Object selectedObject) {
    return selectedObject instanceof FileBasedProjectContentProvider || selectedObject instanceof ProjectPath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#edit(org.bundlemaker.core.
   * IBundleMakerProject, org.bundlemaker.core.projectdescription.IProjectContentProvider, java.lang.Object)
   */
  @Override
  public boolean edit(Shell shell, IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {

    FileBasedProjectContentProvider fileBasedContentProvider = (FileBasedProjectContentProvider) provider;

    if (selectedObject instanceof FileBasedProjectContentProvider) {
      return editFileBasedContentProvider(shell, project, fileBasedContentProvider);
    }

    if (selectedObject instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) selectedObject;
      VariablePath path = projectPath.getPath();
      EditProjectPathDialog editDialog = new EditProjectPathDialog(shell, projectPath);
      if (editDialog.open() == Window.OK) {
        // remove old path
        fileBasedContentProvider.removeRootPath(path, projectPath.getContentType());

        // add modified path
        ProjectPath modifiedPath = editDialog.getEntry();
        fileBasedContentProvider.addRootPath(modifiedPath.getPath(),
            modifiedPath.getContentType());

        return true;
      }
    }

    return false;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canRemove(java.lang.Object)
   */
  @Override
  public boolean canRemove(Object selectedObject) {
    return selectedObject instanceof ProjectPath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#remove(org.eclipse.swt.widgets.Shell,
   * org.bundlemaker.core.IBundleMakerProject, org.bundlemaker.core.projectdescription.IProjectContentProvider,
   * java.lang.Object)
   */
  @Override
  public void remove(Shell shell, IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {
    FileBasedProjectContentProvider fileBasedContentProvider = (FileBasedProjectContentProvider) provider;

    ProjectPath pathToRemove = (ProjectPath) selectedObject;

    fileBasedContentProvider.removeRootPath(pathToRemove.getPath(), pathToRemove.getContentType());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#getContextMenuActions()
   */
  @Override
  public List<IAction> getContextMenuActions(IProjectDescriptionAwareBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {

    // no entries for context menu
    return null;
  }

  protected boolean editFileBasedContentProvider(Shell shell, IProjectDescriptionAwareBundleMakerProject project,
      FileBasedProjectContentProvider fileBasedContentProvider) {
    EditFileBasedContentProviderDialog page = new EditFileBasedContentProviderDialog(shell, fileBasedContentProvider);
    if (page.open() != Window.OK) {
      return false;
    }

    fileBasedContentProvider.setName(page.getName());
    fileBasedContentProvider.setVersion(page.getVersion());
    fileBasedContentProvider.setBinaryPaths(page.getBinaryPaths().toArray(new String[0]));
    fileBasedContentProvider.setSourcePaths(page.getSourcePaths().toArray(new String[0]));
    fileBasedContentProvider.setAnalyzeMode(getAnalyzeMode(page.isAnalyze(), page.isAnalyzeSources()));

    return true;

  }

  public static AnalyzeMode getAnalyzeMode(boolean analyze, boolean analyzeSources) {
    if (analyzeSources) {
      return AnalyzeMode.BINARIES_AND_SOURCES;
    }

    if (analyze) {
      return AnalyzeMode.BINARIES_ONLY;
    }

    return AnalyzeMode.DO_NOT_ANALYZE;
  }

}
