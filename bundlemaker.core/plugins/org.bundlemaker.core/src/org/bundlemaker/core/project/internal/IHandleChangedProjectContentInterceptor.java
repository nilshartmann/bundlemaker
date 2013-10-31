package org.bundlemaker.core.project.internal;

import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IHandleChangedProjectContentInterceptor {

  /**
   * <p>
   * </p>
   * 
   * @param contentEntry
   * @param contentResource
   */
  void resourceContentAdded(IProjectContentEntry contentEntry, IProjectContentResource contentResource);

  /**
   * <p>
   * </p>
   * 
   * @param contentEntry
   * @param contentResource
   */
  void resourceContentModified(IProjectContentEntry contentEntry, IProjectContentResource contentResource);
}
