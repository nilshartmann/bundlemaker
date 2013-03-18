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
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {

        //
        assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
        assertGroupCount(getBinModel(), 2);
        assertGroupCount(getSrcModel(), 2);

        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new group
        IGroupArtifact groupArtifact = getBinModel().getRootArtifact().getOrCreateGroup("group1/group2");
        Assert.assertEquals("group1/group2", groupArtifact.getQualifiedName());

        //
        assertGroupCountInModularizedSystem(getModularizedSystem(), 2);
        assertGroupCount(getBinModel(), 2);
        assertGroupCount(getSrcModel(), 2);
        
        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());
      }
    }, getBinModel(), getSrcModel());
  }
}
