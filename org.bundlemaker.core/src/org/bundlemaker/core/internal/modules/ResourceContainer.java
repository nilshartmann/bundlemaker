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
import java.util.Set;

import org.bundlemaker.core.internal.resource.ResourceStandin;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceContainer;
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
  private Set<IResource>  _binaryResources;

  /** the source resources */
  private Set<IResource>  _sourceResources;

  /** the containing resource module */
  private IResourceModule _resourceModule;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceContainer}.
   * </p>
   */
  public ResourceContainer() {

    // create the resource sets
    _binaryResources = new HashSet<IResource>();

    // TODO: LAZY
    _sourceResources = new HashSet<IResource>();
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
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getReferencedTypeNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {

    // return result
    return getReferences(hideContainedTypes, includeSourceReferences, true, includeIndirectReferences, false);
  }

  @Override
  public Set<IReference> getAllReferences(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {

    // create the result
    Set<IReference> result = new HashSet<IReference>();

    //
    getReferences(_binaryResources, hideContainedTypes, includeIndirectReferences, result);

    //
    if (includeSourceReferences) {
      getReferences(_sourceResources, hideContainedTypes, includeIndirectReferences, result);
    }

    // return result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<String> getReferencedPackageNames(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeIndirectReferences) {

    // return result
    return getReferences(hideContainedTypes, includeSourceReferences, true, includeIndirectReferences, true);
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  public Set<String> getIndirectlyReferencedPackageNames() {

    // return result
    return getReferences(true, true, false, true, true);
  }

  public IResourceModule getResourceModule() {
    return _resourceModule;
  }

  public void setResourceModule(IResourceModule resourceModule) {
    _resourceModule = resourceModule;
  }

  /**
   * <p>
   * Initializes the contained types of this resource container.
   * </p>
   */
  public void initialize() {

    getModifiableContainedTypesMap().clear();

    // step 1: iterate over all binary resources...
    for (IResource resource : _binaryResources) {

      // ... and add all contained types
      for (IType type : resource.getContainedTypes()) {

        getModifiableContainedTypesMap().put(type.getFullyQualifiedName(), type);
      }

      // if (resource instanceof ResourceStandin) {
      // // set the back-reference
      // ((ResourceStandin) resource).setResourceModule(_resourceModule);
      // } else {
      // System.out.println(resource);
      // }
    }

    // step 2: iterate over all source resources...
    for (IResource resource : _sourceResources) {

      // ... and add all contained types
      for (IType type : resource.getContainedTypes()) {

        // TODO
        getModifiableContainedTypesMap().put(type.getFullyQualifiedName(), type);
      }

      // // set the back-reference
      // ((ResourceStandin) resource).setResourceModule(_resourceModule);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceContainer
   * @param contentType
   * @return
   */
  @Override
  public Set<IResource> getModifiableResourcesSet(ContentType contentType) {

    Assert.isNotNull(contentType);

    // return the resource set
    return ContentType.BINARY.equals(contentType) ? _binaryResources : _sourceResources;
  }

  /**
   * <p>
   * </p>
   * 
   * @param hideContainedTypes
   * @param includeSourceReferences
   * @param includeIndirectReferences
   * @param collectPackages
   * @return
   */
  private Set<String> getReferences(boolean hideContainedTypes, boolean includeSourceReferences,
      boolean includeDirectReferences, boolean includeIndirectReferences, boolean collectPackages) {

    // create the result
    Set<String> result = new HashSet<String>();

    //
    getReferences(_binaryResources, hideContainedTypes, includeDirectReferences, includeIndirectReferences, result,
        collectPackages);

    //
    if (includeSourceReferences) {
      getReferences(_sourceResources, hideContainedTypes, includeDirectReferences, includeIndirectReferences, result,
          collectPackages);
    }

    // return result
    return Collections.unmodifiableSet(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @param hideContainedTypes
   * @param includeIndirectReferences
   *          TODO
   * @param result
   * @param containedTypes
   */
  private void getReferences(Set<? extends IResource> resources, boolean hideContainedTypes,
      boolean includeDirectReferences, boolean includeIndirectReferences, Set<String> result, boolean collectPackages) {

    // iterate over all resources
    for (IResource resource : resources) {

      // iterate over all resources
      for (IReference reference : resource.getReferences()) {

        addReference(reference, hideContainedTypes, includeDirectReferences, includeIndirectReferences,
            collectPackages, result);
      }

      //
      for (IType type : resource.getContainedTypes()) {

        //
        for (IReference reference : type.getReferences()) {

          addReference(reference, hideContainedTypes, includeDirectReferences, includeIndirectReferences,
              collectPackages, result);
        }
      }
    }
  }

  private void addReference(IReference reference, boolean hideContainedTypes, boolean includeDirectReferences,
      boolean includeIndirectReferences, boolean collectPackages, Set<String> result) {

    if (reference.isDirectlyReferenced() && !includeDirectReferences) {
      return;
    }

    if (!reference.isDirectlyReferenced() && !includeIndirectReferences) {
      return;
    }

    if (!hideContainedTypes || !getContainedTypeNames().contains(reference.getFullyQualifiedName())) {

      String entry;
      if (collectPackages) {

        if (reference.getFullyQualifiedName().indexOf('.') != -1) {
          entry = reference.getFullyQualifiedName().substring(0, reference.getFullyQualifiedName().lastIndexOf('.'));
        } else {
          entry = "";
        }

      } else {
        entry = reference.getFullyQualifiedName();
      }

      if (!result.contains(entry)) {
        result.add(entry);
      }

    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resources
   * @param hideContainedTypes
   * @param includeIndirectReferences
   *          TODO
   * @param result
   */
  private void getReferences(Set<? extends IResource> resources, boolean hideContainedTypes,
      boolean includeIndirectReferences, Set<IReference> result) {

    // iterate over all resources
    for (IResource resource : resources) {

      // iterate over all resources
      for (IReference reference : resource.getReferences()) {

        if (!hideContainedTypes || !getContainedTypeNames().contains(reference.getFullyQualifiedName())) {

          if (!reference.isDirectlyReferenced()) {

            //
            if (includeIndirectReferences && reference.isIndirectlyReferenced()) {
              result.add(reference);
            }

          } else {
            result.add(reference);
          }
        }
      }

      // step 2
      for (IType type : resource.getContainedTypes()) {

        for (IReference reference : type.getReferences()) {

          if (!hideContainedTypes || !getContainedTypeNames().contains(reference.getFullyQualifiedName())) {

            if (!reference.isDirectlyReferenced()) {

              //
              if (includeIndirectReferences && reference.isIndirectlyReferenced()) {
                result.add(reference);
              }

            } else {
              result.add(reference);
            }
          }
        }
      }
    }
  }
}
