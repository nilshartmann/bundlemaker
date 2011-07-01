package org.bundlemaker.core.modules.modifiable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

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

  /** the associated types */
  private List<IType>                  _associatedTypes;

  /** the source resource */
  private IResource                    _sourceResource;

  /** the binary resource */
  private List<IResource>              _binaryResources;

  /** - */
  private boolean                      _isInitialized      = false;

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
  public static IMovableUnit createFromResource(IResource resource) {
    Assert.isNotNull(resource);

    //
    if (resource.containsTypes()) {
      for (IType type : resource.getContainedTypes()) {
        Assert.isTrue(!type.isLocalOrAnonymousType());
      }
    }

    //
    MovableUnit movableUnit = new MovableUnit();
    movableUnit._mainResource = resource;

    //
    return movableUnit;
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
      }
    }

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
}
