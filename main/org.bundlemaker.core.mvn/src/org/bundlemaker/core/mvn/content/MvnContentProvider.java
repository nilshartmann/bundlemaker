package org.bundlemaker.core.mvn.content;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.mvn.MvnCoreActivator;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.mvn.content.xml.MvnContentType;
import org.bundlemaker.core.mvn.internal.MvnArtifactConverter;
import org.bundlemaker.core.mvn.internal.config.DispatchingRepositoryAdapter;
import org.bundlemaker.core.mvn.internal.config.IAetherRepositoryAdapter;
import org.bundlemaker.core.projectdescription.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
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

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MvnContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  /** the mvn scope */
  private static final String        SCOPE_COMPILE = "compile";

  /** the mvn content type (configuration) */
  private MvnContentType             _mvnContent   = new MvnContentType();

  /** TODO */
  private int                        _counter      = 0;

  /** cached list of all file based contents */
  private List<IProjectContentEntry> _fileBasedContents;

  /** the BundleMakerProject */
  private IBundleMakerProject        _bundleMakerProject;

  /** - */
  private IAetherRepositoryAdapter   _repositoryAdapter;

  /** - */
  private RepositorySystemSession    _currentSystemSession;

  /**
   * <p>
   * Creates a new instance of type {@link MvnContentProvider}.
   * </p>
   */
  public MvnContentProvider() {

    // initialize
    _fileBasedContents = new LinkedList<IProjectContentEntry>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void init(IProjectDescription description) {

    //
    _repositoryAdapter = new DispatchingRepositoryAdapter(description.getBundleMakerProject());
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
    _mvnContent.getArtifacts().add(mvnArtifactType);
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
    _mvnContent.getArtifacts().add(mvnArtifactType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getConfiguration() {

    // return the configuration
    return _mvnContent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(Object configuration) {

    //
    Assert.isNotNull(configuration);
    Assert.isTrue(configuration instanceof MvnContentType);

    // set the content
    _mvnContent = (MvnContentType) configuration;

    // clear the file based content
    _fileBasedContents.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(
      IProgressMonitor progressMonitor,
      final IBundleMakerProject bundleMakerProject)
      throws CoreException {

    Assert.isNotNull(bundleMakerProject);

    // only reload content if the fileBasedContents are not initialized yet
    if (_fileBasedContents != null && !_fileBasedContents.isEmpty()) {
      return _fileBasedContents;
    } else {
      reloadContent(true, false, bundleMakerProject);
    }

    // return the result
    return _fileBasedContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  public void reloadContent(final boolean useRemoteRepository, final boolean reloadFromRemote,
      IBundleMakerProject bundleMakerProject) throws CoreException {

    // set the IBundleMakerProject
    _bundleMakerProject = bundleMakerProject;

    // create a new session
    _currentSystemSession = _repositoryAdapter.newSession();

    // create the result list
    _fileBasedContents.clear();

    // iterate over all the specified mvn artifacts
    for (final MvnArtifactType artifactIdentifier : _mvnContent.getArtifacts()) {

      // create the aether artifact
      final Artifact artifact = new DefaultArtifact(artifactIdentifier.getGroupId(),
          artifactIdentifier.getArtifactId(), "jar",
          artifactIdentifier.getVersion());

      // create the collect request
      CollectRequest collectRequest = new CollectRequest();
      collectRequest.setRoot(new Dependency(artifact, SCOPE_COMPILE));

      // add the remote repository is necessary
      if (useRemoteRepository) {
        for (RemoteRepository remoteRepository : _repositoryAdapter.getRemoteRepositories()) {

          //
          RepositoryPolicy policy = reloadFromRemote ? new RepositoryPolicy(true,
              RepositoryPolicy.UPDATE_POLICY_ALWAYS, RepositoryPolicy.CHECKSUM_POLICY_IGNORE) : new RepositoryPolicy(
              true,
              RepositoryPolicy.UPDATE_POLICY_DAILY, RepositoryPolicy.CHECKSUM_POLICY_IGNORE);

          //
          remoteRepository.setPolicy(true, policy);

          //
          collectRequest.addRepository(remoteRepository);
        }
      }

      //
      try {

        // collect the result
        CollectResult collectResult = _repositoryAdapter.getRepositorySystem()
            .collectDependencies(
                _currentSystemSession, collectRequest);

        final List<Artifact> alreadyHandled = new LinkedList<Artifact>();

        final Map<DependencyNode, CoreException> exceptions = new HashMap<DependencyNode, CoreException>();

        // visit all the dependencies
        collectResult.getRoot().accept(new DependencyVisitorAdapter() {
          @Override
          public boolean visitEnter(DependencyNode node) {
            try {
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
            throw new CoreException(new Status(Status.ERROR, MvnCoreActivator.PLUGIN_ID,
                entry.getValue().getMessage(),
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
        throw new CoreException(new Status(Status.ERROR, MvnCoreActivator.PLUGIN_ID, e.getMessage(), e));
      }

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
    _mvnContent.getArtifacts().clear();
    _fileBasedContents.clear();
  }

  /**
   * <p>
   * Returns the current maven artifacts.
   * </p>
   * 
   * @return the current maven artifacts.
   */
  public List<MvnArtifactType> getMvnArtifacts() {
    return _mvnContent.getArtifacts();
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
  private boolean handleDependencyNode(DependencyNode node, boolean useRemoteRepository,
      List<Artifact> alreadyHandled, boolean downloadSources) throws CoreException {

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
      ArtifactResult artifactResult = _repositoryAdapter.getRepositorySystem()
          .resolveArtifact(
              _currentSystemSession, artifactRequest);

      File sourceFile = null;
      File binaryFile = null;

      //
      Artifact currentMavenArtifact = artifactResult.getArtifact();

      // TODO
      if (downloadSources) {

        Artifact sourceArtifact = new DefaultArtifact(currentMavenArtifact.getGroupId(),
            currentMavenArtifact.getArtifactId(), "sources", currentMavenArtifact.getExtension(),
            currentMavenArtifact
                .getVersion());

        artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact(sourceArtifact);

        for (RemoteRepository remoteRepository : _repositoryAdapter.getRemoteRepositories()) {
          artifactRequest.addRepository(remoteRepository);
        }

        try {
          artifactResult = _repositoryAdapter.getRepositorySystem().resolveArtifact(
              _currentSystemSession, artifactRequest);
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
      boolean analyze = downloadSources;

      //
      FileBasedProjectContent fileBasedContent = createFileBasedContent(moduleIdentifier.getName(),
          moduleIdentifier.getVersion(), binaryFile, sourceFile, _bundleMakerProject, analyze);

      // set user attributes
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_GROUP_ID,
          mvnArtifact.getGroupId());
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_ARTIFACT_ID,
          mvnArtifact.getArtifactId());
      fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MODULE_NAME,
          moduleIdentifier.getName());

    } catch (Exception e) {
      throw new CoreException(new Status(IStatus.ERROR, MvnCoreActivator.PLUGIN_ID, String.format(
          "Exception while trying to resolve '%s'.",
          node.toString()), e));
    }

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentName
   * @param contentVersion
   * @param binaryPath
   * @param sourcePath
   * @throws CoreException
   */
  private FileBasedProjectContent createFileBasedContent(String contentName, String contentVersion, File binaryPath,
      File sourcePath,
      IBundleMakerProject bundleMakerProject, boolean analyze) throws CoreException {

    // asserts
    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPath);
    Assert.isNotNull(bundleMakerProject);

    FileBasedProjectContent result = new FileBasedProjectContent(MvnContentProvider.this);
    if (!analyze) {
      result.setAnalyzeMode(AnalyzeMode.DO_NOT_ANALYZE);
    } else {
      if (sourcePath != null) {
        result.setAnalyzeMode(AnalyzeMode.BINARIES_AND_SOURCES);
      } else {
        result.setAnalyzeMode(AnalyzeMode.BINARIES_ONLY);
      }
    }
    result.setId(getId() + _counter++);
    result.setName(contentName);
    result.setVersion(contentVersion);

    result.addRootPath(new VariablePath(binaryPath.getAbsolutePath()), ProjectContentType.BINARY);

    if (sourcePath != null) {
      result.addRootPath(new VariablePath(sourcePath.getAbsolutePath()), ProjectContentType.SOURCE);
    }

    //
    result.initialize(bundleMakerProject.getProjectDescription());

    //
    _fileBasedContents.add(result);

    return result;
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
