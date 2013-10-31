package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.spi.movableunit.IMovableUnitCreator;

public class DispatchingMovableUnitCreator implements IMovableUnitCreator {

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMovableUnit> assignMovableUnits(Map<String, IProjectContentResource> binaries,
      Map<String, IProjectContentResource> sources) {

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
        for (IProjectContentResource moduleResource : unit.getAssociatedBinaryResources()) {
          binaries.remove(moduleResource.getPath());
        }

        // remove sources
        sources.remove(unit.getAssociatedSourceResource().getPath());
      }
    }

    //
    for (IProjectContentResource moduleResource : sources.values()) {
      result.add(new MovableUnit(moduleResource, (IProjectContentResource) null));
    }

    //
    for (IProjectContentResource moduleResource : binaries.values()) {
      result.add(new MovableUnit((IModuleResource) null, moduleResource));
    }

    //
    return result;
  }
}
