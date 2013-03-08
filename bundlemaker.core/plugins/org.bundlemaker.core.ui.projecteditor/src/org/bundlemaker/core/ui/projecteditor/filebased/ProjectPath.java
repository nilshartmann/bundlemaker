package org.bundlemaker.core.ui.projecteditor.filebased;

import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectPath {

  private final VariablePath _path;

  private boolean            _source;

  /**
   * @param path
   * @param source
   */
  public ProjectPath(String path, boolean source) {
    this(new VariablePath(path), source);
  }

  /**
   * @param path
   * @param source
   */
  public ProjectPath(VariablePath path, boolean source) {
    super();
    _path = path;
    _source = source;
  }

  /**
   * @return the source
   */
  public boolean isSource() {
    return _source;
  }

  public boolean isBinary() {
    return !_source;
  }

  /**
   * @param source
   *          the source to set
   */
  public void setSource(boolean source) {
    _source = source;
  }

  public String asString() {
    return _path.getUnresolvedPath().toString();
  }

  /**
   * @return the path
   */
  public VariablePath getPath() {
    return _path;
  }

  public ProjectContentType getContentType() {
    return (_source ? ProjectContentType.SOURCE : ProjectContentType.BINARY);
  }

}
