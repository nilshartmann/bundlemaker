package org.bundlemaker.core.itest.modules;

import org.bundlemaker.core.itest.AbstractModularizedSystemTest;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemovedTypesTest extends AbstractModularizedSystemTest {

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void testRemovedTypes() throws Exception {

    //
    getModularizedSystem().applyTransformations(null, new ITransformation() {

      @Override
      public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

        //
        IModifiableResourceModule resourceModule = modularizedSystem.getModifiableResourceModule(new ModuleIdentifier(
            "SimpleArtifactModelTest", "1.0.0"));

        //
        for (IType type : resourceModule.getContainedTypes()) {
          resourceModule.getModifiableSelfResourceContainer().removeMovableUnit(
              MovableUnit.createFromType(type, modularizedSystem));
        }
      }
    });

    //
    Assert.assertNull(getModularizedSystem().getResourceModule("SimpleArtifactModelTest", "1.0.0"));
    
    //
    Assert.assertNull(getModularizedSystem().getType("de.test.Klasse"));
    Assert.assertNull(getModularizedSystem().getType("de.test.Test"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String computeTestProjectName() {
    return "SimpleArtifactModelTest";
  }
}
