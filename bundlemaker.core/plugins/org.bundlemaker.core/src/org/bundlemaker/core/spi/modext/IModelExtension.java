package org.bundlemaker.core.spi.modext;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParserContext;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModelExtension {

  /**
   * <p>
   * Is called once directly after the {@link IModelExtension} has been created. This method can be used e.g. to
   * register {@link IAdapterFactory IAdapterFactories} for IModularizedSystem, IModule or IModuleResource.
   * </p>
   */
  void initializeModelExtension();

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param storedResourcesMap
   */
  void prepareStoredResourceModel(IProjectContentEntry projectContent,
      Map<IProjectContentResource, ? extends IParsableResource> storedResourcesMap);

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param resourceCache
   * @param newAndModifiedBinaryResources
   * @param newAndModifiedSourceResources
   */
  void beforeParseResourceModel(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param resourceCache
   * @param newAndModifiedBinaryResources
   * @param newAndModifiedSourceResources
   */
  void afterParseResourceModel(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<? extends IModuleResource> newAndModifiedBinaryResources,
      Set<? extends IModuleResource> newAndModifiedSourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param set2
   * @param set
   * 
   * @param binaryResourceStandins
   * @param sourceResourceStandins
   */
  void resourceModelSetupCompleted(IProjectContentEntry contentEntry, Set<IModuleResource> binaryResources,
      Set<IModuleResource> sourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param modules
   * @param artifactCache
   */
  void prepareAnalysisModel(IModule[] modules, ArtifactCache artifactCache);

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  boolean shouldAddResourceArtifact(IModuleResource resource);

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   * @param resource
   * @return
   */
  void setupResourceArtifact(IResourceArtifact resourceArtifact, IModuleResource resource);
}
