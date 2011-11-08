package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AbstractBundleMakerArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all resource artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSubCache extends
    AbstractSubCache<IResource, AbstractBundleMakerArtifactContainer> {

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
  protected AbstractBundleMakerArtifactContainer create(IResource key) {

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
  public AbstractBundleMakerArtifactContainer getOrCreateParent(IResource resource) {

    Assert.isNotNull(resource);

    // step 1: compute the package name
    String packageName = resource.getPackageName();

    // step 2: get the associated resource module
    IResourceModule resourceModule = resource.getAssociatedResourceModule(getArtifactCache().getModularizedSystem());

    // step 3: create the module package key
    ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(resourceModule), packageName);

    // step 4: return the package artifact
    return getArtifactCache().getPackageCache().getOrCreate(modulePackageKey);
  }
}