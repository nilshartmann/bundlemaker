package org.bundlemaker.core.itest.analysis;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.ModelTransformerCache;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleArtifact_BINARY_RESOURCES_CONFIGURATION_Test extends AbstractSimpleArtifactTest {


  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactModelConfiguration getConfiguration() {
    return ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testTransformedModel() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);
    ArtifactUtils.dumpArtifact(rootArtifact);

    // Step 2: test 'root' with children
    List<IBundleMakerArtifact> children = new LinkedList<IBundleMakerArtifact>(rootArtifact.getChildren());
    Assert.assertEquals(2, children.size());
    for (IBundleMakerArtifact child : children) {
      Assert.assertEquals(rootArtifact, child.getParent());
    }
    assertNode(children.get(0), ArtifactType.Module, "jdk16_jdk16", getModularizedSystem().getName());
    assertNode(children.get(1), ArtifactType.Group, "group1", getModularizedSystem().getName());

    // Step 3: test 'group1' with children
    children = new LinkedList<IBundleMakerArtifact>(children.get(1).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Group, "group2", "group1");

    // Step 4: test 'group2' with children
    children = new LinkedList<IBundleMakerArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Module, "SimpleArtifactModelTest_1.0.0", "group2");

    // Step 5: test 'SimpleArtifactModelTest_1.0.0' with children
    children = new LinkedList<IBundleMakerArtifact>(children.get(0).getChildren());
    Assert.assertEquals(1, children.size());
    assertNode(children.get(0), ArtifactType.Package, "test", "SimpleArtifactModelTest_1.0.0");

    // Step 6: test 'de.test' package with children
    children = new LinkedList<IBundleMakerArtifact>(children.get(0).getChildren());
    Collections.sort(children, new Comparator<IBundleMakerArtifact>() {
      @Override
      public int compare(IBundleMakerArtifact o1, IBundleMakerArtifact o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    Assert.assertEquals(2, children.size());
    assertNode(children.get(0), ArtifactType.Resource, "Klasse.class", "test");
    assertNode(children.get(1), ArtifactType.Resource, "Test.class", "test");
  }

  @Test
  public void testPackage_SimpleRemoveAndAdd() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the module
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get package group
    IBundleMakerArtifact packageDeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test");
    Assert.assertNotNull(packageDeTest);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: remove package
    moduleArtifact.removeArtifact(packageDeTest);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));
    Assert.assertNull(packageDeTest.getParent());
    Assert.assertEquals(2, packageDeTest.getChildren().size());

    // Test 3: add package
    moduleArtifact.addArtifact(packageDeTest);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));
  }

  @Test
  public void testResource_SimpleRemoveAndAdd() throws CoreException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), getConfiguration()).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get package
    IBundleMakerArtifact packageDeTest = rootArtifact.getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test");
    Assert.assertNotNull(packageDeTest);

    // get resources
    IBundleMakerArtifact resourceKlasseClass = rootArtifact
        .getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Klasse.class");
    Assert.assertNotNull(resourceKlasseClass);
    IBundleMakerArtifact resourceTestClass = rootArtifact
        .getChild("group1|group2|SimpleArtifactModelTest_1.0.0|de.test|Test.class");
    Assert.assertNotNull(resourceTestClass);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: remove resources
    packageDeTest.removeArtifact(resourceKlasseClass);
    packageDeTest.removeArtifact(resourceTestClass);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));

    // Test 2: add resources
    packageDeTest.addArtifact(resourceKlasseClass);
    packageDeTest.addArtifact(resourceTestClass);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ContentType.BINARY));
  }

}
