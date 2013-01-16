package org.bundlemaker.core.mvn.internal.repository;

import org.bundlemaker.core.IBundleMakerProject;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IRepositoryLocationProvider {

  /**
   * <p>
   * Returns the location of the local mvn repository. Must not be <code>null</code>.
   * </p>
   * 
   * @param bundleMakerProject
   *          the bundlemaker project
   * 
   * @return the location of the local mvn repository
   */
  String getLocalRepo(IBundleMakerProject bundleMakerProject);

  /**
   * <p>
   * Returns the location of the remote mvn repository. Must not be <code>null</code>.
   * </p>
   * 
   * @param bundleMakerProject
   *          the bundlemaker project
   * 
   * @return the location of the remote mvn repository
   */
  String getRemoteRepo(IBundleMakerProject bundleMakerProject);
}
