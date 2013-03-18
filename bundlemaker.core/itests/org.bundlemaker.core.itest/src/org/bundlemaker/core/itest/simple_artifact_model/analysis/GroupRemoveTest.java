package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
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
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    // Assert.assertTrue(getBinModel().getGroup1Artifact().hasParent());
    // Assert.assertTrue(getSrcModel().getGroup1Artifact().hasParent());
    // Assert.assertEquals(getBinModel().getMainModuleArtifact().getAssociatedModule().getClassification(), new Path(
    // "group1/group2"));
    //
    // // test dependencies: main module is in group 2
    // assertDeps_mainModuleInGroup2();
    //
    // //
    // getBinModel().getRootArtifact().removeArtifact(getBinModel().getGroup1Artifact());
    //
    // // check the group count
    // assertGroupCount(getBinModel(), 0);
    // assertGroupCount(getSrcModel(), 0);
    // assertGroupCountInModularizedSystem(0);
    // Assert.assertFalse(getBinModel().getGroup1Artifact().hasParent());
    // Assert.assertFalse(getSrcModel().getGroup1Artifact().hasParent());
    // Assert.assertEquals(new Path("group1/group2"), getBinModel().getMainModuleArtifact().getAssociatedModule()
    // .getClassification());

    // test dependencies: main module is gone, but dependency is still there
    // TODO: HOW SHOULD WE REACT IF REFERENCED ARTIFACTS ARE REMOVED?
    // System.out.println(getBinModel().getMainModuleArtifact().getDependencyTo(getBinModel().getGroup1Artifact()));
    // assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 0, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 }
    // },
    // new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
  }

  // /**
  // * <p>
  // * </p>
  // */
  // private void assertDeps_mainModuleInGroup2() {
  // assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } },
  // new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 }, { 1, 1, 1, 1 } });
  // }
}
