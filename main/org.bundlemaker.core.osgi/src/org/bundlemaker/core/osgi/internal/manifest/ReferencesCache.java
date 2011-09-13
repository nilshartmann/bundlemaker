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
package org.bundlemaker.core.osgi.internal.manifest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.query.ReferenceQueryFilters;
import org.bundlemaker.core.util.StopWatch;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencesCache {

  /**  */
  private IModularizedSystem                 _modularizedSystem;

  /** - */
  private IResourceModule                    _resourceModule;

  /** - */
  private GenericCache<String, Set<IModule>> _typeToModuleCache;

  /** - */
  private GenericCache<String, Set<String>>  _packageToTypesCache;

  /** - */
  private Set<String>                        _unsatisfiedTypes;

  /** - */
  boolean                                    _includeSourceReferences;

  /** - */
  boolean                                    _includeIndirectReferences;

  /**
   * <p>
   * Creates a new instance of type {@link ReferencesCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param resourceModule
   * @param includeSourceReferences
   * @param includeIndirectReferences
   */
  public ReferencesCache(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      boolean includeSourceReferences, boolean includeIndirectReferences) {

    //
    _modularizedSystem = modularizedSystem;
    _resourceModule = resourceModule;
    _includeSourceReferences = includeSourceReferences;
    _includeIndirectReferences = includeIndirectReferences;

    //
    _typeToModuleCache = new GenericCache<String, Set<IModule>>() {
      @Override
      protected Set<IModule> create(String key) {
        return new HashSet<IModule>();
      }
    };

    //
    _packageToTypesCache = new GenericCache<String, Set<String>>() {
      @Override
      protected Set<String> create(String key) {
        return new HashSet<String>();
      }
    };

    //
    _unsatisfiedTypes = new HashSet<String>();

    //
    initializeCaches();
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @return
   */
  public List<IModule> getExportingModules(String packageName) {

    //
    List<IModule> result = new ArrayList<IModule>();

    //
    Set<String> types = getReferencedPackageToContainingTypesCache().get(packageName);

    if (types == null) {
      return result;
    }

    for (String type : types) {
      Set<IModule> module = getReferenceTypeToExportingModuleCache().get(type);
      if (module != null) {
        result.addAll(module);
      }
    }

    //
    return reduce(result, types);
  }

  /**
   * <p>
   * </p>
   * 
   * @param exportingModules
   * @param typeNames
   * @return
   */
  public List<IModule> reduce(List<IModule> exportingModules, Set<String> typeNames) {

    Assert.isNotNull(exportingModules);
    Assert.isNotNull(typeNames);

    //
    for (IModule module : exportingModules) {
      if (module.containsAll(typeNames)) {
        List<IModule> result = new LinkedList<IModule>();
        result.add(module);
        return result;
      }
    }

    //
    return exportingModules;
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @return
   */
  public boolean hasExportingModules(String packageName) {

    //
    return getExportingModules(packageName).size() > 0;
  }

  /**
   * <p>
   * </p>
   * 
   * @param packageName
   * @return
   */
  public boolean containsUnsatisfiedTypes(String packageName) {

    //
    Set<String> typeNames = getReferencedPackageToContainingTypesCache().get(packageName);

    //
    for (String typeName : typeNames) {
      if (getUnsatisfiedTypes().contains(typeName)) {
        return true;
      }
    }

    //
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<String> getUnsatisfiedTypes() {
    return _unsatisfiedTypes;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private GenericCache<String, Set<IModule>> getReferenceTypeToExportingModuleCache() {
    return _typeToModuleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private GenericCache<String, Set<String>> getReferencedPackageToContainingTypesCache() {
    return _packageToTypesCache;
  }

  /**
   * <p>
   * </p>
   */
  private void initializeCaches() {

    //
    Set<String> typeNames = _resourceModule.getReferencedTypeNames(ReferenceQueryFilters.createReferenceFilter(true,
        _includeSourceReferences, true, true, _includeIndirectReferences));

    //
    for (String typeName : typeNames) {

      // get the package type
      String packageName = typeName.contains(".") ? typeName.substring(0, typeName.lastIndexOf('.')) : "";

      // add to the package to type cache
      _packageToTypesCache.getOrCreate(packageName).add(typeName);

      // get the modules
      Set<IModule> modules = _modularizedSystem.getTypeContainingModules(typeName);

      // add to the type caches
      if (modules.isEmpty()) {
        _unsatisfiedTypes.add(typeName);
      } else {
        _typeToModuleCache.getOrCreate(typeName).addAll(modules);
      }
    }
  }
}
