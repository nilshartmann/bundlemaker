package org.bundlemaker.core.itest.jedit_artifact_model.analysis;

import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.AbstractJeditAnalysisModelTest;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ArtifactTreeVisitorTest extends AbstractJeditAnalysisModelTest {

  @Test
  public void testSrcHierarchicalModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getSrcHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 85, 873, 998);
  }

  @Test
  public void testBinHierarchicalModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 86, 1327, 998);
  }

  @Test
  public void testSrcFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getSrcFlatModel().getJeditModuleArtifact(), 0, 0, 1, 70, 873, 998);
  }

  @Test
  public void testBinFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinFlatModel().getJeditModuleArtifact(), 0, 0, 1, 71, 1327, 998);
  }
}
