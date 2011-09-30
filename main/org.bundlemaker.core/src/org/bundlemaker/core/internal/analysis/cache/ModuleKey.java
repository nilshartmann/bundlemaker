package org.bundlemaker.core.internal.analysis.cache;

import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Class that encapsulates the key for a module. A module can be identified either by its representing {@link IModule}
 * instance <i>or</i> by a simple name in case that there is no representing {@link IModule} instance (e.g. there is no
 * representing {@link IModule} instance for the virtual 'missing types' module).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleKey {

  /** the name of the module */
  private String  _moduleName;

  /** the IModule instance */
  private IModule _module;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleKey}.
   * </p>
   * 
   * @param moduleName
   */
  public ModuleKey(String moduleName) {
    Assert.isNotNull(moduleName);

    // the module name
    _moduleName = moduleName;
  }

  /**
   * <p>
   * Creates a new instance of type {@link ModuleKey}.
   * </p>
   * 
   * @param module
   */
  public ModuleKey(IModule module) {
    Assert.isNotNull(module);

    // the module
    _module = module;
  }

  /**
   * <p>
   * Returns the module name or <code>null</code> if no module name is set.
   * </p>
   * 
   * @return the module name or <code>null</code> if no module name is set.
   */
  public final String getModuleName() {
    return _moduleName;
  }

  /**
   * <p>
   * Returns the module.
   * </p>
   * 
   * @return the module.
   */
  public final IModule getModule() {
    return _module;
  }

  /**
   * <p>
   * Returns <code>true</code> if this key has an associated module.
   * </p>
   * 
   * @return <code>true</code> if this key has an associated module.
   */
  public final boolean hasModule() {
    return _module != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_module == null) ? 0 : _module.hashCode());
    result = prime * result + ((_moduleName == null) ? 0 : _moduleName.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ModuleKey other = (ModuleKey) obj;
    if (_module == null) {
      if (other._module != null)
        return false;
    } else if (!_module.equals(other._module))
      return false;
    if (_moduleName == null) {
      if (other._moduleName != null)
        return false;
    } else if (!_moduleName.equals(other._moduleName))
      return false;
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "ModuleKey [_moduleName=" + _moduleName + ", _module=" + _module + "]";
  }
}