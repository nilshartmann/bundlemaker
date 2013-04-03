package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.utils.ArtifactTestUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveEmptyPackagesFromArtifactModelTest extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void createNewModuleBelowExistingGroup() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 1);
    assertResourceModuleCount(getBinModel(), 1);
    assertResourceModuleCount(getSrcModel(), 1);

    // create a new group
    IModuleArtifact newModuleArtifact = getBinModel().getGroup2Artifact().getOrCreateModule("NewModule", "1.0.0");
    Assert.assertEquals("group1/group2/NewModule_1.0.0", newModuleArtifact.getQualifiedName());

    //
    newModuleArtifact.addArtifact(getBinModel().getTestPackage());

    //
    Assert.assertNull(AnalysisModelQueries.findPackageArtifactByQualifiedName(getBinModel().getMainModuleArtifact(),
        "de.test"));
    Assert.assertNotNull(AnalysisModelQueries.findPackageArtifactByQualifiedName(newModuleArtifact, "de.test"));

    //
    getBinModel().getMainModuleArtifact().addArtifact(
        AnalysisModelQueries.findPackageArtifactByQualifiedName(newModuleArtifact, "de.test"));

    //
    Assert.assertNotNull(AnalysisModelQueries.findPackageArtifactByQualifiedName(getBinModel().getMainModuleArtifact(),
        "de.test"));
    Assert.assertNull(AnalysisModelQueries.findPackageArtifactByQualifiedName(newModuleArtifact, "de.test"));
  }
}
