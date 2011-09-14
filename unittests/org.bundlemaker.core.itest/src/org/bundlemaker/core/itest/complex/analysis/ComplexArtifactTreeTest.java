package org.bundlemaker.core.itest.complex.analysis;

import java.io.IOException;
import java.io.InputStream;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.projectdescription.ContentType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ComplexArtifactTreeTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testSourceNonHierarchical() throws CoreException, IOException {

    // Step 1: transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.getDependencyModel(getModularizedSystem(),
        ArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IArtifact artifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(artifact);

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream(
        "results/ComplexArtifactTreeTest_SourceNonHierarchical.txt");
    assertResult(ArtifactUtils.artifactToString(artifact), inputstream,
        "ComplexArtifactTreeTest_SourceNonHierarchical_" + getCurrentTimeStamp());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testBinaryNonHierarchical() throws CoreException, IOException {

    // transform the model...
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.getDependencyModel(getModularizedSystem(),
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IArtifact artifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(artifact);

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream(
        "results/ComplexArtifactTreeTest_BinaryNonHierarchical.txt");
    assertResult(ArtifactUtils.artifactToString(artifact), inputstream,
        "ComplexArtifactTreeTest_BinaryNonHierarchical_" + getCurrentTimeStamp());
  }

  @Test
  public void testMoveModule() throws CoreException, IOException {

    // Step 1: transform the model
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.getDependencyModel(getModularizedSystem(),
        ArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IArtifact moduleArtifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get the 'group1Artifact' artifact...
    IArtifact group1Artifact = rootArtifact.getChild("group1");
    Assert.assertNotNull(moduleArtifact);

    moduleArtifact.getParent(ArtifactType.Group).removeArtifact(moduleArtifact);
    group1Artifact.addArtifact(moduleArtifact);

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream("results/ComplexArtifactTreeTest_MoveModule.txt");
    assertResult(ArtifactUtils.artifactToString(moduleArtifact), inputstream, "ComplexArtifactTreeTest_MoveModule_"
        + getCurrentTimeStamp());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
