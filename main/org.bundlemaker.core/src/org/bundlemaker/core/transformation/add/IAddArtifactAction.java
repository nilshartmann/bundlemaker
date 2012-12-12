package org.bundlemaker.core.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <T>
 */
public interface IAddArtifactAction<T extends IBundleMakerArtifact> {

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @param artifactToAdd
   */
  void addChildToParent(T parent, IBundleMakerArtifact artifactToAdd);

  /**
   * <p>
   * </p>
   */
  void undo();
}
