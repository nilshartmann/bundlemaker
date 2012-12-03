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
 * Default implementation of {@link IReferencedArtifact}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencedArtifactTrait implements IReferencedArtifact {

  /** the map with all core dependencies */
  private Map<IBundleMakerArtifact, IDependency> _coreDependenciesFromMap;

  /** indicates whether this instance has been initialized or not */
  private boolean                                _isInitialized = false;

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<IDependency> getDependenciesFrom() {

    // initialize
    _initialize();

    //
    if (_coreDependenciesFromMap == null) {
      return Collections.emptyList();
    }

    //
    return _coreDependenciesFromMap.values();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean hasDependencyFrom(IBundleMakerArtifact artifact) {

    // initialize
    _initialize();

    //
    return _coreDependenciesFromMap != null && _coreDependenciesFromMap.containsKey(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final IDependency getDependencyFrom(IBundleMakerArtifact artifact) {

    // initialize
    _initialize();

    //
    if (_coreDependenciesFromMap == null) {
      return null;
    }

    //
    return _coreDependenciesFromMap.get(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<IDependency> getDependenciesFrom(
      Collection<? extends IBundleMakerArtifact> artifacts) {

    // initialize
    _initialize();

    //
    if (_coreDependenciesFromMap == null) {
      return Collections.emptyList();
    }

    //
    List<IDependency> result = new LinkedList<IDependency>();

    //
    for (IBundleMakerArtifact artifact : artifacts) {
      result.add(getDependencyFrom(artifact));
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Collection<IDependency> getDependenciesFrom(IBundleMakerArtifact... artifacts) {

    // initialize // initialize
    _initialize();

    return getDependenciesFrom(Arrays.asList(artifacts));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Map<IBundleMakerArtifact, IDependency> coreDependenciesFromMap() {

    //
    if (_coreDependenciesFromMap == null) {
      return _coreDependenciesFromMap = new HashMap<IBundleMakerArtifact, IDependency>();
    }

    //
    return _coreDependenciesFromMap;
  }

  /**
   * <p>
   * Returns {@code true} if this instance already has been initialized.
   * </p>
   * 
   * @return {@code true} if this instance already has been initialized.
   */
  public final boolean isInitialized() {
    return _isInitialized;
  }

  /**
   * <p>
   * </p>
   * 
   * @param isInitialized
   */
  public final void setInitialized(boolean isInitialized) {
    _isInitialized = isInitialized;
  }

  /**
   * <p>
   * Initializes this instance. Override to add dependencies.
   * </p>
   */
  protected void initialize() {
    // do nothing...
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
}
