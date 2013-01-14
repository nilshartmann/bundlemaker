package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
import org.bundlemaker.core.modules.transformation.IUndoableTransformation;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_ModuleGetExisting_Test extends AbstractSimpleArtifactModelTest {

  @Test
  public void createNewModuleWithGroupBelowExistingGroup() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {
        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);

        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new module
        IModuleArtifact moduleArtifact = _binModel.getRootArtifact().getOrCreateModule(
            "group1/group2/SimpleArtifactModelTest", "1.0.0");
        Assert.assertEquals("group1/group2/SimpleArtifactModelTest_1.0.0", moduleArtifact.getQualifiedName());

        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);

        // We have 1 (!) transformations here, as the "CreateGroupTransformation" is
        // implemented as an inner transformation
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());
      }
    });
  }
}
