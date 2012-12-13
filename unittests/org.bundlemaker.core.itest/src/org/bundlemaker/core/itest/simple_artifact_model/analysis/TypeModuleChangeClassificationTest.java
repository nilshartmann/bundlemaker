package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelWithTypeLibraryTest;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TypeModuleChangeClassificationTest extends AbstractSimpleArtifactModelWithTypeLibraryTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void changeModuleClassification() {

    // 'move' model to group 1
    Assert.assertNotNull(_binModel.getVelocityModuleArtifact());
    _binModel.getGroup1Artifact().addArtifact(_binModel.getVelocityModuleArtifact());
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getVelocityModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);
    
    // assert 
    Assert.assertEquals(new Path("group1"), _binModel.getVelocityModuleArtifact().getAssociatedModule()
        .getClassification());

    // assert module parent in src model
    Assert.assertNotNull(_srcModel.getVelocityModuleArtifact());
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getVelocityModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
  }
}
