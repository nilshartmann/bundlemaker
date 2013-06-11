package org.bundlemaker.core.jdt.content;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.common.utils.IFileBasedProjectContentInfo;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectDescription;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
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

  /** the name of this entry */
  @Expose
  @SerializedName("name")
  private String                   _name;

  @Expose
  @SerializedName("java-project-names")
  private String                   _javaProjectNames;

  /** the java project */
  private Collection<IJavaProject> _javaProjects;

  /**
   * {@inheritDoc}
   */
  @Override
  protected void init(IProjectDescription description) {

    String[] projectNames = _javaProjectNames.split(",");
    _javaProjects = new LinkedList<IJavaProject>();

    for (String projectName : projectNames) {
      //
      IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

      //
      IJavaProject javaProject = JavaCore.create(project);
      _javaProjects.add(javaProject);
    }
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
    Set<ResolvedEntry> resolvedEntries = resolver.resolve(_javaProjects);

    List<ResolvedEntry> orderedEntries = new LinkedList<ResolvedEntry>(resolvedEntries);

    // Make sure selected JDT projects stay first in the list
    Collections.sort(orderedEntries, new ResolvedEntryComparator());

    //
    for (ResolvedEntry resolvedEntry : orderedEntries) {

      //
      String name = "<none>";
      String version = "0.0.0";

      //
      if (resolvedEntry.hasProjectName()) {

        //
        name = resolvedEntry.getProjectName();

      } else {

        //
        IFileBasedProjectContentInfo info = IFileBasedProjectContentInfo.Factory
            .extractFileBasedProjectContentInfo(resolvedEntry.getBinaryPath().toFile());

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
  public boolean addJavaProject(IJavaProject javaProject) {
    if (_javaProjects == null) {
      _javaProjects = new LinkedList<IJavaProject>();
    }

    if (_javaProjects.contains(javaProject)) {
      return false;
    }

    // add
    _javaProjects.add(javaProject);

    StringBuilder b = new StringBuilder();

    for (IJavaProject project : _javaProjects) {
      b.append(project.getElementName()).append(',');
    }

    if (b.length() > 0) {
      b.setLength(b.length() - 1);
    }

    _javaProjectNames = b.toString();

    return true;
  }

  public String getName() {
    if (_name == null) {
      if (_javaProjects == null || _javaProjects.isEmpty()) {
        return "unknown";
      }

      _name = _javaProjects.iterator().next().getElementName();
    }
    return _name;
  }

  public Collection<IJavaProject> getJavaProjects() {
    return _javaProjects;
  }

  protected IJavaProject getJavaProject(String name) {
    Collection<IJavaProject> javaProjects = getJavaProjects();
    for (IJavaProject iJavaProject : javaProjects) {
      if (name.equals(iJavaProject.getElementName())) {
        return iJavaProject;
      }
    }
    return null;
  }

  public IJavaProject getSourceJavaProject(IProjectContentEntry projectContent, String rootPath) throws CoreException {

    IPath resolvedPath = new Path(rootPath);
    IResource resource = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(resolvedPath);
    IProject project = resource.getProject();

    IJavaProject result = JavaCore.create(project);
    return result;
  }

  public void setName(String newName) {
    _name = newName;

    fireProjectDescriptionChangedEvent();
  }

  protected class ResolvedEntryComparator implements Comparator<ResolvedEntry> {

    ResolvedEntryComparator() {

    }

    @Override
    public int compare(ResolvedEntry o1, ResolvedEntry o2) {

      String compareKey1 = (!o1.hasProjectName()) + "_" + o1.getBinaryPath();
      String compareKey2 = (!o2.hasProjectName()) + "_" + o2.getBinaryPath();

      return compareKey1.compareTo(compareKey2);

    }

  }

}
