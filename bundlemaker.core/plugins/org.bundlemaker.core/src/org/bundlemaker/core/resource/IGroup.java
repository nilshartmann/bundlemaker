package org.bundlemaker.core.resource;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Defines the interface for a group.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IGroup {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasParentGroup();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IGroup getParentGroup();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IPath getPath();
}
