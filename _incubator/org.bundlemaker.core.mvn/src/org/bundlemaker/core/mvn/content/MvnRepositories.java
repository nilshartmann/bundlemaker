package org.bundlemaker.core.mvn.content;

import java.io.File;

import org.bundlemaker.core.mvn.aether.Booter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.repository.RemoteRepository;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnRepositories implements IMvnRepositories {

  /** - */
  private RepositorySystem        _system;

  /** - */
  private RepositorySystemSession _session;

  /** - */
  private RemoteRepository        _repo;

  /**
   * <p>
   * Creates a new instance of type {@link MvnRepositories}.
   * </p>
   */
  public MvnRepositories(File localRepo, String remoteRepoUrl) {

    //
    Booter booter = new Booter(localRepo, remoteRepoUrl);

    //
    _system = booter.newRepositorySystem();
    _session = booter.newRepositorySystemSession(_system);
    _repo = booter.newCentralRepository();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RepositorySystem getRepositorySystem() {
    return _system;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RepositorySystemSession getRepositorySystemSession() {
    return _session;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RemoteRepository getRemoteRepository() {
    return _repo;
  }

}
