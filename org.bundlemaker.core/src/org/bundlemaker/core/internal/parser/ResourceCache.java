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
package org.bundlemaker.core.internal.parser;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.internal.resource.FlyWeightCache;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.internal.resource.Type;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.parser.IResourceCache;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceKey;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceCache implements IResourceCache {

  /** the element map */
  Map<IResourceKey, Resource>        _storedResourcesMap;

  /** the element map */
  Map<IResourceKey, Resource>        _newResourceMap;

  /** the element map */
  private Map<String, Type>          _typeMap;

  /** the dependency store */
  private IPersistentDependencyStore _dependencyStore;

  /** - */
  private FlyWeightCache             _flyWeightCache;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceCache}.
   * </p>
   * 
   * @param dependencyStore
   */
  public ResourceCache(IPersistentDependencyStore dependencyStore) {

    Assert.isNotNull(dependencyStore);

    // set the dependency store
    _dependencyStore = dependencyStore;

    //
    _storedResourcesMap = new HashMap<IResourceKey, Resource>();

    // set the element map
    _newResourceMap = new HashMap<IResourceKey, Resource>();

    //
    _typeMap = new HashMap<String, Type>();

    //
    _flyWeightCache = new FlyWeightCache();
  }

  /**
   * <p>
   * </p>
   */
  public synchronized void clear() throws CoreException {

    // clear the map
    _newResourceMap.clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  public synchronized void commit(IProgressMonitor progressMonitor) throws CoreException {

    //
    if (progressMonitor != null) {
      progressMonitor.beginTask("Writing to disc...", _newResourceMap.values().size());
    }

    // update all
    for (IModifiableResource modifiableResource : _newResourceMap.values()) {
      _dependencyStore.updateResource(modifiableResource);

      //
      if (progressMonitor != null) {
        progressMonitor.worked(1);
      }
    }

    // commit the store
    _dependencyStore.commit();

    //
    if (progressMonitor != null) {
      progressMonitor.done();
    }
  }

  /**
   * {@inheritDoc}
   */
  // TODO synchronized
  @Override
  public synchronized IModifiableResource getOrCreateResource(IResourceKey resourceKey) {

    //
    Resource resource = _storedResourcesMap.get(resourceKey);

    // return result if != null
    if (resource != null) {
      return resource;
    }

    //
    resource = _newResourceMap.get(resourceKey);

    // return result if != null
    if (resource != null) {
      return resource;
    }

    // create a new one if necessary
    resource = new Resource(resourceKey.getContentId(), resourceKey.getRoot(), resourceKey.getPath(), this);

    // store the Resource
    _newResourceMap.put(resource, resource);

    // return the result
    return resource;
  }

  // TODO synchronized
  public synchronized Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum) {

    //
    Type type = _typeMap.get(fullyQualifiedName);

    // return result if != null
    if (type != null) {

      if (!type.getType().equals(typeEnum)) {

        // TODO
        throw new RuntimeException("Wrong type requested" + fullyQualifiedName + " : " + typeEnum + " : " + type);
      }

      return type;
    }

    // create a new one if necessary
    type = new Type(fullyQualifiedName, typeEnum, _flyWeightCache);

    // store the Resource
    _typeMap.put(fullyQualifiedName, type);

    // return the result
    return type;
  }

  @Deprecated
  public Map<IResourceKey, Resource> getResourceMap() {
    return _newResourceMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public FlyWeightCache getFlyWeightCache() {
    return _flyWeightCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @param resource
   */
  public void addToStoredResourcesMap(IResourceKey key, Resource resource) {
    _storedResourcesMap.put(key, resource);
  }

  /**
   * <p>
   * </p>
   * 
   * @param fileBasedContent
   * @param map
   */
  public void setupTypeCache(IFileBasedContent fileBasedContent) {

    // clear the type map
    _typeMap.clear();

    //
    for (IResource resource : fileBasedContent.getBinaryResources()) {

      Resource storedResource = _storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.getModifiableContainedTypes()) {
          _typeMap.put(type.getFullyQualifiedName(), type);
          type.createReferenceContainer(_flyWeightCache);
        }
      }
    }

    //
    for (IResource resource : fileBasedContent.getSourceResources()) {

      Resource storedResource = _storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.getModifiableContainedTypes()) {
          if (!_typeMap.containsKey(type.getFullyQualifiedName())) {
            _typeMap.put(type.getFullyQualifiedName(), type);
            type.createReferenceContainer(_flyWeightCache);
          }
        }
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void resetTypeCache() {
    _typeMap.clear();
  }

  public Map<IResourceKey, Resource> getCombinedMap() {

    Map<IResourceKey, Resource> result = new HashMap<IResourceKey, Resource>();

    result.putAll(_storedResourcesMap);
    result.putAll(_newResourceMap);

    return result;
  }
}
