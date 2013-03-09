package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditorElement;

public class ProjectEditorTreeViewerElement implements IProjectContentProviderEditorElement {

  private final IBundleMakerProject           _bundleMakerProject;

  private final Object                        _element;

  private final IProjectContentProvider       _projectContentProvider;

  private final IProjectContentProviderEditor _providingEditor;

  ProjectEditorTreeViewerElement(IBundleMakerProject bundleMakerProject,
      IProjectContentProvider projectContentProvider, Object rootElement, IProjectContentProviderEditor providingEditor) {
    super();
    _bundleMakerProject = bundleMakerProject;
    _projectContentProvider = projectContentProvider;
    _element = rootElement;
    _providingEditor = providingEditor;
  }

  public IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  public Object getElement() {
    return _element;
  }

  public IProjectContentProvider getProjectContentProvider() {
    return _projectContentProvider;
  }

  public IProjectContentProviderEditor getProvidingEditor() {
    return _providingEditor;
  }

  public ProjectEditorTreeViewerElement deriveChild(Object childElement) {
    ProjectEditorTreeViewerElement child = new ProjectEditorTreeViewerElement(getBundleMakerProject(),
        getProjectContentProvider(), childElement, getProvidingEditor());

    return child;

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_element == null) ? 0 : _element.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ProjectEditorTreeViewerElement other = (ProjectEditorTreeViewerElement) obj;
    if (_element == null) {
      if (other._element != null)
        return false;
    } else if (!_element.equals(other._element))
      return false;
    return true;
  }

}
