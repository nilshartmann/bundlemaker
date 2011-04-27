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

  /** - */
  private IPath _path;

  /**
   * <p>
   * Creates a new instance of type {@link RootPath}.
   * </p>
   * 
   * @param path
   */
  public RootPath(String path) {
    Assert.isNotNull(path);

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

}
