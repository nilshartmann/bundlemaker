package org.bundlemaker.core.internal.modelext;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.modext.IModelExtension;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParserContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModelExtFactory {

  /** - */
  private IBundleMakerExtensionRegistry<IModelExtension> _modelExtensionRegistry;

  /**
   * <p>
   * Creates a new instance of type {@link ModelExtFactory}.
   * </p>
   */
  public ModelExtFactory() {

    //
    _modelExtensionRegistry = new AbstractBundleMakerExtensionRegistry<IModelExtension>(
        "org.bundlemaker.core.modelextension") {

      /**
       * {@inheritDoc}
       */
      @Override
      protected IModelExtension createInstanceFromExtension(IExtension extension) throws CoreException {

        //
        IConfigurationElement actionElement = extension.getConfigurationElements()[0];

        //
        IModelExtension modelExtension = (IModelExtension) actionElement.createExecutableExtension("class");
        modelExtension.initializeModelExtension();

        //
        return modelExtension;
      }
    };

    //
    _modelExtensionRegistry.initialize();
  }

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param storedResourcesMap
   */
  public void prepareStoredResourceModel(IProjectContentEntry projectContent,
      Map<IProjectContentResource, ? extends IParsableResource> storedResourcesMap) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.prepareStoredResourceModel(projectContent, storedResourcesMap);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param resourceCache
   * @param newAndModifiedBinaryResources
   * @param newAndModifiedSourceResources
   */
  public void beforeParseResourceModel(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.beforeParseResourceModel(projectContent, resourceCache, newAndModifiedBinaryResources,
          newAndModifiedSourceResources);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param resourceCache
   * @param newAndModifiedBinaryResources
   * @param newAndModifiedSourceResources
   */
  public void afterParseResourceModel(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.afterParseResourceModel(projectContent, resourceCache, newAndModifiedBinaryResources,
          newAndModifiedSourceResources);
    }
  }

  public void resourceModelSetupCompleted(IProjectContentEntry contentEntry, Set<IModuleResource> binaryResources,
      Set<IModuleResource> sourceResources) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.resourceModelSetupCompleted(contentEntry, binaryResources, sourceResources);
    }
  }

  public void prepareAnalysisModel(IModule[] modules, ArtifactCache artifactCache) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.prepareAnalysisModel(modules, artifactCache);
    }
  }

  public boolean shouldAddResourceArtifact(IModuleResource resource) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      if (!modelExtension.shouldAddResourceArtifact(resource)) {
        return false;
      }
    }

    //
    return true;
  }

  public void setupResourceArtifact(IResourceArtifact resourceArtifact, IModuleResource resource) {

    //
    for (IModelExtension modelExtension : _modelExtensionRegistry.getExtensionInstances()) {
      modelExtension.setupResourceArtifact(resourceArtifact, resource);
    }
  }

  /** - */
  private static ModelExtFactory _modelExtFactory;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static ModelExtFactory getModelExtensionFactory() {

    //
    if (_modelExtFactory == null) {
      _modelExtFactory = new ModelExtFactory();
    }

    //
    return _modelExtFactory;
  }
}
