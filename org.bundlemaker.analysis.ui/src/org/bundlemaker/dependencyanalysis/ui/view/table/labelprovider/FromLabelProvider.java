package org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.Analysis;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class FromLabelProvider extends ColumnLabelProvider {

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    return Analysis.instance().getIconForArtifact(dependency.getFrom());
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      return ((IDependency) element).getFrom().getName();
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
