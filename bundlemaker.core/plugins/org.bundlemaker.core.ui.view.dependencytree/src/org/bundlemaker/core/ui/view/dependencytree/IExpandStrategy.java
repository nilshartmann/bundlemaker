package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.jface.viewers.TreeViewer;

/**
 * <p>
 * Interface that defines the methods that
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IExpandStrategy {

  /**
   * <p>
   * </p>
   * 
   * @param fromTreeViewer
   * @param toTreeViewer
   */
  void init(TreeViewer fromTreeViewer, TreeViewer toTreeViewer);

  /**
   * <p>
   * </p>
   * 
   * @param fromTreeViewer
   * @param visibleArtifacts
   */
  void expandFromTreeViewer(TreeViewer fromTreeViewer, Set<IBundleMakerArtifact> visibleArtifacts);

  /**
   * <p>
   * </p>
   * 
   * @param toTreeViewer
   * @param visibleArtifacts
   */
  void expandToTreeViewer(TreeViewer toTreeViewer, Set<IBundleMakerArtifact> visibleArtifacts);
}
