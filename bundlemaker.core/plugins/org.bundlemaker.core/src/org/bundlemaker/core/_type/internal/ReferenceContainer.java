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

import org.bundlemaker.core._type.modifiable.IReferenceRecorder;
import org.bundlemaker.core._type.modifiable.ReferenceAttributes;
import org.bundlemaker.core._type.utils.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class ReferenceContainer implements IReferenceRecorder {

  /** the fly weight cahce */
  private transient TypeFlyWeightCache _flyWeightCache;

  /** the reference map */
  private Map<ReferenceKey, Reference> _referenceMap;

  /** the references */
  private Set<Reference>               _references;

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceContainer}.
   * </p>
   * 
   * @param cache
   */
  public ReferenceContainer(TypeFlyWeightCache cache) {
    Assert.isNotNull(cache);

    //
    _flyWeightCache = cache;
    _referenceMap = new HashMap<ReferenceKey, Reference>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract Set<Reference> createReferencesSet();

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   */
  @Override
  public void recordReference(String fullyQualifiedName, ReferenceAttributes requestedAttributes) {

    //
    Assert.isNotNull(fullyQualifiedName);
    Assert.isNotNull(requestedAttributes);

    //
    if (fullyQualifiedName.startsWith("java.")) {

      // do nothing
      return;
    }

    // if the referenced type is an local or anonymous class,
    // we redirect the dependency to the outer type
    if (JavaTypeUtils.isLocalOrAnonymousTypeName(fullyQualifiedName)) {

      // set the references
      fullyQualifiedName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(fullyQualifiedName);

      // create new attributes
      requestedAttributes = new ReferenceAttributes(requestedAttributes.getReferenceType(), false, false, false,
          requestedAttributes.isCompileTime(), requestedAttributes.isRuntimeTime(),
          requestedAttributes.isDirectlyReferenced(), requestedAttributes.isIndirectlyReferenced());

    }

    // create the key
    Assert.isNotNull(requestedAttributes);
    ReferenceKey key = new ReferenceKey(fullyQualifiedName, requestedAttributes.getReferenceType());

    // get the reference
    Assert.isNotNull(_referenceMap, "Reference Map is null");
    Reference existingReference = _referenceMap.get(key);

    Assert.isNotNull(_flyWeightCache, "_flyWeightCache is not set!");

    // create completely new one
    if (existingReference == null) {

      existingReference = _flyWeightCache.getReference(fullyQualifiedName, requestedAttributes);

      references().add(existingReference);
      _referenceMap.put(key, existingReference);

      return;
    }

    // return if current dependency matches the requested one
    if (existingReference.getReferenceAttributes().equals(requestedAttributes)) {
      return;
    }

    // if current dependency does not match the requested one, we have to
    // request a new one
    references().remove(existingReference);

    ReferenceAttributes mergedAttributes = merge(requestedAttributes, existingReference.getReferenceAttributes());

    existingReference = _flyWeightCache.getReference(fullyQualifiedName, mergedAttributes);

    references().add(existingReference);
    _referenceMap.put(key, existingReference);
  }

  /**
   * <p>
   * </p>
   * 
   * @param a1
   * @param a2
   * @return
   */
  private ReferenceAttributes merge(ReferenceAttributes a1, ReferenceAttributes a2) {

    //
    return new ReferenceAttributes(a1.getReferenceType(), a1.isExtends() || a2.isExtends(), a1.isImplements()
        || a2.isImplements(), a1.isClassAnnotation() || a2.isClassAnnotation(), a1.isCompileTime()
        || a2.isCompileTime(), a1.isRuntimeTime() || a2.isRuntimeTime(), a1.isDirectlyReferenced()
        || a2.isDirectlyReferenced(), a1.isIndirectlyReferenced() || a2.isIndirectlyReferenced());
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<Reference> references() {

    // create if necessary
    if (_references == null) {
      _references = createReferencesSet();
    }

    // return the references
    return _references;
  }

}
