package org.bundlemaker.analysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.model.IDependency;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class WeightLabelProvider extends ColumnLabelProvider {

  @Override
  public Image getImage(Object element) {
    return null;
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IDependency) {
      return ((IDependency) element).getWeight() + "";
    }
    return element == null ? "" : element.toString();//$NON-NLS-1$
  }

}
