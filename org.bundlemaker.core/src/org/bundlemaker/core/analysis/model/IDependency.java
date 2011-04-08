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

import org.bundlemaker.core.analysis.rules.Violation;

/**
 * <p>
 * Interface einer Abgaengigkeit
 * 
 * <p>
 * Eine Abhaengigkeit wird formal beschrieben durch das geordnete Paar zweier Artefakte, zwischen denen eine
 * Abhaengigkeit besteht.
 * 
 * <p>
 * Jede Abhaengigkeit besteht aus einer Quell- und einem Zielartefakte sowie einer Gewichtung, die die Anzahl der
 * Abhaengigkeiten zum Zielartefakt beschreibt
 * 
 * <p>
 * Optional kann eine Abhaengigkeit aus aggregierten Abhaengigkeiten bestehen. Dies ist bspw. dann der Fall, falls die
 * Abhaengigkeiten zwischen gruppierenden Artefakten wie Packages, Bundles oder aehnliches betrachtet wird.
 * 
 * <p>
 * Neben den strukturellen Eigenschaften koennen einer Abhaengigkeit Regelverletzungen in Form von Violations zugefuegt
 * werden
 * 
 * <p>
 * Diese Interface erweitert das Edge<V> Interface von Sonargraph
 * 
 * @author Frank Schlueter
 * @author Kai Lehmann
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IDependency {

  /**
   * Gibt eine <code>Collection</code> von Abhaengigkeiten zurueck, falls die Abhaengigkeit aus aggegrierten
   * Abhaengigkeiten besteht.
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   * 
   */
  public Collection<IDependency> getDependencies();

  public IArtifact getTo();

  public IArtifact getFrom();

  /**
   * Fuegt der Abhaengigkeit eine andere Abhaengigkeit hinzu. Diese Methode wird verwendet, um aggregierte
   * Abhaengigkeiten zu erstellen
   * 
   * @param dependency
   *          Abhaengigkeit, die hinzugefuegt werden soll
   */
  public void addDependency(IDependency dependency);

  public int getWeight();

  public void getLeafDependencies(Collection<IDependency> leafDependencies);

  public void setDependencyKind(DependencyKind dependencyKind);

  public void addWeight();

  public boolean isTaggedIgnore();

  public void setTaggedIgnore(boolean taggedIgnore);

  public void setTaggedIgnore(boolean taggedIgnore, boolean withNotifyDSL);

  public int getTaggedIgnoreCount();

  public void getNewDependencies(Collection<IDependency> newDependencies);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public DependencyKind getDependencyKind();

  public int getViolationWeight();

  /**
   * Fuegt der Abhaengigkeit einen Regelverstoﬂ hinzu
   * 
   * @param violation
   *          Die Violation
   */
  public void addViolation(Violation violation);

  /**
   * Gibt eine <code>Collection</code> der Violations zurueck
   * 
   * @return <code>Collection</code> von Abhaengigkeiten
   */
  public Collection<Violation> getViolations();

  public void visitVisitors();

  /**
   * Gibt <code>true</code> zurueck, falls die Abhaengigkeit Regeln verletzt hat.
   * 
   * @return <code>true</code>, bei Regelverstoﬂ, ansonsten <code>false</code>
   */
  public boolean hasViolations();

  /**
   * Entfernt die bislang in der Abh‰ngigkeit enthalten Regelverstˆﬂe
   */
  public void clearViolations();

}
