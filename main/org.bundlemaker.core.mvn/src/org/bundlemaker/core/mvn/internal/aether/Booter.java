/*******************************************************************************
 * Copyright (c) 2010, 2012 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sonatype, Inc. - initial API and implementation
 *******************************************************************************/
package org.bundlemaker.core.mvn.internal.aether;

import java.io.File;

import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.eclipse.core.runtime.Assert;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.collection.DependencyCollectionContext;
import org.sonatype.aether.collection.DependencySelector;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.repository.LocalRepository;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.util.DefaultRepositorySystemSession;
import org.sonatype.aether.util.artifact.JavaScopes;

/**
 * A helper to boot the repository system and a repository system session.
 */
public class Booter {

  /** - */
  private File   _localRepo;

  /** - */
  private String _remoteRepoUrl;

  /**
   * <p>
   * Creates a new instance of type {@link Booter}. 
   * </p>
   * 
   * @param localRepo
   * @param remoteRepoUrl
   */
  public Booter(File localRepo, String remoteRepoUrl) {
    Assert.isNotNull(localRepo);
    Assert.isNotNull(remoteRepoUrl);

    _localRepo = localRepo;
    _remoteRepoUrl = remoteRepoUrl;
  }

  public RepositorySystem newRepositorySystem() {
    return ManualRepositorySystemFactory.newRepositorySystem();
  }

  public DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
    MavenRepositorySystemSession session = new MavenRepositorySystemSession();

    LocalRepository localRepo = new LocalRepository(_localRepo);
    session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));

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

  public RemoteRepository newCentralRepository() {
    return new RemoteRepository("central", "default", _remoteRepoUrl);
  }
}
