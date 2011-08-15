package org.bundlemaker.analysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.DefaultArtifactLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class FromLabelProvider extends ColumnLabelProvider {
  
  final  DefaultArtifactLabelProvider _artifactLabelProvider = new DefaultArtifactLabelProvider();

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    return _artifactLabelProvider.getImage(dependency.getFrom());
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      return _artifactLabelProvider.getText(((IDependency)element).getFrom());

    }
    return element == null ? "" : element.toString();
  }

  @Override
  public String getToolTipText(Object element) {
    if (element instanceof IDependency) {
      return ((IDependency) element).getFrom().getQualifiedName();
    }
    return element == null ? "" : element.toString();
  }

}
