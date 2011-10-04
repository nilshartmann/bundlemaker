package org.bundlemaker.core.itest.complex.analysis;

import java.io.IOException;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerArtifactTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testRemoveFromParent() {

    //
    IModuleArtifact moduleArtifact = (IModuleArtifact) getRootArtifact().getChild("group1|group2|jedit_1.0.0");
    moduleArtifact.removeFromParent();
    Assert.assertNull(moduleArtifact.getParent());
    Assert.assertSame(getRootArtifact(), moduleArtifact.getRoot());

    //
    IGroupArtifact groupArtifact = (IGroupArtifact) getRootArtifact().getChild("group1");
    groupArtifact.removeFromParent();
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

    IGroupArtifact groupArtifact = (IGroupArtifact) getRootArtifact().getChild("group1");

    // TODO: method "removeFromParent()"
    groupArtifact.removeFromParent();
    Assert.assertNull(groupArtifact.getParent());
    Assert.assertSame(getRootArtifact(), groupArtifact.getRoot());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
