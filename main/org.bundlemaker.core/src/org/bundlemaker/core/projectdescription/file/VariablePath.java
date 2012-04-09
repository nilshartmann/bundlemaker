package org.bundlemaker.core.projectdescription.file;

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VariablePath {

  /** - */
  private IPath _path;

  /**
   * <p>
   * Creates a new instance of type {@link VariablePath}.
   * </p>
   * 
   * @param path
   * @param binaryPath
   *          true if this is a binary path, false if it is a source path
   */
  public VariablePath(String path) {
    Assert.isNotNull(path);

    // set the path
    _path = new Path(path);
  }

  /**
   * {@inheritDoc}
   */
  public IPath getUnresolvedPath() {
    return _path;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  public IPath getResolvedPath() throws CoreException {

    //
    IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();

    //
    return new Path(stringVariableManager.performStringSubstitution(_path.toString()));
  }

  public File getAsFile() throws CoreException {
    return getResolvedPath().toFile();
  }

  @Override
  public String toString() {
    return "VariablePath [_path=" + _path + "]";
  }

  @Override
  public int hashCode() {
    int result = 31;
    try {
      result = result + getResolvedPath().hashCode();
    } catch (CoreException e) {
      e.printStackTrace();
    }
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

    VariablePath other = (VariablePath) obj;
    try {
      return getResolvedPath().equals(other.getResolvedPath());
    } catch (CoreException e) {
      e.printStackTrace();
      return false;
    }
  }
}
