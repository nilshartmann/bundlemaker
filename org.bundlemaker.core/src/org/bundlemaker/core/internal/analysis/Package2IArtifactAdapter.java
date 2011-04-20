package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Package2IArtifactAdapter {

  /**
   * <p>
   * </p>
   *
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   *
   */
  public static class PackageKey {

    private IModule _resourceModule;

    private String  _packageName;

    /**
     * <p>
     * </p>
     * 
     * @param resourceModule
     * @param packageName
     */
    public PackageKey(IModule resourceModule, String packageName) {

      _resourceModule = resourceModule;
      _packageName = packageName;
    }

    public IModule getModule() {
      return _resourceModule;
    }

    public String getPackageName() {
      return _packageName;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_packageName == null) ? 0 : _packageName.hashCode());
      result = prime * result + ((_resourceModule == null) ? 0 : _resourceModule.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PackageKey other = (PackageKey) obj;
      if (_packageName == null) {
        if (other._packageName != null)
          return false;
      } else if (!_packageName.equals(other._packageName))
        return false;
      if (_resourceModule == null) {
        if (other._resourceModule != null)
          return false;
      } else if (!_resourceModule.equals(other._resourceModule))
        return false;
      return true;
    }
  }
}
