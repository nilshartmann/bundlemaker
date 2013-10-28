package org.bundlemaker.core.spi.modext;

import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;

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
  void movableUnitAdded(IModuleAwareMovableUnit movableUnit, IModule module);

  /**
   * <p>
   * </p>
   */
  void movableUnitRemoved(IModuleAwareMovableUnit movableUnit, IModule module);
}
