package org.bundlemaker.core.internal.transformation.add;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.AdapterUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AddArtifactToGroupAndModuleContainer implements IAddArtifactAction<IGroupAndModuleContainer> {

  /** - */
  private IBundleMakerArtifact     _addedArtifact;

  /** - */
  private IGroupAndModuleContainer _oldParentArtifact;

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChildToParent(IGroupAndModuleContainer parent, IBundleMakerArtifact artifactToAdd) {

    //
    Assert.isNotNull(parent);
    Assert.isNotNull(artifactToAdd);

    //
    _oldParentArtifact = (IGroupAndModuleContainer) artifactToAdd.getParent();
    _addedArtifact = artifactToAdd;

    // add to parent
    addToParent(parent, artifactToAdd);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void undo() {

    // add to parent
    addToParent(_oldParentArtifact, _addedArtifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @param parent
   * @param artifactToAdd
   */
  private void addToParent(IGroupAndModuleContainer parent, IBundleMakerArtifact artifactToAdd) {

    Assert.isNotNull(parent);
    Assert.isNotNull(artifactToAdd);

    //
    if (artifactToAdd instanceof IModuleArtifact) {

      //
      String parentClassification = parent instanceof IRootArtifact ? null : parent
          .getQualifiedName()
          .replace('|', '/');

      //
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IModuleArtifact) artifactToAdd, parentClassification);
    }

    //
    else if (artifactToAdd instanceof IGroupArtifact) {

      //
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IGroupArtifact) artifactToAdd,
          (IGroupAndModuleContainer) parent);
    }
  }
}
