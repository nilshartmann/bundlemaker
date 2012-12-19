package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModule;
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
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);

    // 'move' model to root
    _binModel.getRootArtifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertNull(_binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getRootArtifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);

    // test dependencies: main module is in root
    assertDeps_mainModuleInRoot();

    // 'move' model to group 1
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // 'move' model to group 2
    _binModel.getGroup2Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1/group2"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup2Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);

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
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);

    //
    IGroupArtifact newGroupArtifact = _binModel.getRootArtifact().getOrCreateGroup("newGroup");
    Assert.assertEquals("newGroup", newGroupArtifact.getQualifiedName());

    //
    _binModel.getGroup1Artifact().addArtifact(newGroupArtifact);
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
    _binModel.getGroup2Artifact().addArtifact(_binModel.getMainModuleArtifact());
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
    _binModel.getGroup2Artifact().addArtifact(_binModel.getGroup2Artifact());
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
    _binModel.getGroup2Artifact().addArtifact(_binModel.getGroup1Artifact());
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
    _binModel.getGroup2Artifact().addArtifact(_binModel.getRootArtifact());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void changeResourceModuleClassification() throws Exception {

    assertDeps_mainModuleInGroup2();

    // 'move' model to group 1
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup1Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to root
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(null);

    // test dependencies: main module is in root
    assertDeps_mainModuleInRoot();

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getRootArtifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getRootArtifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to group 1
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    // test dependencies: main module is in group 1
    assertDeps_mainModuleInGroup1();

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup1Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to group 2
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1/group2"));

    // test dependencies: main module is in group 2
    assertDeps_mainModuleInGroup2();

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup2Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup2Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);
  }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInRoot() {
    assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } },
        new int[][] { { 1, 1, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 } });
  }

  /**
   * <p>
   * </p>
   */
  private void assertDeps_mainModuleInGroup1() {
    assert_Main_Jre_G1_G2_Dependencies(new int[][] { { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } },
        new int[][] { { 1, 1, 1, 0 }, { 0, 0, 0, 0 }, { 1, 1, 1, 0 }, { 0, 0, 0, 0 } });
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
