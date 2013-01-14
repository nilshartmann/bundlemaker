package org.bundlemaker.core.internal.transformation.add;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class AddMovableUnitsToModule implements IAddArtifactAction<IBundleMakerArtifact> {

  /** - */
  private List<IMovableUnit>        _movedMovableUnits;

  /** - */
  private IModifiableResourceModule _oldParentModule;

  /**
   * <p>
   * </p>
   * 
   * @param parentArtifact
   * @param artifact
   */
  public void addChildToParent(IBundleMakerArtifact parentArtifact,
      IBundleMakerArtifact artifact) {

    // add a package or a resource or a type to the module
    if (artifact.isInstanceOf(IPackageArtifact.class) || artifact.isInstanceOf(IResourceArtifact.class)
        || artifact.isInstanceOf(ITypeArtifact.class)) {

      //
      _movedMovableUnits = AdapterUtils.getAllMovableUnits(artifact);
      _oldParentModule = (IModifiableResourceModule) artifact.getParent(IModuleArtifact.class).getAssociatedModule();

      //
      IModuleArtifact moduleArtifact = parentArtifact instanceof IModuleArtifact ? parentArtifact
          .castTo(IModuleArtifact.class)
          : parentArtifact
              .getParent(IModuleArtifact.class);

      //
      AdapterUtils.addResourcesToModule((IModifiableResourceModule) moduleArtifact.getAssociatedModule(),
          _movedMovableUnits);
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
