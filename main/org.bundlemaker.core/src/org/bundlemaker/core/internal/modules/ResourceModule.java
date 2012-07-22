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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.IResourceContainer;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters.ReferenceFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceModule extends AbstractModule<IResourceContainer, ResourceContainer> implements
    IModifiableResourceModule {

  /**
   * <p>
   * Creates a new instance of type {@link ResourceModule}.
   * </p>
   * 
   * @param moduleIdentifier
   */
  public ResourceModule(IModuleIdentifier moduleIdentifier, IModularizedSystem modularizedSystem) {
    super(moduleIdentifier, modularizedSystem, new ResourceContainer(modularizedSystem));
    ((ResourceContainer) getSelfResourceContainer()).setResourceModule(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResource(String path, ContentType contentType) {
    return getResource(path, contentType) != null;
  }

  @Override
  public List<IMovableUnit> getMovableUnits() {

    // create the result set
    final List<IMovableUnit> result = new LinkedList<IMovableUnit>();

    //
    doWithAllContainers(new ContainerClosure<ResourceContainer>() {
      @Override
      public boolean doWithContainer(ResourceContainer resourceContainer) {
        result.addAll(resourceContainer.getMovableUnits());
        return false;
      }
    });

    // return the result
    return Collections.unmodifiableList(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IReference> getReferences(final IQueryFilter<IReference> filter) {

    if (filter instanceof ReferenceFilter) {
      ((ReferenceFilter) filter).setResourceModule(this);
    }

    // create the result set
    final Set<IReference> result = new HashSet<IReference>();

    //
    doWithAllContainers(new ContainerClosure<ResourceContainer>() {
      @Override
      public boolean doWithContainer(ResourceContainer resourceContainer) {
        result.addAll(resourceContainer.getReferences(filter));
        return false;
      }
    });

    // return the result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getResource(final String path, final ContentType contentType) {

    // create the result set
    final IResource[] result = new IResource[1];

    //
    doWithAllContainers(new ContainerClosure<ResourceContainer>() {
      @Override
      public boolean doWithContainer(ResourceContainer resourceContainer) {
        result[0] = resourceContainer.getResource(path, contentType);
        return result[0] != null;
      }
    });

    // return the result
    return result[0];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IResource> getResources(final ContentType contentType) {

    // create the result set
    final Set<IResource> result = new HashSet<IResource>();

    //
    doWithAllContainers(new ContainerClosure<ResourceContainer>() {
      @Override
      public boolean doWithContainer(ResourceContainer resourceContainer) {
        result.addAll(resourceContainer.getResources(contentType));
        return false;
      }
    });

    // return the result
    return Collections.unmodifiableSet(result);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceContainer getSelfResourceContainer() {
    return getModifiableSelfResourceContainer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, ? extends IResourceContainer> getContainedResourceContainers() {
    return getEmbeddedContainers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResourceModule getResourceModule() {
    return this;
  }

  @Override
  public boolean containsSources() {
    return !getResources(ContentType.SOURCE).isEmpty();
  }

  // TODO
  public void validate() {

    //
    Map<String, IResource> entries = new HashMap<String, IResource>();

    //
    for (IResource resource : getResources(ContentType.SOURCE)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }

    //
    entries.clear();
    for (IResource resource : getResources(ContentType.BINARY)) {

      if (entries.containsKey(resource.getPath())) {

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : "
            + entries.get(resource.getPath()).getRoot() + " : " + entries.get(resource.getPath()).getPath());

        //
        System.out.println("DUPLICATE ENTRY in " + getModuleIdentifier().toString() + " : " + resource.getRoot()
            + " : " + resource.getPath());
      } else {

        //
        entries.put(resource.getPath(), resource);
      }
    }
  }
}
