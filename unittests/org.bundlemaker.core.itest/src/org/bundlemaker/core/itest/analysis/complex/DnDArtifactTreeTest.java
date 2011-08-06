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

  @Test
  public void testMoveModuleToNewGroup() throws CoreException, IOException {

    // Step 1: transform the model
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getBundleMakerProject(),
        getModularizedSystem(), ArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(dependencyModel);

    //
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) dependencyModel.getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IArtifact moduleArtifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    //
    IArtifact testGroup = dependencyModel.createArtifactContainer("testGroup", "testGroup", ArtifactType.Group);
    rootArtifact.addArtifact(testGroup);

    //
    moduleArtifact.getParent().removeArtifact(moduleArtifact);
    testGroup.addArtifact(moduleArtifact);

    // get the 'group2Artifact' artifact...
    IArtifact group2Artifact = rootArtifact.getChild("group1|group2");
    Assert.assertNotNull(group2Artifact);
    Assert.assertEquals(0, group2Artifact.getChildren().size());

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream("results/DnDArtifactTreeTest_MoveModuleToNewGroup.txt");
    assertResult(ArtifactUtils.artifactToString(moduleArtifact), inputstream,
        "DnDArtifactTreeTest_MoveModuleToNewGroup_" + getCurrentTimeStamp());

    // assert
    IModule module = getModularizedSystem().getModule("jedit", "1.0.0");
    Assert.assertEquals(new Path("testGroup"), module.getClassification());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
