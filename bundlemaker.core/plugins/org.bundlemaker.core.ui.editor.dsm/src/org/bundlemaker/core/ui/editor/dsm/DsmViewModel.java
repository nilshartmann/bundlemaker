package org.bundlemaker.core.ui.editor.dsm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
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
public class DsmViewModel extends AbstractDsmViewModel {

  /** - */
  private List<List<IBundleMakerArtifact>> _cycles;

  /** - */
  private IBundleMakerArtifact[]           _artifacts;

  /** - */
  private IDependency[][]                  _dependencies;

  /** - */
  private int[][]                          _cycleArray;

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewModel}.
   * </p>
   * 
   * @param headers
   * @param dependencies
   */
  /**
   * <p>
   * Creates a new instance of type {@link DsmViewModel}.
   * </p>
   * 
   * @param unorderedArtifacts
   */
  public DsmViewModel(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {
    this();
    initialize(unorderedArtifacts);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getItemCount() {
    return _artifacts.length;
  }

  /**
   * <p>
   * Creates a new instance of type {@link DsmViewModel}.
   * </p>
   */
  public DsmViewModel() {
    _cycleArray = new int[0][0];
    _artifacts = new IBundleMakerArtifact[0];
    _dependencies = new IDependency[0][0];
  }

  @Override
  public Object[][] getDependencies() {
    return _dependencies;
  }

  @Override
  public Object[] getNodes() {
    return _artifacts;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public IDependency getDependency(int x, int y) {

    //
    if (x == -1 || y == -1) {
      return null;
    }

    // return null if dependency does not exist
    if (x >= _dependencies.length || y >= _dependencies[x].length) {
      return null;
    }

    // return dependency
    return _dependencies[x][y];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInCycle(int i) {
    return isInCycle(i, i);
  }

  @Override
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
  @Override
  public boolean isToggled() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[][] getCycles() {
    return _cycleArray;
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // protected IDsmViewConfiguration createConfiguration() {
  // return new DefaultDsmViewConfiguration();
  // }
  //
  // @Override
  // protected String[][] createValues() {
  //
  // String[][] result = new String[_artifacts.length][_artifacts.length];
  // for (int i = 0; i < result.length; i++) {
  // for (int j = 0; j < result.length; j++) {
  // if (_dependencies[i][j] != null) {
  // result[i][j] = "" + _dependencies[i][j].getWeight();
  // }
  // }
  // }
  //
  // //
  // return result;
  // }
//
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  protected String[] createLabels() {
//
//    //
//    String[] result = new String[_artifacts.length];
//
//    //
//    for (int i = 0; i < _artifacts.length; i++) {
//      result[i] = _artifacts[i].getQualifiedName();
//    }
//
//    //
//    return result;
//  }

  // @Override
  // protected String[] doGetDisplayLabels() {
  // //
  // String[] result = new String[_artifacts.length];
  //
  // //
  // for (int i = 0; i < _artifacts.length; i++) {
  // result[i] = getDisplayLabel(_artifacts[i]);
  // }
  //
  // //
  // return result;
  // }

  @Override
  public String getToolTip(int x, int y) {
    System.out.println("Tooltip: " + x + ", " + y);
    return null;
  }

  private void initialize(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {

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

    _dependencies = new IDependency[orderedArtifacts.size()][orderedArtifacts.size()];
    for (int i = 0; i < orderedArtifacts.size(); i++) {
      for (int j = 0; j < orderedArtifacts.size(); j++) {
        IDependency dependency = orderedArtifacts.get(i).getDependencyTo(orderedArtifacts.get(j));
        _dependencies[j][i] = dependency != null && dependency.getWeight() != 0 ? dependency : null;
      }
    }
  }
}
