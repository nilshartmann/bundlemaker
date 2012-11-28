package org.bundlemaker.core.analysis.algorithms;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

public class AdjacencyList {

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  public static int[][] computeAdjacencyList(Collection<IBundleMakerArtifact> artifacts, IProgressMonitor monitor) {
    Assert.isNotNull(artifacts);

    return computeAdjacencyList((IBundleMakerArtifact[]) artifacts.toArray(new IBundleMakerArtifact[artifacts.size()]),
        monitor);
  }

  /**
   * @param artifacts
   */
  public static int[][] computeAdjacencyList(IBundleMakerArtifact[] artifacts, IProgressMonitor monitor) {

    //
    if (monitor != null) {
      monitor.beginTask("Computing dependencies...", artifacts.length);
    }

    //
    int[][] matrix;
    try {
      // prepare
      int i = 0;
      Map<IBundleMakerArtifact, Integer> map = new HashMap<IBundleMakerArtifact, Integer>();
      for (IBundleMakerArtifact iArtifact : artifacts) {
        map.put(iArtifact, i);
        i++;
      }

      matrix = new int[artifacts.length][];

      //
      for (IBundleMakerArtifact artifact : artifacts) {

        // get the referenced artifacts
        Collection<? extends IDependency> dependencies = artifact.getDependenciesTo(Arrays.asList(artifacts));

        if (dependencies == null) {
          dependencies = Collections.emptyList();
        }

        //
        int index = map.get(artifact);
        matrix[index] = new int[dependencies.size()];

        // GENERICS HACK
        int count = 0;
        for (IDependency dependency : dependencies) {
          matrix[index][count] = map.get(dependency.getTo());
          count++;
        }

        //
        if (monitor != null) {
          monitor.worked(1);
        }
      }
    }
    //
    finally {
      if (monitor != null) {
        monitor.done();
      }
    }

    //
    return matrix;
  }
}
