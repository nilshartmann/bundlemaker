package org.bundlemaker.core.itest.complex.analysis;

import java.io.IOException;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
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
    Assert.assertSame(getRootArtifact(), getRootArtifact().getChild("group1|group2|jedit_1.0.0|org").getRoot());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
