package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.resource.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleClassificationChangedEvent {

  /** - */
  private IModule _module;

  /** - */
  private Group   _renamedGroup;

  /** - */
  private Group   _newParentGroup;

  /** - */
  private Group   _movedGroup;

  // /** - */
  // private IModule _movedModule;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleClassificationChangedEvent}.
   * </p>
   * 
   * @param module
   */
  public ModuleClassificationChangedEvent(IModule module) {
    Assert.isNotNull(module);

    _module = module;
  }

  public ModuleClassificationChangedEvent(IModule module, Group renamedGroup) {
    Assert.isNotNull(module);

    _module = module;
    _renamedGroup = renamedGroup;
  }

  public ModuleClassificationChangedEvent(IModule module, Group newParentGroup, Group movedGroup) {
    Assert.isNotNull(module);

    _module = module;
    _newParentGroup = newParentGroup;
    _movedGroup = movedGroup;
  }

  // public ModuleClassificationChangedEvent(IModule module, Group newParentGroup, IModule movedModule) {
  // Assert.isNotNull(module);
  //
  // _module = module;
  // _newParentGroup = newParentGroup;
  // _movedModule = movedModule;
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModule getModule() {
    return _module;
  }

  public boolean isGroupRenamed() {
    return _renamedGroup != null;
  }

  public boolean isMovedGroup() {
    return _movedGroup != null;
  }

  // public boolean isMovedModule() {
  // return _movedModule != null;
  // }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getRenamedGroup() {
    return _renamedGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getNewParentGroup() {
    return _newParentGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getMovedGroup() {
    return _movedGroup;
  }

  // /**
  // * <p>
  // * </p>
  // *
  // * @return
  // */
  // public IModule getMovedModule() {
  // return _movedModule;
  // }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "ModuleClassificationChangedEvent [_module=" + _module + "]";
  }
}
