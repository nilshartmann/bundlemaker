package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCountInModularizedSystem;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.NoModificationAssertion;
import org.junit.Assert;
import org.junit.Test;

public class Undo_GroupGetExisting_Test extends AbstractSimpleArtifactModelTest {

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
        IGroupArtifact groupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("group1/group2");
        Assert.assertEquals("group1/group2", groupArtifact.getQualifiedName());

        //
        assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
        assertGroupCount(getBinModel(), 2);
        assertGroupCount(getSrcModel(), 2);

        // We have 1 (2) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }
    });
  }
}
