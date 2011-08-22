package org.bundlemaker.core.analysis;

import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Abstract interface for artifacts with additional methods.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAdvancedArtifact extends IArtifact {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isVirtual();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isMovable();

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  boolean canAdd(IArtifact artifact);

  /**
   * <p>
   * Returns the root artifact.
   * </p>
   * 
   * @return the root artifact.
   */
  IRootArtifact getRoot();

  /**
   * <p>
   * Returns the {@link IModularizedSystem} this artifact belongs to.
   * </p>
   * 
   * @return the modularized system, never null
   */
  IModularizedSystem getModularizedSystem();

  /**
   * <p>
   * Returns the associated {@link IDependencyModel}.
   * </p>
   * 
   * @return the associated {@link IDependencyModel}.
   */
  IDependencyModel getDependencyModel();

  /**
   * <p>
   * </p>
   * 
   * @param visitor
   */
  void accept(IArtifactTreeVisitor visitor);

  /**
   * <p>
   * </p>
   * 
   */
  List<IArtifact> invalidateDependencyCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IArtifact, IDependency> getCachedDependencies();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean containsTypesOrResources();
}
