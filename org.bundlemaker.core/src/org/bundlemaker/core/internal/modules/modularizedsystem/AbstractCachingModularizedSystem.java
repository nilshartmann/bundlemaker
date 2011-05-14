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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ITypeSelector;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCachingModularizedSystem extends AbstractTransformationAwareModularizedSystem {

  /** type name -> type */
  private GenericCache<String, Set<IType>> _typeNameToTypeCache;

  /** type name -> referring type */
  private GenericCache<String, Set<IType>> _typeNameToReferringCache;

  /** - */
  private Map<IResource, IResourceModule>  _resourceToResourceModuleMap;

  /** - */
  private Map<IType, IModule>              _typeToModuleMap;

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

    // create type name to type cache
    _typeNameToTypeCache = new GenericCache<String, Set<IType>>() {
      @Override
      protected Set<IType> create(String key) {
        return new HashSet<IType>();
      }
    };

    //
    _typeNameToReferringCache = new GenericCache<String, Set<IType>>() {
      @Override
      protected Set<IType> create(String key) {
        return new HashSet<IType>();
      }
    };

    //
    _resourceToResourceModuleMap = new HashMap<IResource, IResourceModule>();
    _typeToModuleMap = new HashMap<IType, IModule>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public GenericCache<String, Set<IType>> getTypeNameToTypeCache() {
    return _typeNameToTypeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public GenericCache<String, Set<IType>> getTypeNameToReferringCache() {
    return _typeNameToReferringCache;
  }

  /**
   * {@inheritDoc}
   */
  public IResourceModule getAssociatedResourceModule(IResource resource) {

    Assert.isNotNull(resource);

    if (resource instanceof Resource) {
      resource = ((Resource) resource).getResourceStandin();
    }
    return _resourceToResourceModuleMap.get(resource);
  }

  public IModule getAssociatedModule(IType type) {

    Assert.isNotNull(type);
    return _typeToModuleMap.get(type);
  }

  /**
   * <p>
   * </p>
   */
  @Override
  public void initializeResourceModules() {
    super.initializeResourceModules();

    getTypeNameToTypeCache().clear();
    getTypeNameToReferringCache().clear();
    _typeToModuleMap.clear();
    _resourceToResourceModuleMap.clear();

    // step 1: cache the type modules
    for (IModule module : getNonResourceModules()) {
      for (IType type : module.getContainedTypes()) {
        getTypeNameToTypeCache().getOrCreate(type.getFullyQualifiedName()).add(type);
        _typeToModuleMap.put(type, module);
      }
    }

    // step 2: cache the resource modules
    for (IResourceModule module : getResourceModules()) {

      //
      for (IResource resource : module.getResources(ContentType.SOURCE)) {
        _resourceToResourceModuleMap.put(resource, module);
      }
      for (IResource resource : module.getResources(ContentType.BINARY)) {
        _resourceToResourceModuleMap.put(resource, module);
      }

      //
      for (IType type : module.getContainedTypes()) {

        _typeToModuleMap.put(type, module);

        if (getTypeNameToTypeCache().getOrCreate(type.getFullyQualifiedName()).add(type)) {

          for (IReference reference : type.getReferences()) {
            getTypeNameToReferringCache().getOrCreate(reference.getFullyQualifiedName()).add(type);
          }
        }
      }
    }
  }
}
