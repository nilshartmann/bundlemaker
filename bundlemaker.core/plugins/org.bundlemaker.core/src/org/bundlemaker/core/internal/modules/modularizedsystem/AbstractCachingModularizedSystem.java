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
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.event.ClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.GroupChangedEvent;
import org.bundlemaker.core.internal.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.internal.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleIdentifierChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleMovedEvent;
import org.bundlemaker.core.internal.modules.event.MovableUnitMovedEvent;
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

  /** - */
  private List<IModularizedSystemChangedListener>     _changedListeners;

  /** - */
  private boolean                                     _isModelModifiedNotificationDisabled = false;

  /** - */
  private boolean                                     _handleModelModification             = true;

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

    //
    _changedListeners = new CopyOnWriteArrayList<IModularizedSystemChangedListener>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addModularizedSystemChangedListener(IModularizedSystemChangedListener listener) {

    //
    if (listener != null && !_changedListeners.contains(listener)) {
      _changedListeners.add(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeModularizedSystemChangedListener(IModularizedSystemChangedListener listener) {

    //
    if (_changedListeners.contains(listener)) {
      _changedListeners.remove(listener);
    }
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

  // /**
  // * <p>
  // * </p>
  // *
  // * @param resources
  // * @param resourceModule
  // * @param action
  // */
  // public void resourcesChanged(Collection<? extends IModuleResource> resources, IModule resourceModule,
  // ChangeAction action) {
  //
  // // iterate over all the resources...
  // for (IModuleResource resource : resources) {
  //
  // // ... and handle them
  // internalResourceChanged(resource, resourceModule, action);
  // }
  //
  // //
  // // fireEvent(resources, resourceModule, action);
  // }

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
    fireModuleChanged(resourceModule, ChangeAction.ADDED);

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
    fireModuleChanged(resourceModule, ChangeAction.REMOVED);
  }

  @Override
  protected void groupAdded(Group group) {
    Assert.isNotNull(group);

    //
    fireGroupChanged(group, ChangeAction.ADDED);
  }

  @Override
  protected void groupRemoved(Group group) {
    Assert.isNotNull(group);

    //
    fireGroupChanged(group, ChangeAction.REMOVED);
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

  /**
   * <p>
   * </p>
   * 
   * @param itemChanged
   * @param module
   * @param changeAction
   */
  public void fireMovableUnitEvent(IMovableUnit movableUnit, IModule module, ChangeAction changeAction) {

    MovableUnitMovedEvent event = new MovableUnitMovedEvent(movableUnit, module, changeAction);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {

      switch (changeAction) {
      case ADDED:
        listener.movableUnitAdded(event);
        break;
      case REMOVED:
        listener.movableUnitRemoved(event);
        break;
      default:
        break;
      }
    }
  }

  /**
   * <p>
   * </p>
   */
  public void fireModuleChanged(IModule module, ChangeAction changeAction) {

    //
    ModuleMovedEvent event = new ModuleMovedEvent(module, changeAction);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {

      if (ChangeAction.ADDED.equals(changeAction)) {
        listener.moduleAdded(event);
      } else if (ChangeAction.REMOVED.equals(changeAction)) {
        listener.moduleRemoved(event);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param module
   */
  public void fireModuleIdentifierChanged(IModule module) {

    //
    ModuleIdentifierChangedEvent event = new ModuleIdentifierChangedEvent(module);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.moduleIdentifierChanged(event);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param group
   * @param added
   */
  private void fireGroupChanged(Group group, ChangeAction added) {
    Assert.isNotNull(group);
    Assert.isNotNull(added);

    //
    GroupChangedEvent event = new GroupChangedEvent(group, added);

    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {

      if (ChangeAction.ADDED.equals(added)) {
        listener.groupAdded(event);
      } else if (ChangeAction.REMOVED.equals(added)) {
        listener.groupRemoved(event);
      }
    }

  }

  /**
   * <p>
   * </p>
   * 
   */
  public void fireModuleClassificationChanged(ModuleClassificationChangedEvent event) {
    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.moduleClassificationChanged(event);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void fireClassificationChanged(ClassificationChangedEvent event) {
    //
    for (IModularizedSystemChangedListener listener : _changedListeners) {
      listener.classificationChanged(event);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void disableModelModifiedNotification(boolean isDisabled) {

    //
    boolean fireImmediately = !isDisabled && _isModelModifiedNotificationDisabled;

    //
    _isModelModifiedNotificationDisabled = isDisabled;

    //
    for (IModularizedSystemChangedListener modularizedSystemChangedListener : _changedListeners) {
      modularizedSystemChangedListener.modelModifiedNotificationDisabled(isDisabled);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isModelModifiedNotificationDisabled() {
    return _isModelModifiedNotificationDisabled;
  }

  @Override
  public boolean isHandleModelModification() {
    return _handleModelModification;
  }

  @Override
  public void setHandleModelModification(boolean handleModelModification) {

    if (!_handleModelModification && handleModelModification) {
      _handleModelModification = handleModelModification;

      //
      for (IModularizedSystemChangedListener modularizedSystemChangedListener : _changedListeners) {
        modularizedSystemChangedListener.handleModelModification();
      }

    } else {
      _handleModelModification = handleModelModification;
    }
  }
}
