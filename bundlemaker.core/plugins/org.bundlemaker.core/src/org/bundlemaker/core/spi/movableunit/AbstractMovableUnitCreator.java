package org.bundlemaker.core.spi.movableunit;

import java.util.List;

import org.bundlemaker.core.internal.resource.MovableUnit;
import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentResource;

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
  protected IMovableUnit createMovableUnit(IProjectContentResource sourceResource,
      List<IProjectContentResource> binaryResources) {

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
  protected IMovableUnit createMovableUnit(IProjectContentResource sourceResource,
      IProjectContentResource binaryResource) {

    //
    return new MovableUnit(sourceResource, binaryResource);
  }
}
