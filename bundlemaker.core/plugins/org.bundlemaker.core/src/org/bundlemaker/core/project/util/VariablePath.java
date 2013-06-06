package org.bundlemaker.core.project.util;

import java.io.File;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * Encapsulates a path that can contain eclipse variables, e.g
 * <code>${project_loc:myEclipseProject/lib/myJar.jar}</code>. This class provides several convenience methods to
 * resolve the variable path and retrieve the corresponding {@link File}.
 * </p>
 * <p>
 * Instances of this class are automatically externalized as JSON strings when used in a IProjectContentProvider
 * implementation (if the corresponding fields are annotated with an {@link Expose} annotation).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This interface is not intended to be extended by clients.
 */
public class VariablePath {

  /** the path that may contains variables */
  @Expose
  @SerializedName("path")
  private final IPath _path;

  /**
   * <p>
   * Creates a new instance of type {@link VariablePath}. Must not be {@code null} and not empty.
   * </p>
   * 
   * @param path
   *          the path that may contains variables
   */
  public VariablePath(String path) {
    Assert.isNotNull(path);
    Assert.isTrue(!path.isEmpty());

    // set the path
    _path = new Path(path);
  }

  /**
   * <p>
   * Returns the raw, unresolved path that may contains variables.
   * </p>
   * 
   * @return the raw, unresolved path that may contains variables.
   */
  public IPath getUnresolvedPath() {
    return _path;
  }

  /**
   * <p>
   * Returns the resolved path using the eclipse {@link IStringVariableManager}. If the path contains one or more
   * undefined variables, a {@link CoreException} will be thrown.
   * </p>
   * 
   * @return the resolved path
   * @throws CoreException
   *           if the path contains one or more undefined variables
   */
  public IPath getResolvedPath() throws CoreException {

    // get the IStringVariableManager
    IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();

    // return the resolved path
    return new Path(stringVariableManager.performStringSubstitution(_path.toString()));
  }

  /**
   * <p>
   * Returns the resolved path as a {@link File}. The result of this method is equivalent to calling
   * {@code getResolvedPath().toFile()}.
   * </p>
   * 
   * @return the resolved path as a {@link File}
   * @throws CoreException
   *           if the path contains one or more undefined variables
   */
  public File getAsFile() throws CoreException {
    return getResolvedPath().toFile();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "VariablePath [_path=" + _path + "]";
  }

  /**
   * {@inheritDoc}
   */
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

    VariablePath other = (VariablePath) obj;
    try {
      return getResolvedPath().equals(other.getResolvedPath());
    } catch (CoreException e) {
      e.printStackTrace();
      return false;
    }
  }
}
