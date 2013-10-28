/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.project.internal.DefaultProjectContentResource;
import org.bundlemaker.core.project.internal.IResourceStandinAwareProjectContentResource;
import org.bundlemaker.core.project.internal.IResourceStandinNEW;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceStandin extends DefaultProjectContentResource implements IResourceStandin, IResourceStandinNEW {

  /** - */
  private IResourceStandinAwareProjectContentResource _resource;

  /** - */
  private Set<IModuleResource>                        _stickyResourceStandins;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceStandin}.
   * </p>
   * 
   * @param resource
   */
  public ResourceStandin(Resource resource) {

    this(nullCheck(resource).getProjectContentEntryId(), nullCheck(resource).getRoot(), nullCheck(resource).getPath());

    resource.setResourceStandin(this);
    setResource(resource);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ResourceStandin}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @param archiveFileCache
   */
  public ResourceStandin(String contentId, String root, String path) {
    super(contentId, root, path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getModelExtension() {
    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.adaptAs(IModuleResource.class).getModelExtension();
  }

  @Override
  public IModule getModule(IModularizedSystem modularizedSystem) {

    //
    return ((ModularizedSystem) modularizedSystem).getAssociatedResourceModule(this);
  }

  public IResourceStandinAwareProjectContentResource getResource() {
    return _resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("rawtypes")
  public Object getAdapter(Class adapter) {
    return adaptAs(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    //
    return (T) _resource.adaptAs(clazz);
  }

  @Override
  public void setResource(IResourceStandinAwareProjectContentResource resource) {
    _resource = resource;
  }

  @Override
  public int compareTo(IModuleResource other) {

    if (!getProjectContentEntryId().equals(other.getProjectContentEntryId())) {
      return getProjectContentEntryId().compareTo(other.getProjectContentEntryId());
    }
    if (!getRoot().equals(other.getRoot())) {
      return getRoot().compareTo(other.getRoot());
    }
    if (!getPath().equals(other.getPath())) {
      return getPath().compareTo(other.getPath());
    }

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMovableUnit getMovableUnit() {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.adaptAs(IModuleResource.class).getMovableUnit();
  }

  @Override
  public Set<IModuleResource> getStickyResources() {

    //
    if (_resource == null) {
      throw new RuntimeException();
    }

    //
    if (_resource.adaptAs(IModuleResource.class).getStickyResources().isEmpty()) {
      return Collections.emptySet();
    }

    // lazy init
    if (_stickyResourceStandins == null) {

      // create new set
      _stickyResourceStandins = new HashSet<IModuleResource>();

      // add resource standins
      for (IModuleResource resource : _resource.adaptAs(IModuleResource.class).getStickyResources()) {
        _stickyResourceStandins.add(((Resource) resource).getResourceStandin());
      }
    }

    return _stickyResourceStandins;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  private static Resource nullCheck(Resource resource) {
    Assert.isNotNull(resource, "Parameter resource must not be null.");
    return resource;
  }
}
