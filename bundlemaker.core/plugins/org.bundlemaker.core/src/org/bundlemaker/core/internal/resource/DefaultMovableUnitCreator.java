package org.bundlemaker.core.internal.resource;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IMovableUnitCreator;

public class DefaultMovableUnitCreator implements IMovableUnitCreator {

  public Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries,
      Map<String, IModuleResource> sources) {

    //
    Set<String> intersection = new HashSet<String>(binaries.keySet());
    intersection.retainAll(sources.keySet());

    //
    Set<IMovableUnit> result = new HashSet<IMovableUnit>();
    for (String key : intersection) {
      result.add(new MovableUnit(sources.get(key), binaries.get(key)));
    }

    //
    return result;
  }

}
