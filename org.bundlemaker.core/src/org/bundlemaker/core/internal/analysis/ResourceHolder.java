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
public class ResourceHolder implements IResourceHolder {

  private static final List<IResource> EMPTY_RESOURCE_LIST = Collections.emptyList();

  /** - */
  private IType                        _type;

  /** - */
  private IResource                    _sourceResource;

  /** - */
  private IResource                    _binaryResource;

  /** - */
  private List<IResource>              _sourceResources;

  /** - */
  private List<IResource>              _binaryResources;

  /** - */
  private boolean                      _isInitialized      = false;

  /** - */
  private boolean                      _addStickyResources = false;

  /**
   * <p>
   * Creates a new instance of type {@link ResourceHolder}.
   * </p>
   * 
   * @param sourceResource
   * @param binaryResource
   */
  public ResourceHolder(IResource binaryResource, IResource sourceResource) {

    Assert.isTrue(binaryResource != null || sourceResource != null);

    _sourceResource = sourceResource;
    _binaryResource = binaryResource;

    _isInitialized = false;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ResourceHolder}.
   * </p>
   * 
   * @param type
   */
  public ResourceHolder(IType type, boolean addStickyResources) {

    Assert.isNotNull(type);

    _type = type;

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
  public List<IResource> getAssociatedSourceResources() {

    init();

    //
    return _sourceResources != null ? _sourceResources : EMPTY_RESOURCE_LIST;
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
    if (_type != null) {

      if (_type.hasSourceResource()) {
        _sourceResources = new LinkedList<IResource>();
        _sourceResources.add(_type.getSourceResource());
      }

      if (_type.hasBinaryResource()) {
        _binaryResources = new LinkedList<IResource>();

        IResource binaryResource = _type.getBinaryResource();
        _binaryResources.add(binaryResource);

        if (_addStickyResources) {
          _binaryResources.addAll(binaryResource.getStickyResources());
        }
      }
    }

    _isInitialized = true;
  }
}
