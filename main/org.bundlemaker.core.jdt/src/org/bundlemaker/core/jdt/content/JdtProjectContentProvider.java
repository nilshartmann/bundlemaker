package org.bundlemaker.core.jdt.content;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.content.xml.JdtProjectContentType;
import org.bundlemaker.core.projectdescription.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContent;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfo;
import org.bundlemaker.core.projectdescription.file.FileBasedProjectContentInfoService;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProjectContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  /** - */
  private IJavaProject               _javaProject;

  /** - */
  private int                        _counter           = 0;

  /** - */
  private List<IProjectContentEntry> _fileBasedContents = new LinkedList<IProjectContentEntry>();

  /** - */
  private IBundleMakerProject        _bundleMakerProject;

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getConfiguration() {

    //
    JdtProjectContentType result = new JdtProjectContentType();
    result.setProject(_javaProject.getElementName());

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setConfiguration(Object configuration) {

    //
    Assert.isNotNull(configuration);
    Assert.isTrue(configuration instanceof JdtProjectContentType);

    // cast down
    JdtProjectContentType config = (JdtProjectContentType) configuration;

    //
    String projectName = config.getProject();

    //
    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
    _javaProject = JavaCore.create(project);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(IProgressMonitor progressMonitor,
      IBundleMakerProject bundleMakerProject) throws CoreException {
    
    //
    _bundleMakerProject = bundleMakerProject;

    // create instance of entry helper & clear the 'already resolved' list
    _fileBasedContents.clear();

    //
    Resolver resolver = new Resolver();
    List<ResolvedEntry> resolvedEntries = resolver.resolve(_javaProject);

    //
    for (ResolvedEntry resolvedEntry : resolvedEntries) {

      //
      String name = "<none>";
      String version = "0.0.0";

      //
      if (resolvedEntry.hasProjectName()) {

        //
        name = resolvedEntry.getProjectName();

      } else {

        //
        FileBasedProjectContentInfo<?> info = FileBasedProjectContentInfoService.Factory.getInfoService()
            .extractJarInfo(resolvedEntry.getBinaryPath().toFile());

        //
        name = info.getName();
        version = info.getVersion();
      }

      //
      AnalyzeMode mode = resolvedEntry.isAnalyze() ? resolvedEntry.getSources().isEmpty() ? AnalyzeMode.BINARIES_ONLY
          : AnalyzeMode.BINARIES_AND_SOURCES : AnalyzeMode.DO_NOT_ANALYZE;

      //
      createFileBasedContent(name, version, mode, resolvedEntry.getBinaryPath(), resolvedEntry.getSources());
    }

    //
    return _fileBasedContents;
  }

  /**
   * <p>
   * </p>
   * 
   * @param javaProject
   */
  public void setJavaProject(IJavaProject javaProject) {
    _javaProject = javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IJavaProject getJavaProject() {
    return _javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentName
   * @param contentVersion
   * @param analyzeMode
   * @param binaryPath
   * @param sourcePath
   * @throws CoreException
   */
  private void createFileBasedContent(String contentName, String contentVersion, AnalyzeMode analyzeMode,
      IPath binaryPath, List<IPath> sourcePath) throws CoreException {

    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPath);
    Assert.isNotNull(analyzeMode);

    FileBasedProjectContent result = new FileBasedProjectContent(this);
    result.setId(this.getId() + _counter++);
    result.setName(contentName);
    result.setVersion(contentVersion);

    result.setAnalyzeMode(analyzeMode);

    result.addRootPath(new VariablePath(binaryPath.toOSString()), ProjectContentType.BINARY);

    if (sourcePath != null) {
      for (IPath iPath : sourcePath) {
        result.addRootPath(new VariablePath(iPath.toOSString()), ProjectContentType.SOURCE);
      }
    }

    //
    result.initialize(_bundleMakerProject.getProjectDescription());

    //
    _fileBasedContents.add(result);
  }
}
