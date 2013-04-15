package org.bundlemaker.core.ui.editor.dsm.widget.internal.util;

import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.jface.viewers.LabelProvider;

public class DependencyLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    if (element != null) {
      return ((IDependency) element).getWeight() + "";
    } else {
      return "";
    }
  }
}
