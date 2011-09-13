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
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.TypeModule;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
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

  /**
   * <p>
   * Determines if a resource or type was added or removed from a module.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public enum ChangeAction {
    ADDED, REMOVED;
  }

  /** type name -> type */
  private GenericCache<String, Set<IType>>              _typeNameToTypeCache;

  /** type name -> referring type */
  private GenericCache<String, Set<IType>>              _typeNameToReferringCache;

  /** resource -> resource module */
  private GenericCache<IResource, Set<IResourceModule>> _resourceToResourceModuleCache;

  /** type -> module */
  private GenericCache<IType, Set<IModule>>             _typeToModuleCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractCachingModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param projectDescription
   */
  public AbstractCachingModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {

    // call the super constructor
    super(name, projectDescription);

    // create _typeNameToTypeCache
    _typeNameToTypeCache = new GenericCache<String, Set<IType>>() {
      @Override
      protected Set<IType> create(String key) {
        return new HashSet<IType>();
      }
    };

    // create _typeNameToReferringCache
    _typeNameToReferringCache = new GenericCache<String, Set<IType>>() {
      @Override
      protected Set<IType> create(String key) {
        return new HashSet<IType>();
      }
    };

    // create _resourceToResourceModuleCache
    _resourceToResourceModuleCache = new GenericCache<IResource, Set<IResourceModule>>() {
      @Override
      protected Set<IResourceModule> create(IResource resource) {
        return new HashSet<IResourceModule>();
      }
    };

    // create _typeToModuleCache
    _typeToModuleCache = new GenericCache<IType, Set<IModule>>() {
      @Override
      protected Set<IModule> create(IType type) {
        return new HashSet<IModule>();
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void preApplyTransformations() {

    // clear all the caches
    _typeNameToTypeCache.clear();
    _typeNameToReferringCache.clear();
    _resourceToResourceModuleCache.clear();
    _typeToModuleCache.clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @param resourceModule
   * @param action
   */
  public void resourcesChanged(Collection<? extends IResource> resources, IResourceModule resourceModule,
      ChangeAction action) {

    // iterate over all the resources...
    for (IResource resource : resources) {

      // ... and handle them
      resourceChanged(resource, resourceModule, action);
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
  public void resourceChanged(IResource resource, IResourceModule resourceModule, ChangeAction action) {

    // step 1: add/remove to resource map
    switch (action) {
    case ADDED: {
      _resourceToResourceModuleCache.getOrCreate(resource).add(resourceModule);
      break;
    }
    case REMOVED: {
      Set<IResourceModule> resourceModules = _resourceToResourceModuleCache.get(resource);
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
   * <p>
   * </p>
   * 
   * @param types
   * @param module
   * @param action
   */
  public void typesChanged(Collection<? extends IType> types, IModule module, ChangeAction action) {
    for (IType type : types) {
      typeChanged(type, module, action);
    }
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void resourceModuleAdded(IModifiableResourceModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    for (IResource resource : resourceModule.getResources(ContentType.SOURCE)) {
      resourceChanged(resource, resourceModule, ChangeAction.ADDED);

      //
      for (IType type : resource.getContainedTypes()) {
        typeChanged(type, resourceModule, ChangeAction.ADDED);
      }
    }

    //
    for (IResource resource : resourceModule.getResources(ContentType.BINARY)) {
      resourceChanged(resource, resourceModule, ChangeAction.ADDED);

      //
      for (IType type : resource.getContainedTypes()) {
        typeChanged(type, resourceModule, ChangeAction.ADDED);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void resourceModuleRemoved(IModifiableResourceModule resourceModule) {

    Assert.isNotNull(resourceModule);

    //
    for (IResource resource : resourceModule.getResources(ContentType.SOURCE)) {
      resourceChanged(resource, resourceModule, ChangeAction.REMOVED);

      //
      for (IType type : resource.getContainedTypes()) {
        typeChanged(type, resourceModule, ChangeAction.REMOVED);
      }
    }

    //
    for (IResource resource : resourceModule.getResources(ContentType.BINARY)) {
      resourceChanged(resource, resourceModule, ChangeAction.REMOVED);

      //
      for (IType type : resource.getContainedTypes()) {
        typeChanged(type, resourceModule, ChangeAction.REMOVED);
      }
    }
  }

  @Override
  protected void typeModuleAdded(TypeModule module) {
    Assert.isNotNull(module);

    //
    for (IType type : module.getContainedTypes()) {
      typeChanged(type, module, ChangeAction.ADDED);
    }
  }

  @Override
  protected void typeModuleRemoved(TypeModule module) {
    for (IType type : module.getContainedTypes()) {
      typeChanged(type, module, ChangeAction.REMOVED);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO: incremental updates - replace with API
  @Deprecated
  public GenericCache<String, Set<IType>> getTypeNameToTypeCache() {
    return _typeNameToTypeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO: incremental updates - replace with API
  @Deprecated
  public GenericCache<String, Set<IType>> getTypeNameToReferringCache() {
    return _typeNameToReferringCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public IResourceModule getAssociatedResourceModule(IResource resource) {

    Assert.isNotNull(resource);

    if (resource instanceof Resource) {
      resource = ((Resource) resource).getResourceStandin();
    }

    //
    Set<IResourceModule> resourceModules = _resourceToResourceModuleCache.get(resource);

    //
    if (resourceModules == null || resourceModules.isEmpty()) {
      return null;
    } else if (resourceModules.size() > 1) {
      throw new RuntimeException("Resource is contained in multiple ResourceModules.");
    } else {
      return resourceModules.toArray(new IResourceModule[0])[0];
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

  protected final GenericCache<IType, Set<IModule>> getTypeToModuleCache() {
    return _typeToModuleCache;
  }
}
