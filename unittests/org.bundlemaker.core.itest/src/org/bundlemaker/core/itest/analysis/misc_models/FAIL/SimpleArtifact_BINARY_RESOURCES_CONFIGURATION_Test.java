package org.bundlemaker.core.itest.analysis.misc_models.FAIL;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.transformation.RemoveArtifactsTransformation;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonElement;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleArtifact_BINARY_RESOURCES_CONFIGURATION_Test extends AbstractModularizedSystemTest {

  /** - */
  protected IRootArtifact   _rootArtifact;

  /** - */
  protected IModuleArtifact _module_Artifact;

  /** - */
  protected IGroupArtifact  _group2_Artifact;

  /** - */
  protected IGroupArtifact  _group1_Artifact;

  /**
   * {@inheritDoc}
   */
  @Override
  public void before() throws CoreException {
    super.before();

    _rootArtifact = createArtifactModel();
    _module_Artifact = assertSimpleArtifactModule(_rootArtifact);
    _group1_Artifact = assertGroupArtifact(_rootArtifact, "group1");
    _group2_Artifact = assertGroupArtifact(_rootArtifact, "group1/group2");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void after() throws CoreException {
    super.after();

    _rootArtifact = null;
    _module_Artifact = null;
    _group1_Artifact = null;
    _group2_Artifact = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "SimpleArtifactModelTest";
  }

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

    //
    System.out.println(klasseArtifact.getFullPath());
    
    
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

  /**
   * <p>
   * This test removes and adds a module artifact from/to the group artifact.
   * </p>
   * 
   * @throws CoreException
   * 
   * @throws Exception
   */
  @Test
  public void testGroup_RemoveModuleAndAddToSameGroup() throws CoreException {

    // TEST 1: REMOVE
    _group2_Artifact.removeArtifact(_module_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    // TEST 2: RE-ADD
    _group2_Artifact.addArtifact(_module_Artifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification(),
        new Path("group1/group2"));
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveModuleAndAddToExistingOtherGroup() throws CoreException {

    // REMOVE FROM GROUP_2 AND ADD TO GROUP_1
    _group2_Artifact.removeArtifact(_module_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    _group1_Artifact.addArtifact(_module_Artifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group1"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveModuleAndAddToNewGroup() throws CoreException {

    //
    IBundleMakerArtifact groupNew_Artifact = _rootArtifact.getOrCreateGroup(new Path("groupNew"));
    Assert.assertNotNull(groupNew_Artifact);

    // TEST: REMOVE FROM GROUP_1 AND ADD TO GROUP_2
    _group2_Artifact.removeArtifact(_module_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    groupNew_Artifact.addArtifact(_module_Artifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("groupNew"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToSameGroup() throws CoreException {

    // TEST: REMOVE AND ADD
    _group1_Artifact.removeArtifact(_group2_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    _group1_Artifact.addArtifact(_group2_Artifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group1/group2"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToExistingOtherGroup() throws CoreException {

    // TEST: remove from group1
    _group1_Artifact.removeArtifact(_group2_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    // TEST: add to root
    _rootArtifact.addArtifact(_group2_Artifact);
    ArtifactUtils.dumpArtifact(_rootArtifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("group2"), getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0")
        .getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_RemoveGroupAndAddToNewGroup() throws CoreException {

    // get newGroup group
    IBundleMakerArtifact newGroupGroup = _rootArtifact.getOrCreateGroup(new Path("newGroup"));
    Assert.assertNotNull(newGroupGroup);

    // TEST 3: REMOVE AND ADD TO PARENT
    _group1_Artifact.removeArtifact(_group2_Artifact);
    Assert.assertNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));

    newGroupGroup.addArtifact(_group2_Artifact);
    Assert.assertNotNull(getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0"));
    Assert.assertEquals(new Path("newGroup/group2"),
        getModularizedSystem().getModule("SimpleArtifactModelTest", "1.0.0").getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testGroup_CreateDuplicateGroup_getOrCreateGroup() throws CoreException {

    //
    IGroupArtifact group2_2_Artifact = _group1_Artifact.getOrCreateGroup(new Path("group2"));
    Assert.assertSame(_group2_Artifact, group2_2_Artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test(expected = RuntimeException.class)
  public void testGroup_CreateDuplicateGroup_createArtifactContainer() throws CoreException {

    //
    IGroupArtifact group1_no2_Artifact = _group1_Artifact.getOrCreateGroup(new Path("group1"));

    //
    _rootArtifact.addArtifact(group1_no2_Artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void testDependencies() throws Exception {

    //
    IModule executionEnvironmentModule = getModularizedSystem().getExecutionEnvironment();
    Assert.assertNotNull(executionEnvironmentModule.getType("javax.activation.DataHandler"));

    //
    Assert.assertNotNull(getModularizedSystem().getType("javax.activation.DataHandler"));

    //
    IBundleMakerArtifact jreModule = _rootArtifact.getModuleArtifact(executionEnvironmentModule);

    //
    IDependency dependency = _module_Artifact.getDependencyTo(jreModule);
    Assert.assertNotNull(dependency);
    Assert.assertEquals(_module_Artifact, dependency.getFrom());
    Assert.assertEquals(jreModule, dependency.getTo());
    Assert.assertEquals(dependency.getWeight(), 1);

    //
    Collection<IDependency> dependencies = dependency.getDependencies();

    //
    Assert.assertNotNull(dependencies);
    Assert.assertEquals(dependencies.size(), 1);

    IDependency[] underlyingDeps = dependencies.toArray(new IDependency[0]);
    Assert.assertEquals("de.test.Klasse", underlyingDeps[0].getFrom().getQualifiedName());
    Assert.assertEquals("javax.activation.DataHandler", underlyingDeps[0].getTo().getQualifiedName());
    Assert.assertEquals(dependency.getWeight(), 1);
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  protected IModuleArtifact assertSimpleArtifactModule(IBundleMakerArtifact rootArtifact) {

    // get the module
    IModuleArtifact moduleArtifact = ArtifactVisitorUtils.findModuleArtifact(rootArtifact, "SimpleArtifactModelTest",
        "1.0.0");

    // assert
    assertNode(moduleArtifact, IModuleArtifact.class, "SimpleArtifactModelTest_1.0.0");
    assertNode(moduleArtifact.getParent(), IGroupArtifact.class, "group2");
    assertNode(moduleArtifact.getParent().getParent(), IGroupArtifact.class, "group1");

    return moduleArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param node
   * @param type
   * @param nodeName
   */
  protected void assertNode(IBundleMakerArtifact node, Class<?> type, String nodeName) {
    Assert.assertTrue(String.format("Node '%s' has to be assignable from %s", node, type),
        type.isAssignableFrom(node.getClass()));
    Assert.assertEquals(nodeName, node.getName());
    Assert.assertNotNull(node.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param moduleArtifact
   * @return
   */
  protected IPackageArtifact assertTestPackage(IModuleArtifact moduleArtifact) {

    IPackageArtifact packageArtifact = ArtifactVisitorUtils.findPackageArtifact(moduleArtifact, "de.test");
    List<IBundleMakerArtifact> resources = new LinkedList<IBundleMakerArtifact>(packageArtifact.getChildren());
    Collections.sort(resources, new Comparator<IBundleMakerArtifact>() {
      @Override
      public int compare(IBundleMakerArtifact o1, IBundleMakerArtifact o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });

    assertResourceArtifacts(resources);

    return packageArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param qualifiedName
   * @return
   */
  protected IGroupArtifact assertGroupArtifact(IBundleMakerArtifact rootArtifact, String qualifiedName) {
    IGroupArtifact result = ArtifactVisitorUtils.findGroupArtifactByQualifiedName(rootArtifact, qualifiedName);
    Assert.assertNotNull(result);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected IRootArtifact createArtifactModel() {

    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(getConfiguration()).getRoot();

    Assert.assertNotNull(rootArtifact);
    Assert.assertEquals(2, rootArtifact.getChildren().size());

    // assert JRE
    Assert.assertNotNull(ArtifactVisitorUtils.findJreModuleArtifact(rootArtifact));

    // return the result
    return rootArtifact;
  }
}
