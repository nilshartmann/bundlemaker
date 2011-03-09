package org.bundlemaker.core.osgi.manifest.internal.importresolver;

import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.GenericCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReferencesCache {

  /** - */
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
   * @return
   */
  public GenericCache<String, Set<IModule>> getReferenceTypeToExportingModuleCache() {
    return _typeToModuleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public GenericCache<String, Set<String>> getReferencedPackageToContainingTypesCache() {
    return _packageToTypesCache;
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
   */
  private void initializeCaches() {

    //
    for (String typeName : _resourceModule.getReferencedTypeNames(true, _includeSourceReferences,
        _includeIndirectReferences)) {

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
