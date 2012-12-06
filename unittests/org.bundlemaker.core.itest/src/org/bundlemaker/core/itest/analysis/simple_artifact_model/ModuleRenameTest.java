package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.itest.analysis.simple_artifact_model.framework.AbstractSimpleArtifactModelTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleRenameTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * Tests if the artifact models are updated correct if a resource module is removed in the resource model.
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameModuleArtifact() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);
    Assert.assertEquals("SimpleArtifactModelTest", _binModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.0.0", _binModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("SimpleArtifactModelTest", _srcModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.0.0", _srcModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("SimpleArtifactModelTest", _srcModel.getMainModuleArtifact().getAssociatedModule()
        .getModuleIdentifier().getName());
    Assert.assertEquals("1.0.0", _srcModel.getMainModuleArtifact().getAssociatedModule().getModuleIdentifier()
        .getVersion());
    Assert.assertNotNull(getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0"));

    //
    _binModel.getMainModuleArtifact().setNameAndVersion("neuerName", "1.2.3");

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);
    Assert.assertEquals("neuerName", _binModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.2.3", _binModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("neuerName", _srcModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.2.3", _srcModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("neuerName", _srcModel.getMainModuleArtifact().getAssociatedModule()
        .getModuleIdentifier().getName());
    Assert.assertEquals("1.2.3", _srcModel.getMainModuleArtifact().getAssociatedModule().getModuleIdentifier()
        .getVersion());
    Assert.assertNotNull(getModularizedSystem().getResourceModule("neuerName", "1.2.3"));
  }
  
  /**
   * <p>
   * </p>
   *
   * @throws Exception
   */
  @Test
  public void renameModule() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);
    Assert.assertEquals("SimpleArtifactModelTest", _binModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.0.0", _binModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("SimpleArtifactModelTest", _srcModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.0.0", _srcModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("SimpleArtifactModelTest", _srcModel.getMainModuleArtifact().getAssociatedModule()
        .getModuleIdentifier().getName());
    Assert.assertEquals("1.0.0", _srcModel.getMainModuleArtifact().getAssociatedModule().getModuleIdentifier()
        .getVersion());
    Assert.assertNotNull(getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0"));

    // 
    Assert.fail("TODO: Missing Method...");
    // _binModel.getMainModuleArtifact().getAssociatedModule().setNameAndVersion("neuerName", "1.2.3");

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);
    Assert.assertEquals("neuerName", _binModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.2.3", _binModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("neuerName", _srcModel.getMainModuleArtifact().getModuleName());
    Assert.assertEquals("1.2.3", _srcModel.getMainModuleArtifact().getModuleVersion());
    Assert.assertEquals("neuerName", _srcModel.getMainModuleArtifact().getAssociatedModule()
        .getModuleIdentifier().getName());
    Assert.assertEquals("1.2.3", _srcModel.getMainModuleArtifact().getAssociatedModule().getModuleIdentifier()
        .getVersion());
    Assert.assertNotNull(getModularizedSystem().getResourceModule("neuerName", "1.2.3"));
  }
}
