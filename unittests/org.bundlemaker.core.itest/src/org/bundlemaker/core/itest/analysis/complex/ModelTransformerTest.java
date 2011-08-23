package org.bundlemaker.core.itest.analysis.complex;

import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * Example: group1/group2/jedit_1.0.0 velocity_1.5 jdk16_jdk16 << Missing Types >>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModelTransformerTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testModelTransformer() {

    //
    IModifiableModularizedSystem modularizedSystem = getModularizedSystem();

    // model 1
    IDependencyModel dependencyModel = ModelTransformer.getDependencyModel(modularizedSystem, null);
    Assert.assertSame(dependencyModel, ModelTransformer.getDependencyModel(modularizedSystem, null));

    // model 2
    IDependencyModel dependencyModel2 = ModelTransformer.getDependencyModel(modularizedSystem,
        ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION);
    Assert.assertSame(dependencyModel2, ModelTransformer.getDependencyModel(modularizedSystem,
        ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION));
    Assert.assertNotSame(dependencyModel, dependencyModel2);

    // model 3
    IDependencyModel dependencyModel3 = ModelTransformer.getDependencyModel(modularizedSystem,
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION);
    Assert.assertSame(dependencyModel3, ModelTransformer.getDependencyModel(modularizedSystem,
        ArtifactModelConfiguration.BINARY_RESOURCES_CONFIGURATION));
    Assert.assertNotSame(dependencyModel2, dependencyModel3);
    Assert.assertNotSame(dependencyModel, dependencyModel3);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "jedit";
  }

}
