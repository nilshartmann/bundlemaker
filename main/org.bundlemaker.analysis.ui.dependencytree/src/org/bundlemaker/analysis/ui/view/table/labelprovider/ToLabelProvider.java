package org.bundlemaker.analysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.ui.Analysis;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ToLabelProvider extends ColumnLabelProvider {

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    // Classification classification = DependencyRoot.getClassification(dependency.getTo());;
    // if( (classification != null) && (classification.getKind().equals("Type"))) {
    // return Activator.getDefault().getIcon("type");
    // } else {
    return Analysis.instance().getIconForArtifact(dependency.getTo());
    // }
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      return ((IDependency) element).getTo().getName();
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
