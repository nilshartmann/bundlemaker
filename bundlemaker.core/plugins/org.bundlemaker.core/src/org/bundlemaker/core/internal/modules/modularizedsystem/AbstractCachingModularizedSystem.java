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
package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implements the caching of types and resources.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCachingModularizedSystem extends AbstractTransformationAwareModularizedSystem {

  /** resource -> resource module */
  private GenericCache<IModuleResource, Set<IModule>> _resourceToResourceModuleCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractCachingModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param project
   */
  public AbstractCachingModularizedSystem(String name, IModuleAwareBundleMakerProject project) {

    // call the super constructor
    super(name, project);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final GenericCache<IModuleResource, Set<IModule>> getResourceToResourceModuleCache() {

    //
    if (_resourceToResourceModuleCache == null) {

      // create _resourceToResourceModuleCache
      _resourceToResourceModuleCache = new GenericCache<IModuleResource, Set<IModule>>() {
        @Override
        protected Set<IModule> create(IModuleResource resource) {
          return new HashSet<IModule>();
        }
      };
    }

    //
    return _resourceToResourceModuleCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preApplyTransformations() {

    // clear all the caches
    getResourceToResourceModuleCache().clear();

    this.adaptAs(ITypeModularizedSystem.class).clearCaches();
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param resourceModule
   * @param action
   */
  public void movableUnitChanged(IMovableUnit movableUnit, IModule resourceModule, ChangeAction action) {

    for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
      internalResourceChanged(moduleResource, resourceModule, action);
    }

    if (movableUnit.hasAssociatedSourceResource()) {
      internalResourceChanged(movableUnit.getAssociatedSourceResource(), resourceModule, action);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void resourceModuleAdded(IModifiableModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    getListenerList().fireModuleChanged(resourceModule, ChangeAction.ADDED);

    //
    for (IMovableUnit movableUnit : resourceModule.getMovableUnits()) {
      movableUnitChanged(movableUnit, resourceModule, ChangeAction.ADDED);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void resourceModuleRemoved(IModifiableModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    for (IMovableUnit movableUnit : resourceModule.getMovableUnits()) {
      movableUnitChanged(movableUnit, resourceModule, ChangeAction.REMOVED);
    }

    //
    getListenerList().fireModuleChanged(resourceModule, ChangeAction.REMOVED);
  }

  @Override
  protected void groupAdded(Group group) {
    Assert.isNotNull(group);

    //
    getListenerList().fireGroupChanged(group, ChangeAction.ADDED);
  }

  @Override
  protected void groupRemoved(Group group) {
    Assert.isNotNull(group);

    //
    getListenerList().fireGroupChanged(group, ChangeAction.REMOVED);
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public IModule getAssociatedResourceModule(IModuleResource resource) {

    Assert.isNotNull(resource);

    if (resource instanceof Resource) {
      resource = ((Resource) resource).getResourceStandin();
    }

    //
    Set<IModule> resourceModules = _resourceToResourceModuleCache.get(resource);

    //
    if (resourceModules == null || resourceModules.isEmpty()) {
      return null;
    } else if (resourceModules.size() > 1) {
      throw new RuntimeException(String.format("Resource '%s' is contained in multiple ResourceModules: %s.", resource,
          resourceModules));
    } else {
      return resourceModules.toArray(new IModule[0])[0];
    }
  }

  private void internalResourceChanged(IModuleResource resource, IModule resourceModule, ChangeAction action) {

    // step 1: add/remove to resource map
    switch (action) {
    case ADDED: {
      _resourceToResourceModuleCache.getOrCreate(resource).add(resourceModule);
      break;
    }
    case REMOVED: {
      Set<IModule> resourceModules = _resourceToResourceModuleCache.get(resource);
      if (resourceModules != null) {
        resourceModules.remove(resourceModule);
        if (resourceModules.isEmpty()) {
          _resourceToResourceModuleCache.remove(resource);
        }
      }
      break;
    }
    default: {
      throw new RuntimeException(String.format("Unkown ChangeAction '%s'!", action));
    }
    }

    // step 2: cache the contained types
    this.adaptAs(ITypeModularizedSystem.class).typesChanged(resource.adaptAs(ITypeResource.class).getContainedTypes(),
        resourceModule,
        action);
  }
}
