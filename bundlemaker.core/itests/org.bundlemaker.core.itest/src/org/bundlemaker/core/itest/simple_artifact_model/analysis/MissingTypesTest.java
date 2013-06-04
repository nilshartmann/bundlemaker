package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCount;
import static org.bundlemaker.core.itestframework.simple_artifact_model.ArtifactAssert.assertResourceModuleCountInModularizedSystem;

import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itestframework.simple_artifact_model.AbstractSimpleArtifactModelWithTypeLibraryTest;
import org.bundlemaker.core.itestframework.utils.ArtifactTestUtil;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MissingTypesTest extends AbstractSimpleArtifactModelWithTypeLibraryTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testMissingTypes() throws Exception {

    //
    assertResourceModuleCountInModularizedSystem(getModularizedSystem(), 3);
    assertResourceModuleCount(getBinModel(), 3);
    assertResourceModuleCount(getSrcModel(), 3);

    //
    IModuleArtifact missingTypes = AnalysisModelQueries.getMissingTypesModuleArtifact(getBinModel().getRootArtifact());
    Assert.assertNotNull(missingTypes);

    //
    getBinModel().getGroup1Artifact().addArtifact(missingTypes);
  }
}
