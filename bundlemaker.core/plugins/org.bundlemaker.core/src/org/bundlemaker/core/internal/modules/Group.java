package org.bundlemaker.core.internal.modules;

import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractCachingModularizedSystem;
import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.event.ClassificationChangedEvent;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Group implements IGroup {

  /** - */
  private String             _name;

  /** - */
  private Group              _parent;

  /** - */
  private boolean            _hasRootParent;

  /** - */
  private IModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link Group}.
   * </p>
   * 
   * @param path
   */
  public Group(String name, Group parent, IModularizedSystem modularizedSystem) {
    Assert.isNotNull(name);
    Assert.isTrue(!name.isEmpty());
    Assert.isNotNull(modularizedSystem);

    //
    _name = name;
    _parent = parent;

    //
    _modularizedSystem = modularizedSystem;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public IPath getPath() {

    //
    if (_parent != null) {
      return _parent.getPath().append(_name);
    }

    //
    return new Path(_name);
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   */
  public void setName(String name) {
    Assert.isNotNull(name);
    Assert.isTrue(!name.isEmpty());

    _name = name;

    //
    ClassificationChangedEvent event = new ClassificationChangedEvent(this);

    //
    ((AbstractCachingModularizedSystem) _modularizedSystem)
        .fireClassificationChanged(event);
  }

  /**
   * <p>
   * </p>
   * 
   * @param parent
   */
  public void setParent(Group parent) {

    _parent = parent;
    _hasRootParent = false;

    //
    ClassificationChangedEvent event = new ClassificationChangedEvent(
        _parent,
        this);

    //
    ((AbstractCachingModularizedSystem) _modularizedSystem)
        .fireClassificationChanged(event);
  }

  /**
   * <p>
   * </p>
   */
  public void setRootParent() {
    _parent = null;
    _hasRootParent = true;

    //
    ClassificationChangedEvent event = new ClassificationChangedEvent(
        _parent,
        this);

    //
    ((AbstractCachingModularizedSystem) _modularizedSystem)
        .fireClassificationChanged(event);
  }

  public boolean hasRootParent() {
    return _hasRootParent;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  @Override
  public IGroup getParent() {
    return _parent;
  }
}
