package org.bundlemaker.core.analysis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Common interface for all nodes in a BundleMaker artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerArtifact extends IArtifact, Comparable<IBundleMakerArtifact> {

  /**
   * <p>
   * Returns the path identifier for this artifact.
   * </p>
   * 
   * @return
   */
  String getUniquePathIdentifier();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IPath getFullPath();

  /**
   * {@inheritDoc}
   */
  @Deprecated
  IBundleMakerArtifact getChild(String path);

  /**
   * {@inheritDoc}
   */
  Collection<IBundleMakerArtifact> getChildren();

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param clazz
   * @param filter
   * @return
   */
  <T extends IBundleMakerArtifact> T findChild(Class<T> clazz, String filter);

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param clazz
   * @return
   */
  <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz);

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param filter
   * @param clazz
   * @return
   */
  <T extends IBundleMakerArtifact> List<T> findChildren(Class<T> clazz, String filter);

  /**
   * <p>
   * </p>
   * 
   * @param clazz
   * @param path
   * 
   * @param <T>
   * @return
   */
  <T extends IBundleMakerArtifact> T getChildByPath(Class<T> clazz, IPath path);

  /**
   * {@inheritDoc}
   */
  IBundleMakerArtifact getParent();

  /**
   * {@inheritDoc}
   */
  IBundleMakerArtifact getParent(ArtifactType type);

  /**
   * <p>
   * Returns the root artifact for this artifact node.
   * </p>
   * 
   * @return the root artifact.
   */
  IRootArtifact getRoot();

  // public IGroupArtifact getParentGroup();
  //
  // public boolean hasParentGroup();
  //
  // public IModuleArtifact getParentModule();
  //
  // public boolean hasParentModule();
  //
  // public IPackageArtifact getParentPackage();
  //
  // public boolean hasParentPackage();
  //
  // public IResourceArtifact getParentResource();
  //
  // public boolean hasParentResource();

  /**
   * {@inheritDoc}
   */
  public Collection<? extends IDependency> getDependencies(IBundleMakerArtifact... artifacts);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasParent();

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
