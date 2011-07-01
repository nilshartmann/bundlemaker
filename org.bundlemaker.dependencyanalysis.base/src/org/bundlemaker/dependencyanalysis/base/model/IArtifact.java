package org.bundlemaker.dependencyanalysis.base.model;

import java.util.Collection;

/**
 * <p>
 * Defines the common interface of an artifact. An artifact describes a part of a software system, e.g. a <i>module</i>,
 * a <i>resources</i>, a <i>type</i> and has a name that can be displayed in a GUI. It also has a type that determines
 * which kind of artifact (e.g. module, package, type) the artifact represents.
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
   * Returns the simple name of the artifact.
   * </p>
   * 
   * @return the simple name of the artifact.
   */
  public String getName();

  /**
   * <p>
   * Returns the fully qualified name of the artifact.
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
  public void setProperty(String key, Object value);

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  public String getProperty(String key);

  /**
   * <p>
   * </p>
   * 
   * @param <T>
   * @param key
   * @param t
   * @return
   */
  public <T> T getProperty(String key, Class<T> t);

  /**
   * <p>
   * Returns a {@link Collection} with all children of this {@link IArtifact}.
   * </p>
   * 
   * @return a {@link Collection} with all children of this {@link IArtifact}.
   */
  public Collection<IArtifact> getChildren();

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
   * Returns the <i>'size'</i> of this artifact. The size of an artifact is the sum of all primary artifact that are
   * (recursively) contained in this artifact.
   * </p>
   * 
   * @return the <i>'size'</i> of this artifact.
   */
  public Integer size();

  /**
   * <p>
   * Returns the parent artifact of this artifact or <code>null</code> if this artifact is the root artifact.
   * </p>
   */
  public IArtifact getParent();

  /**
   * Gibt den Elternteil eines bestimmten Typs zurueck
   * 
   * @param type
   *          Der uebergebenen Typ des Artefaktes
   * @return
   */
  public IArtifact getParent(ArtifactType type);

  /**
   * Gibt die Abhaengigkeit zu dem uebergebenen Artefakt zurueck
   * 
   * @param artifact
   *          das uebergebene Artefakt
   * @return Die Abhaengigkeit
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
   * Gibt alle Abhaengigkeiten zurueck, die das Artefakt zu anderen Artefakten haelt
   * 
   * @return
   */
  public Collection<IDependency> getDependencies();

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
