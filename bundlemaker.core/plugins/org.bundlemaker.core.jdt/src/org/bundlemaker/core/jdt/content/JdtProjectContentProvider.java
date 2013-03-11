package org.bundlemaker.core.jdt.content;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.spi.AbstractProjectContentProvider;
import org.bundlemaker.core.projectdescription.spi.FileBasedProjectContentInfo;
import org.bundlemaker.core.projectdescription.spi.FileBasedProjectContentInfoService;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
  @SerializedName("java-project-name")
  private String       _javaProjectName;

  /** the java project */
  private IJavaProject _javaProject;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void init(IProjectDescription description) {

    //
    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(_javaProjectName);

    //
    _javaProject = JavaCore.create(project);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public void onGetBundleMakerProjectContent(IProgressMonitor progressMonitor) throws CoreException {

    // create instance of entry helper & clear the 'already resolved' list
    clearFileBasedContents();

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
      File[] binaryPaths = new File[] { resolvedEntry.getBinaryPath().toFile() };

      //
      List<File> sourceFiles = new ArrayList<File>();
      for (IPath path : resolvedEntry.getSources()) {
        sourceFiles.add(path.toFile());
      }
      File[] sourcePaths = sourceFiles.toArray(new File[0]);

      //
      createFileBasedContent(name, version, binaryPaths, sourcePaths, mode);
    }
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
}
