package org.bundlemaker.core.ui.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectViewerFilter extends ViewerFilter {

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {

    if (element instanceof IJavaProject) {
      return !((IJavaProject) element).getElementName().contains(Activator.BUNDLEMAKER_ARTIFICIAL_PROJECT_IDENTIFIER);
    } else if (element instanceof IProject) {
      return !((IProject) element).getName().contains(Activator.BUNDLEMAKER_ARTIFICIAL_PROJECT_IDENTIFIER);
    }

    return true;
  }
}
