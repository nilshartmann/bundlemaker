package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertGroupCount;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
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

    // //
    // assertResourceModuleCountInModularizedSystem(1);
    // assertResourceModuleCount(getBinModel(), 1);
    // assertResourceModuleCount(getSrcModel(), 1);
    //
    // // assert that we have two groups
    // assertGroupCountInModularizedSystem(2);
    // assertGroupCount(getBinModel(), 2);
    // assertGroupCount(getSrcModel(), 2);
    //
    // //
    // IModifiableModule module = getModularizedSystem().getModifiableResourceModule(
    // new ModuleIdentifier("SimpleArtifactModelTest", "1.0.0"));
    // module.setClassification(new Path("neu"));
    //
    // //
    // assertResourceModuleCountInModularizedSystem(1);
    // assertResourceModuleCount(getBinModel(), 1);
    // assertResourceModuleCount(getSrcModel(), 1);
    //
    // // assert that we have two groups
    // assertGroupCountInModularizedSystem(3);
    // assertGroupCount(getBinModel(), 3);
    // assertGroupCount(getSrcModel(), 3);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void changeModuleClassification() {

    // 'move' model to group 1
    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    Assert.assertEquals(new Path("group1"), getBinModel().getMainModuleArtifact().getAssociatedModule()
        .getClassification());
    assertGroupCount(getBinModel(), 2);

    // assert module parent in src model
    Assert.assertEquals(getSrcModel().getGroup1Artifact(), getSrcModel().getMainModuleArtifact().getParent());
    assertGroupCount(getSrcModel(), 2);
  }
}
