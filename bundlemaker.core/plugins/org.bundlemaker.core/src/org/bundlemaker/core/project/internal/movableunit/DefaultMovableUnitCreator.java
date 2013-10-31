package org.bundlemaker.core.project.internal.movableunit;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.internal.resource.ModuleAwareMovableUnit;
import org.bundlemaker.core.project.IMovableUnit;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.spi.IMovableUnitCreator;

public class DefaultMovableUnitCreator implements IMovableUnitCreator {

  @Override
  public Set<IMovableUnit> assignMovableUnits(Map<String, IProjectContentResource> binaries,
      Map<String, IProjectContentResource> sources) {

    //
    Set<String> intersection = new HashSet<String>(binaries.keySet());
    intersection.retainAll(sources.keySet());

    //
    Set<IMovableUnit> result = new HashSet<IMovableUnit>();
    for (String key : intersection) {
      result.add(new ModuleAwareMovableUnit(sources.get(key), binaries.get(key)));
    }

    //
    return result;
  }
}
