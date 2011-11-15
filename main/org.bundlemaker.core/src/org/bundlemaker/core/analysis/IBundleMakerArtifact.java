package org.bundlemaker.core.analysis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * Common interface for all nodes in a BundleMaker artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerArtifact extends IArtifact {

  /**
   * {@inheritDoc}
   */
  IBundleMakerArtifact getChild(String path);

  /**
   * {@inheritDoc}
   */
  public Collection<IBundleMakerArtifact> getChildren();

  /**
   * {@inheritDoc}
   */
  public IBundleMakerArtifact getParent();

  /**
   * {@inheritDoc}
   */
  public IBundleMakerArtifact getParent(ArtifactType type);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasParent();

  /**
   * <p>
   * Returns the root artifact for this artifact node.
   * </p>
   * 
   * @return the root artifact.
   */
  IRootArtifact getRoot();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IArtifactModelConfiguration getConfiguration();

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
   * Returns <code>true</code>, if this artifact node is <i>virtual</i>. A virtual artifact node has no associated
   * resource element.
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
   * </p>
   * 
   * @return
   */
  boolean containsTypesOrResources();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean containsTypes();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean containsResources();

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
   * @param visitors
   */
  void accept(IArtifactTreeVisitor... visitors);

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
}
