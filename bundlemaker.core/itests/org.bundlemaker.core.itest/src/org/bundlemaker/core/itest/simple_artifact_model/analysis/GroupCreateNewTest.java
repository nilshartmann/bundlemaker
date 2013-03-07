package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Assert;
import org.junit.Test;

public class GroupCreateNewTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewGroupBelowExistingGroup() throws Exception {

    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);

    // create a new group
    IGroupArtifact newGroupArtifact = _binModel.getGroup2Artifact().getOrCreateGroup("NewGroup");
    Assert.assertEquals("group1/group2/NewGroup", newGroupArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(3, getModularizedSystem().getGroups().size());
    assertGroupCount(_binModel, 3);
    assertGroupCount(_srcModel, 3);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewGroupBelowRoot() throws Exception {

    // assert that we have two groups
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);

    // create a new group
    IGroupArtifact newGroupArtifact = _binModel.getRootArtifact().getOrCreateGroup("NewGroup");
    Assert.assertEquals("NewGroup", newGroupArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(3, getModularizedSystem().getGroups().size());
    assertGroupCount(_binModel, 3);
    assertGroupCount(_srcModel, 3);
  }
}
