package org.bundlemaker.core.itest.jedit_artifact_model.analysis;

import static org.junit.Assert.assertArrayEquals;
import junit.framework.Assert;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.algorithms.AdjacencyMatrix;
import org.bundlemaker.core.itest._framework.analysis.ArtifactVisitorUtils;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.AbstractJeditAnalysisModelTest;
import org.bundlemaker.core.itest._framework.analysis.jedit_artifact_model.JeditAnalysisModel;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemodularizeJEditTestModule extends AbstractJeditAnalysisModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void createNewModule_SrcHierarchicalModel() {

    //
    JeditAnalysisModel model = getSrcHierarchicalModel();
    assertModificationCount(0);

    // STEP: Create group 'newGroup'
    IGroupArtifact newGroupArtifact = model.getRootArtifact().getOrCreateGroup("newGroup");
    Assert.assertNotNull(newGroupArtifact);
    assertModificationCount(1);

    // STEP: Create module 'jedit-util'
    IModuleArtifact utilModuleArtifact = model.getRootArtifact().getOrCreateModule("jedit-util", "4.5.2");
    Assert.assertNotNull(utilModuleArtifact);
    assertModificationCount(2);

    // STEP: move package 'org.gjt.sp.util' to module 'jedit-util'
    IPackageArtifact utilPackageArtifact = AnalysisModelQueries.findPackageArtifact(model.getJeditModuleArtifact(),
        "org.gjt.sp.util");
    Assert.assertNotNull(utilPackageArtifact);
    utilModuleArtifact.addArtifact(utilPackageArtifact);
    assertArrayEquals(new int[][] { { 52, 4, 0 }, { 386, 4713, 0 }, { 0, 0, 0 } },
        AdjacencyMatrix.computeAdjacencyMatrix(null, utilModuleArtifact, model.getJeditModuleArtifact(),
            newGroupArtifact));
    assertModificationCount(3);

    // STEP: move module 'jedit' to group 'newGroup'
    newGroupArtifact.addArtifact(utilModuleArtifact);
    assertArrayEquals(new int[][] { { 52, 4 }, { 386, 4713 } },
        AdjacencyMatrix.computeAdjacencyMatrix(null, newGroupArtifact, model.getJeditModuleArtifact()));
    assertModificationCount(4);

    // STEP: create new module 'jedit-bsh'
    IModuleArtifact bshModuleArtifact = model.getRootArtifact().getOrCreateModule("jedit-bsh", "4.5.2");
    Assert.assertNotNull(bshModuleArtifact);
    assertModificationCount(5);

    // STEP: move package 'org.gjt.sp.jedit.bsh' to module 'jedit-bsh'
    IPackageArtifact bshPackageArtifact = AnalysisModelQueries.findPackageArtifact(model.getJeditModuleArtifact(),
        "org.gjt.sp.jedit.bsh");
    Assert.assertNotNull(bshPackageArtifact);
    bshModuleArtifact.addArtifact(bshPackageArtifact);
    assertModificationCount(6);
  }
}
