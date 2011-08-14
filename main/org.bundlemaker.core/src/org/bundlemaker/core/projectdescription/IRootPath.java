package org.bundlemaker.core.projectdescription;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * A {@link IRootPath} encapsulates the root path of a content entry.
 * </p>
 * <p>
 * A path can either be a binary- or a source path
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRootPath {

  /**
   * <p>
   * Returns the unresolved path.
   * </p>
   * 
   * @return the unresolved path.
   */
  IPath getUnresolvedPath();

  /**
   * <p>
   * Returns the resolved path.
   * </p>
   * 
   * @return the resolved path.
   * @throws CoreException
   */
  IPath getResolvedPath() throws CoreException;

  /**
   * <p>
   * Returns the path as a file.
   * </p>
   * 
   * @return the path as a file.
   */
  File getAsFile() throws CoreException;

  /**
   * Returns true if this path represents a source path
   * <p>
   * An instance can either be a source or a binary path
   * </p>
   * 
   * @return true if this path represents a source path
   */
  public boolean isSourcePath();

  /**
   * Returns true if this path represents a binary path
   * <p>
   * An instance can either be a source or a binary path
   * </p>
   * 
   * @return true if this path represents a binary path
   */
  public boolean isBinaryPath();
}
