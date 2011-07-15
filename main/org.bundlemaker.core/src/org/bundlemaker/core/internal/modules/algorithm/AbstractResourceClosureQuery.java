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
package org.bundlemaker.core.internal.modules.algorithm;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 */
public abstract class AbstractResourceClosureQuery {

  /** - */
  private Set<IResource>    _resources;

  /** - */
  private ModularizedSystem _modularizedSystem;

  /**
   * <p>
   * </p>
   */
  public AbstractResourceClosureQuery(ModularizedSystem modularizedSystem) {

    Assert.isNotNull(modularizedSystem);

    _modularizedSystem = modularizedSystem;

    _resources = new HashSet<IResource>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected ModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IResource> getResources() {
    return _resources;
  }
}
