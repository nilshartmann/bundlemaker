package org.bundlemaker.core.itest.analysis.misc_models.FAIL;

import java.util.Collection;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactUtils;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.itest._framework.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.AmbiguousElementException;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.CoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class InnerClassTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   * @throws AmbiguousElementException
   */
  @Test
  public void testMoveOuterClass() throws CoreException, AmbiguousElementException {

    // transform the model
    IRootArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.BINARY_RESOURCES_CONFIGURATION);

    //
    IBundleMakerArtifact classResource = rootArtifact
        .getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes|A.class");
    Assert.assertNotNull(classResource);
    IBundleMakerArtifact packageArtifact = rootArtifact
        .getChild("group1|group2|InnerClassTest_1.0.0|de.test.innertypes");
    Assert.assertNotNull(packageArtifact);

    // TODO: is this the intended behavior?
    Assert.assertEquals(4, packageArtifact.getChildren().size());

    //
    packageArtifact.removeArtifact(classResource);
    Assert.assertEquals(0, packageArtifact.getChildren().size());

    //
    packageArtifact.addArtifact(classResource);
    Assert.assertEquals(4, packageArtifact.getChildren().size());
  }
}
