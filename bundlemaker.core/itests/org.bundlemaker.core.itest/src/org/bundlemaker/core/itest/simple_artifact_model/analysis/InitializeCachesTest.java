package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import junit.framework.Assert;

import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InitializeCachesTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void initializeCaches() throws Exception {

    //
    Assert.assertFalse(getBinModel().getRootArtifact().areCachesInitialized());
    getBinModel().getRootArtifact().initializeCaches(null);
    Assert.assertTrue(getBinModel().getRootArtifact().areCachesInitialized());
    
    // 'move' model to group 1
    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    
    //
    Assert.assertFalse(getBinModel().getRootArtifact().areCachesInitialized());
    getBinModel().getRootArtifact().initializeCaches(null);
    Assert.assertTrue(getBinModel().getRootArtifact().areCachesInitialized());
  }
}
