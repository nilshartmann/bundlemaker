package org.bundlemaker.core.transformation.add;

import java.util.List;

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
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

public class AddArtifactToModule implements IAddArtifactAction<IModuleArtifact> {

  /** - */
  private List<IMovableUnit>        _movedMovableUnits;

  /** - */
  private IModifiableResourceModule _oldParentModule;

  /**
   * <p>
   * </p>
   * 
   * @param moduleArtifact
   * @param artifact
   */
  public void addChildToParent(IModuleArtifact moduleArtifact,
      IBundleMakerArtifact artifact) {

    // add a package to the module
    if (artifact.isInstanceOf(IPackageArtifact.class)) {

      //
      _movedMovableUnits = AdapterUtils.getAllMovableUnits(artifact);
      _oldParentModule = (IModifiableResourceModule) artifact.getParent(IModuleArtifact.class).getAssociatedModule();
      AdapterUtils.addResourcesToModule((IModifiableResourceModule) moduleArtifact.getAssociatedModule(),
          _movedMovableUnits);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void undo() {
    AdapterUtils.addResourcesToModule(_oldParentModule,
        _movedMovableUnits);
  }
}
