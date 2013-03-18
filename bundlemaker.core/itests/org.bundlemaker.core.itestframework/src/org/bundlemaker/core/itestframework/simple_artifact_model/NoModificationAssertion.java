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

  public static void assertNoModification(AbstractSimpleArtifactModelTest test, Runnable runnable,
      SimpleArtifactModel binModel, SimpleArtifactModel srcModel) {

    //
    String expectedBinModel = AnalysisModelQueries.artifactToString(binModel.getRootArtifact());
    String expectedSrcModel = AnalysisModelQueries.artifactToString(srcModel.getRootArtifact());

    //
    runnable.run();

    //
    Assert.assertEquals(expectedBinModel, AnalysisModelQueries.artifactToString(binModel.getRootArtifact()));
    Assert.assertEquals(expectedSrcModel, AnalysisModelQueries.artifactToString(srcModel.getRootArtifact()));
  }
}
