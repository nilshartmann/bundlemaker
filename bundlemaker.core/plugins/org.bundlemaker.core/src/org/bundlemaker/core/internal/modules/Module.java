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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.internal.modules.event.ModuleClassificationChangedEvent;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.resource.ModuleIdentifier;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;

/**
 * <p>
 * Abstract base class for all modules.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Module implements IModifiableModule {

  /** the module identifier */
  private IModuleIdentifier   _moduleIdentifier;

  /** the classification */
  private Group               _classification;

  /** the user attributes */
  private Map<String, Object> _userAttributes;

  /** the modularized system the module belongs to */
  private IModularizedSystem  _modularizedSystem;

  /** specified whether or not the module is attached to a modularized system */
  private boolean             _isDetached;

  /** - */
  private MovableUnitSet      _movableUnitSet;

  /** - */
  private boolean             _isResourceModule;

  /**
   * <p>
   * Creates a new instance of type {@link Module}.
   * </p>
   * 
   * @param moduleIdentifier
   * @param modularizedSystem
   *          TODO
   * @param selfContainer
   */
  public Module(IModuleIdentifier moduleIdentifier, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(moduleIdentifier);
    Assert.isNotNull(modularizedSystem);

    // set the parameters
    _moduleIdentifier = moduleIdentifier;
    _modularizedSystem = modularizedSystem;

    // create the hash map
    _userAttributes = new HashMap<String, Object>();

    // create the resource sets
    _movableUnitSet = new MovableUnitSet();

    //
    _isResourceModule = true;
  }

  @Override
  public Object getAdapter(Class adapter) {
    return adaptAs(adapter);
  }

  @Override
  public <T> T adaptAs(Class<T> clazz) {

    //
    T result = (T) Platform.getAdapterManager().getAdapter(this, clazz);
    if (result != null) {
      return result;
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResourceModule() {
    return _isResourceModule;
  }

  /**
   * <p>
   * </p>
   * 
   * @param isResourceModule
   */
  public void setResourceModule(boolean isResourceModule) {
    _isResourceModule = isResourceModule;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleIdentifier getModuleIdentifier() {
    return _moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getClassification() {
    return _classification != null ? _classification.getPath() : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasClassification() {
    return _classification != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return _isDetached ? null : _modularizedSystem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasModularizedSystem() {
    return !_isDetached;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getUserAttributes() {
    return _userAttributes;
  }

  public Group getClassificationGroup() {
    return _classification;
  }

  public final void setModuleIdentifier(String name, String version) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);

    setModuleIdentifier(new ModuleIdentifier(name, version));
  }

  public final void setModuleIdentifier(IModuleIdentifier moduleIdentifier) {
    Assert.isNotNull(moduleIdentifier);

    _moduleIdentifier = moduleIdentifier;

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem()).fireModuleIdentifierChanged(this);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param classificationPath
   */
  public void setClassification(IPath classificationPath) {

    //
    if (classificationPath == null || classificationPath.isEmpty()) {
      _classification = null;
    }

    //
    else {

      _classification = ((AbstractTransformationAwareModularizedSystem) getModularizedSystem())
          .getOrCreateGroup(classificationPath);
    }

    //
    if (hasModularizedSystem()) {
      ((ModularizedSystem) getModularizedSystem())
          .fireModuleClassificationChanged(new ModuleClassificationChangedEvent(this));
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void detach() {
    _isDetached = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  public void attach(IModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    Assert.isTrue(modularizedSystem.equals(_modularizedSystem),
        "You can only add a module to the modularized system you specified when creating the module.");

    //
    _isDetached = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    //
    _movableUnitSet.addMovableUnit(movableUnit);

    ((IModifiableModularizedSystem) getModularizedSystem()).movableUnitChanged(movableUnit,
        this, ChangeAction.ADDED);

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, this, ChangeAction.ADDED);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMovableUnit(IMovableUnit movableUnit) {

    //
    _movableUnitSet.removeMovableUnit(movableUnit);

    //
    ((IModifiableModularizedSystem) getModularizedSystem()).movableUnitChanged(movableUnit,
        this, ChangeAction.REMOVED);

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, this, ChangeAction.REMOVED);
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + " [_moduleIdentifier=" + _moduleIdentifier + ", _classification="
        + _classification + "]";
  }

  // TODO
  public void validate() {

    //
    Map<String, IModuleResource> entries = new HashMap<String, IModuleResource>();

    //
    for (IModuleResource resource : getResources(ResourceType.SOURCE)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }

    //
    entries.clear();
    for (IModuleResource resource : getResources(ResourceType.BINARY)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }
  }

  /********************************************************/
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResource(String resourceType, ResourceType contentType) {
    return getResource(resourceType, contentType) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModuleResource getResource(String path, ResourceType contentType) {

    //
    return getResource(path, contentType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IModuleResource> getResources(ResourceType contentType) {

    //
    return _movableUnitSet.getResources(contentType);
  }

  @Override
  public boolean containsSources() {
    return !getResources(ResourceType.SOURCE).isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends IMovableUnit> getMovableUnits() {
    return _movableUnitSet.getMovableUnits();
  }

  // /**
  // * {@inheritDoc}
  // */
  // private void add(IResourceStandin resource, ResourceType contentType) {
  //
  // Assert.isNotNull(resource);
  // Assert.isNotNull(contentType);
  //
  // // add the resource to the resource set...
  // getModifiableResourcesSet(contentType).add(resource);
  //
  // // ... and add all contained types to the cache
  // resourceAdded(resource);
  //
  // // notify
  // if (hasModularizedSystem()) {
  // ((AbstractCachingModularizedSystem) getModularizedSystem()).resourceChanged(resource,
  // this, ChangeAction.ADDED);
  // }
  // }

  // /**
  // * {@inheritDoc}
  // */
  // @Deprecated
  // @Override
  // public void addAll(Set<IResourceStandin> resources, ResourceType contentType) {
  //
  // Assert.isNotNull(resources);
  // Assert.isNotNull(contentType);
  //
  // // add the resource to the resource set...
  // getModifiableResourcesSet(contentType).addAll(resources);
  //
  // // ... and add all contained types to the cache
  // for (IModuleResource resource : resources) {
  // resourceAdded(resource);
  // }
  //
  // // notify
  // if (hasModularizedSystem()) {
  // ((IModifiableModularizedSystem) getModularizedSystem()).resourcesChanged(resources,
  // this, ChangeAction.ADDED);
  // }
  // }

  // /**
  // * {@inheritDoc}
  // */
  // private void remove(IModuleResource resource, ResourceType contentType) {
  //
  // Assert.isNotNull(resource);
  // Assert.isNotNull(contentType);
  //
  // //
  // if (getModifiableResourcesSet(contentType).contains(resource)) {
  //
  // resourceRemoved(resource);
  //
  // // add the resource to the resource set...
  // getModifiableResourcesSet(contentType).remove(resource);
  //
  // // notify
  // if (hasModularizedSystem()) {
  // ((AbstractCachingModularizedSystem) getModularizedSystem()).resourceChanged(resource,
  // this, ChangeAction.REMOVED);
  // }
  // }
  // }

  // /**
  // * {@inheritDoc}
  // */
  // private void removeAll(Collection<? extends IModuleResource> resources, ResourceType contentType) {
  //
  // Assert.isNotNull(resources);
  // Assert.isNotNull(contentType);
  //
  // // ... and add all contained types to the cache
  // for (IModuleResource resource : resources) {
  // resourceRemoved(resource);
  // }
  //
  // // add the resource to the resource set...
  // getModifiableResourcesSet(contentType).removeAll(resources);
  //
  // // notify
  // if (hasModularizedSystem()) {
  // ((AbstractCachingModularizedSystem) getModularizedSystem()).resourcesChanged(resources,
  // this, ChangeAction.REMOVED);
  // }
  // }
}
