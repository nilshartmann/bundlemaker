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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifact;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.AbstractBundleMakerArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.bundlemaker.core.internal.analysis.DispatchingArtifactTreeVisitor;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

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
  public VirtualType2IArtifact(String name, String fullyQualifiedName, IArtifact parent) {

    super(ArtifactType.Type, name);

    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractBundleMakerArtifactContainer) parent).getModifiableChildren().add(this);

    _fullyQualifiedName = fullyQualifiedName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getConfiguration() {
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
  public IResourceModule getContainingResourceModule() {
    return null;
  }

  @Override
  public List<IArtifact> invalidateDependencyCache() {
    return Arrays.asList(new IArtifact[] { this });
  }

  @Override
  public Map<IArtifact, IDependency> getCachedDependencies() {
    return Collections.emptyMap();
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

  @Override
  public void setParent(IArtifact parent) {

    //
    super.setParent(parent);

    //
    getRoot();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (IRootArtifact) getParent(ArtifactType.Root);
    }

    //
    return _root;
  }

  @Override
  public boolean removeArtifact(IArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addArtifact(IArtifact artifact) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Collection<IBundleMakerArtifact> getChildren() {
    return Collections.emptySet();
  }

  public IBundleMakerArtifact getParent() {
    return (IBundleMakerArtifact) super.getParent();
  }

  public IBundleMakerArtifact getParent(ArtifactType type) {
    return (IBundleMakerArtifact) super.getParent(type);
  }

  @Override
  public boolean contains(IArtifact artifact) {
    return this.equals(artifact);
  }

  @Override
  public IDependency getDependency(IArtifact artifact) {
    return null;
  }

  @Override
  public Collection<IDependency> getDependencies() {
    return Collections.emptyList();
  }

  @Override
  public Collection<IArtifact> getLeafs() {
    // simply return null
    return null;
  }

  @Override
  public IModularizedSystem getModularizedSystem() {
    return AdapterUtils.getModularizedSystem(this);
  }

  @Override
  public IDependencyModel getDependencyModel() {
    return ((AbstractBundleMakerArtifactContainer) getParent(ArtifactType.Root)).getDependencyModel();
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
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
  public void accept(IArtifactTreeVisitor visitor) {
    //
    visitor.visit(this);
  }

  public void accept(IArtifactTreeVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  @Override
  public boolean isVirtual() {
    return true;
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
