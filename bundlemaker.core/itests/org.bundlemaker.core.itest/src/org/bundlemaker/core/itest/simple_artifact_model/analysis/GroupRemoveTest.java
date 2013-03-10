package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.hamcrest.CoreMatchers.is;

import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

public class GroupRemoveTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void removeGroupArtifact() throws Exception {

    // // check the group count
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(_binModel, 2);
    // assertGroupCount(_srcModel, 2);
    // Assert.assertTrue(_binModel.getGroup1Artifact().hasParent());
    // Assert.assertTrue(_srcModel.getGroup1Artifact().hasParent());
    // Assert.assertEquals(_binModel.getMainModuleArtifact().getAssociatedModule().getClassification(), new Path(
    // "group1/group2"));
    //
    // // test dependencies: main module is in group 2
    // assertDeps_mainModuleInGroup2();
    //
    // //
    // _binModel.getRootArtifact().removeArtifact(_binModel.getGroup1Artifact());
    //
    // // check the group count
    // assertGroupCount(_binModel, 0);
    // assertGroupCount(_srcModel, 0);
    // assertGroupCountInModularizedSystem(0);
    // Assert.assertFalse(_binModel.getGroup1Artifact().hasParent());
    // Assert.assertFalse(_srcModel.getGroup1Artifact().hasParent());
    // Assert.assertEquals(new Path("group1/group2"), _binModel.getMainModuleArtifact().getAssociatedModule()
    // .getClassification());

    // test dependencies: main module is gone, but dependency is still there
    // TODO: HOW SHOULD WE REACT IF REFERENCED ARTIFACTS ARE REMOVED?
    // System.out.println(_binModel.getMainModuleArtifact().getDependencyTo(_binModel.getGroup1Artifact()));
    // assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 0, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }
    // },
    // new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
  }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInGroup2() {
    assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } },
        new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
  }
}
