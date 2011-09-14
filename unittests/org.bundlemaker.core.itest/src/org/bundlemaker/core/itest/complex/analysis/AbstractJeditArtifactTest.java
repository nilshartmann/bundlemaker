package org.bundlemaker.core.itest.complex.analysis;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.util.ModuleUtils;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Before;

public abstract class AbstractJeditArtifactTest extends AbstractModularizedSystemTest {

  /** - */
  private IAdvancedArtifact _rootArtifact;

  /** - */
  private IArtifact         _jeditModuleArtifact;

  /** - */
  private IArtifact         _velocityModuleArtifact;

  /** - */
  private IArtifact         _jdk16Artifact;

  /** - */
  private IArtifact         _group1Artifact;

  /** - */
  private IArtifact         _group2Artifact;

  /** - */
  private IArtifact         _missingTypesArtifact;

  @Before
  public void init() throws CoreException {
    super.init();

    // prepare the model
    assertTypeCount(1438);

    // assert the input
    InputStream inputstream = getClass().getResourceAsStream("results/" + getTestProjectName() + ".txt");
    assertResult(ModuleUtils.dump(getModularizedSystem().getResourceModule(getTestProjectName(), "1.0.0"),
        getModularizedSystem()), inputstream, getTestProjectName() + getCurrentTimeStamp());

    //
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getModularizedSystem(),
        getArtifactModelConfiguration());
    Assert.assertNotNull(dependencyModel);

    _rootArtifact = (IAdvancedArtifact) dependencyModel.getRoot();
    Assert.assertNotNull(_rootArtifact);

    // assert the input
    String expectedResultName = "AbstractComplexTest_Input_" + getArtifactModelConfiguration();
    String resourceName = "results/" + expectedResultName + ".txt";
    inputstream = getClass().getResourceAsStream(resourceName);
    Assert.assertNotNull(String.format("Resource '%s' not found.", resourceName), inputstream);
    assertResult(ArtifactUtils.artifactToString(_rootArtifact), inputstream, expectedResultName + getCurrentTimeStamp());

    Assert.assertEquals(8275, _rootArtifact.getDependencies().size());

    _group1Artifact = getArtifact(_rootArtifact, "group1");
    _group2Artifact = getArtifact(_rootArtifact, "group1|group2");
    _jeditModuleArtifact = getArtifact(_rootArtifact, "group1|group2|jedit_1.0.0");
    _velocityModuleArtifact = getArtifact(_rootArtifact, "velocity_1.5");
    _jdk16Artifact = getArtifact(_rootArtifact, "jdk16_jdk16");
    _missingTypesArtifact = getArtifact(_rootArtifact, "<< Missing Types >>");

    assertDependencyWeight(getGroup1Artifact(), getJdkArtifact(), 1904);
    assertDependencyWeight(getVelocityModuleArtifact(), getJdkArtifact(), 4);
  }

  public ArtifactModelConfiguration getArtifactModelConfiguration() {
    return ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IAdvancedArtifact getRootArtifact() {
    return _rootArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected final IArtifact getJeditModuleArtifact() {
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
  protected IArtifact getArtifact(IArtifact root, String path) {
    IArtifact artifact = _rootArtifact.getChild(path);
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
  protected IArtifact createNewGroup(IArtifact artifact, String name) {
    //
    IArtifact testGroup = getRootArtifact().getDependencyModel()
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
  protected IArtifact createNewModule(IArtifact artifact, String name) {
    //
    IArtifact testGroup = getRootArtifact().getDependencyModel().createArtifactContainer(name, name,
        ArtifactType.Module);
    artifact.addArtifact(testGroup);
    return testGroup;
  }
}
