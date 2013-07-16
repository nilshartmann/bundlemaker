package org.bundlemaker.core.jtype.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.jtype.IParsableTypeResource;
import org.bundlemaker.core.jtype.IReference;
import org.bundlemaker.core.jtype.IType;
import org.bundlemaker.core.jtype.ITypeModularizedSystem;
import org.bundlemaker.core.jtype.ITypeModule;
import org.bundlemaker.core.jtype.ITypeResource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.modext.IAnalysisModelContext;
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

  /** - */
  private TypeArtifactCache   _typeSubCache;

  /**
   * {@inheritDoc}
   */
  @Override
  public void initializeModelExtension() {

    //
    Platform.getAdapterManager().registerAdapters(new TypeModularizedSystemAdapterFactory(), IModularizedSystem.class);
    Platform.getAdapterManager().registerAdapters(new TypeResourceAdapterFactory(), IModuleResource.class);
    Platform.getAdapterManager().registerAdapters(new TypeModuleAdapterFactory(), IModule.class);
  }

  @Override
  public void prepareStoredResourceModel(IProjectContentEntry projectContentEntry,
      Map<IProjectContentResource, ? extends IParsableResource> storedResourcesMap) {

    //
    _typeCache = new TypeCache();
    _typeCache.setupTypeCache(projectContentEntry, storedResourcesMap);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void beforeParseResourceModel(IProjectContentEntry projectContent, IParserContext parserContext,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().put(TYPE_CACHE_KEY, getTypeCache());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterParseResourceModel(IProjectContentEntry projectContent, IParserContext parserContext,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    parserContext.getProjectContentSpecificUserAttributes().remove(TYPE_CACHE_KEY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resourceModelSetupCompleted(IProjectContentEntry contentEntry, Set<IModuleResource> binaryResources,
      Set<IModuleResource> sourceResources) {

    for (IModuleResource resourceStandin : binaryResources) {
      connectParsedResourceToModel(resourceStandin, false);
    }

    for (IModuleResource resourceStandin : sourceResources) {
      connectParsedResourceToModel(resourceStandin, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void prepareAnalysisModel(IModule[] modules, IAnalysisModelContext context) {

    //
    _typeSubCache = new TypeArtifactCache(context);

    // MISSING TYPES: create virtual module for missing types
    if (context.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {

      // add the
      for (IModule module : modules) {
        for (IReference iReference : context.getModularizedSystem()
            .adaptAs(ITypeModularizedSystem.class).getUnsatisfiedReferences(module)) {
          _typeSubCache.getOrCreate(new TypeKey(iReference.getFullyQualifiedName()));
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean shouldAddResourceArtifact(IModuleResource resource) {

    //
    ITypeResource typeResource = resource.adaptAs(ITypeResource.class);

    if (!typeResource.containsTypes()) {
      return true;
    }

    //
    for (IType type : typeResource.getContainedTypes()) {

      // filter local or anonymous type names
      if (!type.isLocalOrAnonymousType()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public void setupResourceArtifact(IResourceArtifact resourceArtifact, IModuleResource resource) {

    //
    ITypeResource typeResource = resource.adaptAs(ITypeResource.class);

    //
    for (IType type : typeResource.getContainedTypes()) {
      // create the artifact
      _typeSubCache.getTypeArtifact(type, true);
    }
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
   * {@inheritDoc}
   */
  private void connectParsedResourceToModel(IModuleResource resource, boolean isSource) {

    // set the references
    Set<Reference> resourceReferences = new HashSet<Reference>();
    for (Reference reference : resource.adaptAs(IParsableTypeResource.class).getModifiableReferences()) {
      Reference newReference = new Reference(reference);
      newReference.setResource(resource);
      resourceReferences.add(newReference);
    }
    resource.adaptAs(IParsableTypeResource.class).getModifiableReferences().clear();
    resource.adaptAs(IParsableTypeResource.class).getModifiableReferences().addAll(resourceReferences);

    // set the type-back-references
    for (Type type : resource.adaptAs(IParsableTypeResource.class).getModifiableContainedTypes()) {

      //
      if (isSource) {
        type.setSourceResource(resource);
      } else {
        type.setBinaryResource(resource);
      }

      // set the references
      Map<String, Reference> typeReferences = new HashMap<String, Reference>();
      for (Reference reference : type.getModifiableReferences()) {
        // TODO
        if (reference == null) {
          continue;
        }
        Reference newReference = new Reference(reference);
        newReference.setType(type);
        if (typeReferences.containsKey(newReference)) {
          throw new RuntimeException();
        } else {
          typeReferences.put(newReference.getFullyQualifiedName(), newReference);
        }
      }
      type.getModifiableReferences().clear();
      type.getModifiableReferences().addAll(typeReferences.values());
    }
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
