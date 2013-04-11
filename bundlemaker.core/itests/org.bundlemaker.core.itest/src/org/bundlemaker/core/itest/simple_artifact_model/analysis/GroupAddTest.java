package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCountInModularizedSystem;
import static org.bundlemaker.core.itestframework.simple_artifact_model.SimpleArtifactModelAssert.assert_Main_Jre_G1_G2_Dependencies;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

public class GroupAddTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Tests if the classification of a module is changed when a ModuleArtifact is added to GroupArtifact. Tests also, if
   * all artifact models are updated correctly.
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void addModuleArtifactToGroupArtifact() throws Exception {

    // test dependencies: main module is in group 2
    assertDeps_mainModuleInGroup2();

    // 'move' model to group 1
    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), getBinModel().getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    getBinModel().assertGroupCount(2);

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // assert module parent in src model
    Assert.assertEquals(getSrcModel().getGroup1Artifact(), getSrcModel().getMainModuleArtifact().getParent());
    getSrcModel().assertGroupCount(2);

    // 'move' model to root
    getBinModel().getRootArtifact().addArtifact(getBinModel().getMainModuleArtifact());
    Assert.assertNull(getBinModel().getMainModuleArtifact().getAssociatedModule().getClassification());
    getBinModel().assertGroupCount(2);

    // assert module parent in src model
    Assert.assertEquals(getSrcModel().getRootArtifact(), getSrcModel().getMainModuleArtifact().getParent());
    getSrcModel().assertGroupCount(2);

    // test dependencies: main module is in root
    assertDeps_mainModuleInRoot();

    // 'move' model to group 1
    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), getBinModel().getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    getBinModel().assertGroupCount(2);

    // assert module parent in src model
    Assert.assertEquals(getSrcModel().getGroup1Artifact(), getSrcModel().getMainModuleArtifact().getParent());
    getSrcModel().assertGroupCount(2);

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // 'move' model to group 2
    getBinModel().getGroup2Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    Assert.assertEquals(new Path("group1/group2"), getBinModel().getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    getBinModel().assertGroupCount(2);

    // assert module parent in src model
    Assert.assertEquals(getSrcModel().getGroup2Artifact(), getSrcModel().getMainModuleArtifact().getParent());
    getSrcModel().assertGroupCount(2);

    // test dependencies: main module is in group 2
    assertDeps_mainModuleInGroup2();
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void addEmptyGroupToGroup() throws Exception {

    // assert that we have two groups
    assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
    getBinModel().assertGroupCount(2);
    getSrcModel().assertGroupCount(2);

    //
    IGroupArtifact newGroupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("newGroup");
    Assert.assertEquals("newGroup", newGroupArtifact.getQualifiedName());

    //
    getBinModel().getGroup1Artifact().addArtifact(newGroupArtifact);
    Assert.assertEquals("group1/newGroup", newGroupArtifact.getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddModuleArtifactToParentGroupArtifact() throws Exception {

    // add to it's own parent
    getBinModel().getGroup2Artifact().addArtifact(getBinModel().getMainModuleArtifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddGroupArtifactToSelf() throws Exception {

    // add to self
    getBinModel().getGroup2Artifact().addArtifact(getBinModel().getGroup2Artifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddAncestorArtifact() throws Exception {

    // add to self
    getBinModel().getGroup2Artifact().addArtifact(getBinModel().getGroup1Artifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddRootArtifact() throws Exception {

    // add to self
    getBinModel().getGroup2Artifact().addArtifact(getBinModel().getRootArtifact());
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void changeResourceModuleClassification() throws Exception {
  //
  // assertDeps_mainModuleInGroup2();
  //
  // // 'move' model to group 1
  // ((IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule()).setClassification(new Path(
  // "group1"));
  //
  // // test dependencies: main module is in group 1
  // assertDeps_mainModuleInGroup1();
  //
  // // assert module parent in src model
  // Assert.assertEquals(getSrcModel().getGroup1Artifact(), getSrcModel().getMainModuleArtifact().getParent());
  // getSrcModel().assertGroupCount(2);
  // Assert.assertEquals(getBinModel().getGroup1Artifact(), getBinModel().getMainModuleArtifact().getParent());
  // getBinModel().assertGroupCount(2);
  //
  // // 'move' model to root
  // ((IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule()).setClassification(null);
  //
  // // test dependencies: main module is in root
  // assertDeps_mainModuleInRoot();
  //
  // // assert module parent in src model
  // Assert.assertEquals(getSrcModel().getRootArtifact(), getSrcModel().getMainModuleArtifact().getParent());
  // getSrcModel().assertGroupCount(2);
  // Assert.assertEquals(getBinModel().getRootArtifact(), getBinModel().getMainModuleArtifact().getParent());
  // getBinModel().assertGroupCount(2);
  //
  // // 'move' model to group 1
  // ((IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule()).setClassification(new Path(
  // "group1"));
  //
  // // test dependencies: main module is in group 1
  // assertDeps_mainModuleInGroup1();
  //
  // // assert module parent in src model
  // Assert.assertEquals(getSrcModel().getGroup1Artifact(), getSrcModel().getMainModuleArtifact().getParent());
  // getSrcModel().assertGroupCount(2);
  // Assert.assertEquals(getBinModel().getGroup1Artifact(), getBinModel().getMainModuleArtifact().getParent());
  // getBinModel().assertGroupCount(2);
  //
  // // 'move' model to group 2
  // ((IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule()).setClassification(new Path(
  // "group1/group2"));
  //
  // // test dependencies: main module is in group 2
  // assertDeps_mainModuleInGroup2();
  //
  // // assert module parent in src model
  // Assert.assertEquals(getSrcModel().getGroup2Artifact(), getSrcModel().getMainModuleArtifact().getParent());
  // getSrcModel().assertGroupCount(2);
  // Assert.assertEquals(getBinModel().getGroup2Artifact(), getBinModel().getMainModuleArtifact().getParent());
  // getBinModel().assertGroupCount(2);
  // }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInRoot() {
    assert_Main_Jre_G1_G2_Dependencies(getBinModel(), new int[][] { { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
        { 0, 0, 0, 0 } });
    assert_Main_Jre_G1_G2_Dependencies(getSrcModel(), new int[][] { { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 },
        { 0, 0, 0, 0 } });
  }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInGroup1() {
    assert_Main_Jre_G1_G2_Dependencies(getBinModel(), new int[][] { { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 0 },
        { 0, 0, 0, 0 } });
    assert_Main_Jre_G1_G2_Dependencies(getSrcModel(), new int[][] { { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 0 },
        { 0, 0, 0, 0 } });
  }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInGroup2() {
    assert_Main_Jre_G1_G2_Dependencies(getBinModel(), new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 },
        { 1, 1, 1, 1 } });
    assert_Main_Jre_G1_G2_Dependencies(getSrcModel(), new int[][] { { 1, 1, 1, 1 }, { 0, 0, 0, 0 }, { 1, 1, 1, 1 },
        { 1, 1, 1, 1 } });
  }
}
