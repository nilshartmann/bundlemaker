package org.bundlemaker.core.spi.modext;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;


public interface IMovableUnitCreator {

  Set<IMovableUnit> assignMovableUnits(Map<String, IModuleResource> binaries, Map<String, IModuleResource> sources);
}
