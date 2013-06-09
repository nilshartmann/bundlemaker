package org.bundlemaker.core.spi.resource;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;

public interface IResourceModelLifecycleCallback {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  void prepare(IModuleAwareBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * </p>
   * 
   * @param projectContentEntry
   * @param storedResourcesMap
   * @param newAndModifiedSourceResources
   */
  void setupModel(IProjectContentEntry projectContentEntry, Map<IProjectContentResource, Resource> storedResourcesMap,
      Set<IResourceStandin> newAndModifiedSourceResources);

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  void cleanUp(IModuleAwareBundleMakerProject bundleMakerProject);
}
