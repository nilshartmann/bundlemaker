package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ProjectEditorTreeViewerResourceLabelProvider extends ColumnLabelProvider {

  public ProjectEditorTreeViewerResourceLabelProvider() {
  }

  @Override
  public Image getImage(Object element) {

    ProjectEditorTreeViewerElement treeViewerElement = (ProjectEditorTreeViewerElement) element;
    IProjectContentProviderEditor editor = treeViewerElement.getProvidingEditor();
    Object contentElement = treeViewerElement.getElement();

    return editor.getImage(contentElement);
  }

  @Override
  public String getText(Object element) {
    ProjectEditorTreeViewerElement treeViewerElement = (ProjectEditorTreeViewerElement) element;

    return treeViewerElement.getProvidingEditor().getLabel(treeViewerElement.getElement());
  }

}
