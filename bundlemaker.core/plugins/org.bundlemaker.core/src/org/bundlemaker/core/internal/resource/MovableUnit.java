package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IMovableUnit;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.JavaTypeUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implementation of the {@link IMovableUnit}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnit implements IMovableUnit {

  /** EMPTY_RESOURCE_LIST */
  private static final List<IResourceStandin> EMPTY_RESOURCE_LIST = Collections.emptyList();

  /** EMPTY_TYPE_LIST */
  private static final List<IType>            EMPTY_TYPE_LIST     = Collections.emptyList();

  /** the main type */
  private IType                               _mainType;

  /** the main resource */
  private IResource                           _mainResource;

  /** the modularized system */
  private IModularizedSystem                  _modularizedSystem;

  /** the associated types */
  private List<IType>                         _associatedTypes;

  /** the source resource */
  private IResource                           _sourceResource;

  /** the binary resource */
  private List<IResourceStandin>              _binaryResources;

  /** - */
  private boolean                             _isInitialized      = false;

  // private IResourceModule _resourceModule;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public static IMovableUnit createFromType(IType type, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(type);

    MovableUnit movableUnit = new MovableUnit();
    movableUnit._mainType = type;
    movableUnit._modularizedSystem = modularizedSystem;

    return movableUnit;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  // TODO an der Resource anbieten
  public static MovableUnit createFromResource(IResource resource, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(resource);

    //
    if (resource.getName().endsWith(".class") && resource.containsTypes()) {

      try {
        IType type = resource.getContainedType();
        if (type.isLocalOrAnonymousType()) {
          String typeName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(type.getFullyQualifiedName());

          // TODO!!
          IModule resourceModule = resource.getAssociatedResourceModule(modularizedSystem);
          IResource nonAnonymousResource = resourceModule.getResource(typeName.replace('.', '/') + ".class",
              ProjectContentType.BINARY);

          if (nonAnonymousResource != null) {
            resource = nonAnonymousResource;
          }
        }
      } catch (CoreException e) {
        //
      }
    }

    //
    MovableUnit movableUnit = new MovableUnit();
    movableUnit._mainResource = resource;
    movableUnit._modularizedSystem = modularizedSystem;

    //
    return movableUnit;
  }

  @Override
  public boolean hasAssociatedTypes() {
    init();
    return _associatedTypes != null && !_associatedTypes.isEmpty();
  }

  @Override
  public boolean hasAssociatedBinaryResources() {
    init();
    return _binaryResources != null && !_binaryResources.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IResourceStandin> getAssociatedBinaryResources() {

    init();

    //
    return _binaryResources != null ? _binaryResources : EMPTY_RESOURCE_LIST;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasAssociatedSourceResource() {

    init();

    return _sourceResource != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IResource getAssociatedSourceResource() {

    init();

    //
    return _sourceResource;
  }

  @Override
  public List<IType> getAssociatedTypes() {

    init();

    //
    return _associatedTypes != null ? _associatedTypes : EMPTY_TYPE_LIST;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModule getModule() {

    init();

    IModule resourceModule = null;

    //
    if (!_associatedTypes.isEmpty()) {
      IModule module = _associatedTypes.get(0).getModule(_modularizedSystem);
      if (module instanceof IModule) {
        resourceModule = (IModule) module;
      }
    } else if (!_binaryResources.isEmpty()) {
      IModule module = _binaryResources.toArray(new IResourceStandin[0])[0]
          .getAssociatedResourceModule(_modularizedSystem);
      if (module instanceof IModule) {
        resourceModule = (IModule) module;
      }
    } else {
      resourceModule = _sourceResource.getAssociatedResourceModule(_modularizedSystem);
    }

    //
    return resourceModule;
  }

  public boolean hasModule() {
    //
    return getModule() != null;
  }

  /**
   * <p>
   * </p>
   */
  private void init() {

    // return if already is initiaized
    if (_isInitialized) {
      return;
    }

    // assert only one 'main element' is set
    Assert.isTrue((_mainType == null && _mainResource != null) || (_mainType != null && _mainResource == null));

    // create the binary result list
    _binaryResources = new LinkedList<IResourceStandin>();
    _associatedTypes = new LinkedList<IType>();

    // process type...
    if (_mainType != null) {
      handleType(_mainType);
      if (_sourceResource != null) {
        for (IType type : _sourceResource.getContainedTypes()) {
          handleType(type);
        }
      }
    }

    // ...or process main resource...
    else if (_mainResource != null) {
      if (_mainResource.containsTypes()) {
        for (IType type : _mainResource.getContainedTypes()) {
          handleType(type);
        }
        if (_sourceResource != null) {
          for (IType type : _sourceResource.getContainedTypes()) {
            handleType(type);
          }
        }
      } else {

        //
        IModule resourceModule = _mainResource.getAssociatedResourceModule(_modularizedSystem);

        //
        _sourceResource = resourceModule.getResource(_mainResource.getPath(), ProjectContentType.SOURCE);

        //
        IResource binaryResource = resourceModule.getResource(_mainResource.getPath(), ProjectContentType.BINARY);
        if (binaryResource != null) {
          _binaryResources.add((IResourceStandin) binaryResource);
        }
      }
    }

    //
    Collections.sort(_binaryResources);
    Collections.sort(_associatedTypes);

    // set initialized
    _isInitialized = true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param type
   */
  private void handleType(IType type) {

    //
    if (_associatedTypes.contains(type)) {
      return;
    }

    //
    _associatedTypes.add(type);

    //
    if (type.hasSourceResource()) {
      _sourceResource = type.getSourceResource();
      for (IType sourceType : _sourceResource.getContainedTypes()) {
        handleType(sourceType);
      }
    }

    //
    if (type.hasBinaryResource()) {
      IResource binaryResource = type.getBinaryResource();
      _binaryResources.add((IResourceStandin) binaryResource);
      _binaryResources.addAll((Set<IResourceStandin>) binaryResource.getStickyResources());
    }
  }

  @Override
  public String toString() {

    //
    init();

    //
    return "MovableUnit [_sourceResource=" + _sourceResource + ", _binaryResources=" + _binaryResources
        + ", _associatedTypes=" + _associatedTypes + "]";
  }

  @Override
  public int hashCode() {

    //
    init();

    final int prime = 31;
    int result = 1;
    result = prime * result + ((_associatedTypes == null) ? 0 : _associatedTypes.hashCode());
    result = prime * result + ((_binaryResources == null) ? 0 : _binaryResources.hashCode());
    result = prime * result + ((_modularizedSystem == null) ? 0 : _modularizedSystem.hashCode());
    result = prime * result + ((_sourceResource == null) ? 0 : _sourceResource.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    //
    init();

    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MovableUnit other = (MovableUnit) obj;
    other.init();
    if (_associatedTypes == null) {
      if (other._associatedTypes != null)
        return false;
    } else if (!_associatedTypes.equals(other._associatedTypes))
      return false;
    if (_binaryResources == null) {
      if (other._binaryResources != null)
        return false;
    } else if (!_binaryResources.equals(other._binaryResources))
      return false;
    if (_modularizedSystem == null) {
      if (other._modularizedSystem != null)
        return false;
    } else if (!_modularizedSystem.equals(other._modularizedSystem))
      return false;
    if (_sourceResource == null) {
      if (other._sourceResource != null)
        return false;
    } else if (!_sourceResource.equals(other._sourceResource))
      return false;
    return true;
  }

}
