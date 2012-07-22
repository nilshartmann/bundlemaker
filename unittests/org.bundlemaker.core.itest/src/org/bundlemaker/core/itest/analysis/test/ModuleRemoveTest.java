package org.bundlemaker.core.itest.analysis.test;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest.analysis.test.framework.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.junit.Assert;
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

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    //
    getModularizedSystem().removeModule(
        new ModuleIdentifier("SimpleArtifactModelTest", "1.0.0"));

    // assert that we have no resource modules
    assertResourceModuleCountInModularizedSystem(0);
    assertResourceModuleCount(_binModel, 0);
    assertResourceModuleCount(_srcModel, 0);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void removeModule() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // create a new group
    IModuleArtifact newModuleArtifact = _binModel.getGroup2Artifact().getOrCreateModule("SimpleArtifactModelTest",
        "1.0.0");
    Assert.assertEquals("group1/group2/SimpleArtifactModelTest_1.0.0", newModuleArtifact.getQualifiedName());
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // remove module
    _binModel.getGroup2Artifact().removeArtifact(newModuleArtifact);

    // assert that we have no resource modules
    assertResourceModuleCountInModularizedSystem(0);
    assertResourceModuleCount(_binModel, 0);
    assertResourceModuleCount(_srcModel, 0);
  }
}
