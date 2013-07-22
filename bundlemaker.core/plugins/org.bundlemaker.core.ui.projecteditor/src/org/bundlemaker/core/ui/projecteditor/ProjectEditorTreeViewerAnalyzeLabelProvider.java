package org.bundlemaker.core.ui.projecteditor;

import org.bundlemaker.core.project.AnalyzeMode;
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

    AnalyzeMode analyzeMode = editor.getAnalyzeMode(contentElement);

    if (analyzeMode == null) {
      return null;
    }

    switch (_column) {
    case 0:
      return getCheckBox(analyzeMode.isAnalyze());
    case 1:
      return getCheckBox(analyzeMode.equals(AnalyzeMode.BINARIES_AND_SOURCES));
    }

    return null;
  }

  private Image getCheckBox(boolean state) {

    if (state) {
      return BundleMakerImages.CHECKED.getImage();
    }

    return BundleMakerImages.UNCHECKED.getImage();
  }

}
