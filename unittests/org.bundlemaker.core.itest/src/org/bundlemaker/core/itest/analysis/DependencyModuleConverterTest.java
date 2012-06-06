package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class DependencyModuleConverterTest extends AbstractModularizedSystemTest {

  @Test
  public void testDependencies() throws CoreException {

    // transform the model
    IBundleMakerArtifact artifact = ((IBundleMakerArtifact) getModularizedSystem().getArtifactModel(
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION).getRoot())
        .getChild("group1|group2|DependencyModuleConverterTest_1.0.0|de.test.inner2|Inner2.class|Inner2");

    Assert.isNotNull(artifact);
  }
}
