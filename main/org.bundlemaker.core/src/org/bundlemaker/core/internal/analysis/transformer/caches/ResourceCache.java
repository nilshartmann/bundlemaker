package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterResource2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.internal.analysis.transformer.ModulePackageKey;
import org.bundlemaker.core.internal.analysis.transformer.ModuleResourceKey;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceCache extends AbstractArtifactCacheAwareGenericCache<ModuleResourceKey, AbstractArtifactContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link ResourceCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public ResourceCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  @Override
  protected AbstractArtifactContainer create(ModuleResourceKey key) {

    // compute the package name
    String packageName = key.getResource().getPackageName();

    // get the module package
    ModulePackageKey modulePackageKey = new ModulePackageKey(key.getResourceModule(), packageName);

    // get the parent
    AbstractArtifactContainer parent = getArtifactCache().getPackageCache().getOrCreate(modulePackageKey);

    //
    return new AdapterResource2IArtifact(key.getResource(), false, parent);
  }
}