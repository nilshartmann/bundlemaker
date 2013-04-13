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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.event.ClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.GroupChangedEvent;
import org.bundlemaker.core.internal.modules.event.IModularizedSystemChangedListener;
import org.bundlemaker.core.internal.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleIdentifierChangedEvent;
import org.bundlemaker.core.internal.modules.event.ModuleMovedEvent;
import org.bundlemaker.core.internal.modules.event.MovableUnitMovedEvent;
import org.bundlemaker.core.internal.modules.modifiable.IModifiableModule;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IMovableUnit;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implements the caching of types and resources.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCachingModularizedSystem extends AbstractTransformationAwareModularizedSystem {

  /** type name -> type */
  private GenericCache<String, Set<IType>>        _typeNameToTypeCache;

  /** type name -> referring type */
  private GenericCache<String, Set<IType>>        _typeNameToReferringCache;

  /** resource -> resource module */
  private GenericCache<IResource, Set<IModule>>   _resourceToResourceModuleCache;

  /** type -> module */
  private GenericCache<IType, Set<IModule>>       _typeToModuleCache;

  /** - */
  private List<IModularizedSystemChangedListener> _changedListeners;

  /** - */
  private boolean                                 _isModelModifiedNotificationDisabled = false;

  /** - */
  private boolean                                 _handleModelModification             = true;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractCachingModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractCachingModularizedSystem(String name, IProjectDescription projectDescription) {

    // call the super constructor
    super(name, projectDescription);

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
  protected final GenericCache<IResource, Set<IModule>> getResourceToResourceModuleCache() {

    //
    if (_resourceToResourceModuleCache == null) {

      // create _resourceToResourceModuleCache
      _resourceToResourceModuleCache = new GenericCache<IResource, Set<IModule>>() {
        @Override
        protected Set<IModule> create(IResource resource) {
          return new HashSet<IModule>();
        }
      };
    }

    //
    return _resourceToResourceModuleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO set to protected
  public final GenericCache<String, Set<IType>> getTypeNameToTypeCache() {

    //
    if (_typeNameToTypeCache == null) {

      // create _typeNameToTypeCache
      _typeNameToTypeCache = new GenericCache<String, Set<IType>>() {
        @Override
        protected Set<IType> create(String key) {
          return new HashSet<IType>();
        }
      };
    }

    //
    return _typeNameToTypeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Set<IType>> getReferencedTypes() {
    return Collections.unmodifiableMap(_typeNameToReferringCache);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO: incremental updates - replace with API
  public GenericCache<String, Set<IType>> getTypeNameToReferringCache() {

    //
    if (_typeNameToReferringCache == null) {

      // create _typeNameToReferringCache
      _typeNameToReferringCache = new GenericCache<String, Set<IType>>() {
        @Override
        protected Set<IType> create(String key) {
          return new HashSet<IType>();
        }
      };
    }

    //
    return _typeNameToReferringCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final GenericCache<IType, Set<IModule>> getTypeToModuleCache() {

    //
    if (_typeToModuleCache == null) {

      // create _typeToModuleCache
      _typeToModuleCache = new GenericCache<IType, Set<IModule>>() {
        @Override
        protected Set<IModule> create(IType type) {
          return new HashSet<IModule>();
        }
      };
    }

    return _typeToModuleCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preApplyTransformations() {

    // clear all the caches
    getTypeNameToTypeCache().clear();
    getTypeNameToReferringCache().clear();
    getResourceToResourceModuleCache().clear();
    getTypeToModuleCache().clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @param resourceModule
   * @param action
   */
  public void resourcesChanged(Collection<? extends IResource> resources, IModule resourceModule,
      ChangeAction action) {

    // iterate over all the resources...
    for (IResource resource : resources) {

      // ... and handle them
      internalResourceChanged(resource, resourceModule, action);
    }

    //
    // fireEvent(resources, resourceModule, action);
  }

  /**
   * <p>
   * </p>
   * 
   * @param types
   * @param module
   * @param action
   */
  protected void typesChanged(Collection<IType> types, IModule module, ChangeAction action) {
    //
    for (IType type : types) {
      internalTypeChanged(type, module, action);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @param resourceModule
   * @param action
   */
  public void resourceChanged(IResource resource, IModule resourceModule, ChangeAction action) {
    internalResourceChanged(resource, resourceModule, action);
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param module
   * @param action
   */
  public void typeChanged(IType type, IModule module, ChangeAction action) {

    //
    internalTypeChanged(type, module, action);
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
    for (IResource resource : resourceModule.getResources(ProjectContentType.SOURCE)) {
      internalResourceChanged(resource, resourceModule, ChangeAction.ADDED);

      //
      for (IType type : resource.getContainedTypes()) {
        internalTypeChanged(type, resourceModule, ChangeAction.ADDED);
      }
    }

    //
    for (IResource resource : resourceModule.getResources(ProjectContentType.BINARY)) {
      internalResourceChanged(resource, resourceModule, ChangeAction.ADDED);

      //
      for (IType type : resource.getContainedTypes()) {
        internalTypeChanged(type, resourceModule, ChangeAction.ADDED);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void resourceModuleRemoved(IModifiableModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    for (IResource resource : resourceModule.getResources(ProjectContentType.SOURCE)) {
      internalResourceChanged(resource, resourceModule, ChangeAction.REMOVED);

      //
      for (IType type : resource.getContainedTypes()) {
        internalTypeChanged(type, resourceModule, ChangeAction.REMOVED);
      }
    }

    //
    for (IResource resource : resourceModule.getResources(ProjectContentType.BINARY)) {
      internalResourceChanged(resource, resourceModule, ChangeAction.REMOVED);

      //
      for (IType type : resource.getContainedTypes()) {
        internalTypeChanged(type, resourceModule, ChangeAction.REMOVED);
      }
    }

    //
    fireModuleChanged(resourceModule, ChangeAction.REMOVED);
  }

  // @Override
  // protected void typeModuleAdded(IModule module) {
  // Assert.isNotNull(module);
  //
  // for (IType type : module.getContainedTypes()) {
  // internalTypeChanged(type, module, ChangeAction.ADDED);
  // }
  // }
  //
  // @Override
  // protected void typeModuleRemoved(TypeModule module) {
  // Assert.isNotNull(module);
  //
  // for (IType type : module.getContainedTypes()) {
  // internalTypeChanged(type, module, ChangeAction.REMOVED);
  // }
  // }

  @Override
  protected void groupAdded(IGroup group) {
    Assert.isNotNull(group);

    //
    fireGroupChanged(group, ChangeAction.ADDED);
  }

  @Override
  protected void groupRemoved(IGroup group) {
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
  public IModule getAssociatedResourceModule(IResource resource) {

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
   * @param type
   * @return
   */
  public IModule getAssociatedModule(IType type) {

    //
    Assert.isNotNull(type);

    //
    Set<IModule> modules = _typeToModuleCache.get(type);

    //
    if (modules == null || modules.isEmpty()) {
      return null;
    } else if (modules.size() > 1) {
      throw new RuntimeException("Type is contained in multiple modules.");
    } else {
      return modules.toArray(new IModule[0])[0];
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
  private void fireGroupChanged(IGroup group, ChangeAction added) {
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

  private void internalResourceChanged(IResource resource, IModule resourceModule, ChangeAction action) {

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
    typesChanged(resource.getContainedTypes(), resourceModule, action);
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

  private void internalTypeChanged(IType type, IModule module, ChangeAction action) {
    switch (action) {
    case ADDED: {

      // step 1: type -> module
      _typeToModuleCache.getOrCreate(type).add(module);

      // step 2: type name -> type
      _typeNameToTypeCache.getOrCreate(type.getFullyQualifiedName()).add(type);

      // step 3: referenced type name -> type
      for (IReference reference : type.getReferences()) {
        _typeNameToReferringCache.getOrCreate(reference.getFullyQualifiedName()).add(type);
      }

      //
      break;
    }
    case REMOVED: {

      // step 2a: type -> module
      Set<IModule> typeModules = _typeToModuleCache.get(type);
      if (typeModules != null) {
        typeModules.remove(module);
        if (typeModules.isEmpty()) {
          _typeToModuleCache.remove(type);

          // step 2b: type name -> type
          Set<IType> types = _typeNameToTypeCache.get(type.getFullyQualifiedName());
          if (types != null) {

            // remove the type
            types.remove(type);

            // remove types if empty
            if (types.isEmpty()) {
              _typeNameToTypeCache.remove(type.getFullyQualifiedName());
            }
          }

          // step 2c: referenced type name -> type
          for (IReference reference : type.getReferences()) {
            Set<IType> referredTypes = _typeNameToReferringCache.get(reference.getFullyQualifiedName());
            if (referredTypes != null) {
              // remove the referred type
              referredTypes.remove(type);
              // remove referred types if empty
              if (referredTypes.isEmpty()) {
                _typeNameToReferringCache.remove(reference.getFullyQualifiedName());
              }
            }
          }
        }
      }

      break;
    }

    default: {
      throw new RuntimeException(String.format("Unkown ChangeAction '%s'!", action));
    }
    }
  }
}
