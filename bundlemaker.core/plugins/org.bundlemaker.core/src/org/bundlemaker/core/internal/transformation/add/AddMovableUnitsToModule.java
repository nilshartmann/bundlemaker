package org.bundlemaker.core.internal.transformation.add;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact.IResourceArtifactContent;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.bundlemaker.core.internal.api.resource.IModifiableModule;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class AddMovableUnitsToModule implements IAddArtifactAction<IBundleMakerArtifact> {

  /** - */
  private List<IModuleAwareMovableUnit> _movedMovableUnits;

  /** - */
  private IModule            _oldParentModule;

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
        || artifact.isInstanceOf(IResourceArtifactContent.class)) {

      //
      _movedMovableUnits = AdapterUtils.getAllMovableUnits(artifact);
      _oldParentModule = (IModule) artifact.getParent(IModuleArtifact.class).getAssociatedModule();

      //
      IModuleArtifact moduleArtifact = parentArtifact instanceof IModuleArtifact ? parentArtifact
          .castTo(IModuleArtifact.class)
          : parentArtifact
              .getParent(IModuleArtifact.class);

      //
      AdapterUtils.addResourcesToModule((IModifiableModule) moduleArtifact.getAssociatedModule(),
          _movedMovableUnits);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void undo() {

    AdapterUtils.addResourcesToModule((IModifiableModule) _oldParentModule,
        _movedMovableUnits);
  }
}
