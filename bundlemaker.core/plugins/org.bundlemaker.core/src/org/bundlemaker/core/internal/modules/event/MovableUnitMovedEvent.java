package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.internal.modules.ChangeAction;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IMovableUnit;

public class MovableUnitMovedEvent {

  private IMovableUnit _movableUnit;

  private IModule      _module;

  private ChangeAction _changeAction;

  public MovableUnitMovedEvent(IMovableUnit movableUnit, IModule module, ChangeAction changeAction) {
    super();
    _movableUnit = movableUnit;
    _module = module;
    _changeAction = changeAction;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IMovableUnit getMovableUnit() {
    return _movableUnit;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IModule getModule() {
    return _module;
  }

  public ChangeAction getChangeAction() {
    return _changeAction;
  }

  @Override
  public String toString() {
    return "MovableUnitMovedEvent [_movableUnit=" + _movableUnit + ", _module=" + _module + ", _changeAction="
        + _changeAction + "]";
  }

}
