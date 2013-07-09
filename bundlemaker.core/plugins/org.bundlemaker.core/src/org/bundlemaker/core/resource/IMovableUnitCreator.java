package org.bundlemaker.core.resource;

import java.util.Map;
import java.util.Set;


public interface IMovableUnitCreator {

  Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries, Map<String, IModuleResource> sources);
}
