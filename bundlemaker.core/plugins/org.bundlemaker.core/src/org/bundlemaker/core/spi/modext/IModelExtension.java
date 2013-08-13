package org.bundlemaker.core.spi.modext;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.parser.IParsableResource;
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
  void beforeParseResourceModel(IProjectContentEntry projectContent,
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
  void afterParseResourceModel(IProjectContentEntry projectContent,
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
  void resourceModelSetupCompleted(IProjectContentEntry contentEntry, Collection<IModuleResource> binaryResources,
      Collection<IModuleResource> sourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param modules
   * @param context
   */
  void prepareAnalysisModel(IModule[] modules, IAnalysisModelContext context);

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
