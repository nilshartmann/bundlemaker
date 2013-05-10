package org.bundlemaker.core.ui.editor.dsm.cycle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.algorithms.Tarjan;
import org.bundlemaker.core.analysis.algorithms.sorter.FastFasSorter;
import org.bundlemaker.core.analysis.algorithms.sorter.IArtifactSorter;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CycleDetector {

  /** - */
  private List<List<IBundleMakerArtifact>> _cycles;

  /** - */
  private int[][]                          _cycleArray;

  /** - */
  private IBundleMakerArtifact[]           _artifacts;

  /**
   * <p>
   * Creates a new instance of type {@link CycleDetector}.
   * </p>
   * 
   * @param unorderedArtifacts
   */
  public CycleDetector(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {
    this();
    initialize(unorderedArtifacts);
  }

  /**
   * <p>
   * Creates a new instance of type {@link CycleDetector}.
   * </p>
   */
  public CycleDetector() {
    _cycleArray = new int[0][0];
    _artifacts = new IBundleMakerArtifact[0];
  }

  public IBundleMakerArtifact[] getOrderedArtifacts() {
    return _artifacts;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInCycle(int i) {
    return isInCycle(i, i);
  }

  public boolean isInCycle(int i, int j) {

    //
    if (i < 0 || i >= _artifacts.length || j < 0 || j >= _artifacts.length) {
      return false;
    }

    //
    for (List<IBundleMakerArtifact> artifacts : _cycles) {
      if (artifacts.size() > 1 && artifacts.contains(_artifacts[i]) && artifacts.contains(_artifacts[j])) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public int[][] getCycles() {
    return _cycleArray;
  }

  public void initialize(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {

    // IArtifact[] headers, IDependency[][] dependencies
    Assert.isNotNull(unorderedArtifacts);

    _cycles = new Tarjan<IBundleMakerArtifact>().executeTarjan(unorderedArtifacts);
    IArtifactSorter artifactSorter = new FastFasSorter();
    for (List<IBundleMakerArtifact> cycle : _cycles) {
      if (cycle.size() > 1) {
        artifactSorter.sort(cycle);
      }
    }

    // Map<IArtifact, Integer> artifactColumnMap = new HashMap<IArtifact,
    // Integer>();
    List<IBundleMakerArtifact> orderedArtifacts = new ArrayList<IBundleMakerArtifact>();

    // hack: un-cycled artifacts without dependencies first
    for (List<IBundleMakerArtifact> artifactList : _cycles) {
      if (artifactList.size() == 1 && artifactList.get(0).getDependenciesTo().size() == 0) {
        orderedArtifacts.add(artifactList.get(0));
      }
    }

    //
    for (List<IBundleMakerArtifact> cycle : _cycles) {
      for (IBundleMakerArtifact iArtifact : cycle) {
        if (!orderedArtifacts.contains(iArtifact)) {
          orderedArtifacts.add(iArtifact);
        }
      }
    }
    Collections.reverse(orderedArtifacts);
    _artifacts = orderedArtifacts.toArray(new IBundleMakerArtifact[0]);

    //
    List<int[]> cycles = new LinkedList<int[]>();
    for (List<IBundleMakerArtifact> artifactList : _cycles) {
      if (artifactList.size() > 1) {
        int[] cycle = new int[artifactList.size()];
        for (int i = 0; i < cycle.length; i++) {
          cycle[cycle.length - (i + 1)] = orderedArtifacts.indexOf(artifactList.get(i));
        }
        cycles.add(cycle);
      }
    }

    _cycleArray = cycles.toArray(new int[0][0]);
  }
}
