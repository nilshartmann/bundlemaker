package org.bundlemaker.core.transformations.selectors;

import java.util.List;

import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IMovableUnitSelector {

  /**
   * <p>
   * </p>
   * 
   * @param resourceModule
   * @return
   */
  public List<IMovableUnit> getMatchingMovableUnits(IResourceModule resourceModule);

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   * @return
   */
  public boolean matches(IMovableUnit movableUnit);
}
