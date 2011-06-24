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
package org.bundlemaker.core.internal.modules.modularizedsystem;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.internal.modules.ReferencedModulesQueryResult;
import org.bundlemaker.core.internal.modules.algorithm.ResourceIsReferencedTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.ResourceReferencesTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.TypeIsReferencedTransitiveClosure;
import org.bundlemaker.core.internal.modules.algorithm.TypeReferencesTransitiveClosure;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystem extends AbstractValidatingModularizedSystem {

  /** the referenced modules cache */
  private GenericCache<ModuleAndQueryFilterKey, IReferencedModulesQueryResult> _referencedModulesCache;

  /**
   * <p>
   * Creates a new instance of type {@link ModularizedSystem}.
   * </p>
   * 
   * @param name
   */
  public ModularizedSystem(String name, IBundleMakerProjectDescription projectDescription) {

    //
    super(name, projectDescription);

    //
    _referencedModulesCache = new GenericCache<ModuleAndQueryFilterKey, IReferencedModulesQueryResult>() {
      @Override
      protected IReferencedModulesQueryResult create(ModuleAndQueryFilterKey key) {
        System.out.println("Create " + key.getResourceModule().getModuleIdentifier());
        return internalGetReferencedModules(key.getResourceModule(), key.getQueryFilter());
      }
    };
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Set<IType>> getAmbiguousTypes() {

    // create the result
    Map<String, Set<IType>> result = new HashMap<String, Set<IType>>();

    //
    for (Entry<String, Set<IType>> entry : getTypeNameToTypeCache().getMap().entrySet()) {

      if (entry.getValue().size() > 1) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IType> getTypeReferencesTransitiveClosure(String typeName, IQueryFilter<IType> filter) {

    TypeReferencesTransitiveClosure closure = new TypeReferencesTransitiveClosure(this);
    closure.resolveType(typeName, filter);
    return closure.getTypes();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IResource> getResourceReferencesTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IType> queryFilter) {

    ResourceReferencesTransitiveClosure closure = new ResourceReferencesTransitiveClosure(this);
    closure.resolveResource(resource, contentType, queryFilter);
    return closure.getResources();
  }

  @Override
  public Collection<IResource> getResourceIsReferencedTransitiveClosure(IResource resource, ContentType contentType,
      IQueryFilter<IResource> queryFilter) {

    ResourceIsReferencedTransitiveClosure closure = new ResourceIsReferencedTransitiveClosure(this);
    closure.resolveResource(resource, contentType, queryFilter);
    return closure.getResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName, IQueryFilter<IType> filter) {

    TypeIsReferencedTransitiveClosure closure = new TypeIsReferencedTransitiveClosure(this);
    closure.resolveType(typeName, filter);
    return closure.getTypes();
  }

  @Override
  public Set<IType> getReferencingTypes(String fullyQualifiedName) {

    Set<IType> result = getTypeNameToReferringCache().get(fullyQualifiedName);

    //
    if (result == null) {
      result = Collections.emptySet();
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IReferencedModulesQueryResult getReferencedModules(IResourceModule resourceModule,
      IQueryFilter<IReference> referencesFilter) {

    // assert is not null
    Assert.isNotNull(resourceModule);

    // return the result
    return _referencedModulesCache.getOrCreate(new ModuleAndQueryFilterKey(resourceModule,
        referencesFilter != null ? referencesFilter : ReferenceQueryFilters.ALL_REFERENCES_QUERY_FILTER));
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param referencesFilter
   * @return
   */
  private IReferencedModulesQueryResult internalGetReferencedModules(IResourceModule resourceModule,
      IQueryFilter<IReference> referencesFilter) {

    // assert is not null
    Assert.isNotNull(resourceModule);

    // create the result set
    ReferencedModulesQueryResult result = new ReferencedModulesQueryResult(resourceModule);

    // iterate over all the references
    for (IReference reference : resourceModule.getReferences(referencesFilter != null ? referencesFilter
        : ReferenceQueryFilters.ALL_REFERENCES_QUERY_FILTER)) {

      // get the referenced module...
      IModule referencedModule = getTypeContainingModule(reference.getFullyQualifiedName(), resourceModule);

      // ...add it to the result
      if (referencedModule != null) {
        result.getModifiableReferencedModules().add(referencedModule);
      } else {
        result.getModifiableUnsatisfiedReferences().add(reference);
      }
    }

    // return the result
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IReferencedModulesQueryResult getTransitiveReferencedModules(IResourceModule resourceModule,
      IQueryFilter<IReference> referencesFilter) {

    // assert is not null
    Assert.isNotNull(resourceModule);

    // return the transitive closure
    ReferencedModulesQueryResult result = new ReferencedModulesQueryResult(resourceModule);

    // get the transitive referenced modules
    getTransitiveReferencedModules(resourceModule, referencesFilter, result);

    // return the result
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @param referencesFilter
   * @param result
   */
  private void getTransitiveReferencedModules(IResourceModule resourceModule,
      IQueryFilter<IReference> referencesFilter, ReferencedModulesQueryResult transitiveQueryResult) {

    // assert is not null
    Assert.isNotNull(resourceModule);
    Assert.isNotNull(referencesFilter);
    Assert.isNotNull(transitiveQueryResult);

    // get the referenced modules
    IReferencedModulesQueryResult queryResult = getReferencedModules(resourceModule, referencesFilter);

    //
    for (IModule referencedModule : queryResult.getReferencedModules()) {

      // cycle-check
      if (!(transitiveQueryResult.getModifiableReferencedModules().contains(referencedModule) || referencedModule
          .equals(transitiveQueryResult.getOrigin()))) {

        // add to transitive closure
        transitiveQueryResult.getModifiableReferencedModules().add(referencedModule);

        //
        if (referencedModule instanceof IResourceModule) {
          getTransitiveReferencedModules((IResourceModule) referencedModule, referencesFilter, transitiveQueryResult);
        }
      }
    }

    //
    transitiveQueryResult.getModifiableUnsatisfiedReferences().addAll(queryResult.getUnsatisfiedReferences());
  }

  // /**
  // * {@inheritDoc}
  // */
  // @Override
  // public Map<String, Set<IModule>> getAmbiguousPackages() {
  //
  // // create the result map
  // Map<String, Set<IModule>> result = new HashMap<String, Set<IModule>>();
  //
  // // create the temp map
  // Map<String, IModule> tempMap = new HashMap<String, IModule>();
  //
  // // iterate over all modules
  // for (IModule typeModule : getAllModules()) {
  //
  // // iterate over contained packages
  // for (String containedPackage : typeModule.getContainedPackageNames()) {
  //
  // // add
  // if (result.containsKey(containedPackage)) {
  //
  // result.get(containedPackage).add(typeModule);
  // }
  //
  // //
  // else if (tempMap.containsKey(containedPackage)) {
  //
  // //
  // Set<IModule> newSet = new HashSet<IModule>();
  //
  // //
  // result.put(containedPackage, newSet);
  //
  // // add module to module list
  // newSet.add(typeModule);
  // newSet.add(tempMap.remove(containedPackage));
  // }
  //
  // // put in the temp map
  // else {
  // tempMap.put(containedPackage, typeModule);
  // }
  // }
  // }
  //
  // // return the result
  // return result;
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param result
  // * @param fullyQualifiedType
  // */
  // @Deprecated
  // private void _resolveReferencedModules(ReferencedModulesQueryResult result, IReference reference) {
  //
  // Assert.isNotNull(result);
  // Assert.isNotNull(reference);
  //
  // //
  // Set<IModule> containingModules = _getContainingModules(reference.getFullyQualifiedName());
  //
  // //
  // if (containingModules.isEmpty()) {
  //
  // //
  // result.getUnsatisfiedReferences().add(reference);
  //
  // } else if (containingModules.size() > 1) {
  //
  // if (!result.getReferencesWithAmbiguousModules().containsKey(reference)) {
  //
  // result.getReferencesWithAmbiguousModules().put(reference, new HashSet<IModule>());
  // }
  //
  // result.getReferencesWithAmbiguousModules().get(reference).addAll(containingModules);
  //
  // } else {
  //
  // result.getReferencedModulesMap().put(reference, containingModules.toArray(new IModule[0])[0]);
  // }
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param modularizedSystem
  // * @param fullyQualifiedName
  // * @return
  // */
  // private Set<IModule> _getContainingModules(String fullyQualifiedName) {
  //
  // //
  // if (getTypeNameToTypeCache().containsKey(fullyQualifiedName)) {
  //
  // //
  // Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
  // Set<IModule> result = new HashSet<IModule>();
  //
  // for (IType type : types) {
  // // TODO: direct call
  // result.add(type.getModule(this));
  // }
  //
  // return result;
  //
  // } else {
  // return Collections.emptySet();
  // }
  // }

  private static class ModuleAndQueryFilterKey {

    /** - */
    private IResourceModule          _resourceModule;

    /** - */
    private IQueryFilter<IReference> _queryFilter;

    /**
     * <p>
     * Creates a new instance of type {@link ModuleAndQueryFilterKey}.
     * </p>
     * 
     * @param resourceModule
     * @param queryFilter
     */
    public ModuleAndQueryFilterKey(IResourceModule resourceModule, IQueryFilter<IReference> queryFilter) {

      Assert.isNotNull(resourceModule);
      Assert.isNotNull(queryFilter);

      _resourceModule = resourceModule;
      _queryFilter = queryFilter;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IResourceModule getResourceModule() {
      return _resourceModule;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IQueryFilter<IReference> getQueryFilter() {
      return _queryFilter;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_queryFilter == null) ? 0 : _queryFilter.hashCode());
      result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ModuleAndQueryFilterKey other = (ModuleAndQueryFilterKey) obj;
      if (_queryFilter == null) {
        if (other._queryFilter != null)
          return false;
      } else if (!_queryFilter.equals(other._queryFilter))
        return false;
      if (_resourceModule == null) {
        if (other._resourceModule != null)
          return false;
      } else if (!_resourceModule.equals(other._resourceModule))
        return false;
      return true;
    }
  }
}
