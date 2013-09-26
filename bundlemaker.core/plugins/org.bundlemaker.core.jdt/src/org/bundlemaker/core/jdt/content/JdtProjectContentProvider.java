package org.bundlemaker.core.jdt.content;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.common.utils.IFileBasedProjectContentInfo;
import org.bundlemaker.core.jdt.internal.Activator;
import org.bundlemaker.core.project.AnalyzeMode;
import org.bundlemaker.core.project.BundleMakerProjectContentChangedEvent;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectContentProvider;
import org.bundlemaker.core.project.IProjectContentResource;
import org.bundlemaker.core.project.VariablePath;
import org.bundlemaker.core.spi.project.AbstractProjectContentProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
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
  protected void prepare() {

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
  public void onInitializeProjectContent(IProgressMonitor progressMonitor) throws CoreException {

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
      IProjectContentEntry projectContentEntry = createFileBasedContent(name, version, binaryPaths, sourcePaths, mode);
      // TODO: CACHEN!!
      // for (IProjectContentResource resource :
      // projectContentEntry.getBinaryResources()) {
      // System.out.println("Root: " + resource.getRoot());
      // System.out.println("Path: " + resource.getPath());
      // }
      for (IJavaProject javaProject : _javaProjects) {
        Activator.getInstance().getProject2ProviderMap().getOrCreate(javaProject.getProject()).add(this);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param eclipseResource
   */
  public void eclipseResourceChanged(IResource eclipseResource) {

    //
    ContentEntryAndPath contentEntryAndPath = getContentEntryAndPath(eclipseResource);

    //
    IProjectContentResource contentResource = getProjectContentResource(eclipseResource, contentEntryAndPath);

    handleResourceModified(contentResource);
  }

  /**
   * <p>
   * </p>
   * 
   * @param eclipseResource
   */
  public void eclipseResourceRemoved(IResource eclipseResource) {

    //
    ContentEntryAndPath contentEntryAndPath = getContentEntryAndPath(eclipseResource);
    IProjectContentResource contentResource = getProjectContentResource(eclipseResource, contentEntryAndPath);

    //
    handleResourceRemoved(contentEntryAndPath.getContentEntry(), contentResource,
        contentEntryAndPath.getResourceType());
  }

  /**
   * <p>
   * </p>
   * 
   * @param eclipseResource
   */
  public void eclipseResourceAdded(IResource eclipseResource) {

    //
    ContentEntryAndPath contentEntryAndPath = getContentEntryAndPath(eclipseResource);

    try {
      handleResourceAdded(contentEntryAndPath.getContentEntry(),
          contentEntryAndPath.getVariablePath().getResolvedPath(),
          eclipseResource.getRawLocation().makeRelativeTo(contentEntryAndPath.getVariablePath().getResolvedPath()),
          ResourceType.BINARY);
    } catch (CoreException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param eclipseResource
   * @return
   */
  private IProjectContentResource getProjectContentResource(IResource eclipseResource,
      ContentEntryAndPath contentEntryAndPath) {

    Assert.isNotNull(eclipseResource);
    Assert.isNotNull(contentEntryAndPath);

    try {

      //
      IProjectContentEntry contentEntry = contentEntryAndPath.getContentEntry();
      IPath resolvedVariablePath = contentEntryAndPath.getVariablePath().getResolvedPath();
      ResourceType resourceType = contentEntryAndPath.getResourceType();

      //
      IPath resourcePath = eclipseResource.getRawLocation().makeRelativeTo(resolvedVariablePath);
      String resourcePathAsString = resourcePath.toString();

      //
      IProjectContentResource result = contentEntry.getResource(resourcePathAsString, resourceType);
      Assert.isNotNull(result, contentEntryAndPath.toString());
      return result;

    } catch (CoreException e) {
      //
    }
    throw new RuntimeException();
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

  private ContentEntryAndPath getContentEntryAndPath(IResource eclipseResource) {

    ContentEntryAndPath contentEntryAndPath = getContentEntryAndPath(eclipseResource, ResourceType.BINARY);
    if (contentEntryAndPath == null) {
      contentEntryAndPath = getContentEntryAndPath(eclipseResource, ResourceType.SOURCE);
    }

    Assert.isNotNull(eclipseResource);

    return contentEntryAndPath;
  }

  /**
   * <p>
   * </p>
   * 
   * @param eclipseResource
   * @return
   */
  private ContentEntryAndPath getContentEntryAndPath(IResource eclipseResource, ResourceType resourceType) {

    Assert.isNotNull(eclipseResource);
    Assert.isNotNull(resourceType);

    try {
      //
      for (IProjectContentEntry contentEntry : getBundleMakerProjectContent()) {

        //
        Set<VariablePath> paths = resourceType == ResourceType.BINARY ? contentEntry.getBinaryRootPaths()
            : contentEntry.getSourceRootPaths();

        // binary
        for (VariablePath root : paths) {

          //
          IPath rootPath = root.getResolvedPath();
          IPath eclipseRawLocation = eclipseResource.getRawLocation();

          //
          if (rootPath.isPrefixOf(eclipseRawLocation)) {
            return new ContentEntryAndPath(contentEntry, root, resourceType);
          }
        }
      }
    } catch (Exception e) {
    }

    //
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public class ContentEntryAndPath {

    /** - */
    private IProjectContentEntry _contentEntry;

    /** - */
    private VariablePath         _variablePath;

    /** - */
    private ResourceType         _resourceType;

    /**
     * <p>
     * Creates a new instance of type {@link ContentEntryAndPath}.
     * </p>
     * 
     * @param contentEntry
     * @param variablePath
     */
    public ContentEntryAndPath(IProjectContentEntry contentEntry, VariablePath variablePath, ResourceType resourceType) {
      Assert.isNotNull(contentEntry);
      Assert.isNotNull(variablePath);
      Assert.isNotNull(resourceType);

      _contentEntry = contentEntry;
      _variablePath = variablePath;
      _resourceType = resourceType;
    }

    public IProjectContentEntry getContentEntry() {
      return _contentEntry;
    }

    public VariablePath getVariablePath() {
      return _variablePath;
    }

    public ResourceType getResourceType() {
      return _resourceType;
    }

    @Override
    public String toString() {
      return getClass().getSimpleName() + " [_contentEntry=" + _contentEntry + ", _variablePath=" + _variablePath
          + ", _resourceType=" + _resourceType + "]";
    }
  }
}
