package org.bundlemaker.core.internal.modules.event;

import org.bundlemaker.core.resource.IModule;

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
