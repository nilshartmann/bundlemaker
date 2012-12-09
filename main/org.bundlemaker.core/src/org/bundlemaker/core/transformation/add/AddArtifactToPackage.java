package org.bundlemaker.core.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.internal.analysis.AdapterPackage2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;

public class AddArtifactToPackage {

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @param artifact
   */
  public static void add(IPackageArtifact parent, IBundleMakerArtifact artifact) {

    // handle package
    if (artifact.isInstanceOf(IPackageArtifact.class)) {

      //
      ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(
          ((AdapterPackage2IArtifact) artifact).getContainingModule()),
          artifact.getQualifiedName());

      //
      ArtifactCache artifactCache = ((AdapterRoot2IArtifact) parent.getRoot()).getArtifactCache();
      IPackageArtifact packageArtifact = (IPackageArtifact) artifactCache.getPackageCache().getOrCreate(
          modulePackageKey);

      // move the children to the new package artifact
      for (IBundleMakerArtifact child : artifact.getChildren()) {
        packageArtifact.addArtifact(child);
      }
    } else {
      AdapterUtils.addArtifactToPackage((AdapterPackage2IArtifact) parent, artifact);
    }

  }
}
