package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
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
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleBelowExistingGroup() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
    assertResourceModuleCount(getBinModel(), 2);
    assertResourceModuleCount(getSrcModel(), 2);

    // create a new group
    IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
    Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    assertResourceModuleCount(getBinModel(), 3);
    assertResourceModuleCount(getSrcModel(), 3);

    //
    IModuleArtifact srcModule = AnalysisModelQueries.getModuleArtifact(getBinModel().getRootArtifact(),
        new ModuleIdentifier("NewModule", "1.0.0"));
    IModuleArtifact binModule = AnalysisModelQueries.getModuleArtifact(getSrcModel().getRootArtifact(),
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
    assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
    assertResourceModuleCount(getBinModel(), 2);
    assertResourceModuleCount(getSrcModel(), 2);

    // create a new group
    IModuleArtifact newModuleArtifact = getBinModel().getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
    Assert.assertEquals("NewModule_1.0.0", newModuleArtifact.getQualifiedName());

    // assert that we have three groups
    Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    assertResourceModuleCount(getBinModel(), 3);
    assertResourceModuleCount(getSrcModel(), 3);
  }

  @Test(expected = AnalysisModelException.class)
  public void tryToCreateNewModuleInWrongGroup() throws Exception {

    //
    Assert.assertEquals(2, getModularizedSystem().getGroups().size());
    assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 2);
    assertResourceModuleCount(getBinModel(), 2);
    assertResourceModuleCount(getSrcModel(), 2);

    // We have 2 (!) transformations here, as the "CreateGroupTransformation" is
    // implemented as an inner transformation
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

    // STEP 1: create a new module
    IModuleArtifact moduleArtifact = getBinModel().getRootArtifact().getOrCreateModule(
        "group1/SimpleArtifactModelTest", "1.0.0");
  }
}
