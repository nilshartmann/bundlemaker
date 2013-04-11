package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleMovedEvent {

  /** - */
  private IModule      _module;

  /** - */
  private ChangeAction _changeAction;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleMovedEvent}.
   * </p>
   * 
   * @param module
   * @param changeAction
   */
  public ModuleMovedEvent(IModule module, ChangeAction changeAction) {
    super();
    _module = module;
    _changeAction = changeAction;
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

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ChangeAction getChangeAction() {
    return _changeAction;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "ModuleMovedEvent [_module=" + _module + ", _changeAction=" + _changeAction + "]";
  }
}
