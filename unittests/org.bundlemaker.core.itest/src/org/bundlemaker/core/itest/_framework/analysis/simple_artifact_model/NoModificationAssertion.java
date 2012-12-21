package org.bundlemaker.core.itest._framework.analysis.simple_artifact_model;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class NoModificationAssertion {

  public static void assertNoModification(AbstractSimpleArtifactModelTest test, Runnable runnable) {

    //
    String expectedBinModel = ArtifactUtils.artifactToString(test._binModel.getRootArtifact());
    String expectedSrcModel = ArtifactUtils.artifactToString(test._srcModel.getRootArtifact());

    runnable.run();
    
    //
    Assert.assertEquals(expectedBinModel, ArtifactUtils.artifactToString(test._binModel.getRootArtifact()));
    Assert.assertEquals(expectedSrcModel, ArtifactUtils.artifactToString(test._srcModel.getRootArtifact()));
  }
}
