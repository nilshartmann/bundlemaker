package org.bundlemaker.core.itest.complex.transformation;

import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.itest.complex.core.AbstractJeditTest;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;
import org.bundlemaker.core.transformations.selectors.PatternBasedMovableUnitSelector;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnitSelectorTest extends AbstractJeditTest {

  /**
   * <p>
   * </p>
   */
  @Test
  public void testMovableUnitSelector() {

    // //
    // IResourceModule jeditmodule = getModularizedSystem().getResourceModule("jedit", "1.0.0");
    // Assert.assertEquals(852, jeditmodule.getMovableUnits().size());
    //
    // //
    // PatternBasedMovableUnitSelector movableUnitSelector = new PatternBasedMovableUnitSelector();
    // List<IMovableUnit> movableUnits = movableUnitSelector.getMatchingMovableUnits(jeditmodule);
    // Assert.assertEquals(0, movableUnits.size());
    //
    // //
    // movableUnitSelector.getIncludes().clear();
    // movableUnitSelector.getIncludes().add("**");
    // movableUnits = movableUnitSelector.getMatchingMovableUnits(jeditmodule);
    // Assert.assertEquals(852, movableUnits.size());
    //
    // //
    // movableUnitSelector.getIncludes().clear();
    // movableUnitSelector.getIncludes().add("org/gjt/**/*");
    // movableUnits = movableUnitSelector.getMatchingMovableUnits(jeditmodule);
    // Assert.assertEquals(852, movableUnits.size());
    //
    // //
    // movableUnitSelector.getIncludes().clear();
    // movableUnitSelector.getIncludes().add("org/gjt/sp/jedit/browser/*");
    // movableUnits = movableUnitSelector.getMatchingMovableUnits(jeditmodule);
    // Assert.assertEquals(13, movableUnits.size());
    //
    // //
    // movableUnitSelector.getIncludes().clear();
    // movableUnitSelector.getIncludes().add("org/gjt/sp/util/*");
    // movableUnits = movableUnitSelector.getMatchingMovableUnits(jeditmodule);
    // Assert.assertEquals(24, movableUnits.size());
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnits
   */
  private void dumpMovableUnit(List<IMovableUnit> movableUnits) {

    //
    for (IMovableUnit iMovableUnit : movableUnits) {
      System.out.println(iMovableUnit);
    }
  }
}
