package org.bundlemaker.core.internal.resource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core._type.IType;
import org.bundlemaker.core._type.ITypeResource;
import org.bundlemaker.core._type.utils.JavaTypeUtils;
import org.bundlemaker.core.internal.analysis.ITempTypeProvider;
import org.bundlemaker.core.internal.projectdescription.IResourceStandin;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.resource.IModuleResource;
import org.bundlemaker.core.resource.IMovableUnit;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implementation of the {@link IMovableUnit}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnit implements IMovableUnit, ITempTypeProvider {

  /** EMPTY_RESOURCE_LIST */
  private static final List<IResourceStandin> EMPTY_RESOURCE_LIST = Collections.emptyList();

  /** EMPTY_TYPE_LIST */
  private static final List<IType>            EMPTY_TYPE_LIST     = Collections.emptyList();

  /** the main type */
  private IType                               _mainType;

  /** the main resource */
  private IModuleResource                     _mainResource;

  /** the modularized system */
  private IModularizedSystem                  _modularizedSystem;

  /** the associated types */
  private List<IType>                         _associatedTypes;

  /** the source resource */
  private IModuleResource                     _sourceResource;

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
  public static MovableUnit createFromResource(IModuleResource resource, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(resource);

    //
    if (resource.getName().endsWith(".class") && resource.adaptAs(ITypeResource.class).containsTypes()) {

      try {
        IType type = resource.adaptAs(ITypeResource.class).getContainedType();
        if (type.isLocalOrAnonymousType()) {
          String typeName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(type.getFullyQualifiedName());

          // TODO!!
          IModule resourceModule = resource.getModule(modularizedSystem);
          IModuleResource nonAnonymousResource = resourceModule.getResource(typeName.replace('.', '/') + ".class",
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
  public IModuleResource getAssociatedSourceResource() {

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
  public IModule getAssoicatedModule() {

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
          .getModule(_modularizedSystem);
      if (module instanceof IModule) {
        resourceModule = (IModule) module;
      }
    } else {
      resourceModule = _sourceResource.getModule(_modularizedSystem);
    }

    //
    return resourceModule;
  }

  public boolean hasModule() {
    //
    return getAssoicatedModule() != null;
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
        for (IType type : _sourceResource.adaptAs(ITypeResource.class).getContainedTypes()) {
          handleType(type);
        }
      }
    }

    // ...or process main resource...
    else if (_mainResource != null) {
      if (_mainResource.adaptAs(ITypeResource.class).containsTypes()) {
        for (IType type : _mainResource.adaptAs(ITypeResource.class).getContainedTypes()) {
          handleType(type);
        }
        if (_sourceResource != null) {
          for (IType type : _sourceResource.adaptAs(ITypeResource.class).getContainedTypes()) {
            handleType(type);
          }
        }
      } else {

        //
        IModule resourceModule = _mainResource.getModule(_modularizedSystem);

        //
        _sourceResource = resourceModule.getResource(_mainResource.getPath(), ProjectContentType.SOURCE);

        //
        IModuleResource binaryResource = resourceModule.getResource(_mainResource.getPath(), ProjectContentType.BINARY);
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
      for (IType sourceType : _sourceResource.adaptAs(ITypeResource.class).getContainedTypes()) {
        handleType(sourceType);
      }
    }

    //
    if (type.hasBinaryResource()) {
      IModuleResource binaryResource = type.getBinaryResource();
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
