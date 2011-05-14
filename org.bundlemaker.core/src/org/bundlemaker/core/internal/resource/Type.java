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
package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableType;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Type implements IType, IModifiableType {

  /** the fully qualified name */
  private FlyWeightString              _fullyQualifiedName;

  /** the references set */
  private Set<Reference>               _references;

  /** the type of this type (enum, class, interface, annotation) **/
  private TypeEnum                     _typeEnum;

  /** transient: the source resource */
  private transient Resource           _sourceResource;

  /** transient: the binary resource */
  private transient Resource           _binaryResource;

  /** transient: the reference container */
  private transient ReferenceContainer _referenceContainer;

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @param typeEnum
   */
  public Type(String fullyQualifiedName, TypeEnum typeEnum) {

    Assert.isNotNull(fullyQualifiedName);
    Assert.isNotNull(typeEnum);

    //
    _fullyQualifiedName = new FlyWeightString(fullyQualifiedName);

    // the type of the type
    _typeEnum = typeEnum;
  }

  /**
   * <p>
   * Creates a new instance of type {@link Type}.
   * </p>
   * 
   * @param fullyQualifiedName
   */
  public Type(String fullyQualifiedName) {

    Assert.isNotNull(fullyQualifiedName);

    //
    _fullyQualifiedName = new FlyWeightString(fullyQualifiedName);
  }

  /**
   * <p>
   * </p>
   * 
   * @param flyWeightCache
   */
  public Type(String fullyQualifiedName, TypeEnum typeEnum, FlyWeightCache flyWeightCache) {

    Assert.isNotNull(fullyQualifiedName);
    Assert.isNotNull(typeEnum);
    Assert.isNotNull(flyWeightCache);

    //
    _fullyQualifiedName = flyWeightCache.getFlyWeightString(fullyQualifiedName);

    // the type of the type
    _typeEnum = typeEnum;

    createReferenceContainer(flyWeightCache);
  }

  @Override
  public String getFullyQualifiedName() {
    return _fullyQualifiedName.toString();
  }

  @Override
  public String getPackageName() {

    //
    String typeName = _fullyQualifiedName.toString();

    // get index of the last '.'
    int lastIndex = typeName.lastIndexOf('.');

    //
    return lastIndex == -1 ? "" : typeName.substring(0, lastIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {

    // get the fully qualified name
    String fullyQualifiedName = _fullyQualifiedName.toString();

    // get the index
    int index = fullyQualifiedName.lastIndexOf('.');

    // return the result
    return index != -1 ? fullyQualifiedName.substring(index + 1) : fullyQualifiedName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends IReference> getReferences() {
    return Collections.unmodifiableSet(references());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IReference getReference(String fullyQualifiedName) {

    //
    Assert.isNotNull(fullyQualifiedName);

    //
    for (Reference reference : _references) {

      //
      if (fullyQualifiedName.equals(reference.getFullyQualifiedName())) {
        return reference;
      }
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TypeEnum getType() {
    return _typeEnum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getSourceResource() {
    return _sourceResource != null ? _sourceResource.getResourceStandin() : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getBinaryResource() {
    return _binaryResource != null ? _binaryResource.getResourceStandin() : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasSourceResource() {
    return _sourceResource != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasBinaryResource() {
    return _binaryResource != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getModule(IModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);

    IModule result = null;

    if (_binaryResource != null) {
      result = _binaryResource.getAssociatedResourceModule(modularizedSystem);
    } else if (_sourceResource != null) {
      result = _sourceResource.getAssociatedResourceModule(modularizedSystem);
    } else {
      result = ((ModularizedSystem) modularizedSystem).getAssociatedModule(this);
    }

    if (result == null) {
      throw new RuntimeException("Type has no module " + this.toString());
    } else {
      return result;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recordReference(String fullyQualifiedName, ReferenceAttributes referenceAttributes) {

    _referenceContainer.recordReference(fullyQualifiedName, referenceAttributes);
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceResource
   */
  public void setSourceResource(Resource sourceResource) {
    _sourceResource = sourceResource;
  }

  /**
   * <p>
   * </p>
   * 
   * @param binaryResource
   */
  public void setBinaryResource(Resource binaryResource) {
    _binaryResource = binaryResource;
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeEnum
   */
  public void setTypeEnum(TypeEnum typeEnum) {
    _typeEnum = typeEnum;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.resource.IModifiableType#getModifiableReferences()
   */
  public Set<Reference> getModifiableReferences() {
    return references();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLocalOrAnonymousType() {
    return _fullyQualifiedName.toString().matches(".*\\$\\d.*");
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isInnerType() {
    return _fullyQualifiedName.toString().matches(".*\\$.*");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "Type [_fullyQualifiedName=" + _fullyQualifiedName + ", _typeEnum=" + _typeEnum + "]";
  }

  /**
   * <p>
   * </p>
   *
   * @param flyWeightCache
   */
  public void createReferenceContainer(FlyWeightCache flyWeightCache) {
    //
    _referenceContainer = new ReferenceContainer(flyWeightCache) {
      @Override
      protected Set<Reference> createReferencesSet() {
        return references();
      }
    };
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
      _references = new HashSet<Reference>();
    }

    // return the result
    return _references;
  }
}
