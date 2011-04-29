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
package org.bundlemaker.core.analysis.internal.transformer;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;

/**
 *
 */
public class ModuleResourceKey {

  /** - */
  private IResourceModule _resourceModule;

  /** - */
  private IResource       _resource;

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resource
   */
  public ModuleResourceKey(IResourceModule resourceModule, IResource resource) {
    _resourceModule = resourceModule;
    _resource = resource;
  }

  public IResourceModule getResourceModule() {
    return _resourceModule;
  }

  public IResource getResource() {
    return _resource;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_resource == null) ? 0 : _resource.hashCode());
    result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
    return result;
  }

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
