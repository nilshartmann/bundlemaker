package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import junit.framework.Assert;

import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
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
    Assert.assertFalse(_binModel.getRootArtifact().areCachesInitialized());
    _binModel.getRootArtifact().initializeCaches(null);
    Assert.assertTrue(_binModel.getRootArtifact().areCachesInitialized());
    
    // 'move' model to group 1
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());
    
    //
    Assert.assertFalse(_binModel.getRootArtifact().areCachesInitialized());
    _binModel.getRootArtifact().initializeCaches(null);
    Assert.assertTrue(_binModel.getRootArtifact().areCachesInitialized());
  }
}
