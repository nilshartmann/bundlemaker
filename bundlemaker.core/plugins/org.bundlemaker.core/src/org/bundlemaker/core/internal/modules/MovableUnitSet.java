package org.bundlemaker.core.internal.modules;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.eclipse.core.runtime.Assert;

public class MovableUnitSet {

  /** the movable units */
  private Set<IModuleAwareMovableUnit> _movableUnits;

  /** the binary resources */
  private Set<IProjectContentResource> _binaryResources;

  /** the source resources */
  private Set<IProjectContentResource> _sourceResources;

  /**
   * <p>
   * Creates a new instance of type {@link MovableUnitSet}.
   * </p>
   */
  public MovableUnitSet() {

    // create the resource sets
    _binaryResources = new HashSet<IProjectContentResource>();
    _sourceResources = new HashSet<IProjectContentResource>();

    //
    _movableUnits = new HashSet<IModuleAwareMovableUnit>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param movableUnit
   */
  public void addMovableUnit(IModuleAwareMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    //
    if (_movableUnits.add(movableUnit)) {

      // add binary resources
      for (IProjectContentResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
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
  public void removeMovableUnit(IModuleAwareMovableUnit movableUnit) {
    Assert.isNotNull(movableUnit);

    //
    if (_movableUnits.remove(movableUnit)) {

      // add binary resources
      for (IProjectContentResource moduleResource : movableUnit.getAssociatedBinaryResources()) {
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
  public IProjectContentResource getResource(String path, ResourceType contentType) {

    //
    for (IProjectContentResource resourceStandin : getModifiableResourcesSet(contentType)) {

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
  public Set<IProjectContentResource> getResources(ResourceType contentType) {

    //
    return Collections.unmodifiableSet(getModifiableResourcesSet(contentType));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<? extends IModuleAwareMovableUnit> getMovableUnits() {
    return Collections.unmodifiableSet(_movableUnits);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentType
   * @return
   */
  private Set<IProjectContentResource> getModifiableResourcesSet(ResourceType contentType) {
    Assert.isNotNull(contentType);

    // return the resource set
    return ResourceType.BINARY.equals(contentType) ? _binaryResources : _sourceResources;
  }
}
