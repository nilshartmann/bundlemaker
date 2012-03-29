package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleArtifact_NO_TYPE_RESOURCES_CONFIGURATION_Test extends AbstractSimpleArtifactTest {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactModelConfiguration getConfiguration() {
    return ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION;
  }

  @Test
  public void testType_RemoveAndAddToParentPackage() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformer.getDependencyModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get package
    IArtifact packageDeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test");
    Assert.assertNotNull(packageDeTest);

    // get resources
    IArtifact typeKlasse = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Klasse");
    Assert.assertNotNull(typeKlasse);
    IArtifact typeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Test");
    Assert.assertNotNull(typeTest);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getType("de.test.Test"));
    Assert.assertNotNull(resourceModule.getType("de.test.Klasse"));

    // Test 2: remove resources
    packageDeTest.removeArtifact(typeKlasse);
    packageDeTest.removeArtifact(typeTest);
    Assert.assertNull(resourceModule.getType("de.test.Test"));
    Assert.assertNull(resourceModule.getType("de.test.Klasse"));
    Assert.assertEquals(0, packageDeTest.getChildren().size());

    // Test 2: add resources
    packageDeTest.addArtifact(typeKlasse);
    packageDeTest.addArtifact(typeTest);
    Assert.assertNotNull(resourceModule.getType("de.test.Test"));
    Assert.assertNotNull(resourceModule.getType("de.test.Klasse"));
    Assert.assertEquals(2, packageDeTest.getChildren().size());
  }

  @Test
  public void testType_RemoveAndAddToParentModule() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformer.getDependencyModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get module artifact
    IArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get resources
    IArtifact typeKlasse = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Klasse");
    Assert.assertNotNull(typeKlasse);
    IArtifact typeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Test");
    Assert.assertNotNull(typeTest);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getType("de.test.Test"));
    Assert.assertNotNull(resourceModule.getType("de.test.Klasse"));

    // get package
    IArtifact packageDeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test");
    Assert.assertNotNull(packageDeTest);

    // Test 2: remove resources
    moduleArtifact.removeArtifact(typeKlasse);
    moduleArtifact.removeArtifact(typeTest);
    Assert.assertNull(resourceModule.getType("de.test.Test"));
    Assert.assertNull(resourceModule.getType("de.test.Klasse"));
    Assert.assertEquals(0, packageDeTest.getChildren().size());

    // Test 2: add resources
    moduleArtifact.addArtifact(typeKlasse);
    moduleArtifact.addArtifact(typeTest);
    Assert.assertNotNull(resourceModule.getType("de.test.Test"));
    Assert.assertNotNull(resourceModule.getType("de.test.Klasse"));
    Assert.assertEquals(2, packageDeTest.getChildren().size());
  }
}
