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

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResource2IArtifact extends AbstractArtifactContainer implements IResourceArtifact,
    IMovableUnit, ITempTypeProvider {

  /** the bundle maker resource */
  private IModuleResource _resource;

  /** - */
  private boolean         _isSourceResource;

  /** - */
  private IMovableUnit    _movableUnit;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResource2IArtifact}.
   * </p>
   * 
   * @param type
   * @param parent
   */
  public AdapterResource2IArtifact(IModuleResource resource, boolean isSourceResource, IBundleMakerArtifact parent,
      ArtifactCache artifactCache) {
    super(resource.getName());

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);

    //
    _resource = resource;

    //
    _isSourceResource = isSourceResource;

    //
    _movableUnit = MovableUnit.createFromResource(resource, artifactCache.getModularizedSystem());
  }

  @Override
  public List<IType> getAssociatedTypes() {
    return ((ITempTypeProvider) _movableUnit).getAssociatedTypes();
  }

  public List<? extends IModuleResource> getAssociatedBinaryResources() {
    return _movableUnit.getAssociatedBinaryResources();
  }

  @Override
  public boolean hasAssociatedTypes() {
    return ((ITempTypeProvider) _movableUnit).hasAssociatedTypes();
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

  public IModuleResource getAssociatedSourceResource() {
    return _movableUnit.getAssociatedSourceResource();
  }

  public IModule getAssoicatedModule() {
    return _movableUnit.getAssoicatedModule();
  }

  public boolean hasModule() {
    return _movableUnit.hasModule();
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
    IBundleMakerArtifact artifact = getParent(IModuleArtifact.class);

    //
    return artifact instanceof IModuleArtifact
        && ((IModuleArtifact) artifact).getAssociatedModule().isResourceModule();
  }

  @Override
  public IModuleResource getAssociatedResource() {
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
  public String getName() {
    return _resource.getName();
  }

  @Override
  public String getQualifiedName() {
    return _resource.getPath();
  }

  public IModuleResource getResource() {
    return _resource;
  }

  public boolean isSourceResource() {
    return _isSourceResource;
  }

  @Override
  protected String getArtifactType() {
    return "resource";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IAnalysisModelVisitor visitor) {

    //
    if (visitor.visit(this)) {
      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  public void accept(IAnalysisModelVisitor... visitors) {
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
