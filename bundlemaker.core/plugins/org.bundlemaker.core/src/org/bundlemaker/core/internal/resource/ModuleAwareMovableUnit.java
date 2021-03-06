package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.api.resource.IResourceStandin;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.bundlemaker.core.resource.IModule;
import org.bundlemaker.core.resource.IModuleAwareMovableUnit;
import org.bundlemaker.core.resource.IModuleResource;
import org.eclipse.core.runtime.Assert;

public class ModuleAwareMovableUnit implements IModuleAwareMovableUnit {

  /** EMPTY_RESOURCE_LIST */
  private static final List<IResourceStandin> EMPTY_RESOURCE_LIST = Collections.emptyList();

  /** the source resource */
  private IProjectContentResource             _sourceResource;

  /** the binary resource */
  private List<IProjectContentResource>       _binaryResources;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleAwareMovableUnit}.
   * </p>
   * 
   * @param sourceResource
   * @param binaryResources
   */
  public ModuleAwareMovableUnit(IProjectContentResource sourceResource,
      List<IProjectContentResource> binaryResources) {

    Assert.isTrue(sourceResource != null || (binaryResources != null && !binaryResources.isEmpty()));

    _sourceResource = sourceResource;
    _binaryResources = binaryResources;

    setMovableUnit();
  }

  public ModuleAwareMovableUnit(IProjectContentResource sourceResource,
      IProjectContentResource binaryResources) {

    Assert.isTrue(sourceResource != null || binaryResources != null);

    _sourceResource = sourceResource;

    if (binaryResources != null) {
      _binaryResources = new LinkedList<IProjectContentResource>();
      _binaryResources.add(binaryResources);
    }

    setMovableUnit();
  }

  /**
   * {@inheritDoc}
   */
  public IModule getAssoicatedModule(IModularizedSystem modularizedSystem) {

    Assert.isNotNull(modularizedSystem);

    //
    if (_binaryResources != null && !_binaryResources.isEmpty()) {

      //
      return _binaryResources.get(0).adaptAs(IModuleResource.class)
          .getModule(modularizedSystem);

    }

    //
    else {
      return _sourceResource.adaptAs(IModuleResource.class).getModule(modularizedSystem);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasModule(IModularizedSystem modularizedSystem) {

    //
    Assert.isNotNull(modularizedSystem);

    //
    return getAssoicatedModule(modularizedSystem) != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedBinaryResources() {

    //
    return _binaryResources != null && !_binaryResources.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<? extends IProjectContentResource> getAssociatedBinaryResources() {

    //
    return _binaryResources != null ? _binaryResources : EMPTY_RESOURCE_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedSourceResource() {

    //
    return _sourceResource != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectContentResource getAssociatedSourceResource() {

    //
    return _sourceResource;
  }

  public void addBinaryResource(IModuleResource moduleResource) {
    Assert.isNotNull(moduleResource);

    //
    if (!_binaryResources.contains(moduleResource)) {
      _binaryResources.add(moduleResource);
      setMovableUnit(moduleResource, this);
    }
  }

  public void removeBinaryResource(IModuleResource moduleResource) {
    Assert.isNotNull(moduleResource);

    //
    if (_binaryResources.contains(moduleResource)) {
      _binaryResources.remove(moduleResource);
      setMovableUnit(moduleResource, null);
    }
  }

  public void addSourceResource(IModuleResource moduleResource) {
    Assert.isNotNull(moduleResource);

    // TODO
    if (_sourceResource != null) {
      throw new RuntimeException();
    }

    _sourceResource = moduleResource;
    setMovableUnit(_sourceResource, this);
  }

  public void removeSourceResource(IModuleResource moduleResource) {
    Assert.isNotNull(moduleResource);

    // TODO
    if (_sourceResource != moduleResource) {
      throw new RuntimeException();
    }

    setMovableUnit(_sourceResource, null);
    _sourceResource = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_binaryResources == null) ? 0 : _binaryResources.hashCode());
    result = prime * result + ((_sourceResource == null) ? 0 : _sourceResource.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ModuleAwareMovableUnit other = (ModuleAwareMovableUnit) obj;
    if (_binaryResources == null) {
      if (other._binaryResources != null) {
        return false;
      }
    } else if (!_binaryResources.equals(other._binaryResources)) {
      return false;
    }
    if (_sourceResource == null) {
      if (other._sourceResource != null) {
        return false;
      }
    } else if (!_sourceResource.equals(other._sourceResource)) {
      return false;
    }
    return true;
  }

  private void setMovableUnit() {
    setMovableUnit(_sourceResource, this);
    if (_binaryResources != null) {
      for (IProjectContentResource resource : _binaryResources) {
        setMovableUnit(resource, this);
      }
    }
  }

  private void setMovableUnit(IProjectContentResource resource, ModuleAwareMovableUnit movableUnit) {

    if (resource != null) {
      if (resource instanceof Resource) {
        ((Resource) resource).setMovableUnit(movableUnit);
      }
      else if (resource instanceof ResourceStandin) {
        ((Resource) ((ResourceStandin) resource).getResource()).setMovableUnit(movableUnit);
      }
    }
  }
}
