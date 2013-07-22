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

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.Module;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.transformation.BasicProjectContentTransformation;
import org.bundlemaker.core.internal.transformation.IInternalTransformation;
import org.bundlemaker.core.internal.transformation.IUndoableTransformation;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.ITransformation;
import org.bundlemaker.core.spi.modext.ICacheCallback;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractTransformationAwareModularizedSystem extends AbstractModularizedSystem {

  /** resource -> resource module */
  private GenericCache<IModuleResource, Set<IModule>> _resourceToResourceModuleCache;

  /** - */
  private List<ICacheCallback>                        _cacheCallbacks;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractTransformationAwareModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param project
   */
  public AbstractTransformationAwareModularizedSystem(String name, IModuleAwareBundleMakerProject project) {
    super(name, project);

    //
    _cacheCallbacks = new CopyOnWriteArrayList<ICacheCallback>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void registerCacheCallback(ICacheCallback cacheCallback) {
    if (!_cacheCallbacks.contains(cacheCallback)) {
      _cacheCallbacks.add(cacheCallback);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unregisterCacheCallback(ICacheCallback cacheCallback) {
    _cacheCallbacks.remove(cacheCallback);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTransformations(IProgressMonitor progressMonitor, List<ITransformation> transformations) {

    //
    Assert.isNotNull(transformations);

    //
    if (progressMonitor == null) {
      progressMonitor = new NullProgressMonitor();
    }

    SubMonitor subMonitor = SubMonitor.convert(progressMonitor);
    subMonitor.beginTask("Transforming Module '" + getName() + "'", 100);
    _applyTransformations(subMonitor, transformations.toArray(new ITransformation[0]));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void undoTransformations(IProgressMonitor progressMonitor) {
    undoUntilTransformation(progressMonitor, null);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.modules.IModularizedSystem#undoUntilTransformation(org.eclipse.core.runtime.IProgressMonitor,
   * org.bundlemaker.core.transformation.ITransformation)
   */
  @Override
  public void undoUntilTransformation(IProgressMonitor progressMonitor, ITransformation toTransformation) {
    //
    boolean disableModelModifiedNotification = getListenerList().isModelModifiedNotificationDisabled();

    try {

      //
      getListenerList().disableModelModifiedNotification(true);

      //
      for (ITransformation transformation : getTransformations()) {
        if (!(transformation instanceof IUndoableTransformation)) {
          throw new RuntimeException("TODO");
        }
      }

      // We have to undo the transformations in reverse order
      List<ITransformation> transformationList = getModifiableTransformationList();

      while (!transformationList.isEmpty()) {
        // Get last transformation
        IUndoableTransformation undoableTransformation = (IUndoableTransformation) transformationList
            .get(transformationList.size() - 1);

        // check
        if (toTransformation != null && toTransformation.equals(undoableTransformation)) {
          break;
        }

        // undo transformation
        undoableTransformation.undo();

        // remove from transformation list
        transformationList.remove(undoableTransformation);
      }

    } finally {

      //
      getListenerList().disableModelModifiedNotification(disableModelModifiedNotification);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void undoLastTransformation() {

    // get the last transformation
    ITransformation lastTransformation = getTransformations().get(getTransformations().size() - 1);

    // check if we have an undoable transformation
    if (!(lastTransformation instanceof IUndoableTransformation)) {
      throw new RuntimeException("TODO");
    }

    // remove transformation...
    getModifiableTransformationList().remove(getTransformations().size() - 1);

    // ...undo transformation
    IUndoableTransformation undoableTransformation = (IUndoableTransformation) lastTransformation;
    undoableTransformation.undo();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void applyTransformations(IProgressMonitor progressMonitor, ITransformation... transformations) {

    //
    if (progressMonitor == null) {
      progressMonitor = new NullProgressMonitor();
    }

    SubMonitor subMonitor = SubMonitor.convert(progressMonitor);
    subMonitor.beginTask("Transforming Module '" + getName() + "'", 100);
    _applyTransformations(subMonitor, transformations);
  }

  public void initialize(IProgressMonitor progressMonitor) {

    //
    if (progressMonitor == null) {
      progressMonitor = new NullProgressMonitor();
    }

    SubMonitor subMonitor = SubMonitor.convert(progressMonitor);
    subMonitor.beginTask("Transforming Module '" + getName() + "'", 100);

    // step 1: clear prior results
    getModifiableResourceModules().clear();
    preApplyTransformations();

    subMonitor.worked(20);
  }

  /**
   * <p>
   * </p>
   * 
   * @param subMonitor
   */
  private void _applyTransformations(SubMonitor subMonitor, ITransformation... transformations) {

    // step 4: transform modules
    SubMonitor transformationMonitor = subMonitor.newChild(70);
    transformationMonitor.beginTask("Begin", transformations.length * 4);

    for (ITransformation transformation : transformations) {

      // step 4.1: apply transformation
      ((IInternalTransformation) transformation).apply((IModifiableModularizedSystem) this,
          transformationMonitor.newChild(1));

      //
      if (!(transformation instanceof BasicProjectContentTransformation)) {

        if (!getModifiableTransformationList().contains(transformation)) {
          //
          getModifiableTransformationList().add(transformation);
        }
      }

      //
      transformationMonitor.worked(1);
    }

    afterApplyTransformations();
  }

  protected void afterApplyTransformations() {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  public Group getOrCreateGroup(IPath path) {

    Assert.isNotNull(path);
    Assert.isTrue(!path.isEmpty(), "Path must not be emtpy.");

    //
    Group group = getGroup(path);
    if (group != null) {
      return group;
    }

    //
    if (path.segmentCount() == 1) {
      Group result = new Group(path.lastSegment(), null, this);
      internalGroups().add(result);
      Assert.isNotNull(result);

      //
      getListenerList().fireGroupChanged(result, ChangeAction.ADDED);
      return result;
    } else {
      Group parent = getOrCreateGroup(path.removeLastSegments(1));
      Group result = new Group(path.lastSegment(), parent, this);
      internalGroups().add(result);
      Assert.isNotNull(result);

      //
      getListenerList().fireGroupChanged(result, ChangeAction.ADDED);
      return result;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  @Override
  public void removeGroup(Group group) {

    Assert.isNotNull(group);

    internalGroups().remove(group);
    Assert.isNotNull(group);

    //
    getListenerList().fireGroupChanged(group, ChangeAction.REMOVED);
  }

  @Override
  public void removeGroup(IPath path) {

    Assert.isNotNull(path);

    Group group = getGroup(path);

    if (group == null) {
      // TODO
      throw new RuntimeException(String.format("Group '%s' does not exist.", group));
    }

    removeGroup(group);
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  public Group getGroup(IPath path) {

    // We can not use a hash map here, because it is possible to change the path of a group (which would be the key in
    // the map). So we have to iterate over all groups and find the right one...
    for (Group group : internalGroups()) {
      if (group.getPath().equals(path)) {
        return group;
      }
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModifiableModule createResourceModule(IModuleIdentifier createModuleIdentifier) {

    // create the result
    Module resourceModule = new Module(createModuleIdentifier, this);

    // add it to the internal hash map
    getModifiableResourceModules().add(resourceModule);

    // notify
    resourceModuleAdded(resourceModule);

    // return the result
    return resourceModule;
  }

  @Override
  public IModifiableModule createResourceModule(ModuleIdentifier moduleIdentifier, IPath path) {
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  @Override
  public void addModule(IModule module) {
    Assert.isNotNull(module);

    if (module instanceof IModifiableModule) {

      //
      Assert.isTrue(!hasResourceModule(module.getModuleIdentifier()));

      //
      IModifiableModule resourceModule = (IModifiableModule) module;

      //
      ((Module) resourceModule).attach(this);
      getModifiableResourceModules().add(resourceModule);

      // notify
      resourceModuleAdded(resourceModule);
    }
  }

  /**
   * {@inheritDoc}
   */

  @Override
  public void removeModule(IModuleIdentifier identifier) {
    Assert.isNotNull(identifier);

    if (hasResourceModule(identifier)) {

      // remove the entry
      Module resourceModule = (Module) getModule(identifier);
      getModifiableResourceModules().remove(resourceModule);
      resourceModule.detach();

      // notify
      resourceModuleRemoved((IModifiableModule) resourceModule);

    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeModule(IModule module) {
    Assert.isNotNull(module);

    // remove the module
    removeModule(module.getModuleIdentifier());
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
  protected void preApplyTransformations() {

    // clear all the caches
    getResourceToResourceModuleCache().clear();

    //
    for (ICacheCallback cacheCallback : _cacheCallbacks) {
      cacheCallback.clearCaches();
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
  public void movableUnitChanged(IMovableUnit movableUnit, IModule resourceModule, ChangeAction action) {

    for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
      internalResourceChanged(moduleResource, resourceModule, action);
    }

    if (movableUnit.hasAssociatedSourceResource()) {
      internalResourceChanged(movableUnit.getAssociatedSourceResource(), resourceModule, action);
    }

    switch (action) {
    case ADDED: {
      for (ICacheCallback cacheCallback : _cacheCallbacks) {
        cacheCallback.movableUnitAdded(movableUnit, resourceModule);
      }
      break;
    }
    case REMOVED: {
      for (ICacheCallback cacheCallback : _cacheCallbacks) {
        cacheCallback.movableUnitRemoved(movableUnit, resourceModule);
      }
      break;
    }
    }
  }

  /**
   * {@inheritDoc}
   */
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
  protected void resourceModuleRemoved(IModifiableModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    for (IMovableUnit movableUnit : resourceModule.getMovableUnits()) {
      movableUnitChanged(movableUnit, resourceModule, ChangeAction.REMOVED);
    }

    //
    getListenerList().fireModuleChanged(resourceModule, ChangeAction.REMOVED);
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
    }
  }
}
