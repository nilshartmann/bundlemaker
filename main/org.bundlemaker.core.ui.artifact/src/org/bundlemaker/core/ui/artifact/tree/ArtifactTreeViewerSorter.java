package org.bundlemaker.core.ui.artifact.tree;


import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeViewerSorter extends ViewerSorter {

  /**
   * {@inheritDoc}
   */
  @Override
  public int category(Object element) {

    //
    if (element instanceof IArtifact) {
      return ((IArtifact) element).getType().equals(ArtifactType.Package) ? 0 : 1;
    }

    // default category is 1
    return 1;
  }
}
