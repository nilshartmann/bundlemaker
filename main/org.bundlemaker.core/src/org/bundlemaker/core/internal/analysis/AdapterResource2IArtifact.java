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
package org.bundlemaker.core.internal.analysis;

import java.util.List;

import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResource2IArtifact extends AbstractBundleMakerArtifactContainer implements IResourceArtifact,
    IMovableUnit {

  /** the bundle maker resource */
  private IResource    _resource;

  /** - */
  private boolean      _isSourceResource;

  /** - */
  private IMovableUnit _movableUnit;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResource2IArtifact}.
   * </p>
   * 
   * @param type
   * @param parent
   */
  public AdapterResource2IArtifact(IResource resource, boolean isSourceResource, IBundleMakerArtifact parent,
      ArtifactCache artifactCache) {
    super(ArtifactType.Resource, resource.getName());

    // set parent/children dependency
    setParent(parent);
    ((AbstractBundleMakerArtifactContainer) parent).getModifiableChildren().add(this);

    //
    _resource = resource;

    //
    _isSourceResource = isSourceResource;

    //
    _movableUnit = MovableUnit.createFromResource(resource, artifactCache.getModularizedSystem());
  }

  public List<IType> getAssociatedTypes() {
    return _movableUnit.getAssociatedTypes();
  }

  public List<IResource> getAssociatedBinaryResources() {
    return _movableUnit.getAssociatedBinaryResources();
  }

  public boolean hasAssociatedTypes() {
    return _movableUnit.hasAssociatedTypes();
  }

  public boolean hasAssociatedBinaryResources() {
    return _movableUnit.hasAssociatedBinaryResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedSourceResource() {
    return _movableUnit.hasAssociatedSourceResource();
  }

  public IResource getAssociatedSourceResource() {
    return _movableUnit.getAssociatedSourceResource();
  }

  public IResourceModule getContainingResourceModule() {
    return _movableUnit.getContainingResourceModule();
  }

  public boolean hasContainingResourceModule() {
    return _movableUnit.hasContainingResourceModule();
  }

  @Override
  public boolean isVirtual() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {
    //
    IBundleMakerArtifact artifact = getParent(ArtifactType.Module);

    //
    return artifact instanceof IModuleArtifact
        && ((IModuleArtifact) artifact).getAssociatedModule() instanceof IResourceModule;
  }

  @Override
  public IResource getAssociatedResource() {
    return _resource;
  }

  @Override
  public String handleCanAdd(IBundleMakerArtifact artifact) {
    return "Can not add artifacts to resource";
  }

  @Override
  public String handleCanRemove(IBundleMakerArtifact artifact) {
    return "Can not remove artifacts from resource";
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onRemoveArtifact");

  }

  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onAddArtifact");
  }

  @Override
  public String getName() {
    return _resource.getName();
  }

  @Override
  public String getQualifiedName() {
    return _resource.getPath();
  }

  public IResource getResource() {
    return _resource;
  }

  public boolean isSourceResource() {
    return _isSourceResource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {

    //
    if (visitor.visit(this)) {
      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  public void accept(IArtifactTreeVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }

  @Override
  public boolean containsTypes() {
    return hasAssociatedTypes();
  }

  @Override
  public boolean containsResources() {
    return true;
  }
}
