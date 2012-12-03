package org.bundlemaker.core.analysis.spi;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencingArtifactTrait implements IReferencingArtifact {

  /** the map with all core dependencies */
  private Map<IBundleMakerArtifact, IDependency> _coreDependenciesToMap;

  /** indicates whether this instance has been initialized or not */
  private boolean                                _isInitialized = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo() {

    //
    _initialize();

    //
    if (_coreDependenciesToMap == null) {
      return Collections.emptyList();
    }

    //
    return _coreDependenciesToMap.values();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasDependencyTo(IBundleMakerArtifact artifact) {

    //
    _initialize();

    return _coreDependenciesToMap != null && _coreDependenciesToMap.containsKey(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependency getDependencyTo(IBundleMakerArtifact artifact) {

    //
    _initialize();

    //
    if (_coreDependenciesToMap == null) {
      return null;
    }

    //
    return _coreDependenciesToMap.get(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts) {

    //
    _initialize();

    //
    if (_coreDependenciesToMap == null) {
      return Collections.emptyList();
    }

    //
    List<IDependency> result = new LinkedList<IDependency>();

    //
    for (IBundleMakerArtifact artifact : artifacts) {
      result.add(getDependencyTo(artifact));
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts) {

    //
    _initialize();

    return getDependenciesTo(Arrays.asList(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  public Map<IBundleMakerArtifact, IDependency> coreDependenciesToMap() {

    //
    if (_coreDependenciesToMap == null) {
      return _coreDependenciesToMap = new HashMap<IBundleMakerArtifact, IDependency>();
    }

    //
    return _coreDependenciesToMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isInitialized() {
    return _isInitialized;
  }

  /**
   * <p>
   * </p>
   * 
   * @param isInitialized
   */
  public void setInitialized(boolean isInitialized) {
    _isInitialized = isInitialized;
  }

  /**
   * <p>
   * </p>
   */
  private final void _initialize() {

    //
    if (_isInitialized) {
      return;
    }

    //
    initialize();

    // set initialized
    _isInitialized = true;
  }

  /**
   * <p>
   * </p>
   */
  protected void initialize() {
    // do nothing...
  }
}
