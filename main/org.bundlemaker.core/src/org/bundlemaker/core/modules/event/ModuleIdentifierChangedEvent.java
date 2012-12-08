package org.bundlemaker.core.modules.event;

import org.bundlemaker.core.modules.IModule;

public class ModuleIdentifierChangedEvent {

  /** - */
  private IModule _module;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleIdentifierChangedEvent}.
   * </p>
   * 
   * @param module
   */
  public ModuleIdentifierChangedEvent(IModule module) {
    super();
    _module = module;
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
}
