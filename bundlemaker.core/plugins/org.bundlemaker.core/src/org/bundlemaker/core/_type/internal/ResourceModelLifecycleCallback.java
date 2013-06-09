package org.bundlemaker.core._type.internal;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.spi.resource.IResourceModelLifecycleCallback;

public class ResourceModelLifecycleCallback implements IResourceModelLifecycleCallback {

  /** - */
  private TypeCache _resourceCache = new TypeCache();

  /**
   * {@inheritDoc}
   */
  @Override
  public void prepare(IModuleAwareBundleMakerProject bundleMakerProject) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setupModel(IProjectContentEntry projectContentEntry,
      Map<IProjectContentResource, Resource> storedResourcesMap, Set<IResourceStandin> newAndModifiedSourceResources) {

    _resourceCache.setupTypeCache(projectContentEntry, storedResourcesMap, newAndModifiedSourceResources);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanUp(IModuleAwareBundleMakerProject bundleMakerProject) {

  }
}
