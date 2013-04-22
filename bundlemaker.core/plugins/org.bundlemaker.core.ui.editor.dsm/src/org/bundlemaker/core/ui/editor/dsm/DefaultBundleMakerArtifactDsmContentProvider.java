package org.bundlemaker.core.ui.editor.dsm;

import java.util.Collection;
import java.util.Observable;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.ui.editor.dsm.cycle.CycleDetector;
import org.bundlemaker.core.ui.editor.dsm.widget.IDsmContentProvider;
import org.eclipse.core.runtime.Assert;

public class DefaultBundleMakerArtifactDsmContentProvider extends Observable implements IDsmContentProvider {

  /** - */
  private CycleDetector   _cycleDetector;

  /** - */
  private IDependency[][] _dependencies;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultBundleMakerArtifactDsmContentProvider}.
   * </p>
   * 
   * @param unorderedArtifacts
   */
  public DefaultBundleMakerArtifactDsmContentProvider(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {
    this();
    initialize(unorderedArtifacts);
  }

  /**
   * <p>
   * Creates a new instance of type {@link DefaultBundleMakerArtifactDsmContentProvider}.
   * </p>
   */
  public DefaultBundleMakerArtifactDsmContentProvider() {
    _dependencies = new IDependency[0][0];
  }

  /**
   * {@inheritDoc}
   */
  public int getItemCount() {
    return _cycleDetector.getOrderedArtifacts().length;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[][] getDependencies() {
    return _dependencies;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] getNodes() {
    return _cycleDetector.getOrderedArtifacts();
  }

  /**
   * {@inheritDoc}
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
    return _cycleDetector.isInCycle(i);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInCycle(int i, int j) {
    return _cycleDetector.isInCycle(i, j);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int[][] getCycles() {
    return _cycleDetector.getCycles();
  }

  /**
   * <p>
   * </p>
   * 
   * @param unorderedArtifacts
   */
  private void initialize(Collection<? extends IBundleMakerArtifact> unorderedArtifacts) {

    // IArtifact[] headers, IDependency[][] dependencies
    Assert.isNotNull(unorderedArtifacts);

    _cycleDetector = new CycleDetector(unorderedArtifacts);

    _dependencies = new IDependency[_cycleDetector.getOrderedArtifacts().length][_cycleDetector.getOrderedArtifacts().length];
    for (int i = 0; i < _cycleDetector.getOrderedArtifacts().length; i++) {
      for (int j = 0; j < _cycleDetector.getOrderedArtifacts().length; j++) {
        IDependency dependency = _cycleDetector.getOrderedArtifacts()[i].getDependencyTo(_cycleDetector.getOrderedArtifacts()[j]);
        _dependencies[j][i] = dependency != null && dependency.getWeight() != 0 ? dependency : null;
      }
    }
  }
}
