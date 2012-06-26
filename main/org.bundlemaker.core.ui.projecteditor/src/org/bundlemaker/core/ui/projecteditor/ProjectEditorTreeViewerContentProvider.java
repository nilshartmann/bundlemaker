package org.bundlemaker.core.ui.projecteditor;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.internal.ProjectEditorContributionRegistry;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ProjectEditorTreeViewerContentProvider implements ITreeContentProvider {

  private final ProjectEditorContributionRegistry _projectEditorContributionRegistry;

  public ProjectEditorTreeViewerContentProvider(
      ProjectEditorContributionRegistry projectContentProviderEditorRegistry) {
    super();
    _projectEditorContributionRegistry = projectContentProviderEditorRegistry;
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub

  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

  }

  @Override
  public Object[] getElements(Object inputElement) {

    IBundleMakerProject bundleMakerProject = (IBundleMakerProject) inputElement;

    List<Object> result = new LinkedList<Object>();

    List<? extends IProjectContentProvider> contentProviders = bundleMakerProject.getModifiableProjectDescription()
        .getContentProviders();

    for (IProjectContentProvider iProjectContentProvider : contentProviders) {
      Set<IProjectContentProviderEditor> projectContentProviderEditors = _projectEditorContributionRegistry
          .getContentProviderEditors();
      for (IProjectContentProviderEditor iProjectContentProviderEditor : projectContentProviderEditors) {
        if (iProjectContentProviderEditor.canHandle(iProjectContentProvider)) {
          Object rootElement = iProjectContentProviderEditor
              .getRootElement(bundleMakerProject, iProjectContentProvider);
          if (rootElement != null) {

            ProjectEditorTreeViewerElement treeViewerElement = new ProjectEditorTreeViewerElement(bundleMakerProject,
                iProjectContentProvider, rootElement, iProjectContentProviderEditor);

            result.add(treeViewerElement);
            break;
          }
        }
      }
    }

    return result.toArray();
  }

  private final static Object[] EMPTY_RESULT = new Object[0];

  @Override
  public Object[] getChildren(Object parent) {

    if (!(parent instanceof ProjectEditorTreeViewerElement)) {
      // should not happen. in case it does, simply ignore the entry
      return EMPTY_RESULT;
    }

    ProjectEditorTreeViewerElement parentElement = (ProjectEditorTreeViewerElement) parent;

    List<? extends Object> children = null;

    // Ask provider for children
    try {
      children = parentElement.getProvidingEditor().getChildren(parentElement.getBundleMakerProject(),
          parentElement.getProjectContentProvider(), parentElement.getElement());
    } catch (Exception ex) {
      Activator.logError("Error while retrieving children for project editor: " + ex, ex);
    }

    if (children == null || children.isEmpty()) {
      return EMPTY_RESULT;
    }

    // Wrap returned elements in TreeViewerElement objects
    List<Object> result = new LinkedList<Object>();

    for (Object child : children) {
      result.add(parentElement.deriveChild(child));
    }

    return result.toArray();
  }

  @Override
  public Object getParent(Object element) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    return getChildren(element).length > 0;
  }

}
