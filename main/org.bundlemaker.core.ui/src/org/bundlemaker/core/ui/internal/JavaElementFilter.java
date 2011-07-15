package org.bundlemaker.core.ui.internal;

import org.bundlemaker.core.parser.jdt.CoreParserJdt;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class JavaElementFilter extends ViewerFilter {

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    if (element instanceof IJavaProject) {
      return !((IJavaProject) element).getElementName().endsWith(CoreParserJdt.BUNDLEMAKER_JDT_PROJECT_POSTFIX);
    }
    return true;
  }
}
