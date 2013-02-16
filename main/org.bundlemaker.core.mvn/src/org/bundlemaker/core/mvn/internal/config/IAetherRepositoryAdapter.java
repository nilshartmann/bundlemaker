package org.bundlemaker.core.mvn.internal.config;

import java.io.IOException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IAetherRepositoryAdapter {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  RepositorySystem getRepositorySystem() throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  RepositorySystemSession newSession() throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws IOException
   */
  List<RemoteRepository> getRemoteRepositories() throws CoreException;
}
