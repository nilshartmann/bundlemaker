package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;

public class ProjectEditorTreeViewerElement {

  private final IBundleMakerProject           _bundleMakerProject;

  private final Object                        _element;

  private final IProjectContentProvider       _projectContentProvider;

  private final IProjectContentProviderEditor _providingEditor;

  private final boolean                       _root;

  ProjectEditorTreeViewerElement(boolean root, IBundleMakerProject bundleMakerProject,
      IProjectContentProvider projectContentProvider, Object rootElement, IProjectContentProviderEditor providingEditor) {
    super();
    _root = root;
    _bundleMakerProject = bundleMakerProject;
    _projectContentProvider = projectContentProvider;
    _element = rootElement;
    _providingEditor = providingEditor;
  }

  public boolean isChild() {
    return !_root;
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
    ProjectEditorTreeViewerElement child = new ProjectEditorTreeViewerElement(false, getBundleMakerProject(),
        getProjectContentProvider(), childElement, getProvidingEditor());

    return child;

  }

}
