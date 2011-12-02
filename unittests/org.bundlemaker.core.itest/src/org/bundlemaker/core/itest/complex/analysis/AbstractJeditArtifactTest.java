package org.bundlemaker.core.itest.complex.analysis;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
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
  private IBundleMakerArtifact _jdk16Artifact;

  /** - */
  private IBundleMakerArtifact _group1Artifact;

  /** - */
  private IBundleMakerArtifact _group2Artifact;

  /** - */
  private IBundleMakerArtifact _missingTypesArtifact;

  @Before
  public void init() throws CoreException {
    super.init();

    // prepare the model
    assertTypeCount(1438);

    // assert the input
    InputStream inputstream = AbstractJeditArtifactTest.class.getResourceAsStream("results/" + getTestProjectName()
        + ".txt");
    assertResult(ModuleUtils.dump(getModularizedSystem().getResourceModule(getTestProjectName(), "1.0.0")),
        inputstream, getTestProjectName() + getCurrentTimeStamp());

    //
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getModularizedSystem(),
        getArtifactModelConfiguration());
    Assert.assertNotNull(dependencyModel);

    _rootArtifact = (IRootArtifact) dependencyModel.getRoot();
    Assert.assertNotNull(_rootArtifact);

    // assert the input
    String expectedResultName = "AbstractComplexTest_Input_" + getArtifactModelConfiguration();
    String resourceName = "results/" + expectedResultName + ".txt";
    inputstream = AbstractJeditArtifactTest.class.getResourceAsStream(resourceName);
    Assert.assertNotNull(String.format("Resource '%s' not found.", resourceName), inputstream);
    assertResult(ArtifactUtils.artifactToString(_rootArtifact), inputstream, expectedResultName + getCurrentTimeStamp());

    if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
      Assert.assertEquals(8275, _rootArtifact.getDependencies().size());
    } else {
      Assert.assertEquals(8158, _rootArtifact.getDependencies().size());
    }

    _group1Artifact = getArtifact(_rootArtifact, "group1");
    _group2Artifact = getArtifact(_rootArtifact, "group1|group2");
    _jeditModuleArtifact = getArtifact(_rootArtifact, "group1|group2|jedit_1.0.0");
    _velocityModuleArtifact = getArtifact(_rootArtifact, "velocity_1.5");
    _jdk16Artifact = getArtifact(_rootArtifact, "jdk16_jdk16");
    if (_rootArtifact.getConfiguration().isIncludeVirtualModuleForMissingTypes()) {
      _missingTypesArtifact = getArtifact(_rootArtifact, "<< Missing Types >>");
    }

    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 1904);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);
  }

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
  protected final IArtifact getVelocityModuleArtifact() {
    return _velocityModuleArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IArtifact getJdkArtifact() {
    return _jdk16Artifact;
  }

  protected final IArtifact getGroup1Artifact() {
    return _group1Artifact;
  }

  protected final IArtifact getGroup2Artifact() {
    return _group2Artifact;
  }

  protected final IArtifact getMissingTypesArtifact() {
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

  /**
   * <p>
   * </p>
   * 
   * @param root
   * @param path
   * @return
   */
  protected IBundleMakerArtifact getArtifact(IArtifact root, String path) {
    IBundleMakerArtifact artifact = _rootArtifact.getChild(path);
    Assert.assertNotNull(artifact);
    return artifact;
  }

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
  public void assertArtifactChildrenCount(IArtifact artifact, int count) {
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
  public void assertDependencyWeight(IArtifact from, IArtifact to, int weight) {
    IDependency dependency = from.getDependency(to);
    assertEquals(weight, dependency.getWeight());
  }

  public void assertArtifactHasParent(IArtifact child, IArtifact parent) {
    assertEquals(parent, child.getParent());
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected IBundleMakerArtifact createNewGroup(IArtifact artifact, String name) {
    //
    IBundleMakerArtifact testGroup = (IBundleMakerArtifact) getRootArtifact().getDependencyModel()
        .createArtifactContainer(name, name, ArtifactType.Group);
    artifact.addArtifact(testGroup);
    return testGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected IBundleMakerArtifact createNewModule(IArtifact artifact, String name) {
    //
    IBundleMakerArtifact testGroup = (IBundleMakerArtifact) getRootArtifact().getDependencyModel()
        .createArtifactContainer(name, name, ArtifactType.Module);
    artifact.addArtifact(testGroup);
    return testGroup;
  }
}
