package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleCreateNewTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Tests if the artifact models are updated correct if a resource module is added in the resource model.
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleInResourceModel() throws Exception {

    // //
    // assertResourceModuleCountInModularizedSystem(1);
    // assertResourceModuleCount(_binModel, 1);
    // assertResourceModuleCount(_srcModel, 1);
    //
    // //
    // IModifiableResourceModule resourceModule = getModularizedSystem().createResourceModule(
    // new ModuleIdentifier("test", "1.2.3"));
    // Assert.assertNull(resourceModule.getClassification());
    //
    // // assert that we have three groups
    // Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    // assertResourceModuleCount(_binModel, 2);
    // assertResourceModuleCount(_srcModel, 2);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleBelowExistingGroup() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // create a new group
    IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
    Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    assertResourceModuleCount(_binModel, 2);
    assertResourceModuleCount(_srcModel, 2);

    //
    IModuleArtifact srcModule = AnalysisModelQueries.getModuleArtifact(_binModel.getRootArtifact(),
        new ModuleIdentifier("NewModule", "1.0.0"));
    IModuleArtifact binModule = AnalysisModelQueries.getModuleArtifact(_srcModel.getRootArtifact(),
        new ModuleIdentifier("NewModule", "1.0.0"));

    //
    Assert.assertNotNull(srcModule);
    Assert.assertNotNull(binModule);

    //
    Assert.assertNotNull(srcModule.getParent());
    Assert.assertNotNull(binModule.getParent());
    Assert.assertEquals(srcModule.getParent().getName(), "group2");
    Assert.assertEquals(binModule.getParent().getName(), "group2");
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
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // create a new group
    IModuleArtifact newModuleArtifact = _binModel.getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
    Assert.assertEquals("NewModule_1.0.0", newModuleArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    assertResourceModuleCount(_binModel, 2);
    assertResourceModuleCount(_srcModel, 2);
  }

  @Test(expected = AnalysisModelException.class)
  public void tryToCreateNewModuleInWrongGroup() throws Exception {

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
        "group1/SimpleArtifactModelTest", "1.0.0");
  }
}
