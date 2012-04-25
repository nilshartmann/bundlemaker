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
package org.eclipse.aether.examples.util;

import org.apache.maven.repository.internal.MavenRepositorySystemSession;
import org.eclipse.aether.examples.manual.ManualRepositorySystemFactory;
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

  public static RepositorySystem newRepositorySystem() {
    return ManualRepositorySystemFactory.newRepositorySystem();
  }

  public static DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system) {
    MavenRepositorySystemSession session = new MavenRepositorySystemSession();

    LocalRepository localRepo = new LocalRepository("D:/development/_save/o/Repository");
    session.setLocalRepositoryManager(system.newLocalRepositoryManager(localRepo));

    // session.setTransferListener( new ConsoleTransferListener() );
    // session.setRepositoryListener( new ConsoleRepositoryListener() );

    // uncomment to generate dirty trees
    // session.setDependencyGraphTransformer( null );

    session.setDependencySelector(new DependencySelector() {

      @Override
      public boolean selectDependency(Dependency dependency) {
        System.out.println(dependency);
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

  public static RemoteRepository newCentralRepository() {
    return new RemoteRepository("central", "default", "http://repo1.maven.org/maven2/");
  }
}
