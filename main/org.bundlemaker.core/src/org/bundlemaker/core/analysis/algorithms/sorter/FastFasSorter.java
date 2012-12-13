package org.bundlemaker.core.analysis.algorithms.sorter;

import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.algorithms.AdjacencyMatrix;
import org.bundlemaker.core.analysis.algorithms.FastFAS;

public class FastFasSorter implements IArtifactSorter {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IBundleMakerArtifact> sort(List<IBundleMakerArtifact> artifacts) {

    //
    List<IBundleMakerArtifact> result = new ArrayList<IBundleMakerArtifact>(artifacts.size());

    int[][] adjacencyMatrix = AdjacencyMatrix.computeAdjacencyMatrix(null, artifacts);

    FastFAS fastFAS = new FastFAS(adjacencyMatrix);
    int[] ordered = fastFAS.getOrderedSequence();
    for (int i = 1; i <= ordered.length; i++) {
      result.add(artifacts.get(ordered[ordered.length - i]));
    }

    //
    artifacts.clear();
    artifacts.addAll(result);

    //
    return artifacts;
  }
}
