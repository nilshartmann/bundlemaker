package org.bundlemaker.core.analysis.internal.model;

import java.util.Collection;

import org.bundlemaker.core.analysis.model.DependencyKind;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.model.IDependency;
import org.bundlemaker.core.analysis.rules.Violation;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Dependency implements IDependency {

  /** - */
  private final IArtifact _toArtifact;

  /** - */
  private final IArtifact _fromArtifact;

  /** - */
  private int             _weight;

  /**
   * <p>
   * Creates a new instance of type {@link Dependency}.
   * </p>
   * 
   * @param from
   * @param to
   */
  public Dependency(IArtifact from, IArtifact to) {
    this(from, to, 1);
  }

  /**
   * <p>
   * Creates a new instance of type {@link Dependency}.
   * </p>
   * 
   * @param fromArtifact
   * @param toArtifact
   * @param weight
   */
  public Dependency(IArtifact fromArtifact, IArtifact toArtifact, int weight) {

    Assert.isNotNull(fromArtifact);
    Assert.isNotNull(toArtifact);

    //
    this._fromArtifact = fromArtifact;
    this._toArtifact = toArtifact;
    this._weight = weight;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getTo() {
    return _toArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getFrom() {
    return _fromArtifact;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWeight() {
    return _weight;
  }

  @Override
  public Collection<IDependency> getDependencies() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void addDependency(IDependency dependency) {
    // TODO Auto-generated method stub

  }

  @Override
  public void getLeafDependencies(Collection<IDependency> leafDependencies) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setDependencyKind(DependencyKind dependencyKind) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addWeight() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean isTaggedIgnore() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setTaggedIgnore(boolean taggedIgnore) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setTaggedIgnore(boolean taggedIgnore, boolean withNotifyDSL) {
    // TODO Auto-generated method stub

  }

  @Override
  public int getTaggedIgnoreCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void getNewDependencies(Collection<IDependency> newDependencies) {
    // TODO Auto-generated method stub

  }

  @Override
  public DependencyKind getDependencyKind() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int getViolationWeight() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void addViolation(Violation violation) {
    // TODO Auto-generated method stub

  }

  @Override
  public Collection<Violation> getViolations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void visitVisitors() {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean hasViolations() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void clearViolations() {
    // TODO Auto-generated method stub

  }
}
