package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import org.bundlemaker.core.itest.analysis.simple_artifact_model.framework.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModule;
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

    //
    assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
    
    // rename the module
    IModifiableModule module = (IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule();
    module.setModuleIdentifier("neuerName", "1.2.3");
    
    //
    assertMainModuleNameAndVersion("neuerName", "1.2.3");
  }
  
  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void renameModuleArtifact() throws Exception {

    //
    assertMainModuleNameAndVersion("SimpleArtifactModelTest", "1.0.0");
    
    //
    _binModel.getMainModuleArtifact().setNameAndVersion("neuerName", "1.2.3");
    
    //
    assertMainModuleNameAndVersion("neuerName", "1.2.3");
  }
}
