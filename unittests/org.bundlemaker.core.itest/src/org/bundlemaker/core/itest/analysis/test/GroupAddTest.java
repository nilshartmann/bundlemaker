package org.bundlemaker.core.itest.analysis.test;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itest.analysis.test.framework.AbstractSimpleArtifactModelTest;
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

    // 'move' model to group 1
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

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

    // 'move' model to group 1
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);

    // 'move' model to group 2
    _binModel.getGroup2Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1/group2"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup2Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
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

    // 'move' model to group 1
    ((IModifiableModule)_binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup1Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to root
    ((IModifiableModule)_binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(null);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getRootArtifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getRootArtifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to group 1
    ((IModifiableModule)_binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup1Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);

    // 'move' model to group 1
    ((IModifiableModule)_binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1/group2"));

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup2Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals(_binModel.getGroup2Artifact(), _binModel.getMainModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);
  }
}
