package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IPackageArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.analysis.ITypeArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.transformation.ITransformation;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleTransformationHistoryTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testHistory() {

    // step: get the rootArtifact
    IRootArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    // step: get the package child
    IPackageArtifact packageArtifact = ArtifactVisitorUtils.findPackageArtifact(rootArtifact, "de.test.basic");
    Assert.assertNotNull(packageArtifact);

    // step:
    ITypeArtifact typeArtifact = ArtifactVisitorUtils.findTypeArtifact(packageArtifact, "de.test.basic.TestClass");
    Assert.assertNotNull(typeArtifact);

    // step: get the module artifact
    IModuleArtifact moduleArtifact = ArtifactVisitorUtils.findModuleArtifact(rootArtifact, "BasicArtifactTest");
    Assert.assertNotNull(moduleArtifact);

    // TODO:
    packageArtifact.removeArtifact(typeArtifact);
    ArtifactUtils.dumpArtifact(packageArtifact);

    // step: create new module
    IModuleArtifact newModuleArtifact = rootArtifact.getOrCreateModule("newModule", "1.2.3");
    Assert.assertNotNull(newModuleArtifact);

    // step: move
    moduleArtifact.removeArtifact(packageArtifact);
    newModuleArtifact.addArtifact(packageArtifact);

    //
    ArtifactUtils.dumpArtifact(moduleArtifact);
    ArtifactUtils.dumpArtifact(newModuleArtifact);

    //
    Assert.assertEquals(3, getModularizedSystem().getTransformations().size());
    for (ITransformation transformation : getModularizedSystem().getTransformations()) {
      System.out.println(transformation.getClass());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return BasicArtifactTest.class.getSimpleName();
  }
}
