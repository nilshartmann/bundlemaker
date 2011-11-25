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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.internal.modules.modularizedsystem.ModularizedSystem;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceContainer;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters.ReferenceFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceContainer extends TypeContainer implements IModifiableResourceContainer {

  /** the binary resources */
  private Set<IResource>     _binaryResources;

  /** the source resources */
  private Set<IResource>     _sourceResources;

  /** - */
  private IModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceContainer}.
   * </p>
   */
  public ResourceContainer(IModularizedSystem modularizedSystem) {

    //
    Assert.isNotNull(modularizedSystem);

    // create the resource sets
    _binaryResources = new HashSet<IResource>();
    _sourceResources = new HashSet<IResource>();

    //
    _modularizedSystem = modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResource(String resourceType, ContentType contentType) {
    return getResource(resourceType, contentType) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getResource(String path, ContentType contentType) {

    //
    for (IResource resourceStandin : getModifiableResourcesSet(contentType)) {

      //
      if (resourceStandin.getPath().equalsIgnoreCase(path)) {
        return resourceStandin;
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IResource> getResources(ContentType contentType) {

    //
    Set<? extends IResource> result = getModifiableResourcesSet(contentType);
    return Collections.unmodifiableSet(new HashSet<IResource>(result));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences(IQueryFilter<IReference> filter) {

    if (filter instanceof ReferenceFilter) {
      ((ReferenceFilter) filter).setResourceModule(this.getResourceModule());
    }

    Set<IReference> result = new HashSet<IReference>();

    // iterate over all resources
    for (IResource resource : getResources(ContentType.BINARY)) {
      for (IReference reference : resource.getReferences()) {
        if (filter.matches(reference)) {
          result.add(reference);
        }
      }
    }

    for (IResource resource : getResources(ContentType.SOURCE)) {
      for (IReference reference : resource.getReferences()) {
        if (filter.matches(reference)) {
          result.add(reference);
        }
      }
    }

    //
    for (IType type : getContainedTypes()) {
      for (IReference reference : type.getReferences()) {
        if (filter.matches(reference)) {
          result.add(reference);
        }
      }
    }

    // return result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IMovableUnit> getMovableUnits() {

    // the result
    List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    // iterate over all types
    for (IType type : getContainedTypes()) {

      //
      IMovableUnit movableUnit = MovableUnit.createFromType(type, getModularizedSystem());

      //
      if (!result.contains(movableUnit)) {
        result.add(movableUnit);
      }
    }

    // iterate over all resources
    for (IResource resource : getResources(ContentType.BINARY)) {
      if (!resource.containsTypes()) {

        //
        IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());

        //
        if (!result.contains(movableUnit)) {
          result.add(movableUnit);
        }
      }
    }

    // iterate over all resources
    for (IResource resource : getResources(ContentType.SOURCE)) {
      if (!resource.containsTypes()) {

        //
        IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());

        //
        if (!result.contains(movableUnit)) {
          result.add(movableUnit);
        }
      }
    }

    // return the result
    return Collections.unmodifiableList(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getReferencedTypeNames(IQueryFilter<IReference> filter) {

    //
    Set<IReference> references = getReferences(filter);

    //
    Set<String> result = new HashSet<String>();
    for (IReference reference : references) {
      result.add(reference.getFullyQualifiedName());
    }

    //
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getReferencedPackageNames(IQueryFilter<IReference> filter) {

    //
    Set<IReference> references = getReferences(filter);

    //
    Set<String> result = new HashSet<String>();
    for (IReference reference : references) {

      if (reference.getFullyQualifiedName().indexOf('.') != -1) {
        result.add(reference.getFullyQualifiedName().substring(0, reference.getFullyQualifiedName().lastIndexOf('.')));
      } else {
        // TODO: brauchen wir das ?
        // result.add("");
      }
    }

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceModule getResourceModule() {
    return (IResourceModule) getModule();
  }

  /**
   * {@inheritDoc}
   */
  private void add(IResource resource, ContentType contentType) {

    Assert.isNotNull(resource);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).add(resource);

    // ... and add all contained types to the cache
    for (IType type : resource.getContainedTypes()) {
      add(type);
    }

    // notify
    if (getResourceModule().hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getResourceModule().getModularizedSystem()).resourceChanged(resource,
          getResourceModule(), ChangeAction.ADDED);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Deprecated
  public void addAll(Collection<? extends IResource> resources, ContentType contentType) {

    Assert.isNotNull(resources);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).addAll(resources);

    // ... and add all contained types to the cache
    for (IResource resource : resources) {
      for (IType type : resource.getContainedTypes()) {
        add(type);
      }
    }

    // notify
    if (getResourceModule().hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getResourceModule().getModularizedSystem()).resourcesChanged(resources,
          getResourceModule(), ChangeAction.ADDED);
    }
  }

  /**
   * {@inheritDoc}
   */
  private void remove(IResource resource, ContentType contentType) {

    Assert.isNotNull(resource);
    Assert.isNotNull(contentType);

    //
    if (getModifiableResourcesSet(contentType).contains(resource)) {

      // add the resource to the resource set...
      getModifiableResourcesSet(contentType).remove(resource);

      // notify
      if (getResourceModule().hasModularizedSystem()) {
        ((AbstractCachingModularizedSystem) getResourceModule().getModularizedSystem()).resourceChanged(resource,
            getResourceModule(), ChangeAction.REMOVED);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  private void removeAll(Collection<? extends IResource> resources, ContentType contentType) {

    Assert.isNotNull(resources);
    Assert.isNotNull(contentType);

    // add the resource to the resource set...
    getModifiableResourcesSet(contentType).removeAll(resources);

    // // ... and add all contained types to the cache
    // try {
    // for (IResource resource : resources) {
    // for (IType type : resource.getContainedTypes()) {
    // remove(type);
    // }
    // }
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }

    // notify
    if (getResourceModule().hasModularizedSystem()) {
      ((AbstractCachingModularizedSystem) getResourceModule().getModularizedSystem()).resourcesChanged(resources,
          getResourceModule(), ChangeAction.REMOVED);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    // add all types
    for (IType type : movableUnit.getAssociatedTypes()) {
      add(type);
    }

    // add binary resources
    addAll(movableUnit.getAssociatedBinaryResources(), ContentType.BINARY);

    // add source resources
    if (movableUnit.hasAssociatedSourceResource()) {
      add(movableUnit.getAssociatedSourceResource(), ContentType.SOURCE);
    }

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, getModule(), ChangeAction.ADDED);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    // add all types
    for (IType type : movableUnit.getAssociatedTypes()) {
      remove(type);
    }

    // add binary resources
    removeAll(movableUnit.getAssociatedBinaryResources(), ContentType.BINARY);

    // add source resources
    if (movableUnit.hasAssociatedSourceResource()) {
      remove(movableUnit.getAssociatedSourceResource(), ContentType.SOURCE);
    }

    //
    ((ModularizedSystem) getModularizedSystem()).fireMovableUnitEvent(movableUnit, getModule(), ChangeAction.REMOVED);
  }

  /**
   * <p>
   * Set the containing {@link IResourceModule}.
   * </p>
   * 
   * @param resourceModule
   *          the containing {@link IResourceModule}.
   */
  public void setResourceModule(IResourceModule resourceModule) {
    setModule(resourceModule);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentType
   * @return
   */
  private Set<IResource> getModifiableResourcesSet(ContentType contentType) {
    Assert.isNotNull(contentType);

    // return the resource set
    return ContentType.BINARY.equals(contentType) ? _binaryResources : _sourceResources;
  }
}
