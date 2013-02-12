package org.bundlemaker.core.itest.jedit_artifact_model.analysis;

import java.util.List;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.AbstractJeditAnalysisModelTest;
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

    IModuleArtifact moduleArtifact = AnalysisModelQueries.findChild(rootArtifact, "jedit_1.0.0", IModuleArtifact.class);
    List<ITypeArtifact> types = AnalysisModelQueries.findChildren(moduleArtifact, ITypeArtifact.class);

    IModuleArtifact newModule = null;
    for (ITypeArtifact typeArtifact : types) {
      newModule = rootArtifact.getOrCreateModule("DEV/FRAMEWORK/de.test", "1.23");
      newModule.addArtifact(typeArtifact);
    }
  }
}
