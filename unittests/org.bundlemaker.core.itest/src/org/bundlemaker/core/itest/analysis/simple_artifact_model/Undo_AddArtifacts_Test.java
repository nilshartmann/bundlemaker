package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itest.analysis.simple_artifact_model.framework.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest.analysis.simple_artifact_model.framework.NoModificationAssertion;
import org.bundlemaker.core.transformation.IUndoableTransformation;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_AddArtifacts_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void undoAddArtifacts() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {

        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);

        //
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
        Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(2);
        assertResourceModuleCount(_binModel, 2);
        assertResourceModuleCount(_srcModel, 2);

        // STEP 2: add an artifact
        IPackageArtifact packageArtifact = _binModel.getTestPackage();
        newModuleArtifact.addArtifact(packageArtifact);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.class"), _binModel.getTestResource()
            .getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.java"), _srcModel.getTestResource()
            .getFullPath());

        //
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());

        // STEP 3: Undo
        IUndoableTransformation transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
            .remove(2);
        transformation.undo();

        transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
            .remove(1);
        transformation.undo();

        // assert that we one modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);
      }
    });
  }
}
