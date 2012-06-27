package org.bundlemaker.core.mvn.content;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.mvn.MvnArtifactConverter;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.mvn.content.xml.MvnContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.CollectResult;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.graph.DependencyVisitor;
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
public class MvnContentProvider extends AbstractContentProvider implements IProjectContentProvider {

  private static final String        SCOPE_COMPILE = "compile";

  /** - */
  private MvnContentType             _mvnContent   = new MvnContentType();

  /** TODO */
  private int                        _counter      = 0;

  /** - */
  private List<IProjectContentEntry> _fileBasedContents;

  /** - */
  private IBundleMakerProject        _bundleMakerProject;

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

    // cast down
    _mvnContent = (MvnContentType) configuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(final IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    Assert.isNotNull(bundleMakerProject);

    // set the IBundleMakerProject
    _bundleMakerProject = bundleMakerProject;

    // if (_fileBasedContents != null && !_fileBasedContents.isEmpty()) {
    // return _fileBasedContents;
    // }

    // create the result list
    _fileBasedContents = new LinkedList<IProjectContentEntry>();

    // iterate over all the specified mvn artifacts
    for (final MvnArtifactType artifactIdentifier : _mvnContent.getArtifacts()) {

      // create the aether artifact
      Artifact artifact = new DefaultArtifact(artifactIdentifier.getGroupId(),
          artifactIdentifier.getArtifactId(), "jar",
          artifactIdentifier.getVersion());

      CollectRequest collectRequest = new CollectRequest();
      collectRequest.setRoot(new Dependency(artifact, SCOPE_COMPILE));
      collectRequest.addRepository(Activator.getDefault().getMvnRepositories().getRemoteRepository());

      //
      try {

        // collect the result
        CollectResult collectResult = Activator.getDefault().getMvnRepositories().getRepositorySystem()
            .collectDependencies(
                Activator.getDefault().getMvnRepositories().getRepositorySystemSession(), collectRequest);

        // visit all the dependencies
        collectResult.getRoot().accept(new DependencyVisitorAdapter() {
          @Override
          public boolean visitEnter(DependencyNode node) {
            return handleDependencyNode(node);
          }
        });

      } catch (DependencyCollectionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    // return the result
    return _fileBasedContents;
  }

  /**
   * <p>
   * </p>
   */
  public void clearArtifactList() {
    _mvnContent.getArtifacts().clear();
    _fileBasedContents.clear();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
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
   */
  protected boolean handleDependencyNode(DependencyNode node) {

    // get the referenced artifact
    Artifact mvnArtifact = node.getDependency().getArtifact();

    //
    ArtifactRequest artifactRequest = new ArtifactRequest();
    artifactRequest.setArtifact(node.getDependency().getArtifact());
    artifactRequest.addRepository(Activator.getDefault().getMvnRepositories().getRemoteRepository());

    try {

      //
      ArtifactResult artifactResult = Activator.getDefault().getMvnRepositories().getRepositorySystem()
          .resolveArtifact(
              Activator.getDefault().getMvnRepositories().getRepositorySystemSession(), artifactRequest);

      File sourceFile = null;
      File binaryFile = null;

      //
      Artifact currentMavenArtifact = artifactResult.getArtifact();

      // TODO
      // if (currentMavenArtifact
      // .getGroupId().startsWith("de.o")) {

      Artifact sourceArtifact = new DefaultArtifact(currentMavenArtifact.getGroupId(),
          currentMavenArtifact.getArtifactId(), "sources", currentMavenArtifact.getExtension(),
          currentMavenArtifact
              .getVersion());
      artifactRequest = new ArtifactRequest();
      artifactRequest.setArtifact(sourceArtifact);
      artifactRequest.addRepository(Activator.getDefault().getMvnRepositories().getRemoteRepository());
      try {
        artifactResult = Activator.getDefault().getMvnRepositories().getRepositorySystem().resolveArtifact(
            Activator.getDefault().getMvnRepositories().getRepositorySystemSession(), artifactRequest);
        sourceFile = artifactResult.getArtifact().getFile();
      } catch (ArtifactResolutionException e) {
        System.out.println(e.getMessage());
      }
      // }

      //
      binaryFile = currentMavenArtifact.getFile();

      // TODO!!!
      try {

        //
        IModuleIdentifier moduleIdentifier = MvnArtifactConverter.toModuleIdentifier(mvnArtifact.getGroupId(),
            mvnArtifact.getArtifactId(), mvnArtifact.getVersion());

        boolean analyze = currentMavenArtifact
            .getGroupId().startsWith("de.o")
            && (currentMavenArtifact.getArtifactId().contains("standard") || currentMavenArtifact
                .getGroupId()
                .contains("standard"));

        //
        FileBasedContent fileBasedContent = createFileBasedContent(moduleIdentifier.getName(),
            moduleIdentifier.getVersion(), binaryFile, sourceFile, _bundleMakerProject, analyze);

        // set user attributes
        fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_GROUP_ID,
            mvnArtifact.getGroupId());
        fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MVN_ARTIFACT_ID,
            mvnArtifact.getArtifactId());
        fileBasedContent.getUserAttributes().put(MvnArtifactConverter.ORIGINAL_MODULE_NAME,
            moduleIdentifier.getName());

      } catch (CoreException e) {
        System.out.println(e.getMessage());
        throw new RuntimeException(e.getMessage(), e);
      }

    } catch (Exception e) {
      // TODO: handle exception
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
  private FileBasedContent createFileBasedContent(String contentName, String contentVersion, File binaryPath,
      File sourcePath,
      IBundleMakerProject bundleMakerProject, boolean analyze) throws CoreException {

    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPath);

    FileBasedContent result = new FileBasedContent(MvnContentProvider.this);
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

    result.addRootPath(new VariablePath(binaryPath.getAbsolutePath()), ContentType.BINARY);

    if (sourcePath != null) {
      result.addRootPath(new VariablePath(sourcePath.getAbsolutePath()), ContentType.SOURCE);
    }

    //
    result.initialize(bundleMakerProject.getProjectDescription());

    //
    _fileBasedContents.add(result);

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   */
  private static class DependencyVisitorAdapter implements DependencyVisitor {

    @Override
    public boolean visitEnter(DependencyNode node) {
      return true;
    }

    @Override
    public boolean visitLeave(DependencyNode node) {
      return true;
    }
  }
}
