package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.itest.analysis.simple_artifact_model.framework.AbstractSimpleArtifactModelTest;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
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

    // rename the group artifact
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);
    _binModel.getGroup2Artifact().setName("renamed");

    // assert renamed in resource model
    Assert.assertEquals(new Path("group1/renamed"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());

    // assert rename in bin artifact model
    Assert.assertEquals("renamed", _binModel.getGroup2Artifact().getName());
    Assert.assertEquals("group1/renamed", _binModel.getGroup2Artifact().getQualifiedName());

    // assert renamed in src artifact model
    Assert.assertEquals("renamed", _srcModel.getGroup2Artifact().getName());
    Assert.assertEquals("group1/renamed", _srcModel.getGroup2Artifact().getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameNonLeafGroupArtifact() throws Exception {

    // rename the group artifact
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);
    _binModel.getGroup1Artifact().setName("renamed");

    // assert renamed in resource model
    Assert.assertEquals(new Path("renamed/group2"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());

    // assert rename in bin artifact model
    Assert.assertEquals("renamed", _binModel.getGroup1Artifact().getName());
    Assert.assertEquals("renamed/group2", _binModel.getGroup2Artifact().getQualifiedName());

    // assert renamed in src artifact model
    Assert.assertEquals("renamed", _srcModel.getGroup1Artifact().getName());
    Assert.assertEquals("renamed/group2", _srcModel.getGroup2Artifact().getQualifiedName());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameAndMoveGroupArtifact() throws Exception {

    // rename 'group2' -> 'renamedgroup2'
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);
    _binModel.getGroup2Artifact().setName("renamedgroup2");

    // assert renamed in resource model
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);
    Assert.assertEquals("renamedgroup2", _binModel.getGroup2Artifact().getName());
    Assert.assertEquals("group1/renamedgroup2", _binModel.getGroup2Artifact().getQualifiedName());
    Assert.assertEquals("renamedgroup2", _srcModel.getGroup2Artifact().getName());
    Assert.assertEquals("group1/renamedgroup2", _srcModel.getGroup2Artifact().getQualifiedName());
    Assert.assertEquals(new Path("group1/renamedgroup2"),
        getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());

    // add group to root
    _binModel.getRootArtifact().addArtifact(_binModel.getGroup2Artifact());

    // assert group count
    assertGroupCountInModularizedSystem(2);
    ArtifactUtils.dumpArtifact(_binModel.getRootArtifact());
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);

    // assert module has been re-classified
    Assert.assertEquals(new Path("renamedgroup2"),
        getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());

    // assert update in bin artifact model
    ArtifactUtils.dumpArtifact(_binModel.getRootArtifact());
    Assert.assertEquals("renamedgroup2", _binModel.getGroup2Artifact().getName());
    Assert.assertEquals("renamedgroup2", _binModel.getGroup2Artifact().getQualifiedName());

    // assert update in src artifact model
    ArtifactUtils.dumpArtifact(_srcModel.getGroup1Artifact());
    ArtifactUtils.dumpArtifact(_srcModel.getGroup2Artifact());
    Assert.assertEquals("renamedgroup2", _srcModel.getGroup2Artifact().getName());
    Assert.assertEquals("renamedgroup2", _srcModel.getGroup2Artifact().getQualifiedName());
  }
}
