package org.bundlemaker.core.ui.projecteditor.jdt;

import java.util.List;

import org.bundlemaker.core.jdt.content.JdtProjectContentProvider;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.projecteditor.filebased.FileBasedContentRenderer;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;
import org.bundlemaker.core.ui.projecteditor.provider.impl.AbstractProjectContentProviderEditor;
import org.bundlemaker.core.ui.validators.NonEmptyStringValidator;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

public class JdtProjectContentProviderEditor extends AbstractProjectContentProviderEditor {

  private final FileBasedContentRenderer _fileBasedContentRenderer = FileBasedContentRenderer.getInstance();

  @Override
  public boolean canHandle(IProjectContentProvider provider) {
    return (provider instanceof JdtProjectContentProvider);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#getContextMenuActions()
   */
  @Override
  public List<IAction> getContextMenuActions(IProjectDescriptionAwareBundleMakerProject project,
      List<IProjectContentProviderEditorElement> selectedElements) {
    return null;
  }

  @Override
  public Object getRootElement(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider) {
    return provider;
  }

  @Override
  public List<? extends Object> getChildren(IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider,
      Object rootElement) throws Exception {
    if (!(rootElement instanceof JdtProjectContentProvider)) {
      return _fileBasedContentRenderer.getChildren(project, rootElement);
    }

    JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) rootElement;

    return getContentFromProvider(project, projectContentProvider);
  }

  @Override
  public Image getImage(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      return BundleMakerImages.JDT_PROJECT_CONTENT_PROVIDER.getImage();
    }

    return _fileBasedContentRenderer.getImage(element);
  }

  @Override
  public String getLabel(Object element) {
    if (element instanceof JdtProjectContentProvider) {
      JdtProjectContentProvider projectContentProvider = (JdtProjectContentProvider) element;
      return projectContentProvider.getName();
    }

    return _fileBasedContentRenderer.getLabel(element);
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
    return false;
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
    // not supported
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canEdit(java.lang.Object)
   */
  @Override
  public boolean canEdit(Object selectedObject) {
    return (selectedObject instanceof JdtProjectContentProvider);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#edit(org.eclipse.swt.widgets.Shell,
   * org.bundlemaker.core.IBundleMakerProject, org.bundlemaker.core.projectdescription.IProjectContentProvider,
   * java.lang.Object)
   */
  @Override
  public boolean edit(Shell shell, IProjectDescriptionAwareBundleMakerProject project, IProjectContentProvider provider, Object selectedObject) {

    JdtProjectContentProvider jdtProjectContentProvider = (JdtProjectContentProvider) selectedObject;
    
    InputDialog inputDialog = new InputDialog(shell, "Name", "Enter name of this project content",
        jdtProjectContentProvider.getName(), NonEmptyStringValidator.instance());

    // Open
    if (inputDialog.open() != Window.OK) {
      // canceled
      return false;
    }

    final String newName = inputDialog.getValue();
    jdtProjectContentProvider.setName(newName);
    
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#canRemove(java.lang.Object)
   */
  @Override
  public boolean canRemove(Object selectedObject) {

    // can't remove childs of JdtProjectContentProvider
    return false;
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
    // can't remove childs of JdtProjectContentProvider
  }

}
