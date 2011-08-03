package org.bundlemaker.analysis.testkit;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.testkit.framework.AbstractTestKitTest;
import org.junit.Assert;
import org.junit.Test;

public class AdvancedAddAndRemoveCapabilities extends AbstractTestKitTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testDependencies() {

    // get the 'jedit' module
    IArtifact jeditModuleArtifact = getRoot().getChild("group1|group2|jedit_1.0.0");
    Assert.assertNotNull(jeditModuleArtifact);

    // get the 'velocity' module
    IArtifact velocityModuleArtifact = getRoot().getChild("velocity_1.5");
    Assert.assertNotNull(velocityModuleArtifact);

    // get the type artifact
    IArtifact typeArtifact = jeditModuleArtifact.getChild("org.gjt.sp.jedit|org.gjt.sp.jedit.Abbrevs");
    Assert.assertNotNull(typeArtifact);
    
    // remove the type from the module
    jeditModuleArtifact.removeArtifact(typeArtifact);
    velocityModuleArtifact.addArtifact(typeArtifact);
  }
}
