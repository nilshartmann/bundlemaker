package org.bundlemaker.core.itest.simple_artifact_model.core;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelModifiedListener;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InitializeProjectMultipleTimesTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void initializeCaches() throws Exception {

    for (int i = 0; i < 50; i++) {

      //
      getBundleMakerProject().initialize(null);
      getBundleMakerProject().parseAndOpen(null);

      //
      getBundleMakerProject().getModularizedSystemWorkingCopy()
          .getAnalysisModel(AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION)
          .addAnalysisModelModifiedListener(new IAnalysisModelModifiedListener() {
            @Override
            public void analysisModelModified() {
              System.out.println("analysisModelModified");
            }
          });
    }
  }
}
