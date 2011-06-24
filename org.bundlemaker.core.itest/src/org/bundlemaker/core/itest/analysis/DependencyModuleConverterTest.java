package org.bundlemaker.core.itest.analysis;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependencyModel;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.junit.Test;

public class DependencyModuleConverterTest extends AbstractModularizedSystemTest {

  @Test
  public void testDependencies() throws CoreException {

    // transform the model
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(getBundleMakerProject(),
        getModularizedSystem());
    // ArtifactUtils.dumpArtifact(dependencyModel.getRoot());

    IArtifact artifact = ((IAdvancedArtifact) dependencyModel.getRoot())
        .getChild("bla/blub/DependencyModuleConverterTest_1.0.0/de.test.inner2/Inner2");

    Assert.isNotNull(artifact);

    System.out.println(artifact.getDependencies());
  }
}
