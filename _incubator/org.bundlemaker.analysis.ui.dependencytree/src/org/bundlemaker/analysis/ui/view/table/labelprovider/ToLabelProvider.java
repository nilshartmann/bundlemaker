package org.bundlemaker.analysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.DefaultArtifactLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ToLabelProvider extends ColumnLabelProvider {
  private final DefaultArtifactLabelProvider _artifactLabelProvider = new DefaultArtifactLabelProvider();

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    return _artifactLabelProvider.getImage(dependency.getTo());
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      IArtifact to = ((IDependency) element).getTo();
      return _artifactLabelProvider.getText(to);
    }
    return element == null ? "" : element.toString();//$NON-NLS-1$
  }

  @Override
  public String getToolTipText(Object element) {
    if (element instanceof IDependency) {
      return ((IDependency) element).getTo().getQualifiedName();
    }
    return element == null ? "" : element.toString();
  }

}
