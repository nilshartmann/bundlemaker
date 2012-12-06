package org.bundlemaker.core.itest.analysis;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.analysis.framework.AbstractSimpleArtifactTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
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
  public IAnalysisModelConfiguration getConfiguration() {
    return AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * Simply tests the artifact model.
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testArtifactModel() throws CoreException {

    // get the artifact model
    IBundleMakerArtifact rootArtifact = createArtifactModel();

    // assert module artifact
    IModuleArtifact moduleArtifact = assertSimpleArtifactModule(rootArtifact);

    // assert 'de.test' package
    assertTestPackage(moduleArtifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testPackage_SimpleRemoveAndAdd() throws CoreException {

    // get the artifact model
    IBundleMakerArtifact rootArtifact = createArtifactModel();

    // assert module artifact
    IModuleArtifact moduleArtifact = assertSimpleArtifactModule(rootArtifact);

    // assert 'de.test' package
    IPackageArtifact packageArtifact = assertTestPackage(moduleArtifact);

    // Test 1: assert resources in the resource model
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));

    // Test 2: remove package
    moduleArtifact.removeArtifact(packageArtifact);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));
    Assert.assertNull(packageArtifact.getParent());
    Assert.assertEquals(2, packageArtifact.getChildren().size());

    // Test 3: add package
    moduleArtifact.addArtifact(packageArtifact);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));
  }

  @Test
  public void testResource_SimpleRemoveAndAdd() throws CoreException {

    // get the artifact model
    IRootArtifact rootArtifact = createArtifactModel();

    //
    IModuleArtifact moduleArtifact = assertSimpleArtifactModule(rootArtifact);

    // get package
    IBundleMakerArtifact packageArtifact = assertTestPackage(moduleArtifact);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));

    IResourceArtifact testArtifact = rootArtifact.getResourceArtifact(resourceModule.getResource("de/test/Test.class",
        ProjectContentType.BINARY));
    IResourceArtifact klasseArtifact = rootArtifact.getResourceArtifact(resourceModule.getResource(
        "de/test/Klasse.class", ProjectContentType.BINARY));

    // Test 2: remove resources
    packageArtifact.removeArtifact(klasseArtifact);
    packageArtifact.removeArtifact(testArtifact);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));

    // Test 2: add resources
    packageArtifact.addArtifact(klasseArtifact);
    packageArtifact.addArtifact(testArtifact);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testResource_MultiRemoveAndAdd() throws CoreException {

    // get the artifact model
    IRootArtifact rootArtifact = createArtifactModel();

    //
    IModuleArtifact moduleArtifact = assertSimpleArtifactModule(rootArtifact);

    // get package
    IBundleMakerArtifact packageArtifact = assertTestPackage(moduleArtifact);

    // Test 1: assert resources
    IResourceModule resourceModule = getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0");
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));

    List<IResourceArtifact> resourceArtifacts = new LinkedList<IResourceArtifact>();
    resourceArtifacts.add(rootArtifact.getResourceArtifact(resourceModule.getResource("de/test/Test.class",
        ProjectContentType.BINARY)));
    resourceArtifacts.add(rootArtifact.getResourceArtifact(resourceModule.getResource("de/test/Klasse.class",
        ProjectContentType.BINARY)));

    // Test 2: remove resources
    packageArtifact.removeArtifacts(resourceArtifacts);
    Assert.assertNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));

    // Test 3: add resources
    packageArtifact.addArtifacts(resourceArtifacts);
    Assert.assertNotNull(resourceModule.getResource("de/test/Test.class", ProjectContentType.BINARY));
    Assert.assertNotNull(resourceModule.getResource("de/test/Klasse.class", ProjectContentType.BINARY));
  }

  /**
   * {@inheritDoc}
   */
  public void assertResourceArtifacts(List<IBundleMakerArtifact> resources) {
    Assert.assertEquals(2, resources.size());
    assertNode(resources.get(0), IResourceArtifact.class, "Klasse.class", "test");
    assertNode(resources.get(1), IResourceArtifact.class, "Test.class", "test");
  }
}
