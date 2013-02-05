package org.bundlemaker.core.jdt.content;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProjectContentProvider extends AbstractProjectContentProvider implements IProjectContentProvider {

  /** - */
  private IBundleMakerProject            _bundleMakerProject;

  /** - */
  private IJavaProject                   _javaProject;

  /** - */
  private int                            _counter                = 0;

  /** - */
  private List<IProjectContentEntry>     _fileBasedContents      = new LinkedList<IProjectContentEntry>();

  /** - */
  private List<IJavaProject>             _resolvedJavaProjects   = new LinkedList<IJavaProject>();

  /** - */
  private List<VariablePath>             _resolvedVariablePathes = new LinkedList<VariablePath>();

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<Key, List<IPath>> _output2SourceLocations = new GenericCache<Key, List<IPath>>() {
                                                                   @Override
                                                                   protected List<IPath> create(Key key) {
                                                                     return new LinkedList<IPath>();
                                                                   }
                                                                 };

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
  public List<IProjectContentEntry> getBundleMakerProjectContent(IProgressMonitor progressMonitor, IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    _bundleMakerProject = bundleMakerProject;

    // create instance of entry helper & clear the 'already resolved' list
    _resolvedJavaProjects.clear();
    _resolvedVariablePathes.clear();
    _output2SourceLocations.clear();
    _fileBasedContents.clear();

    //
    resolveJavaProject(_javaProject);

    //
    return getFileBasedContents();
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
   * @param javaProject
   * @return
   * @throws CoreException
   * @throws JavaModelException
   */
  private void resolveJavaProject(IJavaProject javaProject) throws CoreException, JavaModelException {
    Assert.isNotNull(javaProject);

    System.out.println("resolveJavaProject " + javaProject.getElementName());

    //
    if (_resolvedJavaProjects.contains(javaProject)) {
      return;
    }

    //
    _resolvedJavaProjects.add(javaProject);

    // build the project first
//    if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
//      javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
//    }

    //
    for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
      handleClasspathEntry(classpathEntry, javaProject, AnalyzeMode.BINARIES_AND_SOURCES);
    }

    //
    for (Entry<Key, List<IPath>> entry : _output2SourceLocations.entrySet()) {
      createFileBasedContent(entry.getKey().getIndex(), entry.getKey().getModule(), "1.0.0", AnalyzeMode.BINARIES_AND_SOURCES, new Path(entry
          .getKey()
          .getOutputDirectory()),
          entry.getValue().toArray(new IPath[0]));
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param classpathEntry
   * @throws CoreException
   * @throws JavaModelException
   */
  private void handleClasspathEntry(IClasspathEntry classpathEntry, IJavaProject javaProject, AnalyzeMode analyzeMode)
      throws CoreException, JavaModelException {

    //
    if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
      addLibraryEntry(classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {

      System.out.println(classpathEntry);

      addSourceEntry(classpathEntry, javaProject);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
      if (!classpathEntry.getPath().toString().startsWith("org.eclipse.jdt.launching.JRE_CONTAINER")) {
        IClasspathContainer classpathContainer = JavaCore.getClasspathContainer(classpathEntry.getPath(), javaProject);
        for (IClasspathEntry iClasspathEntry : classpathContainer.getClasspathEntries()) {
          handleClasspathEntry(iClasspathEntry, javaProject, AnalyzeMode.DO_NOT_ANALYZE);
        }
      }
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {

      IProject project = ResourcesPlugin.getWorkspace().getRoot()
          .getProject(classpathEntry.getPath().toPortableString());
      resolveJavaProject(JavaCore.create(project));
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
      System.out.println("CPE_VARIABLE: " + classpathEntry);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   * @throws CoreException
   */
  public void addLibraryEntry(IClasspathEntry classpathEntry) throws CoreException {

    IPath library = classpathEntry.getPath();
    library = makeAbsolute(library);

    IPath librarySource = classpathEntry.getSourceAttachmentPath();
    if (librarySource != null) {
      librarySource = makeAbsolute(librarySource);
    }

    try {
      File file = new File(library.toOSString());
      FileBasedProjectContentInfo<?> info = FileBasedProjectContentInfoService.Factory.getInfoService().extractJarInfo(
          file);

      //
      createFileBasedContent(info.getName(), info.getVersion(), AnalyzeMode.DO_NOT_ANALYZE, library,
          librarySource);

    } catch (Exception e) {

      //
      createFileBasedContent(classpathEntry.getPath().lastSegment(), "0.0.0", AnalyzeMode.DO_NOT_ANALYZE, library,
          librarySource);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   */
  public void addSourceEntry(IClasspathEntry classpathEntry, IJavaProject javaProject) throws CoreException {
    Assert.isNotNull(classpathEntry);
    Assert.isNotNull(javaProject);

    IPath source = classpathEntry.getPath();
    source = makeAbsolute(source);

    IPath classes = classpathEntry.getOutputLocation() != null ? classpathEntry.getOutputLocation() : javaProject
        .getOutputLocation();
    classes = makeAbsolute(classes);

    //
    List<IPath> paths = _output2SourceLocations.getOrCreate(new Key(javaProject.getProject().getName(), classes
        .toPortableString(), _fileBasedContents.size()));

    paths.add(source);
  }

  /**
   * <p>
   * </p>
   * 
   * @return the fileBasedContents
   */
  public List<IProjectContentEntry> getFileBasedContents() {
    return _fileBasedContents;
  }

  private void createFileBasedContent(String contentName, String contentVersion, AnalyzeMode analyzeMode,
      IPath binaryPath,
      IPath... sourcePath) throws CoreException {

    createFileBasedContent(-1, contentName, contentVersion, analyzeMode,
        binaryPath,
        sourcePath);
  }

  /**
   * <p>
   * </p>
   * 
   * @param contentName
   * @param contentVersion
   * @param binaryPath
   * @param sourcePath
   * @return
   * @throws CoreException
   */
  private void createFileBasedContent(int index, String contentName, String contentVersion, AnalyzeMode analyzeMode,
      IPath binaryPath,
      IPath... sourcePath) throws CoreException {

    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPath);
    Assert.isNotNull(analyzeMode);

    VariablePath variablePath = new VariablePath(binaryPath.toOSString());

    if (_resolvedVariablePathes.contains(variablePath)) {
      return;
    }

    _resolvedVariablePathes.add(variablePath);

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

    if (index == -1) {
      _fileBasedContents.add(result);
    }
    //
    else {
      _fileBasedContents.add(index, result);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param path
   * @return
   */
  private IPath makeAbsolute(IPath path) {

    //
    if (path == null) {
      return path;
    }

    if (ResourcesPlugin.getWorkspace().getRoot().findMember(path) != null) {
      IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
      path = resource.getRawLocation();
    }
    return path;
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class Key {

    /** - */
    private String _outputDirectory;

    /** - */
    private String _module;

    /** - */
    private int    _index;

    /**
     * <p>
     * Creates a new instance of type {@link Key}.
     * </p>
     * 
     * @param module
     * @param outputDirectory
     */
    public Key(String module, String outputDirectory, int index) {
      _module = module;
      _outputDirectory = outputDirectory;
      _index = index;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getOutputDirectory() {
      return _outputDirectory;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public String getModule() {
      return _module;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public int getIndex() {
      return _index;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_module == null) ? 0 : _module.hashCode());
      result = prime * result + ((_outputDirectory == null) ? 0 : _outputDirectory.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Key other = (Key) obj;
      if (_module == null) {
        if (other._module != null)
          return false;
      } else if (!_module.equals(other._module))
        return false;
      if (_outputDirectory == null) {
        if (other._outputDirectory != null)
          return false;
      } else if (!_outputDirectory.equals(other._outputDirectory))
        return false;
      return true;
    }
  }
}
