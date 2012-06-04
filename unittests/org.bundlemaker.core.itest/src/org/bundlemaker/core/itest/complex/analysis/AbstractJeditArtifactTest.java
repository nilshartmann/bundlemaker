package org.bundlemaker.core.itest.complex.analysis;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.util.ModuleUtils;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractJeditArtifactTest extends AbstractModularizedSystemTest {

  /** - */
  private IRootArtifact        _rootArtifact;

  /** - */
  private IBundleMakerArtifact _jeditModuleArtifact;

  /** - */
  private IBundleMakerArtifact _velocityModuleArtifact;

  /** - */
  private IBundleMakerArtifact _jreArtifact;

  /** - */
  private IBundleMakerArtifact _group1Artifact;

  /** - */
  private IBundleMakerArtifact _group2Artifact;

  /** - */
  private IBundleMakerArtifact _missingTypesArtifact;

  /**
   * {@inheritDoc}
   */
  @Before
  public void before() throws CoreException {
    super.before();

    // prepare the model
    assertTypeCount(1438);

    // assert the input
    InputStream inputstream = AbstractJeditArtifactTest.class.getResourceAsStream("results/" + getTestProjectName()
        + ".txt");
    assertResult(ModuleUtils.dump(getModularizedSystem().getResourceModule(getTestProjectName(), "1.0.0")),
        inputstream, getTestProjectName() + getCurrentTimeStamp());

    // TODO
    //
    _rootArtifact = getModularizedSystem().getArtifactModel(getArtifactModelConfiguration());
    Assert.assertNotNull(_rootArtifact);

    // // assert the input
    // String expectedResultName = "AbstractComplexTest_Input_" + getArtifactModelConfiguration();
    // String resourceName = "results/" + expectedResultName + ".txt";
    // inputstream = AbstractJeditArtifactTest.class.getResourceAsStream(resourceName);
    // Assert.assertNotNull(String.format("Resource '%s' not found.", resourceName), inputstream);
    // assertResult(ArtifactUtils.artifactToString(_rootArtifact), inputstream, expectedResultName +
    // getCurrentTimeStamp());
    //
    // if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
    // Assert.assertEquals(8275, _rootArtifact.getDependencies().size());
    // } else {
    // Assert.assertEquals(8158, _rootArtifact.getDependencies().size());
    // }

    //
    _group1Artifact = ArtifactVisitorUtils.findGroupArtifactByQualifiedName(_rootArtifact, "group1");
    _group1Artifact = ArtifactVisitorUtils.findGroupArtifactByQualifiedName(_rootArtifact, "group1/group2");
    _group1Artifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact, "jedit", "1.0.0");

    //
    _velocityModuleArtifact = ArtifactVisitorUtils.findModuleArtifact(_rootArtifact, "velocity", "1.5");

    //
    _jreArtifact = ArtifactVisitorUtils.findJreModuleArtifact(_rootArtifact);

    //
    if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
      _missingTypesArtifact = ArtifactVisitorUtils.findMissingTypesModuleArtifact(_rootArtifact);
    }

    assertDependencyWeight(getGroup1Artifact(), getJreArtifact(), 1904);
    assertDependencyWeight(getVelocityModuleArtifact(), getJreArtifact(), 4);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IArtifactModelConfiguration getArtifactModelConfiguration() {
    return IArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IBundleMakerArtifact getJeditModuleArtifact() {
    return _jeditModuleArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IBundleMakerArtifact getVelocityModuleArtifact() {
    return _velocityModuleArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IBundleMakerArtifact getJreArtifact() {
    return _jreArtifact;
  }

  protected final IBundleMakerArtifact getGroup1Artifact() {
    return _group1Artifact;
  }

  protected final IBundleMakerArtifact getGroup2Artifact() {
    return _group2Artifact;
  }

  protected final IBundleMakerArtifact getMissingTypesArtifact() {
    return _missingTypesArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeCountWithoutJdkTypes
   */
  protected void assertTypeCount(int typeCountWithoutJdkTypes) {
    Assert.assertEquals(getModularizedSystem().getExecutionEnvironment().getContainedTypes().size()
        + typeCountWithoutJdkTypes, getModularizedSystem().getTypes().size());
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @param root
  // * @param path
  // * @return
  // */
  // protected IBundleMakerArtifact getArtifact(IBundleMakerArtifact root, String path) {
  // IBundleMakerArtifact artifact = _rootArtifact.getChild(path);
  // Assert.assertNotNull(artifact);
  // return artifact;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
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
    IDependency dependency = from.getDependency(to);
    assertEquals(weight, dependency.getWeight());
  }

  public void assertArtifactHasParent(IBundleMakerArtifact child, IBundleMakerArtifact parent) {
    assertEquals(parent, child.getParent());
  }
}
