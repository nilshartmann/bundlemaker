package org.bundlemaker.core.itest.complex.analysis;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactHelper;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TransitiveClosureTest extends AbstractJeditArtifactTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testTransitiveClosure() {

    //
    IResourceArtifact resourceArtifact = ArtifactHelper.findChild(getJeditModuleArtifact(), "AllBufferSet.class",
        IResourceArtifact.class);

    //
    Set<IBundleMakerArtifact> indirectlyReferencedArtifacts = ArtifactUtils.getIndirectlyReferencedArtifacts(resourceArtifact);
    Set<IBundleMakerArtifact> directlyReferencedArtifacts = ArtifactUtils.getDirectlyReferencedArtifacts(resourceArtifact);

    //
    Assert.assertEquals(5, directlyReferencedArtifacts.size());
    Assert.assertEquals(879, indirectlyReferencedArtifacts.size());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }
}
