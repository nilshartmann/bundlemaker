package org.bundlemaker.core.modules;

import org.eclipse.core.runtime.Assert;

public class ModuleClassificationChangedEvent {

  /** - */
  private IModule _module;

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

  public IModule getModule() {
    return _module;
  }

  @Override
  public String toString() {
    return "ModuleClassificationChangedEvent [_module=" + _module + "]";
  }
}
