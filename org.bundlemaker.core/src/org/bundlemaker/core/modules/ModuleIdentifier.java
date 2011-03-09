package org.bundlemaker.core.modules;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleIdentifier implements IModuleIdentifier {

  /** - */
  private String _version;

  /** - */
  private String _name;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleIdentifier}.
   * </p>
   * 
   * @param name
   * @param version
   */
  public ModuleIdentifier(String name, String version) {
    super();
    _version = version;
    _name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_name == null) ? 0 : _name.hashCode());
    result = prime * result + ((_version == null) ? 0 : _version.hashCode());
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param name
   * @param version
   * @return
   */
  public boolean equals(String name, String version) {

    if (_name == null) {
      if (name != null)
        return false;
    } else if (!_name.equals(name))
      return false;
    if (_version == null) {
      if (version != null)
        return false;
    } else if (!_version.equals(version))
      return false;
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ModuleIdentifier other = (ModuleIdentifier) obj;
    if (_name == null) {
      if (other._name != null)
        return false;
    } else if (!_name.equals(other._name))
      return false;
    if (_version == null) {
      if (other._version != null)
        return false;
    } else if (!_version.equals(other._version))
      return false;
    return true;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(_name);
    stringBuilder.append("_");
    stringBuilder.append(_version);
    return stringBuilder.toString();
  }
}
