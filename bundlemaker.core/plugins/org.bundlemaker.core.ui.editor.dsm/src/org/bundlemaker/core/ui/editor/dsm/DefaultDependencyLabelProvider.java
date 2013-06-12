package org.bundlemaker.core.ui.editor.dsm;

import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultDependencyLabelProvider extends LabelProvider {

  @Override
  public String getText(Object element) {

    if (element != null) {
      return Integer.toString(((IDependency) element).getWeight());
    } else {
      return "";
    }
  }
}
