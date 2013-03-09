package org.bundlemaker.core.analysis.algorithms.sorter;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

public interface IArtifactSorter {

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  List<IBundleMakerArtifact> sort(List<IBundleMakerArtifact> artifacts);
}
