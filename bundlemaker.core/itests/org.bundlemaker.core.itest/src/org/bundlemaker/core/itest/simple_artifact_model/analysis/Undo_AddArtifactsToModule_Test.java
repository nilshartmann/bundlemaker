package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.NoModificationAssertion;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Undo_AddArtifactsToModule_Test extends AbstractSimpleArtifactModelTest {

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
        IPackageArtifact packageArtifact = getBinModel().getTestPackage();
        newModuleArtifact.addArtifact(packageArtifact);

        //
        Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
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
  public void undoAddResourcesToModuleArtifacts() throws Exception {

    perform(new AddToModule() {
      public void addToModule(IModuleArtifact newModuleArtifact) {

        //
        newModuleArtifact.addArtifact(getBinModel().getKlasseResource());
        newModuleArtifact.addArtifact(getBinModel().getTestResource());

        //
        Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
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
  public void undoAddTypesToModuleArtifacts() throws Exception {
    perform(new AddToModule() {
      public void addToModule(IModuleArtifact newModuleArtifact) {

        //
        newModuleArtifact.addArtifact(getBinModel().getKlasseResource().getChild("Klasse"));
        newModuleArtifact.addArtifact(getBinModel().getTestResource().getChild("Test"));

        //
        Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
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
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
        assertResourceModuleCount(getBinModel(), 1);
        assertResourceModuleCount(getSrcModel(), 1);

        //
        Assert.assertEquals(1, getModularizedSystem().getTransformations().size());

        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
        Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        assertResourceModuleCount(getSrcModel(), 2);

        addToModule.addToModule(newModuleArtifact);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.class"), getBinModel()
            .getTestResource().getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.java"), getSrcModel()
            .getTestResource().getFullPath());

        // STEP 3: Undo...
        for (int i = getModularizedSystem().getTransformations().size() - 1; i > 0; i--) {
          getModularizedSystem().undoLastTransformation();
        }

        // assert that we one modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
        assertResourceModuleCount(getBinModel(), 1);
        assertResourceModuleCount(getSrcModel(), 1);
      }
    }, getBinModel(), getSrcModel());
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
