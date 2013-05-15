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

import java.util.concurrent.ConcurrentHashMap;

import org.bundlemaker.core._type.modifiable.ReferenceAttributes;
import org.bundlemaker.core.internal.resource.FlyWeightStringCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeFlyWeightCache {

  /** - */
  private static final int                                    REFERENCE_CACHE_INITIAL_CAPACITY            = 10000;

  /** - */
  private static final int                                    REFERENCE_ATTRIBUTES_CACHE_INITIAL_CAPACITY = 10000;

  /** - */
  ConcurrentHashMap<Reference, Reference>                     _referenceCache;

  /** - */
  ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes> _referenceAttributesCache;

  /** - */
  private FlyWeightStringCache                                _flyWeightStringCache;

  /**
   * <p>
   * Creates a new instance of type {@link TypeFlyWeightCache}.
   * </p>
   */
  public TypeFlyWeightCache(FlyWeightStringCache stringFlyWeightCache) {

    _flyWeightStringCache = stringFlyWeightCache;

    // create the concurrent hash maps
    _referenceCache = new ConcurrentHashMap<Reference, Reference>(REFERENCE_CACHE_INITIAL_CAPACITY);

    _referenceAttributesCache = new ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes>(
        REFERENCE_ATTRIBUTES_CACHE_INITIAL_CAPACITY);
  }

  /**
   * <p>
   * </p>
   * 
   * @return the flyWeightStringCache
   */
  public FlyWeightStringCache getFlyWeightStringCache() {
    return _flyWeightStringCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @param referenceAttributes
   * @return
   */
  public Reference getReference(String fullyQualifiedName, ReferenceAttributes referenceAttributes) {

    // create the key
    ReferenceAttributes attributes = getReferenceAttributes(referenceAttributes);

    Reference key = new Reference(_flyWeightStringCache.getFlyWeightString(fullyQualifiedName), attributes);

    // return if already there
    if (_referenceCache.containsKey(key)) {
      return _referenceCache.get(key);
    }

    // map doesn't contain key, create one -- note that first writer wins,
    // all others just throw away their value
    _referenceCache.putIfAbsent(key, key);

    // return the value that won
    return _referenceCache.get(key);
  }

  /**
   * <p>
   * </p>
   * 
   * @param attributes
   * @return
   */
  ReferenceAttributes getReferenceAttributes(ReferenceAttributes attributes) {

    // return if already there
    if (_referenceAttributesCache.containsKey(attributes)) {
      return _referenceAttributesCache.get(attributes);
    }

    // map doesn't contain key, create one -- note that first writer wins,
    // all others just throw away their value
    _referenceAttributesCache.putIfAbsent(attributes, attributes);

    // return the value that won
    return _referenceAttributesCache.get(attributes);
  }

  public ConcurrentHashMap<Reference, Reference> getReferenceCache() {
    return _referenceCache;
  }

  public ConcurrentHashMap<ReferenceAttributes, ReferenceAttributes> getReferenceAttributesCache() {
    return _referenceAttributesCache;
  }
}
