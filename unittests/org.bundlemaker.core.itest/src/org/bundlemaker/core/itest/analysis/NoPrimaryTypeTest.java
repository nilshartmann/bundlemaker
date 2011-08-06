package org.bundlemaker.core.itest.analysis;

import java.io.IOException;

import org.bundlemaker.analysis.model.DependencyKind;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.testutils.ArtifactTestUtil;
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
    IAdvancedArtifact rootArtifact = (IAdvancedArtifact) ModelTransformer.getDependencyModel(getBundleMakerProject(),
        getModularizedSystem(), ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION).getRoot();
    Assert.assertNotNull(rootArtifact);

    //
    IArtifact moduleArtifact = rootArtifact.getChild("group1|group2|NoPrimaryTypeTest_1.0.0");
    Assert.assertNotNull(moduleArtifact);

    //
    IArtifact clientTypeArtifact = moduleArtifact
        .getChild("org.bundlemaker.noprimarytype|org.bundlemaker.noprimarytype.Client");
    Assert.assertNotNull(clientTypeArtifact);

    //
    IArtifact noPrimaryTestInterfaceTypeArtifact = moduleArtifact
        .getChild("org.bundlemaker.noprimarytype|org.bundlemaker.noprimarytype.NoPrimaryTestInterface");
    Assert.assertNotNull(noPrimaryTestInterfaceTypeArtifact);

    Assert.assertEquals(1, moduleArtifact.getDependencies().size());

    IDependency dependency = clientTypeArtifact.getDependency(noPrimaryTestInterfaceTypeArtifact);
    Assert.assertNotNull(dependency);
    Assert.assertEquals(clientTypeArtifact, dependency.getFrom());
    Assert.assertEquals(noPrimaryTestInterfaceTypeArtifact, dependency.getTo());
    Assert.assertEquals(DependencyKind.USES, dependency.getDependencyKind());
  }
}
