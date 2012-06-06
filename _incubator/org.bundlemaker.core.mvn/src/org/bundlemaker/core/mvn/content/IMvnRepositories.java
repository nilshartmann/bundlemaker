package org.bundlemaker.core.mvn.content;

import java.io.File;

import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IMvnRepositories {

  /**
   * <p>
   * </p>
   *
   * @param localRepo
   * @param remoteRepoUrl
   */
  void setMvnRepositories(File localRepo, String remoteRepoUrl);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  RepositorySystem getRepositorySystem();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  RepositorySystemSession getRepositorySystemSession();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  RemoteRepository getRemoteRepository();
}
