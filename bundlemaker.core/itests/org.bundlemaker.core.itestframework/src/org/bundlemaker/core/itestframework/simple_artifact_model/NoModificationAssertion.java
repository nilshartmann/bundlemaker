package org.bundlemaker.core.itestframework.simple_artifact_model;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class NoModificationAssertion {

  public static void assertNoModification(AbstractSimpleArtifactModelTest test, Action action) {

    //
    String expectedBinModel = AnalysisModelQueries.artifactToString(test.getBinModel().getRootArtifact());
    String expectedSrcModel = AnalysisModelQueries.artifactToString(test.getSrcModel().getRootArtifact());

    //
    action.prePostCondition();

    //
    int initialCount = test.getModularizedSystem().getTransformations().size();

    //
    action.execute();

    // Undo...
    for (int i = test.getModularizedSystem().getTransformations().size() - 1; i >= initialCount; i--) {
      test.getModularizedSystem().undoLastTransformation();
    }

    //
    action.prePostCondition();

    //
    Assert.assertEquals(expectedBinModel, AnalysisModelQueries.artifactToString(test.getBinModel().getRootArtifact()));
    Assert.assertEquals(expectedSrcModel, AnalysisModelQueries.artifactToString(test.getSrcModel().getRootArtifact()));
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static interface Action {

    /**
     * <p>
     * </p>
     */
    public void prePostCondition();

    /**
     * <p>
     * </p>
     * 
     */
    public void execute();
  }
}
