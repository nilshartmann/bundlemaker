package org.bundlemaker.core.mvn.internal.repository;

import java.io.File;

import org.bundlemaker.core.mvn.internal.aether.Booter;
import org.eclipse.core.runtime.Assert;
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

  /** - */
  private Booter                  _booter;

  /**
   * <p>
   * Creates a new instance of type {@link MvnRepositories}.
   * </p>
   *
   * @param localRepo
   * @param remoteRepoUrl
   */
  public MvnRepositories(File localRepo, String remoteRepoUrl) {

    Assert.isNotNull(localRepo);
    Assert.isNotNull(remoteRepoUrl);

    try {

      _booter = new Booter(localRepo, remoteRepoUrl);
      _system = _booter.newRepositorySystem();
      _session = _booter.newRepositorySystemSession(_system);
      _repo = _booter.newCentralRepository();

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
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
