package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_AddArtifactsToModuleMultipleTimes_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void undoAddPackageToModuleArtifactsMultipleTimes() throws Exception {
    
    perform(new AddToModule() {
      
      public void addToFirstModule(IModuleArtifact newModuleArtifact) {
        IPackageArtifact packageArtifact = _binModel.getTestPackage();
        newModuleArtifact.addArtifact(packageArtifact);
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
      }

      @Override
      public void addToSecondModule(IModuleArtifact newModuleArtifact) {
        newModuleArtifact.addArtifact(_binModel.getKlasseResource());
        newModuleArtifact.addArtifact(_binModel.getTestResource());
        Assert.assertEquals(6, getModularizedSystem().getTransformations().size());
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
  public void undoAddResourcesToModuleArtifactsMultipleTimes() throws Exception {

    perform(new AddToModule() {
      
      public void addToFirstModule(IModuleArtifact newModuleArtifact) {
        newModuleArtifact.addArtifact(_binModel.getKlasseResource());
        newModuleArtifact.addArtifact(_binModel.getTestResource());
        Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
      }
      
      @Override
      public void addToSecondModule(IModuleArtifact newModuleArtifact) {
        newModuleArtifact.addArtifact(_binModel.getKlasseResource());
        newModuleArtifact.addArtifact(_binModel.getTestResource());
        Assert.assertEquals(7, getModularizedSystem().getTransformations().size());
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
  public void undoAddTypesToModuleArtifactsMultipleTimes() throws Exception {
    perform(new AddToModule() {

      public void addToFirstModule(IModuleArtifact newModuleArtifact) {
        newModuleArtifact.addArtifact(_binModel.getKlasseResource().getChild("Klasse"));
        newModuleArtifact.addArtifact(_binModel.getTestResource().getChild("Test"));
        Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
      }
      
      public void addToSecondModule(IModuleArtifact newModuleArtifact) {
        newModuleArtifact.addArtifact(_binModel.getKlasseResource().getChild("Klasse"));
        newModuleArtifact.addArtifact(_binModel.getTestResource().getChild("Test"));
        Assert.assertEquals(7, getModularizedSystem().getTransformations().size());
      }
    });
  }

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

        addToModule.addToFirstModule(newModuleArtifact);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.class"), _binModel.getTestResource()
            .getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.java"), _srcModel.getTestResource()
            .getFullPath());
        
        // STEP 2: create another new module
        IModuleArtifact newModuleArtifact2 = _binModel.getGroup2Artifact().getOrCreateModule("NewModule2", "1.0.0");
        Assert.assertEquals("group1/group2/NewModule2_1.0.0", newModuleArtifact2.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(3);
        assertResourceModuleCount(_binModel, 3);
        assertResourceModuleCount(_srcModel, 3);

        addToModule.addToSecondModule(newModuleArtifact2);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule2_1.0.0/de/test/Test.class"), _binModel.getTestResource()
            .getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule2_1.0.0/de/test/Test.java"), _srcModel.getTestResource()
            .getFullPath());

        // STEP 3: Undo...
        for (int i = getModularizedSystem().getTransformations().size() - 1; i > 0; i--) {
          getModularizedSystem().undoLastTransformation();
        }

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
    public void addToFirstModule(IModuleArtifact newModuleArtifact);
    
    /**
     * <p>
     * </p>
     *
     * @param newModuleArtifact
     */
    public void addToSecondModule(IModuleArtifact newModuleArtifact);
  }
}
