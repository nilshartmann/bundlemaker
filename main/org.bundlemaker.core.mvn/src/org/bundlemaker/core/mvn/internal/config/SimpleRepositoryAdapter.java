package org.bundlemaker.core.mvn.internal.config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.bundlemaker.core.mvn.internal.aether.ManualRepositorySystemFactory;
import org.eclipse.core.runtime.Assert;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.collection.DependencyCollectionContext;
import org.sonatype.aether.collection.DependencySelector;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.artifact.JavaScopes;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleRepositoryAdapter implements IAetherRepositoryAdapter {

  /** - */
  private RepositorySystem _system;

  /** - */
  private RemoteRepository _repo;

  /** - */
  private File             _localRepo;

  /** - */
  private String           _remoteRepoUrl;

  /**
   * <p>
   * Creates a new instance of type {@link SimpleRepositoryAdapter}.
   * </p>
   * 
   * @param localRepo
   * @param remoteRepoUrl
   */
  public SimpleRepositoryAdapter(File localRepo, String remoteRepoUrl) {

    Assert.isNotNull(localRepo);
    Assert.isNotNull(remoteRepoUrl);

    try {

      // set the parameters
      _localRepo = localRepo;
      _remoteRepoUrl = remoteRepoUrl;

      // create
      _system = ManualRepositorySystemFactory.newRepositorySystem();
      _repo = new RemoteRepository("central", "default", _remoteRepoUrl);

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

  @Override
  public RepositorySystemSession newSession() {
    MavenRepositorySystemSession session = new MavenRepositorySystemSession();

    LocalRepository localRepo = new LocalRepository(_localRepo);
    session.setLocalRepositoryManager(_system.newLocalRepositoryManager(localRepo));

    // session.setTransferListener( new ConsoleTransferListener() );
    // session.setRepositoryListener( new ConsoleRepositoryListener() );

    // uncomment to generate dirty trees
    // session.setDependencyGraphTransformer( null );

    session.setDependencySelector(new DependencySelector() {

      @Override
      public boolean selectDependency(Dependency dependency) {
        return dependency.getScope().equals(JavaScopes.COMPILE) || dependency.getScope().equals(JavaScopes.PROVIDED)
            || dependency.getScope().equals(JavaScopes.RUNTIME);
      }

      @Override
      public DependencySelector deriveChildSelector(DependencyCollectionContext context) {
        return this;
      }
    });

    return session;
  }

  @Override
  public List<RemoteRepository> getRemoteRepositories() {

    //
    List<RemoteRepository> remoteRepositories = new LinkedList<RemoteRepository>();
    remoteRepositories.add(_repo);

    //
    return remoteRepositories;
  }
}
