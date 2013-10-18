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

import org.bundlemaker.core.common.FlyWeightStringCache;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.project.internal.DefaultProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.parser.IParsableResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Resource extends DefaultProjectContentResource implements IParsableResource {

  //
  private Object                    _modelExtension;

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
  public Resource(String contentId, String root, String path, FlyWeightStringCache flyWeightStringCache) {
    super(contentId, root, path, flyWeightStringCache);
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
   * <p>
   * </p>
   * 
   * @return the modelExtension
   */
  public Object getModelExtension() {
    return _modelExtension;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modelExtension
   *          the modelExtension to set
   */
  public void addResourceModelExtension(Object modelExtension) {

    // TODO allow more than one extensions
    if (modelExtension != null && _modelExtension != null) {
      throw new RuntimeException();
    }

    _modelExtension = modelExtension;
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
  public IMovableUnit getMovableUnit() {
    return _movableUnit;
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   *          the movableUnit to set
   */
  public void setMovableUnit(MovableUnit movableUnit) {
    _movableUnit = movableUnit;
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
