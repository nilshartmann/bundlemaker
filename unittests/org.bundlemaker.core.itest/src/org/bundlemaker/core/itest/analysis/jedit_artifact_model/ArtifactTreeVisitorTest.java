package org.bundlemaker.core.itest.analysis.jedit_artifact_model;

import org.bundlemaker.core.itest.analysis.framework.ArtifactVisitorUtils;
import org.bundlemaker.core.itest.analysis.jedit_artifact_model.framework.AbstractJeditAnalysisModelTest;
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
    ArtifactVisitorUtils.countArtifacts(getSrcHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 84, 852, 975);
  }

  @Test
  public void testBinHierarchicalModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 84, 1302, 975);
  }

  @Test
  public void testSrcFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getSrcFlatModel().getJeditModuleArtifact(), 0, 0, 1, 69, 852, 975);
  }

  @Test
  public void testBinFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinFlatModel().getJeditModuleArtifact(), 0, 0, 1, 69, 1302, 975);
  }
}
