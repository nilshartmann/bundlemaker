package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterPackage2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all package artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PackageSubCache extends AbstractSubCache<ModulePackageKey, AbstractArtifactContainer> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link PackageSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public PackageSubCache(ArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractArtifactContainer create(ModulePackageKey modulePackageKey) {

    Assert.isNotNull(modulePackageKey);

    // get the parent for this package
    IBundleMakerArtifact parent = getPackageParent(modulePackageKey);

    // return the package artifact
    return new AdapterPackage2IArtifact(modulePackageKey.getPackageName(), parent, modulePackageKey.getModuleKey()
        .getModule() == null, getArtifactCache().getConfiguration().isHierarchicalPackages(), modulePackageKey
        .getModuleKey().getModule(), getArtifactCache());
  }

  /**
   * <p>
   * </p>
   * 
   * @param modulePackageKey
   * @return
   */
  public IBundleMakerArtifact getPackageParent(ModulePackageKey modulePackageKey) {

    Assert.isNotNull(modulePackageKey);

    // step 1: get the package name
    String packageName = modulePackageKey.getPackageName();

    // step 2: if we have hierarchical packages, create the 'super package'...
    if (getArtifactCache().getConfiguration().isHierarchicalPackages() && packageName.contains(".")) {

      // get the parent package
      String parentPackage = packageName.substring(0, packageName.lastIndexOf("."));

      // return the package artifact
      return getArtifactCache().getPackageCache().getOrCreate(
          new ModulePackageKey(modulePackageKey.getModuleKey(), parentPackage));
    }

    // step 3: ... otherwise return the module artifact
    else {
      return getArtifactCache().getModuleCache().getOrCreate(modulePackageKey.getModuleKey());
    }
  }
}