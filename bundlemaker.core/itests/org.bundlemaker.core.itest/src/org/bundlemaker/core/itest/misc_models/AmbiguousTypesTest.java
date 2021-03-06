package org.bundlemaker.core.itest.misc_models;

import java.util.LinkedList;

import org.bundlemaker.core.analysis.AnalysisCore;
import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.AnalysisModelQueries;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IResourceArtifact;
import org.bundlemaker.core.itestframework.AbstractBundleMakerModelTest;
import org.bundlemaker.core.jtype.ITypeModularizedSystem;
import org.bundlemaker.core.jtype.ITypeModule;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AmbiguousTypesTest extends AbstractBundleMakerModelTest {

  /**
   * <p>
   * </p>
   */
  @Test
  @Ignore
  public void testAmbiguousTypes() {

    // set the 'new' type selector
    AmbiguousTypesTest_TestTypeSelector selector = new AmbiguousTypesTest_TestTypeSelector(getBundleMakerProject().getProjectDescription());
    selector.setPreferJdkTypes(true);
    getModularizedSystem().adaptAs(ITypeModularizedSystem.class).getTypeSelectors().clear();
    getModularizedSystem().adaptAs(ITypeModularizedSystem.class).getTypeSelectors().add(selector);

    // get the root artifact
    IBundleMakerArtifact rootArtifact = AnalysisCore.getAnalysisModel(getModularizedSystem(),
        AnalysisModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(rootArtifact);

    // get the 'test' artifact
    IResourceArtifact artifact = AnalysisModelQueries.findResourceArtifactByQualifiedName(rootArtifact,
        "test/Test.java");
    Assert.assertNotNull(artifact);

    // assert that the type
    Assert.assertNotNull(getModularizedSystem().getExecutionEnvironment().adaptAs(ITypeModule.class).getType(
        "javax.xml.ws.handler.soap.SOAPMessageContext"));
    Assert.assertNotNull(getModularizedSystem().getModule("AmbiguousTypesTest", "1.0.0").adaptAs(ITypeModule.class).getType(
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
