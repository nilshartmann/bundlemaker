package org.bundlemaker.core.itest.analysis.complex;

import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DnDArtifactTreeTest extends AbstractModularizedSystemTest {

  /** - */
  private IAdvancedArtifact _rootArtifact;

  @Test
  public void testMoveModuleToNewGroup() throws CoreException, IOException {
    
    // prepare the model
    prepareModel();

    // get the 'jedit' artifact...
    IArtifact moduleArtifact = getArtifact(_rootArtifact, "group1|group2|jedit_1.0.0");
    
    //
    IArtifact testGroup = _rootArtifact.getDependencyModel().createArtifactContainer("testGroup", "testGroup", ArtifactType.Group);
    _rootArtifact.addArtifact(testGroup);

    //
    moduleArtifact.getParent().removeArtifact(moduleArtifact);

    // asserts
    assertTypeCount(246);

    //
    testGroup.addArtifact(moduleArtifact);

    // get the 'group2Artifact' artifact...
    IArtifact group2Artifact = getArtifact(_rootArtifact, "group1|group2");
    Assert.assertEquals(0, group2Artifact.getChildren().size());

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream("results/DnDArtifactTreeTest_MoveModuleToNewGroup.txt");
    assertResult(ArtifactUtils.artifactToString(moduleArtifact), inputstream,
        "DnDArtifactTreeTest_MoveModuleToNewGroup_" + getCurrentTimeStamp());

    // assert
    IModule module = getModularizedSystem().getModule("jedit", "1.0.0");
    Assert.assertEquals(new Path("testGroup"), module.getClassification());

    assertTypeCount(1438);
  }

  @Test
  public void testMoveGroupAndDelete() throws CoreException, IOException {

    prepareModel();

    ArtifactUtils.dumpArtifact(_rootArtifact);

    // get the 'jeditModuleArtifact' artifact...
    IArtifact jeditModuleArtifact = _rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(jeditModuleArtifact);

    // get the 'velocityModuleArtifact' artifact...
    IArtifact velocityModuleArtifact = _rootArtifact.getChild("velocity_1.5");
    Assert.assertNotNull(velocityModuleArtifact);

    //
    IArtifact testGroup = _rootArtifact.getDependencyModel().createArtifactContainer("testGroup", "testGroup", ArtifactType.Group);
    _rootArtifact.addArtifact(testGroup);

    //
    testGroup.addArtifact(jeditModuleArtifact);
    testGroup.addArtifact(velocityModuleArtifact);
    assertTypeCount(1438);

    //
    _rootArtifact.removeArtifact(testGroup);

    //
    assertTypeCount(0);

    //
    Assert.assertEquals(0, getModularizedSystem().getResourceModules().size());

    // //
    // moduleArtifact.getParent().removeArtifact(moduleArtifact);
    //
    // // asserts
    // Assert.assertEquals(getModularizedSystem().getExecutionEnvironment().getContainedTypes().size() + 246,
    // getModularizedSystem().getTypes().size());
    //
    // //
    // testGroup.addArtifact(moduleArtifact);
    //
    // // get the 'group2Artifact' artifact...
    // IArtifact group2Artifact = rootArtifact.getChild("group1|group2");
    // Assert.assertNotNull(group2Artifact);
    // Assert.assertEquals(0, group2Artifact.getChildren().size());
    //
    // // assert the result
    // InputStream inputstream = getClass().getResourceAsStream("results/DnDArtifactTreeTest_MoveModuleToNewGroup.txt");
    // assertResult(ArtifactUtils.artifactToString(moduleArtifact), inputstream,
    // "DnDArtifactTreeTest_MoveModuleToNewGroup_" + getCurrentTimeStamp());
    //
    // // assert
    // IModule module = getModularizedSystem().getModule("jedit", "1.0.0");
    // Assert.assertEquals(new Path("testGroup"), module.getClassification());
  }

  /**
   * <p>
   * </p>
   * 
   * @param typeCountWithoutJdkTypes
   */
  private void assertTypeCount(int typeCountWithoutJdkTypes) {
    Assert.assertEquals(getModularizedSystem().getExecutionEnvironment().getContainedTypes().size()
        + typeCountWithoutJdkTypes, getModularizedSystem().getTypes().size());
  }

  private void prepareModel() {
    
    assertTypeCount(1438);
    
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(
        getModularizedSystem(), ArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(dependencyModel);

    _rootArtifact = (IAdvancedArtifact) dependencyModel.getRoot();
    Assert.assertNotNull(_rootArtifact);
  }
  
  /**
   * <p>
   * </p>
   *
   * @param root
   * @param path
   * @return
   */
  private IArtifact getArtifact(IArtifact root, String path) {
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
}
