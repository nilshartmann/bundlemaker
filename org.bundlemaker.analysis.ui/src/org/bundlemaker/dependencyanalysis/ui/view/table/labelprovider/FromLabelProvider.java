package org.bundlemaker.dependencyanalysis.ui.view.table.labelprovider;

import org.bundlemaker.analysis.ui.internal.Activator;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.eclipse.swt.graphics.Image;

public class FromLabelProvider extends ArtifactLabelProvider {

  @Override
  public Image getImage(Object element) {
    IDependency dependency = (IDependency) element;
    return Activator.getDefault().getIcon(dependency.getFrom().getType().getKuerzel());
  }

  @Override
  public String getText(Object element) {
    return getText(element, true);
  }

  @Override
  public String getToolTipText(Object element) {
    if (element instanceof IDependency) {
      return getTooltipText(((IDependency) element).getFrom());
    }
    return element == null ? "" : element.toString();
  }

}
