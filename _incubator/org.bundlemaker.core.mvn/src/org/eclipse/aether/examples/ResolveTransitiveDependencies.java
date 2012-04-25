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
package org.eclipse.aether.examples;

import java.util.List;

import org.eclipse.aether.examples.util.Booter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.CollectResult;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.resolution.DependencyRequest;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.JavaScopes;
import org.sonatype.aether.util.filter.DependencyFilterUtils;

/**
 * Resolves the transitive (compile) dependencies of an artifact.
 */
public class ResolveTransitiveDependencies {

  public static void main(String[] args) throws Exception {
    System.out.println("------------------------------------------------------------");
    System.out.println(ResolveTransitiveDependencies.class.getSimpleName());

    RepositorySystem system = Booter.newRepositorySystem();

    RepositorySystemSession session = Booter.newRepositorySystemSession(system);

    Artifact artifact = new DefaultArtifact("groupId:artifactId:version");

    RemoteRepository repo = Booter.newCentralRepository();

    // DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter( JavaScopes.PROVIDED );

    CollectRequest collectRequest = new CollectRequest();
    collectRequest.setRoot(new Dependency(artifact, null));
    collectRequest.addRepository(repo);

    DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, null);

//    CollectResult collectResult = system.collectDependencies(session, collectRequest);
//    
//    System.out.println(collectResult.getRoot().getChildren());

    List<ArtifactResult> artifactResults = system.resolveDependencies(session, dependencyRequest).getArtifactResults();

    for (ArtifactResult artifactResult : artifactResults) {
      System.out.println(artifactResult.getArtifact() + " resolved to " + artifactResult.getArtifact().getFile());
    }
  }

}
