package org.bundlemaker.core._type.internal;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.IReference;
import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.ITypeModule;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.modext.ICacheAwareModularizedSystem;
import org.bundlemaker.core.spi.modext.IModelExtension;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParserContext;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;

public class ModelExtension implements IModelExtension {

  /** - */
  private static final String TYPE_CACHE_KEY = ModelExtension.class.getName() + "#TYPE_CACHE_KEY";

  /** - */
  private TypeCache           _typeCache;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {

    //
    Platform.getAdapterManager().registerAdapters(new TypeModularizedSystemAdapterFactory(), IModularizedSystem.class);
    Platform.getAdapterManager().registerAdapters(new TypeResourceAdapterFactory(), IModuleResource.class);
    Platform.getAdapterManager().registerAdapters(new TypeModuleAdapterFactory(), IModule.class);
  }

  @Override
  public void prepareStoredModel(IProjectContentEntry projectContentEntry,
      Map<IProjectContentResource, ? extends IParsableResource> storedResourcesMap) {

    //
    _typeCache = new TypeCache();
    _typeCache.setupTypeCache(projectContentEntry, storedResourcesMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void beforeParse(IProjectContentEntry projectContent, IParserContext parserContext,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().put(TYPE_CACHE_KEY, getTypeCache());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterParse(IProjectContentEntry projectContent, IParserContext parserContext,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().remove(TYPE_CACHE_KEY);
  }

  /**
   * {@inheritDoc}
   */
  public void prepareAnalysisModel(IModule[] modules, ArtifactCache artifactCache) {

    // MISSING TYPES: create virtual module for missing types
    if (artifactCache.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {

      // add the
      for (IModule module : modules) {
        if (module instanceof IModifiableModule) {
          for (IReference iReference : artifactCache.getModularizedSystem()
              .adaptAs(ITypeModularizedSystem.class).getUnsatisfiedReferences((IModifiableModule) module)) {
            artifactCache.getTypeCache().getOrCreate(new TypeKey(iReference.getFullyQualifiedName()));
          }
        }
      }
    }
  }

  @Override
  public void setupResourceArtifact(IResourceArtifact resourceArtifact, IModuleResource resource) {

    ITypeResource typeResource = resource.adaptAs(ITypeResource.class);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private TypeCache getTypeCache() {

    if (_typeCache == null) {
      _typeCache = new TypeCache();
    }

    return _typeCache;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private class TypeResourceAdapterFactory implements IAdapterFactory {

    /**
     * {@inheritDoc}
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {

      //
      if (adapterType == ITypeResource.class || adapterType == IParsableTypeResource.class) {

        IModuleResource moduleResource = (IModuleResource) adaptableObject;

        if (moduleResource.getModelExtension() == null) {

          if (adaptableObject instanceof IParsableResource) {
            IParsableResource parsableResource = (IParsableResource) adaptableObject;
            parsableResource.setModelExtension(new TypeResource(getTypeCache()));
          }
        }

        return moduleResource.getModelExtension();
      }

      //
      return null;
    }

    /**
     * {@inheritDoc}
     */
    public Class[] getAdapterList() {
      return new Class[] { ITypeResource.class, IParsableTypeResource.class };
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private class TypeModularizedSystemAdapterFactory implements IAdapterFactory {

    /**
     * {@inheritDoc}
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {

      //
      if (adapterType == ITypeModularizedSystem.class) {

        ICacheAwareModularizedSystem modularizedSystem = (ICacheAwareModularizedSystem) adaptableObject;

        if (!modularizedSystem.getUserAttributes().containsKey(ITypeModularizedSystem.class.getName())) {

          //
          final TypeModularizedSystem typeModularizedSystem = new TypeModularizedSystem(
              modularizedSystem);

          modularizedSystem.getUserAttributes().put(ITypeModularizedSystem.class.getName(), typeModularizedSystem);
        }

        return modularizedSystem.getUserAttributes().get(ITypeModularizedSystem.class.getName());
      }

      //
      return null;
    }

    /**
     * {@inheritDoc}
     */
    public Class[] getAdapterList() {
      return new Class[] { ITypeModularizedSystem.class };
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private class TypeModuleAdapterFactory implements IAdapterFactory {

    /**
     * {@inheritDoc}
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {

      //
      if (adapterType == ITypeModule.class) {

        IModule module = (IModule) adaptableObject;

        if (!module.getUserAttributes().containsKey(ITypeModule.class.getName())) {
          module.getUserAttributes().put(ITypeModule.class.getName(),
              new TypeModule(module));
        }

        return module.getUserAttributes().get(ITypeModule.class.getName());
      }

      //
      return null;
    }

    /**
     * {@inheritDoc}
     */
    public Class[] getAdapterList() {
      return new Class[] { ITypeModule.class };
    }
  }
}
