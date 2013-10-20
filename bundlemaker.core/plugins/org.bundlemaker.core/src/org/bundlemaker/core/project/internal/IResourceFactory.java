package org.bundlemaker.core.project.internal;

import org.bundlemaker.core.project.IProjectContentResource;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceFactory {

  /**
   * <p>
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @return
   */
  IProjectContentResource createResource(String contentId, String root, String path);

  /**
   * <p>
   * </p>
   * 
   * @param contentId
   * @param root
   * @param path
   * @return
   */
  IResourceStandinNEW createResourceStandin(String contentId, String root, String path);
}
