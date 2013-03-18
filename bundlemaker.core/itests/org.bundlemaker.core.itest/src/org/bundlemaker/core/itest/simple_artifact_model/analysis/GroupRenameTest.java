package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Test;

public class GroupRenameTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameLeafGroupArtifact() throws Exception {

    // // rename the group artifact
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    // getBinModel().getGroup2Artifact().setName("renamed");
    //
    // // assert renamed in resource model
    // Assert.assertEquals(new Path("group1/renamed"), getBinModel().getMainModuleArtifact().getAssociatedModule()
    // .getClassification());
    //
    // // assert rename in bin artifact model
    // Assert.assertEquals("renamed", getBinModel().getGroup2Artifact().getName());
    // Assert.assertEquals("group1/renamed", getBinModel().getGroup2Artifact().getQualifiedName());
    //
    // // assert renamed in src artifact model
    // Assert.assertEquals("renamed", getSrcModel().getGroup2Artifact().getName());
    // Assert.assertEquals("group1/renamed", getSrcModel().getGroup2Artifact().getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameNonLeafGroupArtifact() throws Exception {

    // // rename the group artifact
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    // getBinModel().getGroup1Artifact().setName("renamed");
    //
    // // assert renamed in resource model
    // Assert.assertEquals(new Path("renamed/group2"), getBinModel().getMainModuleArtifact().getAssociatedModule()
    // .getClassification());
    //
    // // assert rename in bin artifact model
    // Assert.assertEquals("renamed", getBinModel().getGroup1Artifact().getName());
    // Assert.assertEquals("renamed/group2", getBinModel().getGroup2Artifact().getQualifiedName());
    //
    // // assert renamed in src artifact model
    // Assert.assertEquals("renamed", getSrcModel().getGroup1Artifact().getName());
    // Assert.assertEquals("renamed/group2", getSrcModel().getGroup2Artifact().getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameAndMoveGroupArtifact() throws Exception {

    // // rename 'group2' -> 'renamedgroup2'
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    // getBinModel().getGroup2Artifact().setName("renamedgroup2");
    //
    // // assert renamed in resource model
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    // Assert.assertEquals("renamedgroup2", getBinModel().getGroup2Artifact().getName());
    // Assert.assertEquals("group1/renamedgroup2", getBinModel().getGroup2Artifact().getQualifiedName());
    // Assert.assertEquals("renamedgroup2", getSrcModel().getGroup2Artifact().getName());
    // Assert.assertEquals("group1/renamedgroup2", getSrcModel().getGroup2Artifact().getQualifiedName());
    // Assert.assertEquals(new Path("group1/renamedgroup2"),
    // getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());
    //
    // // add group to root
    // getBinModel().getRootArtifact().addArtifact(getBinModel().getGroup2Artifact());
    //
    // // assert group count
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    //
    // // assert module has been re-classified
    // Assert.assertEquals(new Path("renamedgroup2"),
    // getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());
    //
    // // assert update in bin artifact model
    // Assert.assertEquals("renamedgroup2", getBinModel().getGroup2Artifact().getName());
    // Assert.assertEquals("renamedgroup2", getBinModel().getGroup2Artifact().getQualifiedName());
    //
    // // assert update in src artifact model
    // Assert.assertEquals("renamedgroup2", getSrcModel().getGroup2Artifact().getName());
    // Assert.assertEquals("renamedgroup2", getSrcModel().getGroup2Artifact().getQualifiedName());
  }
}
