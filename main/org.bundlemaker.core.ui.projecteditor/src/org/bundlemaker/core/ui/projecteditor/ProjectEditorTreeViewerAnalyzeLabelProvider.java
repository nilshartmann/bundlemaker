package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.ui.BundleMakerImages;
import org.bundlemaker.core.ui.CenterImageLabelProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

public class ProjectEditorTreeViewerAnalyzeLabelProvider extends CenterImageLabelProvider implements ILabelProvider {

  private final int _column;

  public ProjectEditorTreeViewerAnalyzeLabelProvider(int column) {
    _column = column;
  }

  @Override
  public String getText(Object element) {
    return null;
  }

  @Override
  public Image getImage(Object element) {
    ProjectEditorTreeViewerElement treeViewerElement = (ProjectEditorTreeViewerElement) element;
    IProjectContentProviderEditor editor = treeViewerElement.getProvidingEditor();
    Object contentElement = treeViewerElement.getElement();

    switch (_column) {
    case 0:
      return getCheckBox(editor.isAnalyze(contentElement));
    case 1:
      return getCheckBox(editor.isAnalyzeSources(contentElement));
    }

    return null;
  }

  private Image getCheckBox(Boolean state) {
    if (state == null) {
      return null;
    }

    if (state) {
      return BundleMakerImages.CHECKED.getImage();
    }

    return BundleMakerImages.UNCHECKED.getImage();
  }

}
