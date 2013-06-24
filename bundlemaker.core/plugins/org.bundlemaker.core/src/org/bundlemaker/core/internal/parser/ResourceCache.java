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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.TypeEnum;
import org.bundlemaker.core._type.internal.FlyWeightReferenceCache;
import org.bundlemaker.core._type.internal.Type;
import org.bundlemaker.core.common.FlyWeightStringCache;
import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IResourceCache;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
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
  Map<IProjectContentResource, Resource> _storedResourcesMap;

  /** the element map */
  Map<IProjectContentResource, Resource> _newResourceMap;

  /** the element map */
  private Map<String, Type>              _typeMap;

  /** the dependency store */
  private IPersistentDependencyStore     _dependencyStore;

  /** - */
  private FlyWeightStringCache           _flyWeightStringCache;

  /** - */
  private FlyWeightReferenceCache        _flyWeightReferenceCache;

  /** - */
  private ConcurrentMap<?, ?>            _projectContentSpecificUserAttributes;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceCache}.
   * </p>
   * 
   * @param dependencyStore
   */
  @SuppressWarnings("rawtypes")
  public ResourceCache(IPersistentDependencyStore dependencyStore) {

    Assert.isNotNull(dependencyStore);

    // set the dependency store
    _dependencyStore = dependencyStore;

    //
    _storedResourcesMap = new HashMap<IProjectContentResource, Resource>();

    // set the element map
    _newResourceMap = new HashMap<IProjectContentResource, Resource>();

    //
    _typeMap = new HashMap<String, Type>();
    _projectContentSpecificUserAttributes = new ConcurrentHashMap();

    //
    _flyWeightStringCache = new FlyWeightStringCache();
    _flyWeightReferenceCache = new FlyWeightReferenceCache(_flyWeightStringCache);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ResourceCache}.
   * </p>
   */
  public ResourceCache() {

    //
    _storedResourcesMap = new HashMap<IProjectContentResource, Resource>();

    // set the element map
    _newResourceMap = new HashMap<IProjectContentResource, Resource>();

    //
    _typeMap = new HashMap<String, Type>();

    //
    _flyWeightStringCache = new FlyWeightStringCache();
    _flyWeightReferenceCache = new FlyWeightReferenceCache(_flyWeightStringCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ConcurrentMap<?, ?> getProjectContentSpecificUserAttributes() {
    return _projectContentSpecificUserAttributes;
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

    Assert.isNotNull(_dependencyStore, "Dependency store must not be null.");

    //
    if (progressMonitor != null) {
      progressMonitor.beginTask("Writing to disc...", _newResourceMap.values().size());
    }

    // update all
    for (Resource modifiableResource : _newResourceMap.values()) {

      //
      if (modifiableResource.isAnalyzeReferences()) {
        _dependencyStore.updateResource(modifiableResource);
      }

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
  public synchronized IParsableResource getOrCreateResource(String contentId, String root, String path) {

    // return the result
    return getOrCreateResource(new DefaultProjectContentResource(contentId, root, path));
  }

  public synchronized IParsableResource getOrCreateResource(IProjectContentResource resourceKey) {

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
    resource = new Resource(resourceKey.getProjectContentEntryId().toString(), resourceKey.getRoot(),
        resourceKey.getPath(), this);
    resource.setAnalyzeReferences(resourceKey.isAnalyzeReferences());

    // store the Resource
    _newResourceMap.put(resource, resource);

    // return the result
    return resource;
  }

  // TODO synchronized
  public synchronized Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum, boolean abstractType) {

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
    type = new Type(fullyQualifiedName, typeEnum, _flyWeightReferenceCache, abstractType);

    // store the Resource
    _typeMap.put(fullyQualifiedName, type);

    // return the result
    return type;
  }

  @Deprecated
  public Map<IProjectContentResource, Resource> getResourceMap() {
    return _newResourceMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public FlyWeightStringCache getFlyWeightCache() {
    return _flyWeightStringCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return the typeFlyWeightCache
   */
  public FlyWeightReferenceCache getTypeFlyWeightCache() {
    return _flyWeightReferenceCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @param resource
   */
  public void addToStoredResourcesMap(IProjectContentResource key, Resource resource) {
    _storedResourcesMap.put(key, resource);
  }

  /**
   * <p>
   * </p>
   * 
   * @param fileBasedContent
   * @param map
   */
  public void setupTypeCache(IProjectContentEntry fileBasedContent) {

    //
    _projectContentSpecificUserAttributes.clear();

    // clear the type map
    _typeMap.clear();

    //
    for (IProjectContentResource resource : fileBasedContent.getBinaryResources()) {

      Resource storedResource = _storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {
          _typeMap.put(type.getFullyQualifiedName(), type);
          type.createReferenceContainer(_flyWeightReferenceCache);
        }
      }
    }

    //
    for (IProjectContentResource resource : fileBasedContent.getSourceResources()) {

      Resource storedResource = _storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {
          if (!_typeMap.containsKey(type.getFullyQualifiedName())) {
            _typeMap.put(type.getFullyQualifiedName(), type);
            type.createReferenceContainer(_flyWeightReferenceCache);
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

  public Map<IProjectContentResource, Resource> getCombinedMap() {

    Map<IProjectContentResource, Resource> result = new HashMap<IProjectContentResource, Resource>();

    result.putAll(_storedResourcesMap);
    result.putAll(_newResourceMap);

    return result;
  }
}
