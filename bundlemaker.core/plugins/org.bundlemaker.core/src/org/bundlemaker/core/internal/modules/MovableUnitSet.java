package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;

public class MovableUnitSet {

  /** the movable units */
  private Set<IMovableUnit>    _movableUnits;

  /** the binary resources */
  private Set<IModuleResource> _binaryResources;

  /** the source resources */
  private Set<IModuleResource> _sourceResources;

  /**
   * <p>
   * Creates a new instance of type {@link MovableUnitSet}.
   * </p>
   */
  public MovableUnitSet() {

    // create the resource sets
    _binaryResources = new HashSet<IModuleResource>();
    _sourceResources = new HashSet<IModuleResource>();

    //
    _movableUnits = new HashSet<IMovableUnit>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  public void addMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    //
    if (_movableUnits.add(movableUnit)) {

      // add binary resources
      for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
        _binaryResources.add(moduleResource);
      }

      // add source resources
      if (movableUnit.hasAssociatedSourceResource()) {
        _sourceResources.add(movableUnit.getAssociatedSourceResource());
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  public void removeMovableUnit(IMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    //
    if (_movableUnits.remove(movableUnit)) {

      // add binary resources
      for (IModuleResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
        _binaryResources.remove(moduleResource);
      }

      // add source resources
      if (movableUnit.hasAssociatedSourceResource()) {
        _sourceResources.remove(movableUnit.getAssociatedSourceResource());
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public IModuleResource getResource(String path, ResourceType contentType) {

    //
    for (IModuleResource resourceStandin : getModifiableResourcesSet(contentType)) {

      //
      if (resourceStandin.getPath().equalsIgnoreCase(path)) {
        return resourceStandin;
      }
    }

    // return null
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Set<IModuleResource> getResources(ResourceType contentType) {

    //
    return Collections.unmodifiableSet(getModifiableResourcesSet(contentType));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<? extends IMovableUnit> getMovableUnits() {
    return Collections.unmodifiableSet(_movableUnits);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentType
   * @return
   */
  private Set<IModuleResource> getModifiableResourcesSet(ResourceType contentType) {
    Assert.isNotNull(contentType);

    // return the resource set
    return ResourceType.BINARY.equals(contentType) ? _binaryResources : _sourceResources;
  }
}
