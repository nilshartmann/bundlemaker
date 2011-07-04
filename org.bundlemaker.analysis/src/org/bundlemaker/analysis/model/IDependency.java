package org.bundlemaker.analysis.model;

import java.util.Collection;

import org.bundlemaker.analysis.rules.Violation;

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
 * @author Kai Lehmann
 * @author Frank Schl&uuml;ter
 */
public interface IDependency {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IArtifact getTo();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IArtifact getFrom();

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
   * Returns the weight of this method.
   * </p>
   * 
   * @return the weight of this method.
   */
  public int getWeight();

  /**
   * <p>
   * </p>
   * 
   * @param leafDependencies
   */
  public void getLeafDependencies(Collection<IDependency> leafDependencies);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isTaggedIgnore();

  /**
   * <p>
   * </p>
   * 
   * @param taggedIgnore
   */
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

  public void addDependency(IDependency dependency);
}
