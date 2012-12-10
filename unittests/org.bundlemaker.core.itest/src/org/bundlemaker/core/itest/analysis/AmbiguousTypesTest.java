package org.bundlemaker.core.itest.analysis;

import java.util.LinkedList;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.itest.analysis.framework.ArtifactVisitorUtils;
import org.bundlemaker.core.itest.analysis.framework.TestTypeSelector;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AmbiguousTypesTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testAmbiguousTypes() {

    // set the 'new' type selector
    TestTypeSelector selector = new TestTypeSelector(getBundleMakerProject().getProjectDescription());
    selector.setPreferJdkTypes(true);
    getModularizedSystem().getTypeSelectors().clear();
    getModularizedSystem().getTypeSelectors().add(selector);

    // get the root artifact
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getAnalysisModel(
        AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(rootArtifact);

    // get the 'test' artifact
    IResourceArtifact artifact = ArtifactVisitorUtils.findResourceArtifactByPathName(rootArtifact, "test/Test.java");
    Assert.assertNotNull(artifact);

    // assert that the type
    Assert.assertNotNull(getModularizedSystem().getExecutionEnvironment().getType(
        "javax.xml.ws.handler.soap.SOAPMessageContext"));
    Assert.assertNotNull(getModularizedSystem().getModule("AmbiguousTypesTest", "1.0.0").getType(
        "javax.xml.ws.handler.soap.SOAPMessageContext"));

    //
    Assert.assertEquals(1, artifact.getDependenciesTo().size());
    IDependency dependency = new LinkedList<IDependency>(artifact.getDependenciesTo()).get(0);

    //
    String executionEnvironmentName = getModularizedSystem().getExecutionEnvironment().getModuleIdentifier().toString();
    String moduleArtifactName = dependency.getTo().getParent(IModuleArtifact.class).getName();
    Assert.assertEquals(executionEnvironmentName, moduleArtifactName);
  }
}
