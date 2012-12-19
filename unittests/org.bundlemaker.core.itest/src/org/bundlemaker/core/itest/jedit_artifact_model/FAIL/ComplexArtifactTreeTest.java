package org.bundlemaker.core.itest.jedit_artifact_model.FAIL;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.AbstractJeditAnalysisModelTest;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ComplexArtifactTreeTest extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   * 
   */
  @Test
  public void testModule_GetOrCreateModule() {

    // Step 1: transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(rootArtifact);

    IModuleArtifact moduleArtifact = ArtifactHelper.findChild(rootArtifact, "jedit_1.0.0", IModuleArtifact.class);
    List<ITypeArtifact> types = ArtifactHelper.findChildren(moduleArtifact, ITypeArtifact.class);

    IModuleArtifact newModule = null;
    for (ITypeArtifact typeArtifact : types) {
      newModule = rootArtifact.getOrCreateModule("DEV/FRAMEWORK/de.test", "1.23");
      newModule.addArtifact(typeArtifact);
    }
  }

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
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IBundleMakerArtifact artifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
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
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IBundleMakerArtifact artifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
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
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.SOURCE_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    // get the 'jedit' artifact...
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    // get the 'group1Artifact' artifact...
    IBundleMakerArtifact group1Artifact = rootArtifact.getChild("group1");
    Assert.assertNotNull(moduleArtifact);

    moduleArtifact.getParent(IGroupArtifact.class).removeArtifact(moduleArtifact);
    group1Artifact.addArtifact(moduleArtifact);

    // assert the result
    InputStream inputstream = getClass().getResourceAsStream("results/ComplexArtifactTreeTest_MoveModule.txt");
    assertResult(ArtifactUtils.artifactToString(moduleArtifact), inputstream, "ComplexArtifactTreeTest_MoveModule_"
        + getCurrentTimeStamp());
  }
}
