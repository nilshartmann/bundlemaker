package org.bundlemaker.core.itest.analysis.simple_artifact_model;

import junit.framework.Assert;

import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModule;
import org.eclipse.core.runtime.Path;
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
    _binModel.getGroup1Artifact().addArtifact(_binModel.getMainModuleArtifact());

    Assert.assertEquals(1, _binModel.getModifiedNotificationCount());
    Assert.assertEquals(1, _srcModel.getModifiedNotificationCount());

    // 'move' model to group 1
    _binModel.getRootArtifact().addArtifact(_binModel.getMainModuleArtifact());

    Assert.assertEquals(2, _binModel.getModifiedNotificationCount());
    Assert.assertEquals(2, _srcModel.getModifiedNotificationCount());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void changeResourceModuleClassification() throws Exception {

    // change classification
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    Assert.assertEquals(1, _binModel.getModifiedNotificationCount());
    Assert.assertEquals(1, _srcModel.getModifiedNotificationCount());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void disableModuleChangedNotifcation() throws Exception {

    _binModel.getRootArtifact().disableModelModifiedNotification(true);

    // 'move' model to group 1
    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    ((IModifiableModule) _binModel.getMainModuleArtifact().getAssociatedModule())
        .setClassification(new Path("group1"));

    Assert.assertEquals(0, _binModel.getModifiedNotificationCount());
    Assert.assertEquals(0, _srcModel.getModifiedNotificationCount());

    _binModel.getRootArtifact().disableModelModifiedNotification(false);

    Assert.assertEquals(1, _binModel.getModifiedNotificationCount());
    Assert.assertEquals(1, _srcModel.getModifiedNotificationCount());
  }
}
