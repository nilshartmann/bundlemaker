package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.ArtifactUtils;
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
public class Undo_AddArtifactsToPackage_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Not allowed: add package to package
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddPackageToModuleArtifacts_1() throws Exception {
    perform(new AddToPackage() {
      @Override
      public void addToPackage(IPackageArtifact newPackageArtifact) {
        IPackageArtifact packageArtifact = _binModel.getTestPackage();
        newPackageArtifact.addArtifact(packageArtifact);
      }
    });
  }

  /**
   * <p>
   * Not allowed: add package to package
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddPackageToModuleArtifacts_2() throws Exception {
    perform(new AddToPackage() {
      @Override
      public void addToPackage(IPackageArtifact newPackageArtifact) {
        IPackageArtifact packageArtifact = _binModel.getTestPackage();
        newPackageArtifact.getParent().addArtifact(packageArtifact);
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
  public void undoToAddResourceToModuleArtifacts() throws Exception {
    perform(new AddToPackage() {
      @Override
      public void addToPackage(IPackageArtifact newPackageArtifact) {
        newPackageArtifact.addArtifact(_binModel.getTestResource());
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
  public void undoToAddTypeToModuleArtifacts() throws Exception {
    perform(new AddToPackage() {
      @Override
      public void addToPackage(IPackageArtifact newPackageArtifact) {
        newPackageArtifact.addArtifact(_binModel.getTestResource().getChild("Test"));
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  private void perform(final AddToPackage addToModule) throws Exception {

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

        newModuleArtifact.addArtifact(_binModel.getKlasseResource());
        IPackageArtifact newPackageArtifact = _binModel.getKlasseResource().getParent(IPackageArtifact.class);
        Assert.assertNotNull(newPackageArtifact);
        Assert.assertNotNull(newPackageArtifact.getParent(IModuleArtifact.class));
        Assert.assertEquals("NewModule", newPackageArtifact.getParent(IModuleArtifact.class).getModuleName());

        addToModule.addToPackage(newPackageArtifact);

        // assert that we two groups and two modules
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.class"), _binModel.getTestResource()
            .getFullPath());
        Assert.assertEquals(new Path("group1/group2/NewModule_1.0.0/de/test/Test.java"), _srcModel.getTestResource()
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
  private static interface AddToPackage {

    /**
     * <p>
     * </p>
     * 
     * @param newModuleArtifact
     */
    public void addToPackage(IPackageArtifact packageArtifact);
  }
}
