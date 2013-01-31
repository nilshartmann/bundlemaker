package org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.itestframework.ModuleUtils;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
@RunWith(JeditAnalysisTestRunner.class)
public abstract class AbstractJeditAnalysisModelTest extends AbstractModularizedSystemTest {

  /** - */
  private JeditAnalysisModel _srcHierarchicalModel;

  /** - */
  private JeditAnalysisModel _binHierarchicalModel;

  /** - */
  private JeditAnalysisModel _srcFlatModel;

  /** - */
  private JeditAnalysisModel _binFlatModel;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {
    super.before();

    // prepare the model
    assertTypeCount(1466);

    // assert the input
    InputStream inputstream = AbstractJeditAnalysisModelTest.class.getResourceAsStream("results/"
        + getTestProjectName()
        + ".txt");
    assertResult(ModuleUtils.dump(getModularizedSystem().getResourceModule(getTestProjectName(), "1.0.0")),
        inputstream, getTestProjectName() + getCurrentTimeStamp());

    _srcHierarchicalModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);

    _binHierarchicalModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_BINARY_RESOURCES_CONFIGURATION);

    _srcFlatModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);

    _binFlatModel = new JeditAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // // get the root artifact
    // _rootArtifact = getModularizedSystem().getAnalysisModel(getArtifactModelConfiguration());
    // Assert.assertNotNull(_rootArtifact);
    //
    // //
    // if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
    // Assert.assertEquals(8275, _rootArtifact.getDependenciesTo().size());
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesFrom().size());
    // } else {
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesTo().size());
    // Assert.assertEquals(8158, _rootArtifact.getDependenciesFrom().size());
    // }
    //
    // assertDependencyWeight(getGroup1Artifact(), getJreArtifact(), 1904);
    // assertDependencyWeight(getVelocityModuleArtifact(), getJreArtifact(), 4);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getSrcHierarchicalModel() {
    return _srcHierarchicalModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getBinHierarchicalModel() {
    return _binHierarchicalModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getSrcFlatModel() {
    return _srcFlatModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public JeditAnalysisModel getBinFlatModel() {
    return _binFlatModel;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @param count
   */
  public void assertArtifactChildrenCount(IBundleMakerArtifact artifact, int count) {
    assertEquals(artifact.getChildren().toString(), count, artifact.getChildren().size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param from
   * @param to
   * @param weight
   */
  public void assertDependencyWeight(IBundleMakerArtifact from, IBundleMakerArtifact to, int weight) {
    IDependency dependency = from.getDependencyTo(to);

    if (weight == 0) {
      Assert.assertNull(dependency);
    } else {
      assertEquals(weight, dependency.getWeight());
    }
  }

  public void assertArtifactHasParent(IBundleMakerArtifact child, IBundleMakerArtifact parent) {
    assertEquals(parent, child.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeCountWithoutJdkTypes
   */
  protected void assertTypeCount(int typeCountWithoutJdkTypes) {

    // assert the specified number of types
    Assert.assertEquals("Expected: " + typeCountWithoutJdkTypes + ", actual: "
        + (getModularizedSystem().getTypes().size()
        - getModularizedSystem().getExecutionEnvironment().getContainedTypes().size()), getModularizedSystem()
        .getExecutionEnvironment().getContainedTypes().size()
        + typeCountWithoutJdkTypes, getModularizedSystem().getTypes().size());
  }

  /**
   * <p>
   * </p>
   *
   * @param count
   */
  protected void assertModificationCount(int count) {
    Assert.assertEquals(count, getSrcHierarchicalModel().getModifiedNotificationCount());
    Assert.assertEquals(count, getSrcFlatModel().getModifiedNotificationCount());
    Assert.assertEquals(count, getBinHierarchicalModel().getModifiedNotificationCount());
    Assert.assertEquals(count, getBinFlatModel().getModifiedNotificationCount());
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  protected final String computeTestProjectName() {
    return "jedit";
  }
}
