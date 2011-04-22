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

/**
 *
 */
public class ModuleResourceKey {

  /** - */
  private IResourceModule _resourceModule;

  /** - */
  private String          _resourcePath;

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param resource
   */
  public ModuleResourceKey(IResourceModule resourceModule, String resource) {

    _resourceModule = resourceModule;
    _resourcePath = resource;
  }

  public IResourceModule getResourceModule() {
    return _resourceModule;
  }

  public String getResourcePath() {
    return _resourcePath;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_resourcePath == null) ? 0 : _resourcePath.hashCode());
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
    if (_resourcePath == null) {
      if (other._resourcePath != null)
        return false;
    } else if (!_resourcePath.equals(other._resourcePath))
      return false;
    if (_resourceModule == null) {
      if (other._resourceModule != null)
        return false;
    } else if (!_resourceModule.equals(other._resourceModule))
      return false;
    return true;
  }
}
