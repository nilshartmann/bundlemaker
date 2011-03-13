package org.bundlemaker.core.internal.projectdescription;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarInfo {

  /** - */
  private String _name;

  /** - */
  private String _version;

  /**
   * <p>
   * Creates a new instance of type {@link JarInfo}.
   * </p>
   * 
   * @param name
   * @param version
   */
  public JarInfo(String name, String version) {
    super();

    _name = name;
    _version = version;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "JarInfo [name=" + _name + ", version=" + _version + "]";
  }
}