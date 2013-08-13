package org.bundlemaker.core.spi.movableunit;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;

/**
 * <p>
 * </p>
 */
public interface IMovableUnitCreator {

  /**
   * @param binaries
   * @param sources
   * @return
   */
  Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries, Map<String, IModuleResource> sources);
}
