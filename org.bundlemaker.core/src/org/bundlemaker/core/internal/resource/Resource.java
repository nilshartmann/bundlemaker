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

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.internal.parser.ResourceCache;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.resource.ResourceKey;
import org.bundlemaker.core.resource.TypeEnum;
import org.bundlemaker.core.resource.modifiable.IModifiableResource;
import org.bundlemaker.core.resource.modifiable.ReferenceAttributes;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Resource extends ResourceKey implements IModifiableResource {

  /** - */
  private Set<Reference>               _references;

  /** - */
  private Set<Type>                    _containedTypes;

  /** - */
  private IType                        _primaryType;

  /** - */
  private Set<IModifiableResource>     _stickyResources;

  /** - */
  private transient ReferenceContainer _referenceContainer;

  /** - */
  private transient ResourceCache      _resourceCache;

  /**  */
  private transient ResourceStandin    _resourceStandin;

  /**
   * <p>
   * Creates a new instance of type {@link Resource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public Resource(String contentId, String root, String path, ResourceCache resourceCache) {
    super(contentId, root, path, resourceCache.getFlyWeightCache());

    Assert.isNotNull(resourceCache);

    _resourceCache = resourceCache;

    _referenceContainer = new ReferenceContainer(resourceCache.getFlyWeightCache()) {
      @Override
      protected Set<Reference> createReferencesSet() {
        return references();
      }
    };

  }

  /**
   * <p>
   * Creates a new instance of type {@link Resource}.
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   */
  public Resource(String contentId, String root, String path) {
    super(contentId, root, path);
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
  public Set<? extends IType> getContainedTypes() {
    return Collections.unmodifiableSet(containedTypes());
  }

  @Override
  public IType getPrimaryType() {

    //
    if (_primaryType == null && _containedTypes.size() == 1) {
      try {
        return getContainedType();
      } catch (CoreException e) {
        // can not happen here...
        throw new RuntimeException(e.getMessage(), e);
      }
    }

    //
    return _primaryType;
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  @Override
  public boolean isPrimaryType(IType type) {
    return type != null && type.equals(_primaryType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getContainedType() throws CoreException {

    //
    if (_containedTypes == null || _containedTypes.isEmpty()) {
      return null;
    }

    //
    if (_containedTypes.size() == 1) {
      return _containedTypes.toArray(new IType[0])[0];
    }

    // throw new exception
    throw new CoreException(new Status(IStatus.ERROR, BundleMakerCore.BUNDLE_ID,
        String.format("Resource '%s' contains more than one type.")));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<? extends IResource> getStickyResources() {
    return Collections.unmodifiableSet(stickyResources());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsTypes() {
    return _containedTypes != null && !_containedTypes.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void recordReference(String fullyQualifiedName, ReferenceAttributes referenceAttributes) {

    //
    _referenceContainer.recordReference(fullyQualifiedName, referenceAttributes);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getOrCreateType(String fullyQualifiedName, TypeEnum typeEnum) {

    //
    Type type = _resourceCache.getOrCreateType(fullyQualifiedName, typeEnum);

    //
    containedTypes().add(type);

    //
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getType(String fullyQualifiedName) {

    for (Type containedType : containedTypes()) {
      if (containedType.getFullyQualifiedName().equals(fullyQualifiedName)) {
        return containedType;
      }
    }

    return null;
  }

  @Override
  public void addStickyResource(IModifiableResource stickyResource) {
    stickyResources().add(stickyResource);
  }

  /**
   * @param resourceStandin
   */
  public void setResourceStandin(ResourceStandin resourceStandin) {
    _resourceStandin = resourceStandin;
  }

  public ResourceStandin getResourceStandin() {
    return _resourceStandin;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableContainedTypes ()
   */
  public Set<Type> getModifiableContainedTypes() {
    return containedTypes();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.resource.IModifiableResource#getModifiableReferences ()
   */
  public Set<Reference> getModifiableReferences() {
    return references();
  }

  @Override
  public IResourceModule getAssociatedResourceModule(IModularizedSystem modularizedSystem) {

    //
    if (_resourceStandin == null) {
      throw new RuntimeException();
    }

    //
    return ((ModularizedSystem) modularizedSystem).getAssociatedResourceModule(this);
  }

  @Override
  public int compareTo(IResource arg0) {

    //
    if (_resourceStandin == null) {
      throw new RuntimeException();
    }

    return _resourceStandin.compareTo(arg0);
  }

  @Override
  public void setPrimaryType(IType type) {
    _primaryType = type;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<Reference> references() {

    //
    if (_references == null) {
      _references = new HashSet<Reference>();
    }

    //
    return _references;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private Set<Type> containedTypes() {

    if (_containedTypes == null) {
      _containedTypes = new HashSet<Type>();
    }

    return _containedTypes;
  }

  private Set<IModifiableResource> stickyResources() {

    if (_stickyResources == null) {
      _stickyResources = new HashSet<IModifiableResource>();
    }

    return _stickyResources;
  }
}
