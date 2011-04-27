/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.analysis.model;

import java.util.Collection;

/**
 * <p>
 * Defines the common interface of an artifact. An artifact is a part of the (software) system that should be analyzed,
 * e.g. a type, a resource, a package or a module.
 * </p>
 * <p>
 * Each artifact has a name that can be shown in the GUI. An artifact also has a type that classifies the artifact (see
 * {@link ArtifactType}).
 * </p>
 * 
 * @author Frank Schlueter
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IArtifact {

  /**
   * <p>
   * Returns the name of the artifact as shown in the GUI.
   * </p>
   * 
   * @return the name of the artifact
   */
  public String getName();

  /**
   * <p>
   * Returns the type of the artifact.
   * </p>
   * 
   * @return the type of the artifact.
   */
  public ArtifactType getType();

  /**
   * <p>
   * Returns the qualified name of this artifact.
   * </p>
   * 
   * @return the qualified name of this artifact.
   */
  public String getQualifiedName();

  /**
   * <p>
   * Adds an artifact to the list of children.
   * </p>
   * 
   * @param artifact
   *          the artifact to add.
   */
  public void addArtifact(IArtifact artifact);

  /**
   * <p>
   * Removes the artifact from the list of children.
   * </p>
   * 
   * @param artifact
   *          the artifact to remove.
   * 
   * @return
   */
  public boolean removeArtifact(IArtifact artifact);

  /**
   * <p>
   * Sets the parent of this artifact.
   * </p>
   * 
   * @param parent
   *          the parent of this artifact
   */
  @Deprecated
  public void setParent(IArtifact parent);

  /**
   * Gibt die Groesse des Artefaktes zurueck. Die Groesse des Artefaktes ergibt sich aus der Summe aller
   * Primaerartefakte, die unter dem betrachteten Artefakt haengen
   * 
   * @return Die Groesse des Artefaktes
   */
  public Integer size();

  /**
   * <p>
   * Returns the parent of this artifact.
   * </p>
   * 
   * @return the parent of this artifact.
   */
  public IArtifact getParent();

  /**
   * <p>
   * Returns the first parent that has the specified type.
   * </p>
   * 
   * @param type
   *          the type of the requested parent artifact
   * 
   * @return the first parent that has the specified type.
   */
  public IArtifact getParent(ArtifactType type);

  /**
   * <p>
   * Returns the children of this artifact. If this artifact does not contain any child artifacts, an empty collection
   * will be returned.
   * </p>
   * 
   * @return
   */
  public Collection<IArtifact> getChildren();

  /**
   * <p>
   * Returns <code>true</code>, if the given artifact is a child of this artifact.
   * </p>
   * 
   * @param artifact
   * @return
   */
  public boolean contains(IArtifact artifact);

  /**
   * <p>
   * Returns the (aggregated) dependencies to the specified artifact.
   * 
   * @param artifact
   *          the target artifact
   * 
   * @return the (aggregated) dependencies to the specified artifact.
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
   * Fuegt dem Artefakt eine Abhaengigkeit zum uebergeben Artefakt hinzu
   * 
   * @return Die neue Dependency
   * @param artifact
   */
  public IDependency addDependency(IArtifact artifact);

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
   * <p>
   * Returns all leafs.
   * </p>
   * 
   * @return all leafs.
   */
  public Collection<IArtifact> getLeafs();
}
