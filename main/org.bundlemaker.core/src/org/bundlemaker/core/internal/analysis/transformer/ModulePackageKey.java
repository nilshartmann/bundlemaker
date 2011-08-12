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

import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache.ModuleKey;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Encapsulates a package within a module.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModulePackageKey {

  /** the module key */
  private ModuleKey _moduleKey;

  /** the package name */
  private String    _packageName;

  /**
   * <p>
   * Creates a new instance of type {@link ModulePackageKey}.
   * </p>
   * 
   * @param moduleKey
   * @param packageName
   */
  public ModulePackageKey(ModuleKey moduleKey, String packageName) {
    Assert.isNotNull(moduleKey);
    Assert.isNotNull(packageName);

    _moduleKey = moduleKey;
    _packageName = packageName;
  }

  /**
   * <p>
   * Returns the module key.
   * </p>
   * 
   * @return
   */
  public ModuleKey getModuleKey() {
    return _moduleKey;
  }

  /**
   * <p>
   * Returns the name of the package.
   * </p>
   * 
   * @return the name of the package.
   */
  public String getPackageName() {
    return _packageName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_packageName == null) ? 0 : _packageName.hashCode());
    result = prime * result + ((_moduleKey == null) ? 0 : _moduleKey.hashCode());
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
    ModulePackageKey other = (ModulePackageKey) obj;
    if (_packageName == null) {
      if (other._packageName != null)
        return false;
    } else if (!_packageName.equals(other._packageName))
      return false;
    if (_moduleKey == null) {
      if (other._moduleKey != null)
        return false;
    } else if (!_moduleKey.equals(other._moduleKey))
      return false;
    return true;
  }
}
