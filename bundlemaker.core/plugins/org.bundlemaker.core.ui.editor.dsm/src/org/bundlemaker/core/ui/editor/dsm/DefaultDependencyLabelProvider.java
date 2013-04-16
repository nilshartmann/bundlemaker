package org.bundlemaker.core.ui.editor.dsm;

import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.jface.viewers.LabelProvider;

public class DefaultDependencyLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    if (element != null) {
      return ((IDependency) element).getWeight() + "";
    } else {
      return "";
    }
  }
}
