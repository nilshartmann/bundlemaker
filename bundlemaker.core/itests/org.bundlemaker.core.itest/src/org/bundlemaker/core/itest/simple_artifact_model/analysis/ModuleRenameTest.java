package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
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
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameModule() throws Exception {

    // //
    // assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
    //
    // // rename the module
    // IModifiableModule module = (IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule();
    // module.setModuleIdentifier("neuerName", "1.2.3");
    //
    // //
    // assertMainModuleNameAndVersion("neuerName", "1.2.3");
  }
  
  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameModuleArtifact() throws Exception {

    // //
    // assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
    //
    // //
    // getBinModel().getMainModuleArtifact().setNameAndVersion("neuerName", "1.2.3");
    //
    // //
    // assertMainModuleNameAndVersion("neuerName", "1.2.3");
  }
}
