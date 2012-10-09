package org.bundlemaker.analysis.model;

import java.util.Collection;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDependency {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IBundleMakerArtifact getTo();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IBundleMakerArtifact getFrom();

  /**
   * <p>
   * Returns a {@link Collection} of dependencies if this dependency is an aggregated dependency.
   * </p>
   * 
   * @return a {@link Collection} of dependencies
   */
  public Collection<IDependency> getDependencies();

  /**
   * <p>
   * Returns the weight of this dependency.
   * </p>
   * 
   * @return the weight of this dependency.
   */
  public int getWeight();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IDependency> getLeafDependencies();

  DependencyKind getDependencyKind();
}
