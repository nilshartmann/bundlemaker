package org.bundlemaker.core.spi.movableunit;

import java.util.List;

import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractMovableUnitCreator implements IMovableUnitCreator {

  /**
   * <p>
   * </p>
   * 
   * @param sourceResource
   * @param binaryResources
   * @return
   */
  protected IModuleAwareMovableUnit createMovableUnit(IModuleResource sourceResource,
      List<IModuleResource> binaryResources) {

    //
    return new MovableUnit(sourceResource, binaryResources);
  }

  /**
   * <p>
   * </p>
   * 
   * @param sourceResource
   * @param binaryResources
   * @return
   */
  protected IModuleAwareMovableUnit createMovableUnit(IModuleResource sourceResource,
      IModuleResource binaryResources) {

    //
    return new MovableUnit(sourceResource, binaryResources);
  }
}
