package org.bundlemaker.core.internal.analysis.transformer;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.internal.analysis.AdapterModularizedSystem2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache.ModuleKey;
import org.bundlemaker.core.internal.analysis.transformer.caches.PackageCache;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractCacheAwareArtifactCache extends AbstractArtifactCache {

  /** - */
  private static final String                                        MISSING_TYPES = "<< Missing Types >>";

  /** - */
  private GenericCache<IPath, AbstractArtifactContainer>             _groupCache;

  /** - */
  private ModuleCache                                                _moduleCache;

  /** - */
  private PackageCache                                               _packageCache;

  /** - */
  private GenericCache<ModuleResourceKey, AbstractArtifactContainer> _resourceCache;

  /** - */
  private GenericCache<TypeKey, IArtifact>                           _typeCache;

  // /** - */
  // private VirtualArtifact _missingTypesVirtualModule;
  //
  // /** - */
  // private GenericCache<String, AbstractArtifactContainer> _missingTypeCache;
  //
  // /** - */
  // private GenericCache<String, AbstractArtifactContainer> _missingTypePackageCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AbstractCacheAwareArtifactCache(IModifiableModularizedSystem modularizedSystem) {
    this(modularizedSystem, new AdapterModularizedSystem2IArtifact(modularizedSystem));
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param rootArtifact
   */
  protected AbstractCacheAwareArtifactCache(IModularizedSystem modularizedSystem, IAdvancedArtifact rootArtifact) {
    super(modularizedSystem, rootArtifact);

    // initialize the caches
    _groupCache = createGroupCache();
    _moduleCache = createModuleCache();
    _packageCache = createPackageCache();
    _resourceCache = createResourceCache();
    _typeCache = createTypeCache();
  }

  // /**
  // * <p>
  // * </p>
  // */
  // public void initializeMissingTypesCaches() {
  // // initialize the 'missing types' caches
  // _missingTypesVirtualModule = new VirtualArtifact(ArtifactType.Module, MISSING_TYPES, getRootArtifact());
  // _missingTypeCache = createMissingTypeCache();
  // _missingTypePackageCache = createMissingTypePackageCache();
  // }

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @return
   */
  public final IArtifact getModuleArtifact(IModule module) {

    //
    try {
      return _moduleCache.getOrCreate(new ModuleKey(module));
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public final IArtifact getTypeArtifact(IType type, boolean createIfMissing) {
    Assert.isNotNull(type);

    //
    try {
      if (createIfMissing) {
        return _typeCache.getOrCreate(new TypeKey(type));
      } else {
        return _typeCache.get(new TypeKey(type));
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public final AdapterResource2IArtifact getResourceArtifact(IResource resource) {
    Assert.isNotNull(resource);

    // create the key
    ModuleResourceKey key = new ModuleResourceKey(resource.getAssociatedResourceModule(getModularizedSystem()),
        resource);

    //
    try {
      return (AdapterResource2IArtifact) _resourceCache.getOrCreate(key);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullyQualifiedName
   * @throws Exception
   */
  public final IArtifact getTypeArtifact(String fullyQualifiedName, boolean createIfMissing) {

    //
    try {

      //
      IType targetType = getModularizedSystem().getType(fullyQualifiedName);

      //
      if (targetType == null) {
        if (createIfMissing) {
          return _typeCache.getOrCreate(new TypeKey(fullyQualifiedName));
        } else {
          return _typeCache.get(new TypeKey(fullyQualifiedName));
        }
      } else {
        return getTypeArtifact(targetType, createIfMissing);
      }
    } catch (AmbiguousElementException e) {
      System.err.println("AmbExc " + fullyQualifiedName);
      return null;
    }
  }

  // /**
  // * <p>
  // * Returns the {@link VirtualArtifact} for the <i>Missing Types</i> node.
  // * </p>
  // *
  // * @return the {@link VirtualArtifact} for the <i>Missing Types</i> node.
  // */
  // public final IArtifact getMissingTypesVirtualModule() {
  // return _missingTypesVirtualModule;
  // }
  //
  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public final GenericCache<String, AbstractArtifactContainer> getMissingTypeCache() {
  // return _missingTypeCache;
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public final GenericCache<String, AbstractArtifactContainer> getMissingTypePackageCache() {
  // return _missingTypePackageCache;
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final GenericCache<IPath, AbstractArtifactContainer> getGroupCache() {
    return _groupCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ModuleCache getModuleCache() {
    return _moduleCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final PackageCache getPackageCache() {
    return _packageCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final GenericCache<ModuleResourceKey, AbstractArtifactContainer> getResourceCache() {
    return _resourceCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final GenericCache<TypeKey, IArtifact> getTypeCache() {
    return _typeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract ModuleCache createModuleCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract GenericCache<IPath, AbstractArtifactContainer> createGroupCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract GenericCache<TypeKey, IArtifact> createTypeCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract GenericCache<ModuleResourceKey, AbstractArtifactContainer> createResourceCache();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected abstract PackageCache createPackageCache();

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class TypeKey {

    /** - */
    private String _typeName;

    /** - */
    private IType  _type;

    /**
     * <p>
     * Creates a new instance of type {@link TypeKey}.
     * </p>
     * 
     * @param typeName
     */
    public TypeKey(String typeName) {
      Assert.isNotNull(typeName);

      _typeName = typeName;
    }

    /**
     * <p>
     * Creates a new instance of type {@link TypeKey}.
     * </p>
     * 
     * @param type
     */
    public TypeKey(IType type) {
      Assert.isNotNull(type);

      _type = type;
    }

    public final boolean hasType() {
      return _type != null;
    }

    public final String getTypeName() {
      return _typeName;
    }

    public final IType getType() {
      return _type;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_type == null) ? 0 : _type.hashCode());
      result = prime * result + ((_typeName == null) ? 0 : _typeName.hashCode());
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
      TypeKey other = (TypeKey) obj;
      if (_type == null) {
        if (other._type != null)
          return false;
      } else if (!_type.equals(other._type))
        return false;
      if (_typeName == null) {
        if (other._typeName != null)
          return false;
      } else if (!_typeName.equals(other._typeName))
        return false;
      return true;
    }
  }
}
