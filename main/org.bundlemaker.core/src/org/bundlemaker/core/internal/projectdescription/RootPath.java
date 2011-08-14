package org.bundlemaker.core.internal.projectdescription;

import java.io.File;

import org.bundlemaker.core.projectdescription.IRootPath;
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
public class RootPath implements IRootPath {

  /**
   * Determines if this instance is a binary path (otherwise it's a source path)
   */
  private final boolean _binaryPath;

  /** - */
  private IPath         _path;

  /**
   * <p>
   * Creates a new instance of type {@link RootPath}.
   * </p>
   * 
   * @param path
   * @param binaryPath
   *          true if this is a binary path, false if it is a source path
   */
  public RootPath(String path, boolean binaryPath) {
    Assert.isNotNull(path);

    // set the type
    _binaryPath = binaryPath;

    // set the path
    _path = new Path(path);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IPath getUnresolvedPath() {
    return _path;
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public IPath getResolvedPath() throws CoreException {

    //
    IStringVariableManager stringVariableManager = VariablesPlugin.getDefault().getStringVariableManager();

    //
    return new Path(stringVariableManager.performStringSubstitution(_path.toString()));
  }

  @Override
  public File getAsFile() throws CoreException {
    return getResolvedPath().toFile();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.projectdescription.IRootPath#isSourcePath()
   */
  @Override
  public boolean isSourcePath() {
    return _binaryPath == false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.projectdescription.IRootPath#isBinaryPath()
   */
  @Override
  public boolean isBinaryPath() {
    return _binaryPath;
  }

}
