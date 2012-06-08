package org.bundlemaker.core.itest.analysis;

import java.io.IOException;

import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.ModelTransformerCache;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class NoPrimaryTypeTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws IOException
   */
  @Test
  public void testNoPrimaryType_aggregatedTypes() throws CoreException, IOException {

    // Step 1: transform the model
    IBundleMakerArtifact rootArtifact = (IBundleMakerArtifact) ModelTransformerCache.getArtifactModel(
        getModularizedSystem(), ArtifactModelConfiguration.SOURCE_RESOURCES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    //
    IBundleMakerArtifact moduleArtifact = rootArtifact.getChild("group1|group2|NoPrimaryTypeTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    //
    IBundleMakerArtifact clientTypeArtifact = moduleArtifact
        .getChild("org.bundlemaker.noprimarytype|Client.java|org.bundlemaker.noprimarytype.Client");
    Assert.assertNotNull(clientTypeArtifact);

    //
    IBundleMakerArtifact noPrimaryTestInterfaceTypeArtifact = moduleArtifact
        .getChild("org.bundlemaker.noprimarytype|TestInterface.java|org.bundlemaker.noprimarytype.NoPrimaryTestInterface");
    Assert.assertNotNull(noPrimaryTestInterfaceTypeArtifact);

    Assert.assertEquals(1, moduleArtifact.getDependencies().size());

    IDependency dependency = clientTypeArtifact.getDependency(noPrimaryTestInterfaceTypeArtifact);
    Assert.assertNotNull(dependency);
    Assert.assertEquals(clientTypeArtifact, dependency.getFrom());
    Assert.assertEquals(noPrimaryTestInterfaceTypeArtifact, dependency.getTo());
    Assert.assertEquals(DependencyKind.USES, dependency.getDependencyKind());
  }
}
