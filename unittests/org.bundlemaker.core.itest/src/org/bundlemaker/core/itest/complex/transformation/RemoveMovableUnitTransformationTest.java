package org.bundlemaker.core.itest.complex.transformation;

import junit.framework.Assert;

import org.bundlemaker.core.itest.complex.core.AbstractJeditTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.transformations.RemoveMovableUnitTransformation;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveMovableUnitTransformationTest extends AbstractJeditTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testRemoveMovableUnitTransformation() {

    //
    IResourceModule jeditmodule = getModularizedSystem().getResourceModule("jedit", "1.0.0");
    Assert.assertEquals(975, jeditmodule.getContainedTypes().size());

    //
    RemoveMovableUnitTransformation removeMovableUnitTransformation = new RemoveMovableUnitTransformation();
    removeMovableUnitTransformation
        .addPatternBasedMovableUnitSelector(null, new String[] { "org/gjt/sp/util/*" }, null);

    // execute test transformation
    getModularizedSystem().applyTransformations(null, removeMovableUnitTransformation);

    //
    Assert.assertEquals(941, jeditmodule.getContainedTypes().size());
  }
}
