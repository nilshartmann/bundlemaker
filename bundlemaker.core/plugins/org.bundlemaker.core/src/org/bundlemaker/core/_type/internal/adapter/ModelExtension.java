package org.bundlemaker.core._type.internal.adapter;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.IParsableTypeResource;
import org.bundlemaker.core._type.ITypeModularizedSystem;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core._type.internal.TypeCache;
import org.bundlemaker.core._type.internal.TypeModularizedSystem;
import org.bundlemaker.core._type.internal.TypeResource;
import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModuleResource;
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
      Set<IResourceStandin> newAndModifiedBinaryResources, Set<IResourceStandin> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().put(TYPE_CACHE_KEY, _typeCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterParse(IProjectContentEntry projectContent, IParserContext parserContext,
      Set<IResourceStandin> newAndModifiedBinaryResources, Set<IResourceStandin> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().remove(TYPE_CACHE_KEY);
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
            parsableResource.setModelExtension(new TypeResource(_typeCache));
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

        IModularizedSystem modularizedSystem = (IModularizedSystem) adaptableObject;

        if (!modularizedSystem.getUserAttributes().containsKey(ITypeModularizedSystem.class.getName())) {
          modularizedSystem.getUserAttributes().put(ITypeModularizedSystem.class.getName(),
              new TypeModularizedSystem(modularizedSystem.getBundleMakerProject()));
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
}
