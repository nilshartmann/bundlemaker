package org.bundlemaker.core.ui.artifact.tree;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
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
    if (element instanceof IBundleMakerArtifact) {
      return ((IBundleMakerArtifact) element).isInstanceOf(IPackageArtifact.class) ? 0 : 1;
    }

    // default category is 1
    return 1;
  }
}
