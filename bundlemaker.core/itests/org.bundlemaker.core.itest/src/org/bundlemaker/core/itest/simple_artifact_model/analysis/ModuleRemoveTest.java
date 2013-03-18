package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleRemoveTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Tests if the artifact models are updated correct if a resource module is removed in the resource model.
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void removeModuleInResourceModel() throws Exception {

    // //
    // assertResourceModuleCountInModularizedSystem(1);
    // assertResourceModuleCount(getBinModel(), 1);
    // assertResourceModuleCount(getSrcModel(), 1);
    //
    // //
    // getModularizedSystem().removeModule(new ModuleIdentifier("SimpleArtifactModelTest", "1.0.0"));
    //
    // // assert that we have no resource modules
    // assertResourceModuleCountInModularizedSystem(0);
    // assertResourceModuleCount(getBinModel(), 0);
    // assertResourceModuleCount(getSrcModel(), 0);
  }

//  /**
//   * <p>
//   * </p>
//   * 
//   * @throws Exception
//   */
//  // TODO!!
//  @Test(expected = UnsupportedOperationException.class)
//  public void removeModule() throws Exception {
//
//    //
//    assertResourceModuleCountInModularizedSystem(1);
//    assertResourceModuleCount(getBinModel(), 1);
//    assertResourceModuleCount(getSrcModel(), 1);
//
//    // create a new group
//    IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("SimpleArtifactModelTest",
//        "1.0.0");
//    Assert.assertEquals("group1/group2/SimpleArtifactModelTest_1.0.0", newModuleArtifact.getQualifiedName());
//    assertResourceModuleCountInModularizedSystem(1);
//    assertResourceModuleCount(getBinModel(), 1);
//    assertResourceModuleCount(getSrcModel(), 1);
//
//    // remove module
//    getBinModel().getGroup2Artifact().removeArtifact(newModuleArtifact);
//
//    // assert that we have no resource modules
//    assertResourceModuleCountInModularizedSystem(0);
//    assertResourceModuleCount(getBinModel(), 0);
//    assertResourceModuleCount(getSrcModel(), 0);
//  }
}
