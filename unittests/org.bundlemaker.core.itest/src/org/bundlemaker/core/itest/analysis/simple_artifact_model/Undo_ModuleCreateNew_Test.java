package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.IModuleArtifact;
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
public class Undo_ModuleCreateNew_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleBelowExistingGroup() throws Exception {

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

        //
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

        // STEP 2: Undo
        IUndoableTransformation transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
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

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
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

        //
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("test/NewModule", "1.0.0");
        Assert.assertEquals("group1/group2/test/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(2);
        assertResourceModuleCount(_binModel, 2);
        assertResourceModuleCount(_srcModel, 2);

        // We have 2 (!) transformations here, as the "CreateGroupTRansformation" is 
        // implemented as an inner transformation
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

        // STEP 2: Undo
        IUndoableTransformation transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
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

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleBelowRoot() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new Runnable() {

      @Override
      public void run() {

        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(1);
        assertResourceModuleCount(_binModel, 1);
        assertResourceModuleCount(_srcModel, 1);

        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = _binModel.getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
        Assert.assertEquals("NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(2);
        assertResourceModuleCount(_binModel, 2);
        assertResourceModuleCount(_srcModel, 2);

        // STEP 2: Undo transformation
        IUndoableTransformation transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
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

  // /**
  // * <p>
  // * Tests if the artifact models are updated correct if a resource module is added in the resource model.
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void createNewModuleInResourceModel() throws Exception {
  //
  // // assert that we have just one module
  // assertResourceModuleCountInModularizedSystem(1);
  // assertResourceModuleCount(_binModel, 1);
  // assertResourceModuleCount(_srcModel, 1);
  //
  // //
  // Assert.assertEquals(1, _binModel.getRootArtifact().getModularizedSystem().getTransformations().size());
  //
  // // create new resourceModule
  // IModifiableResourceModule resourceModule = getModularizedSystem().createResourceModule(
  // new ModuleIdentifier("test", "1.2.3"));
  // Assert.assertNull(resourceModule.getClassification());
  //
  // // assert that we have two modules
  // Assert.assertEquals(2, getModularizedSystem().getGroups().size());
  // assertResourceModuleCount(_binModel, 2);
  // assertResourceModuleCount(_srcModel, 2);
  //
  // //
  // Assert.assertEquals(2, _binModel.getRootArtifact().getModularizedSystem().getTransformations().size());
  // }

  // /**
  // * <p>
  // * Tests if the artifact models are updated correct if a resource module is added in the resource model.
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void createNewModuleWithClassificationInResourceModel() throws Exception {
  //
  // //
  // assertResourceModuleCountInModularizedSystem(1);
  // assertResourceModuleCount(_binModel, 1);
  // assertResourceModuleCount(_srcModel, 1);
  //
  // //
  // IModifiableResourceModule resourceModule = getModularizedSystem().createResourceModule(
  // new ModuleIdentifier("test", "1.2.3"), new Path("group1/NewGroup"));
  // Assert.assertEquals("group1/NewGroup", resourceModule.getClassification());
  //
  // // assert that we have three groups
  // Assert.assertEquals(2, getModularizedSystem().getGroups().size());
  // assertResourceModuleCount(_binModel, 2);
  // assertResourceModuleCount(_srcModel, 2);
  // }
}
