package org.bundlemaker.core.projectdescription;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * A {@link IRootPath} encapsulates the root path of a content entry.
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
}
