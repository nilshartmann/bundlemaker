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
package org.bundlemaker.core.internal.analysis.transformer;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Encapsulates a resource within a module.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleResourceKey {

  /** the resource module */
  private IResourceModule _resourceModule;

  /** the resource */
  private IResource       _resource;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleResourceKey}.
   * </p>
   * 
   * @param resourceModule
   *          the resource module
   * @param resource
   *          the resource
   */
  public ModuleResourceKey(IResourceModule resourceModule, IResource resource) {
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(resource);

    _resourceModule = resourceModule;
    _resource = resource;
  }

  /**
   * <p>
   * Returns the resource module.
   * </p>
   * 
   * @return the resource module.
   */
  public IResourceModule getResourceModule() {
    return _resourceModule;
  }

  /**
   * <p>
   * Returns the resource.
   * </p>
   * 
   * @return the resource.
   */
  public IResource getResource() {
    return _resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_resource == null) ? 0 : _resource.hashCode());
    result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ModuleResourceKey other = (ModuleResourceKey) obj;
    if (_resource == null) {
      if (other._resource != null)
        return false;
    } else if (!_resource.equals(other._resource))
      return false;
    if (_resourceModule == null) {
      if (other._resourceModule != null)
        return false;
    } else if (!_resourceModule.equals(other._resourceModule))
      return false;
    return true;
  }
}
