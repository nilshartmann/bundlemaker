package org.bundlemaker.core.itest.simple_artifact_model.analysis;

import org.bundlemaker.core.analysis.AnalysisModelException;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.AbstractSimpleArtifactModelTest;
import org.bundlemaker.core.itest._framework.analysis.simple_artifact_model.NoModificationAssertion;
import org.bundlemaker.core.transformation.IUndoableTransformation;
import org.eclipse.core.runtime.Path;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AddArtifactsToPackage_Test extends AbstractSimpleArtifactModelTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddPackageToModuleArtifacts_1() throws Exception {

    // STEP 1: create a new module
    IModuleArtifact newModuleArtifact = _binModel.getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
    assertResourceModuleCount(_binModel, 2);
    assertResourceModuleCount(_srcModel, 2);

    // we have to add a resource to force the creation of a new package artifact in the new module
    newModuleArtifact.addArtifact(_binModel.getKlasseResource());
    IPackageArtifact newPackageArtifact = _binModel.getKlasseResource().getParent(IPackageArtifact.class);

    // must fail!
    newPackageArtifact.addArtifact(_binModel.getTestPackage());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test(expected = AnalysisModelException.class)
  public void tryToAddPackageToModuleArtifacts_2() throws Exception {

    // STEP 1: create a new module
    IModuleArtifact newModuleArtifact = _binModel.getRootArtifact().getOrCreateModule("NewModule", "1.0.0");
    assertResourceModuleCount(_binModel, 2);
    assertResourceModuleCount(_srcModel, 2);

    // we have to add a resource to force the creation of a new package artifact in the new module
    newModuleArtifact.addArtifact(_binModel.getKlasseResource());
    IPackageArtifact newPackageArtifact = _binModel.getKlasseResource().getParent(IPackageArtifact.class);
    Assert.assertTrue(newPackageArtifact.getParent().isInstanceOf(IPackageArtifact.class));

    // must fail!
    newPackageArtifact.getParent().addArtifact(_binModel.getTestPackage());
  }
}
