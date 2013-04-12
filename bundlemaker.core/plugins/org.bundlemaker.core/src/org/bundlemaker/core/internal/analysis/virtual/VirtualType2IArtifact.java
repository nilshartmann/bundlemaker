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
package org.bundlemaker.core.internal.analysis.virtual;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.DispatchingArtifactTreeVisitor;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * 
 */
public class VirtualType2IArtifact extends AbstractArtifact implements IMovableUnit, ITypeArtifact {

  /** - */
  private String        _fullyQualifiedName;

  /** - */
  private IRootArtifact _root;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param classification
   */
  public VirtualType2IArtifact(String name, String fullyQualifiedName, IBundleMakerArtifact parent) {

    super(name);

    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);

    _fullyQualifiedName = fullyQualifiedName;
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends IDependency> getDependencies(IBundleMakerArtifact... artifacts) {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getUniquePathIdentifier() {
    return getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getFullPath() {

    //
    if (hasParent()) {

      //
      IPath path = getParent().getFullPath();
      path.append(getUniquePathIdentifier());
      return path;

    } else {

      //
      return new Path(getUniquePathIdentifier());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IAnalysisModelConfiguration getConfiguration() {
    return getRoot().getConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  public void removeFromParent() {
    if (this.getParent() != null) {
      this.getParent().removeArtifact(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasParent() {
    return getParent() != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsTypesOrResources() {
    return true;
  }

  @Override
  public boolean containsTypes() {
    return true;
  }

  @Override
  public boolean containsResources() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {
    return false;
  }

  @Override
  public IModule getContainingResourceModule() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResource> getAssociatedBinaryResources() {
    return Collections.emptyList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedSourceResource() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getAssociatedSourceResource() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IType> getAssociatedTypes() {
    return Collections.emptyList();
  }

  @Override
  public boolean hasAssociatedTypes() {
    return false;
  }

  @Override
  public boolean hasAssociatedBinaryResources() {
    return false;
  }

  public boolean hasContainingResourceModule() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return _fullyQualifiedName;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.analysis.ITypeArtifact#getQualifiedTypeName()
   */
  @Override
  public String getQualifiedTypeName() {
    return _fullyQualifiedName;
  }

  @Override
  public void setParent(IBundleMakerArtifact parent) {

    //
    super.setParent(parent);

    //
    getRoot();
  }

  @Override
  public boolean contains(IBundleMakerArtifact artifact) {
    return this.equals(artifact);
  }

  @Override
  public boolean canAdd(IBundleMakerArtifact artifact) {
    return false;
  }

  @Override
  public boolean canRemove(IBundleMakerArtifact artifact) {
    return false;
  }

  @Override
  public IBundleMakerArtifact getChild(String path) {
    return null;
  }

  @Override
  public IType getAssociatedType() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IAnalysisModelVisitor visitor) {
    //
    visitor.visit(this);
  }

  public void accept(IAnalysisModelVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  @Override
  public boolean isVirtual() {
    return true;
  }

  @Override
  protected String getArtifactType() {
    return "virtualtype";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(IBundleMakerArtifact o) {

    //
    if (o == null) {
      return Integer.MIN_VALUE;
    }

    // compare the qualified name
    return this.getQualifiedName().compareTo(o.getQualifiedName());
  }
}
