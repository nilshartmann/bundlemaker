package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.bundlemaker.core.spi.movableunit.IMovableUnitCreator;

public class DefaultMovableUnitCreator implements IMovableUnitCreator {

  public Set<IModuleAwareMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries,
      Map<String, IModuleResource> sources) {

    //
    Set<String> intersection = new HashSet<String>(binaries.keySet());
    intersection.retainAll(sources.keySet());

    //
    Set<IModuleAwareMovableUnit> result = new HashSet<IModuleAwareMovableUnit>();
    for (String key : intersection) {
      result.add(new MovableUnit(sources.get(key), binaries.get(key)));
    }

    //
    return result;
  }

}
