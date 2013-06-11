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
package org.bundlemaker.core._type.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.TypeEnum;
import org.bundlemaker.core.common.FlyWeightStringCache;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.spi.parser.IParsableResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeCache {

  /** the element map */
  private Map<String, Type>       _typeMap;

  /** - */
  private FlyWeightStringCache    _flyWeightStringCache;

  private FlyWeightReferenceCache _typeFlyWeightCache;

  /**
   * <p>
   * Creates a new instance of type {@link TypeCache}.
   * </p>
   */
  public TypeCache() {

    //
    _typeMap = new HashMap<String, Type>();

    //
    _flyWeightStringCache = new FlyWeightStringCache();
    _typeFlyWeightCache = new FlyWeightReferenceCache(_flyWeightStringCache);
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
    type = new Type(fullyQualifiedName, typeEnum, _typeFlyWeightCache, abstractType);

    // store the Resource
    _typeMap.put(fullyQualifiedName, type);

    // return the result
    return type;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public FlyWeightStringCache getFlyWeightCache() {
  // return _flyWeightCache;
  // }
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @return the typeFlyWeightCache
  // */
  // public TypeFlyWeightCache getTypeFlyWeightCache() {
  // return _typeFlyWeightCache;
  // }

  /**
   * <p>
   * </p>
   * 
   * @param fileBasedContent
   * @param map
   */
  public void setupTypeCache(IProjectContentEntry projectContentEntry,
      Map<IProjectContentResource, IParsableResource> storedResourcesMap,
      Set<IParsableResource> newAndModifiedSourceResources) {

    // clear the type map
    _typeMap.clear();

    //
    for (IProjectContentResource resource : projectContentEntry.getBinaryResources()) {

      IParsableResource storedResource = storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {
          _typeMap.put(type.getFullyQualifiedName(), type);
          type.createReferenceContainer(_typeFlyWeightCache);
        }
      }
    }

    //
    for (IProjectContentResource resource : projectContentEntry.getSourceResources()) {

      IParsableResource storedResource = storedResourcesMap.get(resource);

      if (storedResource != null) {
        for (Type type : storedResource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {
          if (!_typeMap.containsKey(type.getFullyQualifiedName())) {
            _typeMap.put(type.getFullyQualifiedName(), type);
            type.createReferenceContainer(_typeFlyWeightCache);
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
}
