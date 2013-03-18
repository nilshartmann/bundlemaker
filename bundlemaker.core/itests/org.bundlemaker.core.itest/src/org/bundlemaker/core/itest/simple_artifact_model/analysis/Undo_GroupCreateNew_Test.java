package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCountInModularizedSystem;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.NoModificationAssertion;
import org.junit.Assert;
import org.junit.Test;

public class Undo_GroupCreateNew_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewGroupBelowExistingGroup() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
        assertGroupCount(getBinModel(), 2);
        assertGroupCount(getSrcModel(), 2);
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {

        // STEP 1: create a new group
        IGroupArtifact newGroupArtifact = getBinModel().getGroup2Artifact().getOrCreateGroup("NewGroup");
        Assert.assertEquals("group1/group2/NewGroup", newGroupArtifact.getQualifiedName());

        // assert that we have three groups
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertGroupCount(getBinModel(), 3);
        assertGroupCount(getSrcModel(), 3);
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewGroupBelowRoot() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        // assert that we have two groups
        assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
        assertGroupCount(getBinModel(), 2);
        assertGroupCount(getSrcModel(), 2);
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {

        // STEP 1: create a new group
        IGroupArtifact newGroupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("NewGroup");
        Assert.assertEquals("NewGroup", newGroupArtifact.getQualifiedName());

        // assert that we have three groups
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertGroupCount(getBinModel(), 3);
        assertGroupCount(getSrcModel(), 3);
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
      }
    });
  }
}
