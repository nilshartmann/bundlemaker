package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.SimpleArtifactModelAssert.assertMainModuleNameAndVersion;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.NoModificationAssertion;
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
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        assertMainModuleNameAndVersion(getBinModel(), "SimpleArtifactModelTest", "1.0.0");
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {
        getBinModel().getMainModuleArtifact().setNameAndVersion("neuerName", "1.2.3");
        assertMainModuleNameAndVersion(getBinModel(), "neuerName", "1.2.3");
      }
    });
  }
}
