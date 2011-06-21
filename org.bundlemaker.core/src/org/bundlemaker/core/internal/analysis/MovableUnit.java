package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MovableUnit implements IMovableUnit {

  private static final List<IResource> EMPTY_RESOURCE_LIST = Collections.emptyList();

  private static final List<IType>     EMPTY_TYPE_LIST     = Collections.emptyList();

  /** - */
  private IType                        _mainType;

  /** - */
  private List<IType>                  _associatedTypes;

  /** - */
  private IResource                    _sourceResource;

  /** - */
  private List<IResource>              _binaryResources;

  /** - */
  private boolean                      _isInitialized      = false;

  /** - */
  private boolean                      _addStickyResources = false;

  // /**
  // * <p>
  // * Creates a new instance of type {@link MovableUnit}.
  // * </p>
  // *
  // * @param sourceResource
  // * @param binaryResource
  // */
  // public MovableUnit(IResource binaryResource, IResource sourceResource) {
  //
  // Assert.isTrue(binaryResource != null || sourceResource != null);
  //
  // _sourceResource = sourceResource;
  // _binaryResource = binaryResource;
  //
  // _isInitialized = false;
  // }

  /**
   * <p>
   * Creates a new instance of type {@link MovableUnit}.
   * </p>
   * 
   * @param type
   */
  public MovableUnit(IType type, boolean addStickyResources) {

    Assert.isNotNull(type);

    _mainType = type;

    _addStickyResources = addStickyResources;
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

    if (_isInitialized) {
      return;
    }

    _binaryResources = new LinkedList<IResource>();

    // process type
    if (_mainType != null) {

      //
      _associatedTypes = new LinkedList<IType>();

      //
      handleType(_mainType);

      //
      for (IType type : _sourceResource.getContainedTypes()) {
        handleType(type);
      }
    }

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

    _associatedTypes.add(type);

    //
    if (type.hasSourceResource()) {
      _sourceResource = type.getSourceResource();
    }

    //
    if (type.hasBinaryResource()) {
      _binaryResources = new LinkedList<IResource>();

      IResource binaryResource = type.getBinaryResource();
      _binaryResources.add(binaryResource);

      if (_addStickyResources) {
        _binaryResources.addAll(binaryResource.getStickyResources());
      }
    }
  }
}
