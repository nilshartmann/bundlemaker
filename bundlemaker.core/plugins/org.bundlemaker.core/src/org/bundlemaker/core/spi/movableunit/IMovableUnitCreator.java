package org.bundlemaker.core.spi.movableunit;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentResource;

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
  Set<IMovableUnit> assignMovableUnits(Map<String, IProjectContentResource> binaries,
      Map<String, IProjectContentResource> sources);
}
