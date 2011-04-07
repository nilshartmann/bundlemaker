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

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ResourceKey;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceStandin extends ResourceKey implements IResource {

  /** - */
  private Resource       _resource;

  /** - */
  private Set<IResource> _stickyResourceStandins;

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

  @Override
  public IResourceModule getAssociatedResourceModule(IModularizedSystem modularizedSystem) {

    //
    return ((ModularizedSystem) modularizedSystem).getAssociatedResourceModule(this);
  }

  public IResource getResource() {
    return _resource;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  public void setResource(Resource resource) {
    _resource = resource;
  }

  @Override
  public int compareTo(IResource other) {

    if (!getContentId().equals(other.getContentId())) {
      return getContentId().compareTo(other.getContentId());
    }
    if (!getRoot().equals(other.getRoot())) {
      return getRoot().compareTo(other.getRoot());
    }
    if (!getPath().equals(other.getPath())) {
      return getPath().compareTo(other.getPath());
    }

    return 0;
  }

  @Override
  public Set<? extends IReference> getReferences() {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.getReferences();
  }

  @Override
  public Set<? extends IType> getContainedTypes() {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.getContainedTypes();
  }

  @Override
  public IType getContainedType() throws CoreException {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.getContainedType();
  }

  @Override
  public boolean containsTypes() {

    //
    if (_resource == null) {
      // TODO
      throw new RuntimeException();
    }

    return _resource.containsTypes();
  }

  @Override
  public Set<? extends IResource> getStickyResources() {

    //
    if (_resource == null) {
      throw new RuntimeException();
    }

    //
    if (_resource.getStickyResources().isEmpty()) {
      return Collections.emptySet();
    }

    // lazy init
    if (_stickyResourceStandins == null) {

      // create new set
      _stickyResourceStandins = new HashSet<IResource>();

      // add resource standins
      for (IResource resource : _resource.getStickyResources()) {
        _stickyResourceStandins.add(((Resource) resource).getResourceStandin());
      }
    }

    return _stickyResourceStandins;
  }
}
