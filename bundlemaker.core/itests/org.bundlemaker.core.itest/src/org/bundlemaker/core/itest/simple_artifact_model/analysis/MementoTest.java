package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.util.gson.GsonHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MementoTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void artifactModelConfigurationMemento() throws Exception {

    //
    IAnalysisModelConfiguration configuration = getBinModel().getGroup1Artifact().getConfiguration();

    String memento = GsonHelper.gson(getModularizedSystem()).toJson(configuration);
    Assert.assertNotNull(memento);

    AnalysisModelConfiguration result = GsonHelper.gson(getModularizedSystem()).fromJson(memento,
        AnalysisModelConfiguration.class);
    Assert.assertNotNull(result);

    //
    Assert.assertEquals(configuration, result);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void bundleMakerArtifactMemento() throws Exception {

    //
    getBinModel().getRootArtifact().accept(new IAnalysisModelVisitor.Adapter() {

      @Override
      public boolean visit(IModuleArtifact moduleArtifact) {
        // skip non-resource artifacts
        if (moduleArtifact.isResourceModule()) {
          return onVisit(moduleArtifact);
        } else {
          return false;
        }
      }

      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {

        //
        String memento = GsonHelper.gson(getModularizedSystem()).toJson(artifact);
        Assert.assertNotNull(memento);
        //
        IBundleMakerArtifact restoredArtifact = GsonHelper.gson(getModularizedSystem()).fromJson(memento,
            IBundleMakerArtifact.class);
        Assert.assertNotNull(restoredArtifact);
        //
        Assert.assertSame(artifact, restoredArtifact);
        //
        return true;
      }
    });
  }
}
