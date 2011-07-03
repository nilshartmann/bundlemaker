package org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.swt.graphics.Image;

public class ToLabelProvider extends ArtifactLabelProvider {

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    return Activator.getDefault().getIcon(dependency.getTo().getType().getKuerzel());
  }

  @Override
  public String getText(Object element) {
    return getText(element, false);
  }

  @Override
  public String getToolTipText(Object element) {
    if (element instanceof IDependency) {
      return getTooltipText(((IDependency) element).getTo());
    }
    return element == null ? "" : element.toString();
  }

}
