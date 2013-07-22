package org.bundlemaker.core.spi.modext;

import java.util.List;

import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;

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
  protected IMovableUnit createMovableUnit(IModuleResource sourceResource,
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
  protected IMovableUnit createMovableUnit(IModuleResource sourceResource,
      IModuleResource binaryResources) {

    //
    return new MovableUnit(sourceResource, binaryResources);
  }
}
