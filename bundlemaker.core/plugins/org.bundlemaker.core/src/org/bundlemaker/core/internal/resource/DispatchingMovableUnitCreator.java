package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core._type.internal.JTypeMovableUnitCreator;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.spi.modext.IMovableUnitCreator;

public class DispatchingMovableUnitCreator implements IMovableUnitCreator {

  /** - */
  private List<IMovableUnitCreator> _creators;

  /**
   * <p>
   * Creates a new instance of type {@link DispatchingMovableUnitCreator}.
   * </p>
   */
  public DispatchingMovableUnitCreator() {

    //
    _creators = new LinkedList<IMovableUnitCreator>();

    // TODO: EXTENSION POINT!
    _creators.add(new JTypeMovableUnitCreator());
    _creators.add(new DefaultMovableUnitCreator());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries,
      Map<String, IModuleResource> sources) {

    //
    Set<IMovableUnit> result = new HashSet<IMovableUnit>();

    //
    for (IMovableUnitCreator creator : _creators) {

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
