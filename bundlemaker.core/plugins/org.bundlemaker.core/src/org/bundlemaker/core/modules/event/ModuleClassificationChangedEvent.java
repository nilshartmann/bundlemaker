package org.bundlemaker.core.modules.event;

import org.bundlemaker.core.modules.IGroup;
import org.bundlemaker.core.modules.IModule;
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
  private IGroup   _renamedGroup;

  /** - */
  private IGroup   _newParentGroup;

  /** - */
  private IGroup   _movedGroup;

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

  public ModuleClassificationChangedEvent(IModule module, IGroup renamedGroup) {
    Assert.isNotNull(module);

    _module = module;
    _renamedGroup = renamedGroup;
  }

  public ModuleClassificationChangedEvent(IModule module, IGroup newParentGroup, IGroup movedGroup) {
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
  public IGroup getRenamedGroup() {
    return _renamedGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IGroup getNewParentGroup() {
    return _newParentGroup;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IGroup getMovedGroup() {
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
