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

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.internal.analysis.AbstractAdvancedContainer;
import org.bundlemaker.core.internal.analysis.AdapterModularizedSystem2IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implements an cache for artifacts.
 * </p>
 */
public abstract class AbstractArtifactCache {

  /** the root artifact */
  private AbstractAdvancedContainer _rootArtifact;

  /** the modularized system */
  private IModularizedSystem        _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   *          the modularized system
   */
  public AbstractArtifactCache(IModifiableModularizedSystem modularizedSystem) {
    this(modularizedSystem, new AdapterModularizedSystem2IArtifact(modularizedSystem));
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   *          the modularized system
   * @param rootArtifact
   *          the root artifact
   */
  protected AbstractArtifactCache(IModularizedSystem modularizedSystem, AbstractAdvancedContainer rootArtifact) {

    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(rootArtifact);

    // set the modularized system
    _modularizedSystem = modularizedSystem;

    // create the root artifact
    _rootArtifact = rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final AbstractAdvancedContainer getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public final IAdvancedArtifact transform() throws CoreException {
    return transform(_modularizedSystem.getAllModules().toArray(new IModule[0]));
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public final boolean handleAsPrimaryType(IType type) {

    // if the type does not has a source resource,
    // handle the type as primary type
    if (!type.hasSourceResource()) {
      return true;
    }

    //
    IResource sourceResource = type.getSourceResource();

    // if the source resource does not contain a primary type,
    // handle the non primary type as a primary type
    if (!sourceResource.hasPrimaryType()) {
      return true;
    }

    //
    return sourceResource.isPrimaryType(type);
  }

  /**
   * <p>
   * </p>
   * 
   * @param modules
   * @return
   */
  protected abstract IAdvancedArtifact transform(IModule[] modules);
}
