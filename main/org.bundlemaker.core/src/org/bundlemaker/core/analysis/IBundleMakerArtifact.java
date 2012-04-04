package org.bundlemaker.core.analysis;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Common interface for all nodes in a BundleMaker artifact tree.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerArtifact extends Comparable<IBundleMakerArtifact> {

  /**
   * <p>
   * Returns the artifact type.
   * </p>
   * 
   * @return the artifact type.
   */
  public ArtifactType getType();

  /**
   * <p>
   * Returns the simple name of the artifact. For a detailed description of the semantic of <code>getName()</code> and
   * <code>getQualifiedName()</code> see {@link IArtifact#getQualifiedName()}.
   * </p>
   * 
   * @return the simple name of the artifact.
   */
  public String getName();

  /**
   * <p>
   * Returns the fully qualified name of the artifact:
   * <table border="1">
   * <tr>
   * <th>Type</th>
   * <th>getName()</th>
   * <th>getQualifiedName()</th>
   * </tr>
   * <tr>
   * <td>Root</td>
   * <td colspan="2">Both the the qualified name and the (simple) name return the name of the root artifact that was
   * specified when the root was created.<br/>
   * <b>Example:</b> <code>TransformedSystem</code>, <code>MySystem</code></td>
   * </tr>
   * <tr>
   * <td>Group</td>
   * <td>The name is the name of the current artifact element.<br/>
   * <b>Example:</b> <code>group2</code>.</td>
   * <td>The qualified name is the full group path delimited by '|'.<br/>
   * <b>Example:</b> <code>group1|group2</code></td>
   * </tr>
   * <tr>
   * <td>Module</td>
   * <td colspan="2">Both the the qualified name and the (simple) name return the name of the module.<br/>
   * <b>Example:</b> <code>MyModule</code>, <code>MyModule_1.0.0</code></td>
   * <tr>
   * <td>Package</td>
   * <td>The name is the last segment of the current package.<br/>
   * <b>Example:</b> <code>core</code></td>
   * <td>The qualified name is the package delimited by '.'.<br/>
   * <b>Example:</b> <code>org.bundlemaker.core</code></td>
   * </tr>
   * <tr>
   * <td>Resource</td>
   * <td colspan="2">Both the the qualified name and the (simple) name return the name of the Resource without the path<br/>
   * <b>Example:</b> <code>MyType.class</code>, <code>MyImage.png</code>
   * </tr>
   * <tr>
   * <td>Type</td>
   * <td>The name is the simple type name.<br/>
   * <b>Example:</b> <code>MyType</code></td>
   * <td>The qualified name is the fully qualified type name delimited by '.'.<br/>
   * <b>Example:</b> <code>org.bundlemaker.core.MyType</code></td>
   * </tr>
   * </table>
   * </p>
   * 
   * @return the fully qualified name of the artifact.
   */
  public String getQualifiedName();

  /**
   * <p>
   * Sets a property with the given key and value.
   * </p>
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   */
  public void setProperty(Object key, Object value);

  /**
   * <p>
   * Returns the value of the property with the specified key.
   * </p>
   * 
   * @param key
   *          the key of the property
   * @return the value of the property with the specified key.
   */
  public String getProperty(Object key);

  /**
   * <p>
   * Returns the property with the given key and the specified type.
   * </p>
   * 
   * @param <T>
   * @param key
   * @param t
   * @return the property with the given key and the specified type.
   */
  public <T> T getProperty(Object key, Class<T> t);

  /**
   * <p>
   * </p>
   */
  @Deprecated
  boolean hasChild(String path);

  /**
   * <p>
   * Returns <code>true</code> if this artifact contains the specified artifact, <code>false</code> otherwise.
   * </p>
   * 
   * @param the
   *          artifact
   * @return
   */
  @Deprecated
  // must not check for leafs
  public boolean contains(IBundleMakerArtifact artifact);

  /**
   * <p>
   * Adds the given artifact to this artifact.
   * </p>
   * 
   * @param artifact
   *          the given artifact to this artifact.
   */
  public void addArtifact(IBundleMakerArtifact artifact);

  /**
   * <p>
   * Removes the given artifact from this artifact.
   * </p>
   * 
   * @param artifact
   * @return
   */
  public boolean removeArtifact(IBundleMakerArtifact artifact);

  /**
   * <p>
   * Returns all the dependencies that this artifact contains.
   * </p>
   * 
   * @return
   */
  public Collection<IDependency> getDependencies();

  /**
   * <p>
   * Returns the dependency to the given Artifact.
   * </p>
   * 
   * @param artifact
   *          the artifact
   * 
   * @return the dependency to the given Artifact.
   */
  public IDependency getDependency(IBundleMakerArtifact artifact);

  /**
   * <p>
   * Returns all the dependencies to the given collection of artifacts.
   * </p>
   * 
   * @param artifacts
   *          the atifacts
   * @return all the dependencies to the given collection of artifacts.
   */
  public Collection<? extends IDependency> getDependencies(Collection<? extends IBundleMakerArtifact> artifacts);

  /**
   * <p>
   * Returns all leafs of this artifact.
   * </p>
   * 
   * @return
   */
  // replace with getChildren(ArtifactType)
  @Deprecated
  public Collection<IBundleMakerArtifact> getLeafs();

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
   * <p>
   * Returns the child of the this artifact that matches the given path (delimited by '|').
   * </p>
   * <p>
   * For each segment of the given path a matching artifact will be chosen (relative to its parent) using the following
   * algorithm:
   * <ol>
   * <li>If the segment equals the <i>qualified name</i> of a child, the child will be chosen.</li>
   * <li>If no child was chosen before and the segment equals the (simple) name of a child, this child will be chosen.</li>
   * </ol>
   * This algorithm allows it to use paths like
   * <code>"myRoot|group1|group2|myModule|org.bundlemaker.core|MyType"<code> or <code>"myRoot|group1|group2|myModule|org.bundlemaker.core|org.bundlemaker.core.MyType"
   * </p>
   * 
   * @param path
   * @return
   */
  @Deprecated
  IBundleMakerArtifact getChild(String path);

  /**
   * <p>
   * Returns an unmodifiable {@link Collection} with all children of this {@link IArtifact}.
   * </p>
   * 
   * @return an unmodifiable {@link Collection} with all children of this {@link IArtifact}.
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
  @Deprecated
  <T extends IBundleMakerArtifact> T getChildByPath(Class<T> clazz, IPath path);

  /**
   * <p>
   * Returns the parent artifact of this artifact or <code>null</code> if this artifact is the root artifact.
   * </p>
   * 
   * @return the parent artifact of this artifact or <code>null</code> if this artifact is the root artifact.
   */
  IBundleMakerArtifact getParent();

  /**
   * <p>
   * Returns the first parent artifact of this artifact that has the specified type or <code>null</code> if no such
   * parent exists.
   * </p>
   * 
   * @param type
   *          the type of the parent
   * 
   * @return the first parent artifact of this artifact that has the specified type or <code>null</code> if no such
   *         parent exists.
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
  boolean canAdd(IBundleMakerArtifact artifact);

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
  List<IBundleMakerArtifact> invalidateDependencyCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<IBundleMakerArtifact, IDependency> getCachedDependencies();
}
