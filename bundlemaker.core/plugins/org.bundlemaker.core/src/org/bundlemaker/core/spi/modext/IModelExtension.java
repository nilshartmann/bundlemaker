package org.bundlemaker.core.spi.modext;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.parser.IParserContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModelExtension {

  /**
   * <p>
   * Is called once directly after the {@link IModelExtension} has been created.
   * </p>
   */
  public void initialize();

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param storedResourcesMap
   */
  public void prepareStoredModel(IProjectContentEntry projectContent,
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
  public void beforeParse(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<IResourceStandin> newAndModifiedBinaryResources, Set<IResourceStandin> newAndModifiedSourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param projectContent
   * @param resourceCache
   * @param newAndModifiedBinaryResources
   * @param newAndModifiedSourceResources
   */
  public void afterParse(IProjectContentEntry projectContent, IParserContext resourceCache,
      Set<IResourceStandin> newAndModifiedBinaryResources, Set<IResourceStandin> newAndModifiedSourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @param resource
   */
  public void resourceAdded(IModule module, IModuleResource resource);

  /**
   * <p>
   * </p>
   * 
   * @param module
   * @param resource
   */
  public void resourceRemoved(IModule module, IModuleResource resource);

  //
  void prepareAnalysisModel(IModule[] modules, ArtifactCache artifactCache);

  /**
   * <p>
   * </p>
   * 
   * @param resourceArtifact
   * @param resource
   */
  public void setupResourceArtifact(IResourceArtifact resourceArtifact, IModuleResource resource);
}
