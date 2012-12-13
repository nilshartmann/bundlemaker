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

    // we have to compute the adjacency matrix first
    int[][] adjacencyMatrix = AdjacencyMatrix.computeAdjacencyMatrix(null, artifacts);

    // the ordered sequence (highest first!)
    FastFAS fastFAS = new FastFAS(adjacencyMatrix);
    int[] ordered = fastFAS.getOrderedSequence();

    // Bubbles
    for (int outerIndex = 1; outerIndex < ordered.length; outerIndex++) {
      for (int index = 1; index <= outerIndex; index++) {

        //
        if (adjacencyMatrix[ordered[index]][ordered[index - 1]] > adjacencyMatrix[ordered[index - 1]][ordered[index]]) {

          // swap...
          int temp = ordered[index];
          ordered[index] = ordered[index - 1];
          ordered[index - 1] = temp;

        } else {

          // stop bubbling...
          break;
        }
      }
    }

    // reverse it
    ordered = FastFAS.reverse(ordered);

    // create the result list
    List<IBundleMakerArtifact> result = new ArrayList<IBundleMakerArtifact>(artifacts.size());
    for (int index : ordered) {
      result.add(artifacts.get(index));
    }

    //
    artifacts.clear();
    artifacts.addAll(result);
    return artifacts;
  }
}
