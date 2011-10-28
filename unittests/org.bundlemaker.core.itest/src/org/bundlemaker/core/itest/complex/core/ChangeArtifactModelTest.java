package org.bundlemaker.core.itest.complex.core;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.junit.Test;

/**
 * <p>
 * Tests if events are sent if a modularized system changes.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ChangeArtifactModelTest extends AbstractModularizedSystemChangedTest {

  /**
   * <p>
   * </p>
   * 
   * @throws AmbiguousElementException
   */
  @Test
  public void testClassificationChanged() throws AmbiguousElementException {

    // test that "org.gjt.sp.jedit.browser.VFSFileChooserDialog$WorkThreadHandler.class" is contained
    IBundleMakerArtifact artifact = _rootArtifact.getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(artifact);

    //
    artifact.getParent().removeArtifact(artifact);

    //
    Assert.assertFalse("Artifact must not have a parent.", artifact.hasParent());
  }
}
