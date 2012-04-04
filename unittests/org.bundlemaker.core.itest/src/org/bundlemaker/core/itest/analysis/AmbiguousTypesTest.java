package org.bundlemaker.core.itest.analysis;

import java.util.LinkedList;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ArtifactType;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.junit.Assert;
import org.junit.Test;

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
    IBundleMakerArtifact rootArtifact = getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.HIERARCHICAL_SOURCE_RESOURCES_CONFIGURATION);
    Assert.assertNotNull(rootArtifact);

    // get the 'test' artifact
    IBundleMakerArtifact artifact = rootArtifact.getChild("group1|group2|AmbiguousTypesTest_1.0.0|test|Test.java");
    Assert.assertNotNull(artifact);

    // assert that the type
    Assert.assertNotNull(getModularizedSystem().getExecutionEnvironment().getType(
        "javax.xml.ws.handler.soap.SOAPMessageContext"));
    Assert.assertNotNull(getModularizedSystem().getModule("AmbiguousTypesTest", "1.0.0").getType(
        "javax.xml.ws.handler.soap.SOAPMessageContext"));

    //
    Assert.assertEquals(1, artifact.getDependencies().size());
    IDependency dependency = new LinkedList<IDependency>(artifact.getDependencies()).get(0);

    //
    Assert.assertEquals("jdk16_jdk16", dependency.getTo().getParent(ArtifactType.Module).getName());
    // Assert.assertEquals("AmbiguousTypesTest_1.0.0", dependency.getTo().getParent(ArtifactType.Module).getName());
  }
}
