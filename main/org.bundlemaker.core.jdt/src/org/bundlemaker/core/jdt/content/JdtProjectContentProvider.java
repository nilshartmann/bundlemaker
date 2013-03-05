package org.bundlemaker.core.jdt.content;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.ProjectContentType;
import org.bundlemaker.core.projectdescription.VariablePath;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.spi.FileBasedProjectContentInfo;
import org.bundlemaker.core.projectdescription.spi.FileBasedProjectContentInfoService;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectContentEntry;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProjectContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  @Expose
  @SerializedName("javaProjectName")
  private String                     _javaProjectName;

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
  protected void init(IProjectDescription description) {

    //
    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(_javaProjectName);
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
    _javaProjectName = _javaProject.getElementName();
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

    IModifiableProjectContentEntry result = createNewContentEntry();
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
