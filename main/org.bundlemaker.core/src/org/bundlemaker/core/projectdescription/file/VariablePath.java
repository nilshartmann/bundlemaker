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

  // /*
  // * (non-Javadoc)
  // *
  // * @see org.bundlemaker.core.projectdescription.IRootPath#isSourcePath()
  // */
  // @Override
  // public boolean isSourcePath() {
  // return _binaryPath == false;
  // }
  //
  // /*
  // * (non-Javadoc)
  // *
  // * @see org.bundlemaker.core.projectdescription.IRootPath#isBinaryPath()
  // */
  // @Override
  // public boolean isBinaryPath() {
  // return _binaryPath;
  // }

}
