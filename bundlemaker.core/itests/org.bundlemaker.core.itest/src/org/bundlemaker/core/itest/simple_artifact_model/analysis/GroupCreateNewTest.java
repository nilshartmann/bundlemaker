package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.*;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
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

    assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
    assertGroupCount(getBinModel(), 2);
    assertGroupCount(getSrcModel(), 2);

    // create a new group
    IGroupArtifact newGroupArtifact = getBinModel().getGroup2Artifact().getOrCreateGroup("NewGroup");
    Assert.assertEquals("group1/group2/NewGroup", newGroupArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(3, getModularizedSystem().getGroups().size());
    assertGroupCount(getBinModel(), 3);
    assertGroupCount(getSrcModel(), 3);
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
    assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
    assertGroupCount(getBinModel(), 2);
    assertGroupCount(getSrcModel(), 2);

    // create a new group
    IGroupArtifact newGroupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("NewGroup");
    Assert.assertEquals("NewGroup", newGroupArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(3, getModularizedSystem().getGroups().size());
    assertGroupCount(getBinModel(), 3);
    assertGroupCount(getSrcModel(), 3);
  }
}
