package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterPackage2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.internal.analysis.transformer.ModulePackageKey;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PackageCache extends AbstractArtifactCacheAwareGenericCache<ModulePackageKey, AbstractArtifactContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link PackageCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public PackageCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  @Override
  protected AbstractArtifactContainer create(ModulePackageKey modulePackageKey) {

    String packageName = modulePackageKey.getPackageName();

    IArtifact parent = null;

    if (getArtifactCache().getConfiguration().isHierarchicalPackages() && packageName.contains(".")) {
      String parentPackage = packageName.substring(0, packageName.lastIndexOf("."));
      parent = getArtifactCache().getPackageCache().getOrCreate(
          new ModulePackageKey(modulePackageKey.getModuleKey(), parentPackage));
    } else {
      parent = getArtifactCache().getModuleCache().getOrCreate(modulePackageKey.getModuleKey());
    }

    //
    AdapterPackage2IArtifact result = new AdapterPackage2IArtifact(modulePackageKey.getPackageName(), parent, false,
        modulePackageKey.getModuleKey().getModule(), getArtifactCache());

    //
    return result;
  }
}