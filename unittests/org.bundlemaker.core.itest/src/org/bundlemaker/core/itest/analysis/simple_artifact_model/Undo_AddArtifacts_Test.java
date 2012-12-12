package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
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
  public void undoAddPackageToModuleArtifacts() throws Exception {
    perform(new AddToModule() {
      public void addToModule(IModuleArtifact newModuleArtifact) {

        //
        IPackageArtifact packageArtifact = _binModel.getTestPackage();
        newModuleArtifact.addArtifact(packageArtifact);

        //
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
      }
    });
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void undoAddResourcesToModuleArtifacts() throws Exception {
  // perform(new AddToModule() {
  // public void addToModule(IModuleArtifact newModuleArtifact) {
  //
  // //
  // newModuleArtifact.addArtifact(_binModel.getKlasseResource());
  // newModuleArtifact.addArtifact(_binModel.getTestResource());
  //
  // //
  // Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
  // }
  // });
  // }

  // /**
  // * <p>
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void undoAddTypesToModuleArtifacts() throws Exception {
  // perform(new AddToModule() {
  // public void addToModule(IModuleArtifact newModuleArtifact) {
  //
  // //
  // newModuleArtifact.addArtifact(_binModel.getKlasseResource().getChild("Klasse"));
  // newModuleArtifact.addArtifact(_binModel.getTestResource().getChild("Test"));
  //
  // //
  // Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
  // }
  // });
  // }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  private void perform(final AddToModule addToModule) throws Exception {

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

        addToModule.addToModule(newModuleArtifact);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.class"), _binModel.getTestResource()
            .getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.java"), _srcModel.getTestResource()
            .getFullPath());

        // STEP 3: Undo 'add package'
        IUndoableTransformation transformation = (IUndoableTransformation) getModularizedSystem().getTransformations()
            .remove(2);
        transformation.undo();

        // STEP 3: Undo 'new Module'
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

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static interface AddToModule {

    /**
     * <p>
     * </p>
     * 
     * @param newModuleArtifact
     */
    public void addToModule(IModuleArtifact newModuleArtifact);
  }
}
