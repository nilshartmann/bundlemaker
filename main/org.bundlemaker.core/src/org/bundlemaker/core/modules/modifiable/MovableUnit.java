package org.bundlemaker.core.modules.modifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
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
  private static final List<IResource> EMPTY_RESOURCE_LIST = Collections.emptyList();

  /** EMPTY_TYPE_LIST */
  private static final List<IType>     EMPTY_TYPE_LIST     = Collections.emptyList();

  /** the main type */
  private IType                        _mainType;

  /** the main resource */
  private IResource                    _mainResource;

  /** the modularized system */
  private IModularizedSystem           _modularizedSystem;

  /** the associated types */
  private List<IType>                  _associatedTypes;

  /** the source resource */
  private IResource                    _sourceResource;

  /** the binary resource */
  private List<IResource>              _binaryResources;

  /** - */
  private boolean                      _isInitialized      = false;

  private IResourceModule              _resourceModule;

  /**
   * <p>
   * </p>
   * 
   * @param type
   * @return
   */
  public static IMovableUnit createFromType(IType type) {
    Assert.isNotNull(type);

    MovableUnit movableUnit = new MovableUnit();
    movableUnit._mainType = type;

    return movableUnit;
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   * @return
   */
  public static IMovableUnit createFromResource(IResource resource, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(resource);

    //
    if (resource.getName().endsWith(".class") && resource.containsTypes()) {

      try {
        IType type = resource.getContainedType();
        if (type.isLocalOrAnonymousType()) {
          String typeName = JavaTypeUtils.getEnclosingNonLocalAndNonAnonymousTypeName(type.getFullyQualifiedName());

          // TODO!!
          IResourceModule resourceModule = resource.getAssociatedResourceModule(modularizedSystem);
          IResource nonAnonymousResource = resourceModule.getResource(typeName.replace('.', '/') + ".class",
              ContentType.BINARY);

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
  public List<IResource> getAssociatedBinaryResources() {

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
  public IResourceModule getContainingResourceModule() {

    init();

    //
    return _resourceModule;
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
    _binaryResources = new LinkedList<IResource>();
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
        IResourceModule resourceModule = _mainResource.getAssociatedResourceModule(_modularizedSystem);

        //
        _sourceResource = resourceModule.getResource(_mainResource.getPath(), ContentType.SOURCE);

        //
        IResource binaryResource = resourceModule.getResource(_mainResource.getPath(), ContentType.BINARY);
        if (binaryResource != null) {
          _binaryResources.add(binaryResource);
        }
      }
    }

    //
    if (!_associatedTypes.isEmpty()) {
      _resourceModule = (IResourceModule) _associatedTypes.get(0).getModule(_modularizedSystem);
    } else if (!_binaryResources.isEmpty()) {
      _resourceModule = (IResourceModule) _binaryResources.get(0).getAssociatedResourceModule(_modularizedSystem);
    } else {
      _resourceModule = _sourceResource.getAssociatedResourceModule(_modularizedSystem);
    }

    Assert.isNotNull(_resourceModule);

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
      _binaryResources.add(binaryResource);
      _binaryResources.addAll(binaryResource.getStickyResources());
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

}
