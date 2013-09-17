package org.bundlemaker.core.mvn.content;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.common.prefs.BundleMakerPreferences;
import org.bundlemaker.core.common.prefs.IBundleMakerPreferences;
import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.internal.MvnArtifactConverter;
import org.bundlemaker.core.mvn.internal.config.DispatchingRepositoryAdapter;
import org.bundlemaker.core.mvn.internal.config.IAetherRepositoryAdapter;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescription;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.resource.IModuleIdentifier;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.CollectResult;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.graph.DependencyVisitor;
import org.sonatype.aether.repository.RemoteRepository;
import org.sonatype.aether.repository.RepositoryPolicy;
import org.sonatype.aether.resolution.ArtifactRequest;
import org.sonatype.aether.resolution.ArtifactResolutionException;
import org.sonatype.aether.resolution.ArtifactResult;
import org.sonatype.aether.util.artifact.DefaultArtifact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  /** - */
  @Expose
  @SerializedName("mvnArtifacts")
  private List<MvnArtifactType>    _mvnArtifactTypes;

  /** the mvn scope */
  private static final String      SCOPE_COMPILE = "compile";

  /** - */
  private IAetherRepositoryAdapter _repositoryAdapter;

  /** - */
  private RepositorySystemSession  _currentSystemSession;

  /**
   * <p>
   * Creates a new instance of type {@link MvnContentProvider}.
   * </p>
   */
  public MvnContentProvider() {

    // initialize
    _mvnArtifactTypes = new LinkedList<MvnArtifactType>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void prepare() {

    //
    IBundleMakerPreferences preferences = getBundleMakerPreferences();

    //
    _repositoryAdapter = new DispatchingRepositoryAdapter(preferences);
  }

  protected IBundleMakerPreferences getBundleMakerPreferences() {
    return BundleMakerPreferences.getBundleMakerPreferences(MvnCoreActivator.PLUGIN_ID, getBundleMakerProject()
        .getProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @param groupId
   * @param artifactId
   * @param version
   */
  public void addMvnArtifact(String groupId, String artifactId, String version) {

    // asserts
    Assert.isNotNull(groupId);
    Assert.isNotNull(artifactId);
    Assert.isNotNull(version);

    // create the new MvnArtifactType
    MvnArtifactType mvnArtifactType = new MvnArtifactType();
    mvnArtifactType.setGroupId(groupId);
    mvnArtifactType.setArtifactId(artifactId);
    mvnArtifactType.setVersion(version);

    // add it to the list of artifacts
    _mvnArtifactTypes.add(mvnArtifactType);
  }

  /**
   * <p>
   * </p>
   * 
   * @param mvnArtifactType
   */
  public void addMvnArtifact(MvnArtifactType mvnArtifactType) {

    // asserts
    Assert.isNotNull(mvnArtifactType);

    // add it to the list of artifacts
    _mvnArtifactTypes.add(mvnArtifactType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onInitializeProjectContent(IProgressMonitor progressMonitor) throws CoreException {

    // only reload content if the fileBasedContents are not initialized yet
    reloadContent(true, false, getBundleMakerProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  public void reloadContent(final boolean useRemoteRepository, final boolean reloadFromRemote,
      IProjectDescriptionAwareBundleMakerProject bundleMakerProject) throws CoreException {

    // create a new session
    _currentSystemSession = _repositoryAdapter.newSession();

    // create the result list
    clearFileBasedContents();

    // iterate over all the specified mvn artifacts
    for (final MvnArtifactType artifactIdentifier : _mvnArtifactTypes) {

      // create the aether artifact
      final Artifact artifact = new DefaultArtifact(artifactIdentifier.getGroupId(),
          artifactIdentifier.getArtifactId(), "jar", artifactIdentifier.getVersion());

      // create the collect request
      CollectRequest collectRequest = new CollectRequest();
      collectRequest.setRoot(new Dependency(artifact, SCOPE_COMPILE));

      // add the remote repository is necessary
      if (useRemoteRepository) {
        for (RemoteRepository remoteRepository : _repositoryAdapter.getRemoteRepositories()) {

          //
          RepositoryPolicy policy = reloadFromRemote ? new RepositoryPolicy(true,
              RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_IGNORE) : new RepositoryPolicy(
              true, RepositoryPolicy.UPDATE_POLICY_DAILY, RepositoryPolicy.CHECKSUM_POLICY_IGNORE);

          //
          remoteRepository.setPolicy(true, policy);

          //
          collectRequest.addRepository(remoteRepository);
        }
      }

      //
      try {

        // collect the result
        CollectResult collectResult = _repositoryAdapter.getRepositorySystem().collectDependencies(
            _currentSystemSession, collectRequest);

        final List<Artifact> alreadyHandled = new LinkedList<Artifact>();

        final Map<DependencyNode, CoreException> exceptions = new HashMap<DependencyNode, CoreException>();

        // visit all the dependencies
        collectResult.getRoot().accept(new DependencyVisitorAdapter() {
          @Override
          public boolean visitEnter(DependencyNode node) {
            try {

              System.out.println("NODE: " + node);
              for (DependencyNode dependencyNode : node.getChildren()) {
                System.out.println("  - " + dependencyNode + " : " + dependencyNode.getDependency());
              }

              return handleDependencyNode(node, useRemoteRepository, alreadyHandled,
                  artifact.equals(node.getDependency().getArtifact()));
            } catch (CoreException e) {
              exceptions.put(node, e);
              return false;
            }
          }
        });

        //
        if (exceptions.size() > 0) {

          Entry<DependencyNode, CoreException> entry = exceptions.entrySet().toArray(new Entry[0])[0];

          if (artifact.equals(entry.getKey().getDependency().getArtifact())) {
            throw new CoreException(new Status(Status.ERROR, MvnCoreActivator.PLUGIN_ID, entry.getValue().getMessage(),
                entry.getValue()));
          }

          //
          else {
            // TODO
            System.out.println("TODO: Handle missing artifacts:");
            for (DependencyNode node : exceptions.keySet()) {
              System.out.println(node.toString());
            }
          }
        }

      } catch (DependencyCollectionException e) {
        e.printStackTrace();
        throw new CoreException(new Status(Status.ERROR, MvnCoreActivator.PLUGIN_ID, e.getMessage(), e));
      }

      // TODO: Move-up
      // finally: fire project description recomputed event
      fireProjectDescriptionRecomputedEvent();
    }
  }

  /**
   * <p>
   * Clears the artifact list
   * </p>
   */
  public void clearArtifactList() {
    _mvnArtifactTypes.clear();
  }

  /**
   * <p>
   * Returns the current maven artifacts.
   * </p>
   * 
   * @return the current maven artifacts.
   */
  public List<MvnArtifactType> getMvnArtifacts() {
    return _mvnArtifactTypes;
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param artifactType
   * @param node
   * @return
   * @throws CoreException
   */
  private boolean handleDependencyNode(DependencyNode node, boolean useRemoteRepository, List<Artifact> alreadyHandled,
      boolean downloadSources) throws CoreException {

    //
    if (alreadyHandled.contains(node.getDependency().getArtifact())) {
      return false;
    }

    //
    alreadyHandled.add(node.getDependency().getArtifact());

    // get the referenced artifact
    Artifact mvnArtifact = node.getDependency().getArtifact();

    //
    ArtifactRequest artifactRequest = new ArtifactRequest();
    artifactRequest.setArtifact(node.getDependency().getArtifact());

    //
    if (useRemoteRepository) {
      for (RemoteRepository remoteRepository : _repositoryAdapter.getRemoteRepositories()) {
        artifactRequest.addRepository(remoteRepository);
      }
    }

    try {

      //
      ArtifactResult artifactResult = _repositoryAdapter.getRepositorySystem().resolveArtifact(_currentSystemSession,
          artifactRequest);

      File sourceFile = null;
      File binaryFile = null;

      //
      Artifact currentMavenArtifact = artifactResult.getArtifact();

      // TODO
      if (downloadSources) {

        Artifact sourceArtifact = new DefaultArtifact(currentMavenArtifact.getGroupId(),
            currentMavenArtifact.getArtifactId(), "sources", currentMavenArtifact.getExtension(),
            currentMavenArtifact.getVersion());

        artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(sourceArtifact);

        for (RemoteRepository remoteRepository : _repositoryAdapter.getRemoteRepositories()) {
          artifactRequest.addRepository(remoteRepository);
        }

        try {
          artifactResult = _repositoryAdapter.getRepositorySystem().resolveArtifact(_currentSystemSession,
              artifactRequest);
          sourceFile = artifactResult.getArtifact().getFile();
        } catch (ArtifactResolutionException e) {
          e.printStackTrace();
          System.out.println(e.getMessage());
        }
      }

      //
      binaryFile = currentMavenArtifact.getFile();

      // TODO!!!

      //
      IModuleIdentifier moduleIdentifier = MvnArtifactConverter.toModuleIdentifier(mvnArtifact.getGroupId(),
          mvnArtifact.getArtifactId(), mvnArtifact.getVersion());

      //
      AnalyzeMode analyzeMode = AnalyzeMode.DO_NOT_ANALYZE;
      if (downloadSources) {
        if (sourceFile != null) {
          analyzeMode = AnalyzeMode.BINARIES_AND_SOURCES;
        } else {
          analyzeMode = AnalyzeMode.BINARIES_ONLY;
        }
      }

      //
      IProjectContentEntry fileBasedContent = createFileBasedContent(moduleIdentifier.getName(),
          moduleIdentifier.getVersion(), new File[] { binaryFile }, sourceFile == null ? new File[] {}
              : new File[] { sourceFile }, analyzeMode);

      // set user attributes
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_GROUP_ID, mvnArtifact.getGroupId());
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_ARTIFACT_ID,
          mvnArtifact.getArtifactId());
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MODULE_NAME, moduleIdentifier.getName());

    } catch (Exception e) {
      throw new CoreException(new Status(IStatus.ERROR, MvnCoreActivator.PLUGIN_ID, String.format(
          "Exception while trying to resolve '%s'.", node.toString()), e));
    }

    //
    return true;
  }

  /**
   * <p>
   * Internal helper class.
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   */
  private static class DependencyVisitorAdapter implements DependencyVisitor {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visitEnter(DependencyNode node) {
      return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean visitLeave(DependencyNode node) {
      return true;
    }
  }
}
