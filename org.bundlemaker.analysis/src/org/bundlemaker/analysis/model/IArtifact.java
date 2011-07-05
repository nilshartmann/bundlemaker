package org.bundlemaker.analysis.model;

import java.util.Collection;

/**
 * <p>
 * Defines the common interface of an <i>artifact</i>. An artifact describes a part of a software system, e.g. a
 * <i>group</i>, a <i>module</i>, a <i>resource</i> or a <i>type</i>. An artifact has a type that determines which kind
 * of artifact (e.g. module, package, type) the artifact represents. It also has a name that can be displayed in a GUI.
 * </p>
 * 
 * <p>
 * Prinzipiell wird zwischen zwei Arten von Artefakten unterschieden. Zum einen zwischen gruppierenden Artefakten, die
 * andere Artefakte als Kinder haben können sowie Primärartefakten, welche die kleinste Einheit in einem Softwaresystem
 * beschreiben.
 * 
 * <p>
 * Artefakte besitzen Abhaengigkeiten zu anderen Artefakten. Bei gruppierenden Artefakten ergeben sich die
 * Abhaengigkeiten aus den aggregierten Abhaengigkeiten ihrer Kinder. Bei Primaerartefakten werden die Abhaengigkeiten
 * direkt hinterlegt
 * 
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public interface IArtifact {

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
   * Returns the parent artifact of this artifact or <code>null</code> if this artifact is the root artifact.
   * </p>
   * 
   * @return the parent artifact of this artifact or <code>null</code> if this artifact is the root artifact.
   */
  public IArtifact getParent();

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
  public IArtifact getParent(ArtifactType type);

  /**
   * <p>
   * Returns an unmodifiable {@link Collection} with all children of this {@link IArtifact}.
   * </p>
   * 
   * @return an unmodifiable {@link Collection} with all children of this {@link IArtifact}.
   */
  public Collection<IArtifact> getChildren();

  /**
   * <p>
   * Returns the child of the this artifact that matches the given path (delimited by '/').
   * </p>
   * <p>
   * For each segment of the given path a matching artifact will be chosen (relative to its parent) using the following
   * algorithm:
   * <ol>
   * <li>If the segment equals the <i>qualified name</i> of a child, the child will be chosen.</li>
   * <li>If no child was chosen before and the segment equals the (simple) name of a child, this child will be chosen.</li>
   * </ol>
   * This algorithm allows it to use paths like
   * <code>"myRoot/group1/group2/myModule/org.bundlemaker.core/MyType"<code> or <code>"myRoot/group1/group2/myModule/org.bundlemaker.core/MyType"
   * </p>
   * 
   * @param path
   * @return
   */
  IArtifact getChild(String path);

  /**
   * <p>
   * Returns <code>true</code> if this artifact contains the specified artifact, <code>false</code> otherwise.
   * </p>
   * 
   * @param the
   *          artifact
   * @return
   */
  public boolean contains(IArtifact artifact);

  /**
   * <p>
   * Adds the given artifact to this artifact.
   * </p>
   * 
   * @param artifact
   *          the given artifact to this artifact.
   */
  public void addArtifact(IArtifact artifact);

  // TODO: REMOVE!
  @Deprecated
  public void addArtifact(IArtifact artifact, boolean relinkParent);

  /**
   * <p>
   * Removes the given artifact from this artifact.
   * </p>
   * 
   * @param artifact
   * @return
   */
  public boolean removeArtifact(IArtifact artifact);

  /**
   * <p>
   * Returns all the dependencies that this artifact contains.
   * </p>
   * 
   * @return
   */
  public Collection<IDependency> getDependencies();

  /**
   * Gibt die Abhaengigkeit zu dem uebergebenen Artefakt zurueck
   * 
   * @param artifact
   *          das uebergebene Artefakt
   * @return Die Abhaengigkeit
   */
  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  public IDependency getDependency(IArtifact artifact);

  /**
   * Gibt alle Abhaengigkeiten zu den uebergebenen Artefakten zurueck
   * 
   * @param artifacts
   *          Eine Liste von Artefakten
   * @return Eine Liste von Abhaengigkeiten
   */
  public Collection<IDependency> getDependencies(Collection<IArtifact> artifacts);

  /**
   * Setzt die Ordinalzahl des Artefaktes. Ueber die Ordinalzahl wird die Rangfolge von Artefakten bestimmt
   * 
   * @param ordinal
   */
  public void setOrdinal(Integer ordinal);

  /**
   * Gibt die Ordinalzahl zurueck
   * 
   * @return
   */
  public Integer getOrdinal();

  /**
   * Gibt alle Primaerartefakte zurueck
   * 
   * @return
   */
  // replace with getChildren(ArtifactType)
  @Deprecated
  public Collection<IArtifact> getLeafs();
}
