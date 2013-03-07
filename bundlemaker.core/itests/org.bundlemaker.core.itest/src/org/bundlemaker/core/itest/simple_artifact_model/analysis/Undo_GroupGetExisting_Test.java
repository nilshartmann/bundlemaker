package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
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
        assertGroupCountInModularizedSystem(2);
        assertGroupCount(_binModel, 2);
        assertGroupCount(_srcModel, 2);

        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new group
        IGroupArtifact groupArtifact = _binModel.getRootArtifact().getOrCreateGroup("group1/group2");
        Assert.assertEquals("group1/group2", groupArtifact.getQualifiedName());

        //
        assertGroupCountInModularizedSystem(2);
        assertGroupCount(_binModel, 2);
        assertGroupCount(_srcModel, 2);
        
        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());
      }
    });
  }
}
