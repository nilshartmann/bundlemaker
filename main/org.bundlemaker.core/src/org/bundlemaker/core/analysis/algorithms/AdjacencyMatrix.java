package org.bundlemaker.core.analysis.algorithms;

import java.util.Collection;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdjacencyMatrix {

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  public static int[][] computeAdjacencyMatrix(IProgressMonitor monitor, Collection<IBundleMakerArtifact> artifacts) {

    //
    Assert.isNotNull(artifacts);

    //
    return computeAdjacencyMatrix(monitor,
        (IBundleMakerArtifact[]) artifacts.toArray(new IBundleMakerArtifact[artifacts.size()]));
  }

  /**
   * <p>
   * </p>
   * 
   * @param monitor
   * @param artifacts
   * @return
   */
  public static int[][] computeAdjacencyMatrix(IProgressMonitor monitor, IBundleMakerArtifact... artifacts) {

    // report progress...
    if (monitor != null) {
      monitor.beginTask("Computing dependencies...", artifacts.length * artifacts.length);
    }

    //
    int[][] result = new int[artifacts.length][artifacts.length];

    //
    try {

      for (int i = 0; i < result.length; i++) {
        for (int j = 0; j < result.length; j++) {

          // report progress...
          if (monitor != null) {
            monitor.worked(1);
          }

          // get the dependency
          IDependency dependency = artifacts[i].getDependencyTo(artifacts[j]);
          result[i][j] = dependency != null ? dependency.getWeight() : 0;
        }
      }
    }

    //
    finally {

      // report progress...
      if (monitor != null) {
        monitor.done();
      }
    }

    // return the matrix
    return result;
  }
}
