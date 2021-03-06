package org.bundlemaker.core.itest.jedit_artifact_model;

import org.bundlemaker.core.itestframework.jedit_model.AbstractJeditAnalysisModelTest;
import org.bundlemaker.core.itestframework.utils.ArtifactVisitorUtils;
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
    ArtifactVisitorUtils.countArtifacts(getSrcHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 86, 875, 998);
  }

  @Test
  public void testBinHierarchicalModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinHierarchicalModel().getJeditModuleArtifact(), 0, 0, 1, 86, 1353, 998);
  }

  @Test
  public void testSrcFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getSrcFlatModel().getJeditModuleArtifact(), 0, 0, 1, 71, 875, 998);
  }

  @Test
  public void testBinFlatModelAnalysisTreeVisitor() {
    ArtifactVisitorUtils.countArtifacts(getBinFlatModel().getJeditModuleArtifact(), 0, 0, 1, 71, 1353, 998);
  }
}
