package org.bundlemaker.core.itest.analysis.jedit_artifact_model;

import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.AbstractJeditAnalysisModelTest;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TransitiveClosureTest extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testBinHierarchicalTransitiveClosures() {

    //
    IResourceArtifact resourceArtifact = ArtifactVisitorUtils.findResourceArtifactByPathName(getBinHierarchicalModel()
        .getRootArtifact(), "org/gjt/sp/jedit/search/AllBufferSet.class");
    Assert.assertNotNull(resourceArtifact);

    //
    computeAndAssertTransitiveClosure(resourceArtifact, 5, 901);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testSrcHierarchicalTransitiveClosures() {

    //
    IResourceArtifact resourceArtifact = ArtifactVisitorUtils.findResourceArtifactByPathName(getSrcHierarchicalModel().getRootArtifact(),
        "org/gjt/sp/jedit/search/AllBufferSet.java");
    Assert.assertNotNull(resourceArtifact);

    //
    computeAndAssertTransitiveClosure(resourceArtifact, 5, 901);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testBinFlatTransitiveClosures() {

    //
    IResourceArtifact resourceArtifact = ArtifactVisitorUtils.findResourceArtifactByPathName(getBinFlatModel().getRootArtifact(),
        "org/gjt/sp/jedit/search/AllBufferSet.class");
    Assert.assertNotNull(resourceArtifact);

    //
    computeAndAssertTransitiveClosure(resourceArtifact, 5, 901);
  }

  /**
   * <p>
   * </p>
   */
  @Test
  public void testSrcFlatTransitiveClosures() {

    //
    IResourceArtifact resourceArtifact = ArtifactVisitorUtils.findResourceArtifactByPathName(getSrcFlatModel().getRootArtifact(),
        "org/gjt/sp/jedit/search/AllBufferSet.java");
    Assert.assertNotNull(resourceArtifact);

    //
    computeAndAssertTransitiveClosure(resourceArtifact, 5, 901);
  }

  /**
   * <p>
   * </p>
   * 
   * @param rootArtifact
   */
  private void computeAndAssertTransitiveClosure(IBundleMakerArtifact artifact, int directlyReferencedCount,
      int indirectlyReferencedCount) {

    //
    Set<IBundleMakerArtifact> indirectlyReferencedArtifacts = ArtifactUtils
        .getIndirectlyReferencedArtifacts(artifact);

    Set<IBundleMakerArtifact> directlyReferencedArtifacts = ArtifactUtils
        .getDirectlyReferencedArtifacts(artifact);

    //
    Assert.assertEquals(directlyReferencedCount, directlyReferencedArtifacts.size());
    Assert.assertEquals(indirectlyReferencedCount, indirectlyReferencedArtifacts.size());
  }
}
