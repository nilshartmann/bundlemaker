package org.bundlemaker.core._type.internal;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleAwareBundleMakerProject;
import org.bundlemaker.core.spi.parser.IParsableResource;
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
      Map<IProjectContentResource, IParsableResource> storedResourcesMap,
      Set<IParsableResource> newAndModifiedSourceResources) {

    _resourceCache.setupTypeCache(projectContentEntry, storedResourcesMap, newAndModifiedSourceResources);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanUp(IModuleAwareBundleMakerProject bundleMakerProject) {

  }
}
