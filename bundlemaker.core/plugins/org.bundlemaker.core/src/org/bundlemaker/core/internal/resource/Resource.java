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

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core._type.internal.TypeResource;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IParsableResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Resource extends DefaultProjectContentResource implements IParsableResource {

  // TODO
  private ITypeResource             _typeResource;

  /** - */
  private Set<IModuleResource>      _stickyResources;

  /** - */
  private boolean                   _erroneous;

  /** - */
  private long                      _lastTimestamp;

  /**  */
  private transient ResourceStandin _resourceStandin;

  /** - */
  private transient MovableUnit     _movableUnit;

  /**
   * <p>
   * Creates a new instance of type {@link Resource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public Resource(String contentId, String root, String path, ResourceCache resourceCache) {
    super(contentId, root, path, resourceCache.getFlyWeightCache());

    Assert.isNotNull(resourceCache);

    _typeResource = new TypeResource(resourceCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    if (ITypeResource.class.equals(clazz)) {
      return (T) _typeResource;
    }

    //
    if (IParsableTypeResource.class.equals(clazz)) {
      return (T) _typeResource;
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAdapter(Class adapter) {
    return adaptAs(adapter);
  }

  /**
   * <p>
   * Creates a new instance of type {@link Resource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public Resource(String contentId, String root, String path) {
    super(contentId, root, path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getLastParsedTimestamp() {
    return _lastTimestamp;
  }

  /**
   * <p>
   * </p>
   */
  public void storeCurrentTimestamp() {
    _lastTimestamp = getCurrentTimestamp();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IMovableUnit getMovableUnit(IModularizedSystem modularizedSystem) {

    //
    Assert.isNotNull(modularizedSystem);

    //
    if (_movableUnit == null) {
      _movableUnit = MovableUnit.createFromResource(this, modularizedSystem);
    }

    //
    return _movableUnit;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setErroneous(boolean erroneous) {
    _erroneous = erroneous;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IModuleResource> getStickyResources() {
    Set<? extends IModuleResource> result = stickyResources();
    return Collections.unmodifiableSet(result);
  }

  @Override
  public void addStickyResource(IModuleResource stickyResource) {
    stickyResources().add(stickyResource);
  }

  /**
   * @param resourceStandin
   */
  public void setResourceStandin(ResourceStandin resourceStandin) {
    _resourceStandin = resourceStandin;
  }

  public ResourceStandin getResourceStandin() {
    return _resourceStandin;
  }

  @Override
  public IModule getModule(IModularizedSystem modularizedSystem) {

    //
    if (_resourceStandin == null) {
      throw new RuntimeException();
    }

    //
    return ((ModularizedSystem) modularizedSystem).getAssociatedResourceModule(this);
  }

  @Override
  public int compareTo(IModuleResource arg0) {

    //
    if (_resourceStandin == null) {
      throw new RuntimeException();
    }

    return _resourceStandin.compareTo(arg0);
  }

  private Set<IModuleResource> stickyResources() {

    if (_stickyResources == null) {
      _stickyResources = new HashSet<IModuleResource>();
    }

    return _stickyResources;
  }

  public boolean isErroneous() {
    return _erroneous;
  }
}
