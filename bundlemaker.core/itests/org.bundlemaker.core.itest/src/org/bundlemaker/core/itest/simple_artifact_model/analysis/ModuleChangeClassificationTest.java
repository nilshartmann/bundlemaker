package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModule;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleChangeClassificationTest extends AbstractSimpleArtifactModelTest {

  @Test
  public void changeModuleClassificationInResourceModel() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // assert that we have two groups
    assertGroupCountInModularizedSystem(2);
    assertGroupCount(_binModel, 2);
    assertGroupCount(_srcModel, 2);

    //
    IModifiableModule module = getModularizedSystem().getModifiableResourceModule(
        new ModuleIdentifier("SimpleArtifactModelTest", "1.0.0"));
    module.setClassification(new Path("neu"));

    //
    assertResourceModuleCountInModularizedSystem(1);
    assertResourceModuleCount(_binModel, 1);
    assertResourceModuleCount(_srcModel, 1);

    // assert that we have two groups
    assertGroupCountInModularizedSystem(3);
    assertGroupCount(_binModel, 3);
    assertGroupCount(_srcModel, 3);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void changeModuleClassification() {

    // 'move' model to group 1
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), _binModel.getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(_binModel, 2);

    // assert module parent in src model
    Assert.assertEquals(_srcModel.getGroup1Artifact(), _srcModel.getMainModuleArtifact().getParent());
    assertGroupCount(_srcModel, 2);
  }
}
