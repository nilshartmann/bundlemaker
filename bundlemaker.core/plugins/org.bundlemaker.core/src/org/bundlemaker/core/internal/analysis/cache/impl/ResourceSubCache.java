package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.core._type.JavaUtils;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.analysis.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all resource artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSubCache extends AbstractSubCache<IModuleResource, IResourceArtifact> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public ResourceSubCache(ArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IResourceArtifact create(IModuleResource key) {

    // get the parent
    AbstractArtifactContainer parent = getOrCreateParent(key);

    // return the resource artifact
    return new AdapterResource2IArtifact(key, false, parent, getArtifactCache());
  }

  /**
   * <p>
   * Returns the parent (package) artifact for the given resource.
   * </p>
   * 
   * @param resource
   * @return
   */
  public AbstractArtifactContainer getOrCreateParent(IModuleResource resource) {

    Assert.isNotNull(resource);

    // step 1: compute the package name
    String packageName = JavaUtils.getPackageNameFromDirectory(resource.getDirectory());

    // step 2: get the associated resource module
    IModule resourceModule = resource.getModule(getArtifactCache().getModularizedSystem());
    if (resourceModule == null) {
      throw new RuntimeException(String.format("No module for resource '%s'.", resource.getPath()));
    }

    // step 3: create the module package key
    ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(resourceModule), packageName);

    // step 4: return the package artifact
    return getArtifactCache().getPackageCache().getOrCreate(modulePackageKey);
  }
}