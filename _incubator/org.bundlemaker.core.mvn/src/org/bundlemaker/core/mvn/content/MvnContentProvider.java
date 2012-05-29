package org.bundlemaker.core.mvn.content;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.mvn.content.xml.MvnArtifactType;
import org.bundlemaker.core.mvn.content.xml.MvnContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.eclipse.aether.examples.util.Booter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.sonatype.aether.RepositorySystem;
import org.sonatype.aether.RepositorySystemSession;
import org.sonatype.aether.artifact.Artifact;
import org.sonatype.aether.collection.CollectRequest;
import org.sonatype.aether.collection.CollectResult;
import org.sonatype.aether.collection.DependencyCollectionException;
import org.sonatype.aether.graph.Dependency;
import org.sonatype.aether.graph.DependencyNode;
import org.sonatype.aether.graph.DependencyVisitor;
import org.sonatype.aether.repository.RemoteRepository;
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

  private MvnContentType             _mvnContent = new MvnContentType();

  /** - */
  private int                        _counter    = 0;

  /** - */
  private List<IProjectContentEntry> _fileBasedContents;

  /**
   * <p>
   * </p>
   * 
   * @param groupId
   * @param artifactId
   * @param version
   */
  public void addMvnArtifact(String groupId, String artifactId, String version) {

    //
    MvnArtifactType mvnArtifactType = new MvnArtifactType();
    mvnArtifactType.setGroupId(groupId);
    mvnArtifactType.setArtifactId(artifactId);
    mvnArtifactType.setVersion(version);

    //
    _mvnContent.getArtifacts().add(mvnArtifactType);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(final IBundleMakerProject bundleMakerProject)
      throws CoreException {

    _fileBasedContents = new LinkedList<IProjectContentEntry>();

    final RepositorySystem system = Booter.newRepositorySystem();
    final RepositorySystemSession session = Booter.newRepositorySystemSession(system);
    final RemoteRepository repo = Booter.newCentralRepository();

    for (MvnArtifactType artifactType : _mvnContent.getArtifacts()) {

      Artifact artifact = new DefaultArtifact(artifactType.getGroupId(), artifactType.getArtifactId(), "jar",
          artifactType.getVersion());
      CollectRequest collectRequest = new CollectRequest();
      collectRequest.setRoot(new Dependency(artifact, "compile"));
      collectRequest.addRepository(repo);

      try {

        //
        CollectResult collectResult = system.collectDependencies(session, collectRequest);

        //
        collectResult.getRoot().accept(new DependencyVisitor() {

          @Override
          public boolean visitLeave(DependencyNode node) {
            return true;
          }

          @Override
          public boolean visitEnter(DependencyNode node) {

            //
            ArtifactRequest artifactRequest = new ArtifactRequest();
            artifactRequest.setArtifact(node.getDependency().getArtifact());
            artifactRequest.addRepository(repo);

            try {

              //
              ArtifactResult artifactResult = system.resolveArtifact(session, artifactRequest);

              File sourceFile = null;
              File binaryFile = null;

              //
              Artifact currentArtifact = artifactResult.getArtifact();

              //
              Artifact sourceArtifact = new DefaultArtifact(currentArtifact.getGroupId(),
                  currentArtifact.getArtifactId(), "sources", currentArtifact.getExtension(), currentArtifact
                      .getVersion());

              artifactRequest = new ArtifactRequest();
              artifactRequest.setArtifact(sourceArtifact);
              artifactRequest.addRepository(repo);

              try {
                artifactResult = system.resolveArtifact(session, artifactRequest);
                sourceFile = artifactResult.getArtifact().getFile();
              } catch (ArtifactResolutionException e) {
                System.out.println(e.getMessage());
              }

              binaryFile = currentArtifact.getFile();

              try {
                createFileBasedContent(currentArtifact.getGroupId() + "." + currentArtifact.getArtifactId(),
                    currentArtifact.getVersion(), binaryFile, sourceFile, bundleMakerProject, currentArtifact
                        .getGroupId().startsWith("de.o"));
              } catch (CoreException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e.getMessage(), e);
              }

              //
              // System.out.println(artifactResult.getArtifact().getFile().getAbsolutePath());

            } catch (Exception e) {
              // TODO: handle exception
            }
            return true;
          }
        });

        //

        // collectResult.getRoot().accept(new DependencyVisitor() {
        //
        // @Override
        // public boolean visitLeave(DependencyNode node) {
        // return true;
        // }
        //
        // @Override
        // public boolean visitEnter(DependencyNode node) {
        //
        // File sourceFile = null;
        // File binaryFile = null;
        //
        // //
        // Artifact currentArtifact = node.getDependency().getArtifact();
        // //
        // Artifact sourceArtifact = new DefaultArtifact(currentArtifact.getGroupId(),
        // currentArtifact.getArtifactId(), "sources", currentArtifact.getExtension(), currentArtifact
        // .getVersion());
        // ArtifactRequest artifactRequest = new ArtifactRequest();
        // artifactRequest.setArtifact(sourceArtifact);
        // artifactRequest.addRepository(repo);
        //
        // try {
        // ArtifactResult artifactResult;
        // artifactResult = system.resolveArtifact(session, artifactRequest);
        // sourceFile = artifactResult.getArtifact().getFile();
        // } catch (ArtifactResolutionException e) {
        // System.out.println(e.getMessage());
        // }
        //
        // binaryFile = currentArtifact.getFile();
        //
        // try {
        // createFileBasedContent(currentArtifact.getGroupId() + "." + currentArtifact.getArtifactId(),
        // currentArtifact.getVersion(), binaryFile, sourceFile, bundleMakerProject, currentArtifact
        // .getGroupId().startsWith("de.o"));
        // } catch (CoreException e) {
        // System.out.println(e.getMessage());
        // throw new RuntimeException(e.getMessage(), e);
        // }
        //
        // return true;
        // }
        // });
        //
      }
      // catch (DependencyResolutionException e) {
      // System.out.println(e.getMessage());
      // throw new RuntimeException(e.getMessage(), e);
      // }

      catch (DependencyCollectionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    return _fileBasedContents;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getConfiguration() {
    //
    return _mvnContent;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(Object configuration) {

    //
    Assert.isNotNull(configuration);
    System.out.println(configuration.getClass());
    Assert.isTrue(configuration instanceof MvnContentType);

    // cast down
    _mvnContent = (MvnContentType) configuration;
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
  private void createFileBasedContent(String contentName, String contentVersion, File binaryPath, File sourcePath,
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
  }
}
