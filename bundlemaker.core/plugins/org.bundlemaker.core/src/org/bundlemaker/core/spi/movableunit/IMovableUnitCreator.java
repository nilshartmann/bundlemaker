package org.bundlemaker.core.spi.movableunit;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;

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
  Set<IModuleAwareMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries, Map<String, IModuleResource> sources);
}
