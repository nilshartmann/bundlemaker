package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;
import static org.bundlemaker.core.itestframework.simple_artifact_model.SimpleArtifactModelAssert.assertMainModuleNameAndVersion;

import org.bundlemaker.core.analysis.IModuleArtifact;
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
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
        assertResourceModuleCount(getBinModel(), 1);
        assertResourceModuleCount(getSrcModel(), 1);
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {

        // create a new module
        IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
        Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        assertResourceModuleCount(getSrcModel(), 2);

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
  public void createNewModuleWithGroupBelowExistingGroup() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
        assertResourceModuleCount(getBinModel(), 1);
        assertResourceModuleCount(getSrcModel(), 1);
        Assert.assertEquals(2, getModularizedSystem().getTransformations().size());
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {
        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("test/NewModule",
            "1.0.0");
        Assert.assertEquals("group1/group2/test/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

        // assert that we two groups and two modules
        Assert.assertEquals(3, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        assertResourceModuleCount(getSrcModel(), 2);

        // We have 2 (!) transformations here, as the "CreateGroupTRansformation" is
        // implemented as an inner transformation
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
  public void createNewModuleBelowRoot() throws Exception {

    //
    NoModificationAssertion.assertNoModification(this, new NoModificationAssertion.Action() {

      /**
       * {@inheritDoc}
       */
      @Override
      public void prePostCondition() {
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
        assertResourceModuleCount(getBinModel(), 1);
        assertResourceModuleCount(getSrcModel(), 1);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void execute() {
        // STEP 1: create a new module
        IModuleArtifact newModuleArtifact = getBinModel().getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
        Assert.assertEquals("NewModule_1.0.0", newModuleArtifact.getQualifiedName());
        //
        Assert.assertEquals(2, getModularizedSystem().getGroups().size());
        assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
        assertResourceModuleCount(getBinModel(), 2);
        assertResourceModuleCount(getSrcModel(), 2);
      }
    });
  }
}
