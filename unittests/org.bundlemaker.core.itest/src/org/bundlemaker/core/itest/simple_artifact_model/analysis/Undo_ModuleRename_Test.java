package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
import org.bundlemaker.core.transformation.IUndoableTransformation;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_ModuleRename_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Tests if the artifact models are updated correct if a resource module is removed in the resource model.
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameModuleArtifact() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {

        //
        assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        //
        _binModel.getMainModuleArtifact().setNameAndVersion("neuerName", "1.2.3");

        //
        assertMainModuleNameAndVersion("neuerName", "1.2.3");
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

        // STEP 2: Undo
        getModularizedSystem().undoLastTransformation();

        //
        assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());
      }
    });
  }
}
