package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.modext.IMovableUnitCreator;

public class DispatchingMovableUnitCreator implements IMovableUnitCreator {

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries,
      Map<String, IModuleResource> sources) {

    //
    Set<IMovableUnit> result = new HashSet<IMovableUnit>();

    //
    List<IMovableUnitCreator> creators = new LinkedList<IMovableUnitCreator>(MovableUnitRegistry.instance()
        .getCreators());
    creators.add(new DefaultMovableUnitCreator());

    //
    for (IMovableUnitCreator creator : creators) {

      //
      Set<IMovableUnit> movableUnits = creator.assignMovableUnits(binaries, sources);
      result.addAll(movableUnits);

      //
      for (IMovableUnit unit : movableUnits) {

        // remove binaries
        for (IModuleResource moduleResource : unit.getAssociatedBinaryResources()) {
          binaries.remove(moduleResource.getPath());
        }

        // remove sources
        sources.remove(unit.getAssociatedSourceResource().getPath());
      }
    }

    //
    for (IModuleResource moduleResource : sources.values()) {
      result.add(new MovableUnit(moduleResource, (IModuleResource) null));
    }

    //
    for (IModuleResource moduleResource : binaries.values()) {
      result.add(new MovableUnit((IModuleResource) null, moduleResource));
    }

    //
    return result;
  }
}
