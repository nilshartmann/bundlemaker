package org.bundlemaker.core.mvn.content;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.resources.IProject;

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
   * @param project
   *          the project
   * 
   * @return the location of the local mvn repository
   */
  String getLocalRepo(IProject project);

  /**
   * <p>
   * Returns the location of the remote mvn repository. Must not be <code>null</code>.
   * </p>
   * 
   * @param project
   *          the project
   * 
   * @return the location of the remote mvn repository
   */
  String getRemoteRepo(IProject project);
}
