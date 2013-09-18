package org.bundlemaker.core.jtype.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.jtype.IReference;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeModularizedSystem;
import org.bundlemaker.core.jtype.ITypeModule;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.jtype.ITypeSelector;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.modext.ICacheAwareModularizedSystem;
import org.bundlemaker.core.spi.modext.ICacheCallback;
import org.eclipse.core.runtime.Assert;

public class TypeModularizedSystem implements ITypeModularizedSystem, ICacheCallback {

  /** type name -> type */
  private GenericCache<String, Set<IType>>  _typeNameToTypeCache;

  /** type name -> referring type */
  private GenericCache<String, Set<IType>>  _typeNameToReferringCache;

  /** type -> module */
  private GenericCache<IType, Set<IModule>> _typeToModuleCache;

  /** - */
  private List<ITypeSelector>               _moduleSelectors;

  /** - */
  private DefaultTypeSelector               _defaultTypeSelector;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractQueryableModularizedSystem}.
   * </p>
   * 
   * @param name
   * @param system
   */
  public TypeModularizedSystem(ICacheAwareModularizedSystem system) {

    //
    _moduleSelectors = new LinkedList<ITypeSelector>();

    //
    _defaultTypeSelector = new DefaultTypeSelector(system.getBundleMakerProject().getProjectDescription());
    // _defaultTypeSelector.setPreferJdkTypes(true);

    //
    for (IModule module : system.getModules()) {
      for (IMovableUnit movableUnit : module.getMovableUnits()) {
        movableUnitAdded(movableUnit, module);
      }
    }

    //
    system.registerCacheCallback(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ITypeSelector> getTypeSelectors() {
    return _moduleSelectors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(String fullyQualifiedName) {
    return getType(fullyQualifiedName, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IType getType(String fullyQualifiedName, IModule referencingModule) {

    // assert
    Assert.isNotNull(fullyQualifiedName);

    // get type modules
    Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);

    // return null if type is unknown
    if (types == null || types.isEmpty()) {
      return null;
    }

    // if multiple type modules exist, throw an exception
    if (types.size() > 1) {

      //
      for (ITypeSelector moduleSelector : _moduleSelectors) {
        IType type = moduleSelector.selectType(referencingModule, fullyQualifiedName, types);
        if (type != null) {
          return type;
        }
      }

      return _defaultTypeSelector.selectType(referencingModule, fullyQualifiedName, types);
      // throw new AmbiguousElementException(fullyQualifiedName);
    }

    // return the type
    return types.toArray(new IType[0])[0];
  }

  @Override
  public Set<IType> getTypes() {
    return Collections.unmodifiableSet(getTypeToModuleCache().keySet());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IType> getTypes(String fullyQualifiedName) {
    return getTypes(fullyQualifiedName, null);
  }

  @Override
  public Set<IType> getTypes(String fullyQualifiedName, IModule referencingModule) {
    //
    Assert.isNotNull(fullyQualifiedName);
    Assert.isTrue(fullyQualifiedName.trim().length() > 0);

    // get type modules
    Set<IType> types = getTypeNameToTypeCache().get(fullyQualifiedName);
    types = types != null ? types : new HashSet<IType>();

    // return the result
    return Collections.unmodifiableSet(types);
  }

  @Override
  public Set<IReference> getUnsatisfiedReferences(IModule resourceModule) {

    //
    Set<IReference> result = new HashSet<IReference>();

    //
    Set<IReference> references = resourceModule.adaptAs(ITypeModule.class)
        .getReferences();

    for (IReference iReference : references) {
      if (getType(iReference.getFullyQualifiedName(), resourceModule) == null) {
        result.add(iReference);
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO set to protected
  public final GenericCache<String, Set<IType>> getTypeNameToTypeCache() {

    //
    if (_typeNameToTypeCache == null) {

      // create _typeNameToTypeCache
      _typeNameToTypeCache = new GenericCache<String, Set<IType>>() {
        @Override
        protected Set<IType> create(String key) {
          return new HashSet<IType>();
        }
      };
    }

    //
    return _typeNameToTypeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Map<String, Set<IType>> getReferencedTypes() {
    return Collections.unmodifiableMap(_typeNameToReferringCache);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  // TODO: incremental updates - replace with API
  public GenericCache<String, Set<IType>> getTypeNameToReferringCache() {

    //
    if (_typeNameToReferringCache == null) {

      // create _typeNameToReferringCache
      _typeNameToReferringCache = new GenericCache<String, Set<IType>>() {
        @Override
        protected Set<IType> create(String key) {
          return new HashSet<IType>();
        }
      };
    }

    //
    return _typeNameToReferringCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final GenericCache<IType, Set<IModule>> getTypeToModuleCache() {

    //
    if (_typeToModuleCache == null) {

      // create _typeToModuleCache
      _typeToModuleCache = new GenericCache<IType, Set<IModule>>() {
        @Override
        protected Set<IModule> create(IType type) {
          return new HashSet<IModule>();
        }
      };
    }

    return _typeToModuleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public IModule getAssociatedModule(IType type) {

    //
    Assert.isNotNull(type);

    //
    Set<IModule> modules = _typeToModuleCache.get(type);

    //
    if (modules == null || modules.isEmpty()) {
      return null;
    } else if (modules.size() > 1) {
      throw new RuntimeException("Type is contained in multiple modules.");
    } else {
      return modules.toArray(new IModule[0])[0];
    }
  }

  public void internalTypeChanged(IType type, IModule module, ChangeAction action) {
    switch (action) {
    case ADDED: {

      // step 1: type -> module
      getTypeToModuleCache().getOrCreate(type).add(module);

      // step 2: type name -> type
      getTypeNameToTypeCache().getOrCreate(type.getFullyQualifiedName()).add(type);

      // step 3: referenced type name -> type
      for (IReference reference : type.getReferences()) {
        getTypeNameToReferringCache().getOrCreate(reference.getFullyQualifiedName()).add(type);
      }

      //
      ITypeModule typeModule = module.adaptAs(ITypeModule.class);
      typeModule.add(type);

      //
      break;
    }
    case REMOVED: {

      //
      ITypeModule typeModule = module.adaptAs(ITypeModule.class);
      typeModule.remove(type);

      // step 2a: type -> module
      Set<IModule> typeModules = _typeToModuleCache.get(type);
      if (typeModules != null) {
        typeModules.remove(module);
        if (typeModules.isEmpty()) {
          _typeToModuleCache.remove(type);

          // step 2b: type name -> type
          Set<IType> types = _typeNameToTypeCache.get(type.getFullyQualifiedName());
          if (types != null) {

            // remove the type
            types.remove(type);

            // remove types if empty
            if (types.isEmpty()) {
              _typeNameToTypeCache.remove(type.getFullyQualifiedName());
            }
          }

          // step 2c: referenced type name -> type
          for (IReference reference : type.getReferences()) {
            Set<IType> referredTypes = _typeNameToReferringCache.get(reference.getFullyQualifiedName());
            if (referredTypes != null) {
              // remove the referred type
              referredTypes.remove(type);
              // remove referred types if empty
              if (referredTypes.isEmpty()) {
                _typeNameToReferringCache.remove(reference.getFullyQualifiedName());
              }
            }
          }
        }
      }

      break;
    }

    default: {
      throw new RuntimeException(String.format("Unkown ChangeAction '%s'!", action));
    }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param types
   * @param module
   * @param action
   */
  public void typesChanged(Collection<IType> types, IModule module, ChangeAction action) {
    //
    for (IType type : types) {
      internalTypeChanged(type, module, action);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @param module
   * @param action
   */
  public void typeChanged(IType type, IModule module, ChangeAction action) {

    //
    internalTypeChanged(type, module, action);
  }

  /**
   * {@inheritDoc}
   */
  public void clearCaches() {

    // clear all the caches
    getTypeNameToTypeCache().clear();
    getTypeNameToReferringCache().clear();
    getTypeToModuleCache().clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitAdded(IMovableUnit movableUnit, IModule module) {

    for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
      typesChanged(
          moduleResource.adaptAs(ITypeResource.class).getContainedTypes(),
          module,
          ChangeAction.ADDED);
    }

    if (movableUnit.hasAssociatedSourceResource()) {
      typesChanged(
          movableUnit.getAssociatedSourceResource().adaptAs(ITypeResource.class).getContainedTypes(),
          module,
          ChangeAction.ADDED);
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void movableUnitRemoved(IMovableUnit movableUnit, IModule module) {

    for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
      typesChanged(
          moduleResource.adaptAs(ITypeResource.class).getContainedTypes(),
          module,
          ChangeAction.REMOVED);
    }

    if (movableUnit.hasAssociatedSourceResource()) {
      typesChanged(
          movableUnit.getAssociatedSourceResource().adaptAs(ITypeResource.class).getContainedTypes(),
          module,
          ChangeAction.REMOVED);
    }
  }

  private enum ChangeAction {
    ADDED, REMOVED;
  }
}
