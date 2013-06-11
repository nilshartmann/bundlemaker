package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.analysis.ITypeArtifact;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.internal.analysis.AdapterType2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;
import org.bundlemaker.core.internal.analysis.cache.TypeKey;
import org.bundlemaker.core.internal.analysis.virtual.VirtualType2IArtifact;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all type artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeSubCache extends AbstractSubCache<TypeKey, ITypeArtifact> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link TypeSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public TypeSubCache(ArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ITypeArtifact create(TypeKey type) {

    Assert.isNotNull(type);

    // step 1: if the type contains a 'real' type, we have to create a real type artifact...
    if (type.hasType()) {
      return createTypeArtifactFromType(type.getType());
    }

    // step 2: ...otherwise we have to create a 'virtual' one
    else {
      return createTypeArtifactFromTypeName(type.getTypeName());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeName
   * @return
   */
  private ITypeArtifact createTypeArtifactFromTypeName(String typeName) {

    //
    IBundleMakerArtifact parent = null;

    //
    int index = typeName.lastIndexOf('.');

    //
    if (index != -1) {

      // get the module package
      ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey("<< Missing Types >>"),
          typeName.substring(0, index));

      // get the parent
      parent = getArtifactCache().getPackageCache().getOrCreate(modulePackageKey);

    } else {
      parent = getArtifactCache().getModuleCache().getOrCreate(new ModuleKey("<< Missing Types >>"));
    }

    //
    return new VirtualType2IArtifact(typeName.substring(index + 1), typeName, parent);
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  private ITypeArtifact createTypeArtifactFromType(IType type) {

    AbstractArtifactContainer parent = getTypeParent(type);

    //
    return new AdapterType2IArtifact(type, getArtifactCache(), parent);
  }

  /**
   * <p>
   * Returns the parent (package or resource) artifact for the given type.
   * </p>
   * 
   * @param type
   * @return
   */
  public AbstractArtifactContainer getTypeParent(IType type) {

    Assert.isNotNull(type);

    // get the associated resources
    IModuleResource resource = null;

    resource = getArtifactCache().getConfiguration().getContentType().equals(ResourceType.SOURCE)
        && type.hasSourceResource() ? type.getSourceResource() : type.getBinaryResource();

    // get the associated module
    IModule module = resource != null ?
        resource.getModule(getArtifactCache().getModularizedSystem())
        : type.getModule(getArtifactCache().getModularizedSystem());

    if (resource != null && module.isResourceModule()) {

      if (resource == null) {
        System.out.println("Type without resource: " + type);
      }

      // force cast
      return (AbstractArtifactContainer) getArtifactCache().getResourceCache().getOrCreate(resource);

    } else {

      // get the module package
      ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(module), type.getPackageName());

      // get the parent
      return getArtifactCache().getPackageCache().getOrCreate(modulePackageKey);
    }
  }
}