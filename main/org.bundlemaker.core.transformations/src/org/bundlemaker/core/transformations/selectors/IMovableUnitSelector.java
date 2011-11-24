package org.bundlemaker.core.transformations.selectors;

import java.util.List;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

/**
 * <p>
 * Defines the common interface for movable unit selectors.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IMovableUnitSelector {

  /**
   * <p>
   * Returns a list of all {@link IMovableUnit IMovableUnits} of the specified {@link IResourceModule} that matches this
   * selector.
   * </p>
   * 
   * @param resourceModule
   *          the resource module
   * @return a list of all {@link IMovableUnit IMovableUnits} of the specified {@link IResourceModule} that matches this
   *         selector.
   */
  public List<IMovableUnit> getMatchingMovableUnits(IResourceModule resourceModule);

  /**
   * <p>
   * Returns <code>true</code> if the given {@link IMovableUnit} matches this selector, <code>false</code> otherwise.
   * </p>
   * 
   * @param movableUnit
   *          the {@link IMovableUnit} to test
   * @return <code>true</code> if the given {@link IMovableUnit} matches this selector, <code>false</code> otherwise.
   */
  public boolean matches(IMovableUnit movableUnit);
}
