package org.bundlemaker.core.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.AdapterRoot2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.cache.ModulePackageKey;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;

public class AddArtifactToModule {

  /**
   * <p>
   * </p>
   * 
   * @param moduleArtifact
   * @param artifact
   */
  public static void add(IModuleArtifact moduleArtifact,
      IBundleMakerArtifact artifact) {

    // add a package to the module
    if (artifact.isInstanceOf(IPackageArtifact.class)) {
      AdapterUtils.addResourcesToModule((IModifiableResourceModule) moduleArtifact.getAssociatedModule(),
          AdapterUtils.getAllMovableUnits(artifact));
    }

    // add a resource to the module
    else if (artifact.isInstanceOf(IResourceArtifact.class)) {
      AdapterUtils.addResourcesToModule((IModifiableResourceModule) moduleArtifact.getAssociatedModule(),
          AdapterUtils.getAllMovableUnits(artifact));
    }

    // add a type to the module
    else if (artifact.isInstanceOf(ITypeArtifact.class)) {

      //
      if (artifact.getParent() != null && artifact.getParent().isInstanceOf(IResourceArtifact.class)) {
        AdapterUtils.addResourcesToModule((IModifiableResourceModule) moduleArtifact.getAssociatedModule(),
            AdapterUtils.getAllMovableUnits(artifact));
      }

      //
      else {

        //
        if (true) {
          // we have to check if we need this block here. This was implemented to support
          // analysis models without resources. As we don't support this anymore, this block
          // should be removed as well...
          throw new RuntimeException("Do we need this block?");
        }

        // step 1: get the package key
        ModulePackageKey modulePackageKey = new ModulePackageKey(new ModuleKey(moduleArtifact.getAssociatedModule()),
            ((ITypeArtifact) artifact)
                .getAssociatedType().getPackageName());

        //
        ArtifactCache artifactCache = ((AdapterRoot2IArtifact) moduleArtifact.getRoot()).getArtifactCache();

        //
        IPackageArtifact newPackageArtifact = (IPackageArtifact) artifactCache.getPackageCache().getOrCreate(
            modulePackageKey);

        //
        newPackageArtifact.addArtifact(artifact);
      }
    }
  }

}
