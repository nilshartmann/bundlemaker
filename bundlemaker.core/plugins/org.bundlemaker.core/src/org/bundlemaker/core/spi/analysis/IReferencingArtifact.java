package org.bundlemaker.core.spi.analysis;

import java.util.Collection;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReferencingArtifact {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Collection<IDependency> getDependenciesTo();

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  boolean hasDependencyTo(IBundleMakerArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  IDependency getDependencyTo(IBundleMakerArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  Collection<? extends IDependency> getDependenciesTo(Collection<? extends IBundleMakerArtifact> artifacts);

  /**
   * <p>
   * </p>
   * 
   * @param artifacts
   * @return
   */
  Collection<? extends IDependency> getDependenciesTo(IBundleMakerArtifact... artifacts);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IBundleMakerArtifact, IDependency> coreDependenciesToMap();
}
