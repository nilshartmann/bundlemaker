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
package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencedModulesQueryResult implements IReferencedModulesQueryResult {

  /** - */
  private IResourceModule _resourceModule;

  /** - */
  private Set<IReference> _unsatisfiedReferences;

  /** - */
  private Set<IModule>    _referencedModules;

  /**
   * <p>
   * Creates a new instance of type {@link ReferencedModulesQueryResult}.
   * </p>
   */
  public ReferencedModulesQueryResult(IResourceModule origin) {

    //
    Assert.isNotNull(origin);

    //
    _resourceModule = origin;

    //
    _unsatisfiedReferences = new HashSet<IReference>();
    _referencedModules = new HashSet<IModule>();
  }

  /**
   * {@inheritDoc}
   */
  public IResourceModule getOrigin() {
    return _resourceModule;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasUnsatisfiedReferences() {
    return !_unsatisfiedReferences.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getUnsatisfiedReferences() {
    return Collections.unmodifiableSet(_unsatisfiedReferences);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IModule> getReferencedModules() {
    return Collections.unmodifiableSet(_referencedModules);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IReference> getModifiableUnsatisfiedReferences() {
    return _unsatisfiedReferences;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<IModule> getModifiableReferencedModules() {
    return _referencedModules;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    //
    return "ReferencedModulesQueryResult [_resourceModule=" + _resourceModule + ", _unsatisfiedReferences="
        + _unsatisfiedReferences + ", _referencedModules=" + _referencedModules + "]";
  }
}
