package org.bundlemaker.core.mvn.content;

import java.io.File;
import java.util.List;

import org.apache.maven.model.Dependency;
import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.internal.config.SimpleRepositoryAdapter;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.RequestTrace;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.graph.DependencyFilter;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.resolution.DependencyRequest;
import org.sonatype.aether.util.DefaultRequestTrace;
import org.sonatype.aether.util.artifact.DefaultArtifact;
import org.sonatype.aether.util.artifact.JavaScopes;
import org.sonatype.aether.util.filter.DependencyFilterUtils;

/**
 * Resolves the transitive (compile) dependencies of an artifact.
 */
public class ResolveTransitiveDependencies
{

  public static void main(String[] args)
      throws Exception
  {
    System.out.println("------------------------------------------------------------");
    System.out.println(ResolveTransitiveDependencies.class.getSimpleName());

    // create the adapter
    SimpleRepositoryAdapter adapter = new SimpleRepositoryAdapter(new File(MvnCoreActivator.DEFAULT_MVN_LOCAL_REPO),
        MvnCoreActivator.DEFAULT_MVN_REMOTE_REPO);

    RepositorySystem system = adapter.getRepositorySystem();

    RepositorySystemSession session = adapter.newSession();

    Artifact artifact = new DefaultArtifact("org.apache.cxf:cxf-api:2.7.2");

    DependencyFilter classpathFlter = DependencyFilterUtils.classpathFilter(JavaScopes.COMPILE);

    CollectRequest collectRequest = new CollectRequest();
    collectRequest.setRoot(new org.sonatype.aether.graph.Dependency(artifact, JavaScopes.COMPILE));
    
    for (RemoteRepository remoteRepository : adapter.getRemoteRepositories()) {
      collectRequest.addRepository(remoteRepository);
    }

    DependencyRequest dependencyRequest = new DependencyRequest(collectRequest, classpathFlter);
    List<ArtifactResult> artifactResults =
        system.resolveDependencies(session, dependencyRequest).getArtifactResults();

    for (ArtifactResult artifactResult : artifactResults)
    {
      System.out.println(artifactResult.getArtifact() + " resolved to " + artifactResult.getArtifact().getFile());
    }
  }

}
