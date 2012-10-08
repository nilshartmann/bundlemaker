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

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.projecteditor.filebased.edit.EditFileBasedContentProviderDialog;
import org.bundlemaker.core.ui.projecteditor.filebased.edit.EditProjectPathDialog;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class FileBasedContentProviderEditor implements IProjectContentProviderEditor {

  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof FileBasedContentProvider);
  }

  @Override
  public Object getRootElement(IBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
    // FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;
    //
    // return fileBasedContentProvider.getFileBasedContent();
  }

  @Override
  public List<? extends Object> getChildren(IBundleMakerProject project, IProjectContentProvider provider,
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
    return (element instanceof FileBasedContentProvider || element instanceof FileBasedContent);
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
    FileBasedContent fileBasedContent = null;

    if (element instanceof FileBasedContent) {
      fileBasedContent = (FileBasedContent) element;
    } else if (element instanceof FileBasedContentProvider) {
      fileBasedContent = ((FileBasedContentProvider) element).getFileBasedContent();
    }

    if (fileBasedContent != null) {
      fileBasedContent.setAnalyzeMode(analyzeMode);
    }

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
    return selectedObject instanceof FileBasedContentProvider || selectedObject instanceof ProjectPath;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#edit(org.bundlemaker.core.
   * IBundleMakerProject, org.bundlemaker.core.projectdescription.IProjectContentProvider, java.lang.Object)
   */
  @Override
  public boolean edit(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {

    FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;

    if (selectedObject instanceof FileBasedContentProvider) {
      return editFileBasedContentProvider(shell, project, fileBasedContentProvider);
    }

    if (selectedObject instanceof ProjectPath) {
      ProjectPath projectPath = (ProjectPath) selectedObject;
      VariablePath path = projectPath.getPath();
      EditProjectPathDialog editDialog = new EditProjectPathDialog(shell, projectPath);
      if (editDialog.open() == Window.OK) {
        // remove old path
        fileBasedContentProvider.getFileBasedContent().removeRootPath(path, projectPath.getContentType());

        // add modified path
        ProjectPath modifiedPath = editDialog.getEntry();
        fileBasedContentProvider.getFileBasedContent().addRootPath(modifiedPath.getPath(),
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
  public void remove(Shell shell, IBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {
    FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;

    ProjectPath pathToRemove = (ProjectPath) selectedObject;

    fileBasedContentProvider.getFileBasedContent()
        .removeRootPath(pathToRemove.getPath(), pathToRemove.getContentType());

  }

  class HelloWorldAction extends Action {

    public HelloWorldAction() {
      super("Hello World");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
      Shell activeShell = Display.getCurrent().getActiveShell();
      MessageDialog.openInformation(activeShell, "Hello World", "Hello World.");

    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#getContextMenuActions()
   */
  @Override
  public List<IAction> getContextMenuActions(IBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {
    List<IAction> result = new LinkedList<IAction>();
    HelloWorldAction helloWorldAction = new HelloWorldAction();

    for (IProjectContentProviderEditorElement element : selectedElements) {
      if (element.getProvidingEditor() != this) {
        helloWorldAction.setEnabled(false);
        break;
      }
    }
    result.add(helloWorldAction);
    return result;
  }

  protected boolean editFileBasedContentProvider(Shell shell, IBundleMakerProject project,
      FileBasedContentProvider fileBasedContentProvider) {
    EditFileBasedContentProviderDialog page = new EditFileBasedContentProviderDialog(shell, fileBasedContentProvider);
    if (page.open() != Window.OK) {
      return false;
    }

    FileBasedContent content = fileBasedContentProvider.getFileBasedContent();
    content.setName(page.getName());
    content.setVersion(page.getVersion());
    content.setBinaryPaths(page.getBinaryPaths().toArray(new String[0]));
    content.setSourcePaths(page.getSourcePaths().toArray(new String[0]));

    content.setAnalyzeMode(getAnalyzeMode(page.isAnalyze(), page.isAnalyzeSources()));

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
