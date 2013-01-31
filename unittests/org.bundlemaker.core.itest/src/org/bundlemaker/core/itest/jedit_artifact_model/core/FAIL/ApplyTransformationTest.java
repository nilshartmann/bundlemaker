package org.bundlemaker.core.itest.jedit_artifact_model.core.FAIL;

import java.util.Collection;

import junit.framework.Assert;

import org.bundlemaker.core.itest.jedit_artifact_model.core.AbstractJeditTest;
import org.bundlemaker.core.itestframework.ModuleUtils;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.modules.modifiable.MovableUnit;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ApplyTransformationTest extends AbstractJeditTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testModelTransformer() {

    //
    IResourceModule jeditmodule = getModularizedSystem().getResourceModule("jedit", "1.0.0");

    // the resource module
    IResourceModule resourceModule = getModularizedSystem().getResourceModule(new ModuleIdentifier("test", "1.0.0"));
    Assert.assertNull(resourceModule);

    // execute test transformation
    getModularizedSystem().applyTransformations(null, new TestTransformation());

    // the resource module
    resourceModule = getModularizedSystem().getResourceModule(new ModuleIdentifier("test", "1.0.0"));
    Assert.assertNotNull(resourceModule);

    //
    jeditmodule = getModularizedSystem().getResourceModule("jedit", "1.0.0");
    Assert.assertNull(jeditmodule);

    //
    Assert.assertEquals(1214, resourceModule.getContainedTypes().size());
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  class TestTransformation implements ITransformation {

    /**
     * {@inheritDoc}
     */
    public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {

      // get the modifiable resource modules
      Collection<IModifiableResourceModule> resourceModules = modularizedSystem.getModifiableResourceModules();

      // create the new resource module
      IModifiableResourceModule newResourceModule = modularizedSystem.createResourceModule(new ModuleIdentifier("test",
          "1.0.0"));

      // iterate over all the resource modules
      for (IModifiableResourceModule oldResourceModule : resourceModules) {

        //
        for (IResource resource : oldResourceModule.getResources(ProjectContentType.SOURCE)) {

          // move the unit
          IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());
          oldResourceModule.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
          newResourceModule.getModifiableSelfResourceContainer().addMovableUnit(movableUnit);
        }

        //
        for (IResource resource : oldResourceModule.getResources(ProjectContentType.BINARY)) {

          // move the unit
          IMovableUnit movableUnit = MovableUnit.createFromResource(resource, getModularizedSystem());
          oldResourceModule.getModifiableSelfResourceContainer().removeMovableUnit(movableUnit);
          newResourceModule.getModifiableSelfResourceContainer().addMovableUnit(movableUnit);
        }
      }
    }
  }
}
