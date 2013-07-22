package org.bundlemaker.core.spi.modext;

import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IMovableUnit;

public interface ICacheCallback {

  /**
   * <p>
   * </p>
   */
  void clearCaches();

  /**
   * <p>
   * </p>
   */
  void movableUnitAdded(IMovableUnit movableUnit, IModule module);

  /**
   * <p>
   * </p>
   */
  void movableUnitRemoved(IMovableUnit movableUnit, IModule module);
}
