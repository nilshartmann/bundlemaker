package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourcesCount;

import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.selectors.DefaultArtifactSelector;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.resource.ITransformationAddArtifacts;
import org.bundlemaker.core.resource.ITransformationCreateGroup;
import org.bundlemaker.core.resource.ITransformationCreateModule;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TransformationHistoryTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void createGroupHistory() {

    // we have a group transformation that is done by the test....
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

    // create a new group
    getBinModel().getGroup2Artifact().getOrCreateGroup("NewGroup");

    //
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(2) instanceof ITransformationCreateGroup);

    // create a new group
    getBinModel().getRootArtifact().getOrCreateGroup("NewGroup2");

    //
    Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(2) instanceof ITransformationCreateGroup);
    Assert.assertTrue(getModularizedSystem().getTransformations().get(3) instanceof ITransformationCreateGroup);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void createModuleHistory() {

    // we have a group transformation that is done by the test....
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

    // create a new group
    getBinModel().getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");

    //
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(2) instanceof ITransformationCreateModule);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addResourcesHistory() {

    // we have one transformation (a group transformation) that is done by the test....
    Assert.assertEquals(2, getModularizedSystem().getTransformations().size());

    // STEP 1: create a new module
    IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
    assertResourcesCount(getBinModel().getMainModuleArtifact(), 2);
    assertResourcesCount(newModuleArtifact, 0);

    // assert one 'CreateModuleTransformation' transformation
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(2) instanceof ITransformationCreateModule);

    // STEP 2: add the 'Klasse' resource
    newModuleArtifact.addArtifact(getBinModel().getKlasseResource());
    assertResourcesCount(getBinModel().getMainModuleArtifact(), 1);
    assertResourcesCount(newModuleArtifact, 1);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(3) instanceof ITransformationAddArtifacts);

    // STEP 3: add the 'Test' resource
    newModuleArtifact.addArtifact(getBinModel().getTestResource());
    assertResourcesCount(getBinModel().getMainModuleArtifact(), 0);
    assertResourcesCount(newModuleArtifact, 2);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(5, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(4) instanceof ITransformationAddArtifacts);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void addPackageHistory() {

    // create a new group
    IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("hallo", "1.2.3");
    assertResourcesCount(getBinModel().getMainModuleArtifact(), 2);
    assertResourcesCount(newModuleArtifact, 0);

    // assert one 'CreateModuleTransformation' transformation
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(2) instanceof ITransformationCreateModule);

    // add the 'de.test' package
    newModuleArtifact.addArtifact(getBinModel().getTestPackage());
    assertResourcesCount(getBinModel().getMainModuleArtifact(), 0);
    assertResourcesCount(newModuleArtifact, 2);

    // assert 'AddTransformation' transformation
    Assert.assertEquals(4, getModularizedSystem().getTransformations().size());
    Assert.assertTrue(getModularizedSystem().getTransformations().get(3) instanceof ITransformationAddArtifacts);
  }
}
