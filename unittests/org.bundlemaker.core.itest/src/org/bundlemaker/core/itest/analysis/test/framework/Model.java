package org.bundlemaker.core.itest.analysis.test.framework;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.junit.Assert;

/**
 * <p>
 * 
 * <pre>
 * Root : SimpleArtifactModelTest 
 *  Group : group1 
 *  Group : group2 
 *   Module : SimpleArtifactModelTest_1.0.0 
 *    Package : de
 *     Package : test 
 *      Resource : Klasse.class 
 *       Type : Klasse 
 *      Resource : Test.class
 *       Type : Test
 * </pre>
 * 
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class Model {

  /** - */
  private IRootArtifact      _rootArtifact;

  /** - */
  private IModuleArtifact    _module_Artifact;

  /** - */
  private IModuleArtifact    _velocity_module_Artifact;

  /** - */
  private IGroupArtifact     _group2_Artifact;

  /** - */
  private IGroupArtifact     _group1_Artifact;

  /** - */
  private IResourceArtifact  _klasse_Resource;

  /** - */
  private IResourceArtifact  _test_Resource;

  /** - */
  private IPackageArtifact   _test_Package;

  /** - */
  private IPackageArtifact   _de_Package;

  /** - */
  private IModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link Model}.
   * </p>
   * 
   * @param modularizedSystem
   * @param configuration
   */
  public Model(IModularizedSystem modularizedSystem, IArtifactModelConfiguration configuration) {

    //
    Assert.assertNotNull(modularizedSystem);
    Assert.assertNotNull(configuration);

    _modularizedSystem = modularizedSystem;
    _rootArtifact = createArtifactModel(modularizedSystem, configuration);
    _module_Artifact = assertSimpleArtifactModule(_rootArtifact);
    _velocity_module_Artifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact, "velocity",
        "1.5");
    _group1_Artifact = assertGroupArtifact(_rootArtifact, "group1");
    _group2_Artifact = assertGroupArtifact(_rootArtifact, "group1/group2");
    _klasse_Resource = assertResourceArtifact(_module_Artifact, "Klasse");
    _test_Resource = assertResourceArtifact(_module_Artifact, "Test");
    _test_Package = assertPackageArtifact(_module_Artifact, "de.test");
    _de_Package = assertPackageArtifact(_module_Artifact, "de");
  }

  public IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  public IModuleArtifact getMainModuleArtifact() {
    return _module_Artifact;
  }
  
  public IModuleArtifact getVelocityModuleArtifact() {
    return _velocity_module_Artifact;
  }

  public IGroupArtifact getGroup2Artifact() {
    return _group2_Artifact;
  }

  public IGroupArtifact getGroup1Artifact() {
    return _group1_Artifact;
  }

  public IResourceArtifact getKlasseResource() {
    return _klasse_Resource;
  }

  public IResourceArtifact getTestResource() {
    return _test_Resource;
  }

  public IPackageArtifact getDePackage() {
    return _de_Package;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPackageArtifact getTestPackage() {
    return _test_Package;
  }

  /**
   * <p>
   * </p>
   * 
   * @param module_Artifact
   * @param string
   * @return
   */
  private IPackageArtifact assertPackageArtifact(IModuleArtifact moduleArtifact, String qualifiedName) {
    IPackageArtifact result = ArtifactVisitorUtils.findPackageArtifact(moduleArtifact, qualifiedName);
    Assert.assertNotNull(result);
    return result;
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
    Assert.assertNotNull(qualifiedName, result);
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @param resourceName
   * @return
   */
  protected IResourceArtifact assertResourceArtifact(IBundleMakerArtifact rootArtifact, final String resourceNamePrefix) {

    // create the result array
    final List<IResourceArtifact> result = new LinkedList<IResourceArtifact>();

    // visit
    rootArtifact.accept(new IArtifactTreeVisitor.Adapter() {
      @Override
      public boolean visit(IResourceArtifact resourceArtifact) {

        //
        if (resourceArtifact.getName().startsWith(resourceNamePrefix)) {
          result.add(resourceArtifact);
        }

        //
        return true;
      }
    });

    // assert equals
    Assert.assertEquals(
        String.format("Expected exactly one resource with name prefix '%s'.", resourceNamePrefix),
        1, result.size());

    //
    return result.get(0);
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   * @return
   */
  private IModuleArtifact assertSimpleArtifactModule(IBundleMakerArtifact rootArtifact) {

    // get the module
    IModuleArtifact moduleArtifact = ArtifactVisitorUtils.findModuleArtifact(rootArtifact,
        _modularizedSystem.getName(),
        "1.0.0");

    // assert
    assertNode(moduleArtifact, IModuleArtifact.class, _modularizedSystem.getName() + "_1.0.0");
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
   * @return
   */
  private IRootArtifact createArtifactModel(IModularizedSystem modularizedSystem,
      IArtifactModelConfiguration configuration) {

    IRootArtifact rootArtifact = modularizedSystem.getArtifactModel(configuration).getRoot();

    Assert.assertNotNull(rootArtifact);
    // Assert.assertEquals(4, rootArtifact.getChildren().size());

    // assert JRE
    Assert.assertNotNull(ArtifactVisitorUtils.findJreModuleArtifact(rootArtifact));

    // return the result
    return rootArtifact;
  }
}
