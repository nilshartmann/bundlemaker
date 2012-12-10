package org.bundlemaker.core.itest.analysis.jedit_artifact_model.FAIL;

import org.bundlemaker.core.itest.analysis.framework.ArtifactVisitorUtils;
import org.bundlemaker.core.itest.analysis.jedit_artifact_model.framework.AbstractJeditAnalysisModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DependenciesTest extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testBinHierarchicalModelDependencies() {
    ArtifactVisitorUtils.checkDependencies(getBinHierarchicalModel().getRootArtifact(), 8442, 8442);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testSrcHierarchicalModelDependencies() {
    ArtifactVisitorUtils.checkDependencies(getSrcHierarchicalModel().getRootArtifact(), 8442, 8442);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testBinFlatModelDependencies() {
    ArtifactVisitorUtils.checkDependencies(getBinFlatModel().getRootArtifact(), 8442, 8442);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testSrcFlatModelDependencies() {
    ArtifactVisitorUtils.checkDependencies(getSrcFlatModel().getRootArtifact(), 8442, 8442);
  }
}
