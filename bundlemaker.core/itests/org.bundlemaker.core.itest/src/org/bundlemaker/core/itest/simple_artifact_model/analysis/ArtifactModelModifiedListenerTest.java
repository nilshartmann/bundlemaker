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
public class ArtifactModelModifiedListenerTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void addModuleArtifactToGroupArtifact() throws Exception {

    // 'move' model to group 1
    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());

    Assert.assertEquals(1, getBinModel().getModifiedNotificationCount());
    Assert.assertEquals(1, getSrcModel().getModifiedNotificationCount());

    // 'move' model to group 1
    getBinModel().getRootArtifact().addArtifact(getBinModel().getMainModuleArtifact());

    Assert.assertEquals(2, getBinModel().getModifiedNotificationCount());
    Assert.assertEquals(2, getSrcModel().getModifiedNotificationCount());
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @throws Exception
  // */
  // @Test
  // public void changeResourceModuleClassification() throws Exception {
  //
  // // change classification
  // ((IModifiableModule) getBinModel().getMainModuleArtifact().getAssociatedModule()).setClassification(new Path(
  // "group1"));
  //
  // Assert.assertEquals(1, getBinModel().getModifiedNotificationCount());
  // Assert.assertEquals(1, getSrcModel().getModifiedNotificationCount());
  // }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void disableModuleChangedNotifcation() throws Exception {

    getBinModel().getRootArtifact().disableModelModifiedNotification(true);

    getBinModel().getGroup1Artifact().addArtifact(getBinModel().getMainModuleArtifact());
    
    Assert.assertEquals(0, getBinModel().getModifiedNotificationCount());
    Assert.assertEquals(0, getSrcModel().getModifiedNotificationCount());

    getBinModel().getRootArtifact().disableModelModifiedNotification(false);

    Assert.assertEquals(1, getBinModel().getModifiedNotificationCount());
    Assert.assertEquals(1, getSrcModel().getModifiedNotificationCount());
  }
}
