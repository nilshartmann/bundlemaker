package org.bundlemaker.core.itest.complex.analysis;

import java.io.IOException;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.itest.analysis.jedit_artifact_model.framework.AbstractJeditAnalysisModelTest;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerArtifactTest extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testRemoveFromParent() {

    //
    IModuleArtifact moduleArtifact = (IModuleArtifact) getRootArtifact().getChild("group1|group2|jedit_1.0.0");
    moduleArtifact.getParent().removeArtifact(moduleArtifact);
    Assert.assertNull(moduleArtifact.getParent());
    Assert.assertSame(getRootArtifact(), moduleArtifact.getRoot());

    //
    IGroupArtifact groupArtifact = (IGroupArtifact) getRootArtifact().getChild("group1|group2");
    groupArtifact.getParent().removeArtifact(groupArtifact);
    Assert.assertNull(groupArtifact.getParent());
    Assert.assertSame(getRootArtifact(), groupArtifact.getRoot());

    //
    groupArtifact = (IGroupArtifact) getRootArtifact().getChild("group1");
    groupArtifact.getParent().removeArtifact(groupArtifact);
    Assert.assertNull(groupArtifact.getParent());
    Assert.assertSame(getRootArtifact(), groupArtifact.getRoot());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testGetRoot() {

    //
    Assert.assertSame(getRootArtifact(), getRootArtifact().getRoot());
    Assert.assertSame(getRootArtifact(), getRootArtifact().getChild("group1").getRoot());
    Assert.assertSame(getRootArtifact(), getRootArtifact().getChild("group1|group2").getRoot());
    Assert.assertSame(getRootArtifact(), getRootArtifact().getChild("group1|group2|jedit_1.0.0").getRoot());

    IBundleMakerArtifact artifact = getRootArtifact().getChild("group1|group2|jedit_1.0.0");
    artifact.getParent().removeArtifact(artifact);
    Assert.assertNull(artifact.getParent());
    Assert.assertSame(getRootArtifact(), artifact.getRoot());

    artifact = getRootArtifact().getChild("group1|group2");
    artifact.getParent().removeArtifact(artifact);
    Assert.assertNull(artifact.getParent());
    Assert.assertSame(getRootArtifact(), artifact.getRoot());

    artifact = getRootArtifact().getChild("group1");
    artifact.getParent().removeArtifact(artifact);
    Assert.assertNull(artifact.getParent());
    Assert.assertSame(getRootArtifact(), artifact.getRoot());
  }
}
