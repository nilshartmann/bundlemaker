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

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterResource2IArtifact extends AbstractAdvancedContainer implements IResourceArtifact {

  /** the bundle maker resource */
  private IResource _resource;

  /** - */
  private boolean   _isSourceResource;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterResource2IArtifact}.
   * </p>
   * 
   * @param type
   * @param parent
   */
  public AdapterResource2IArtifact(IResource resource, boolean isSourceResource, IArtifact parent) {
    super(ArtifactType.Resource, resource.getName());

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);

    //
    _resource = resource;

    //
    _isSourceResource = isSourceResource;
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
    IArtifact artifact = getParent(ArtifactType.Module);

    //
    return artifact instanceof IModuleArtifact
        && ((IModuleArtifact) artifact).getAssociatedModule() instanceof IResourceModule;
  }

  @Override
  public IResource getAssociatedResource() {
    return _resource;
  }

  @Override
  public boolean handleCanAdd(IArtifact artifact) {
    return false;
  }

  @Override
  public String getName() {
    return _resource.getName();
  }

  @Override
  public String getQualifiedName() {
    return getName();
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
      for (IArtifact artifact : getChildren()) {
        ((IAdvancedArtifact) artifact).accept(visitor);
      }
    }
  }

}
