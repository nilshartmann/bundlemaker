package org.bundlemaker.core.parser.jdt.internal;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class JavaElementFilter extends ViewerFilter {

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    if (element instanceof IJavaProject) {
      return !((IJavaProject) element).getElementName().endsWith(JdtProjectHelper.BUNDLEMAKER_JDT_PROJECT_POSTFIX);
    }
    return true;
  }
}
