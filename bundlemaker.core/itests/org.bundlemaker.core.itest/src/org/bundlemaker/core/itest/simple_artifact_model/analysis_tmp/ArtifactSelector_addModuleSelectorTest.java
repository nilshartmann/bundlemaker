package org.bundlemaker.core.itest.simple_artifact_model.analysis_tmp;

import org.bundlemaker.core.analysis.selectors.ModuleSelector;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelWithTypeLibraryTest;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactSelector_addModuleSelectorTest extends AbstractSimpleArtifactModelWithTypeLibraryTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void serializeModuleSelector() {

    //
    ModuleSelector moduleSelector = new ModuleSelector(_binModel.getRootArtifact(), "velocity**");
    Assert.assertEquals(1, moduleSelector.getBundleMakerArtifacts().size());

    //
    String json = GsonHelper.gson(getModularizedSystem()).toJson(moduleSelector);
    ModuleSelector selector2 =  GsonHelper.gson(getModularizedSystem()).fromJson(json, ModuleSelector.class);
    
    Assert.assertEquals(moduleSelector, selector2);
    Assert.assertEquals(1, selector2.getBundleMakerArtifacts().size());
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addModuleSelector() {

    // 'move' model to group 1
    Assert.assertNotNull(_binModel.getVelocityModuleArtifact());
    _binModel.getGroup1Artifact().addArtifacts(new ModuleSelector(_binModel.getRootArtifact(), "velocity**"));

    // assert
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getVelocityModuleArtifact().getParent());
    assertGroupCount(_binModel, 2);
    Assert.assertEquals(new Path("group1"), _binModel.getVelocityModuleArtifact().getAssociatedModule()
        .getClassification());

    // assert module parent in src model
    Assert.assertNotNull(_srcModel.getVelocityModuleArtifact());
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getVelocityModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
  }
}
