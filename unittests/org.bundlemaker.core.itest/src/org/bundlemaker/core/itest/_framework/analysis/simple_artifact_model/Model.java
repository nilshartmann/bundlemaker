package org.bundlemaker.core.itest._framework.analysis.simple_artifact_model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
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
  private IModuleArtifact    _jre_Artifact;

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

  /** - */
  private int                _modifiedNotificationCount;

  /**
   * <p>
   * Creates a new instance of type {@link Model}.
   * </p>
   * 
   * @param modularizedSystem
   * @param configuration
   */
  public Model(IModularizedSystem modularizedSystem, IAnalysisModelConfiguration configuration) {

    //
    Assert.assertNotNull(modularizedSystem);
    Assert.assertNotNull(configuration);

    _modularizedSystem = modularizedSystem;
    _rootArtifact = createArtifactModel(modularizedSystem, configuration);
    _module_Artifact = assertSimpleArtifactModule(_rootArtifact);
    _jre_Artifact = assertJreArtifactModule(_rootArtifact);
    _velocity_module_Artifact = AnalysisModelQueries.getModuleArtifact(_rootArtifact, "velocity",
        "1.5");
    _group1_Artifact = assertGroupArtifact(_rootArtifact, "group1");
    _group2_Artifact = assertGroupArtifact(_rootArtifact, "group1/group2");
    _klasse_Resource = assertResourceArtifact(_module_Artifact, "Klasse");
    _test_Resource = assertResourceArtifact(_module_Artifact, "Test");
    _test_Package = assertPackageArtifact(_module_Artifact, "de.test");
    _de_Package = assertPackageArtifact(_module_Artifact, "de");

    //
    _rootArtifact.addAnalysisModelModifiedListener(new IAnalysisModelModifiedListener() {
      @Override
      public void analysisModelModified() {
        _modifiedNotificationCount++;
      }
    });
  }

  public int getModifiedNotificationCount() {
    return _modifiedNotificationCount;
  }

  public IModuleArtifact getJreArtifact() {
    return _jre_Artifact;
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
   * @return
   */
  public List<IDependency> getDependenciesTo(IBundleMakerArtifact from, IBundleMakerArtifact... to) {

    // test dependencies
    List<IDependency> deps = new LinkedList<IDependency>(from.getDependenciesTo(
        to));

    //
    Collections.sort(deps, new Comparator<IDependency>() {
      public int compare(IDependency o1, IDependency o2) {
        return o1.toString().compareTo(o2.toString());
      }
    });

    //
    return deps;
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
    IPackageArtifact result = AnalysisModelQueries.findPackageArtifact(moduleArtifact, qualifiedName);
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
    IGroupArtifact result = AnalysisModelQueries.findGroupArtifactByQualifiedName(rootArtifact, qualifiedName);
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
    rootArtifact.accept(new IAnalysisModelVisitor.Adapter() {
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
    IModuleArtifact moduleArtifact = AnalysisModelQueries.getModuleArtifact(rootArtifact,
        _modularizedSystem.getName(),
        "1.0.0");

    // assert
    assertNode(moduleArtifact, IModuleArtifact.class, _modularizedSystem.getName() + "_1.0.0");
    assertNode(moduleArtifact.getParent(), IGroupArtifact.class, "group2");
    assertNode(moduleArtifact.getParent().getParent(), IGroupArtifact.class, "group1");

    return moduleArtifact;
  }

  private IModuleArtifact assertJreArtifactModule(final IRootArtifact rootArtifact) {

    final IModuleArtifact[] result = new IModuleArtifact[1];

    //
    rootArtifact.accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {

        //
        if (rootArtifact.getModularizedSystem().getExecutionEnvironment().equals(moduleArtifact.getAssociatedModule())) {
          result[0] = moduleArtifact;
        }

        return false;
      }
    });

    //
    Assert.assertNotNull(result[0]);

    //
    return result[0];
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
      IAnalysisModelConfiguration configuration) {

    IRootArtifact rootArtifact = modularizedSystem.getAnalysisModel(configuration).getRoot();

    Assert.assertNotNull(rootArtifact);
    // Assert.assertEquals(4, rootArtifact.getChildren().size());

    // assert JRE
    Assert.assertNotNull(AnalysisModelQueries.getJreModuleArtifact(rootArtifact));

    // return the result
    return rootArtifact;
  }
}
