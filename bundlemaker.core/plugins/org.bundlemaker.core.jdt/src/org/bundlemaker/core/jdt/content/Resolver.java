package org.bundlemaker.core.jdt.content;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bundlemaker.core.common.collections.GenericCache;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

public class Resolver {

  /** - */
  private Collection<IJavaProject>                     _mainJavaProjects;

  /** - */
  private IJavaProject                     _currentJavaProject;

  /** - */
  private Set<IJavaProject>                _resolvedJavaProjects   = new HashSet<IJavaProject>();

  /** - */
  private Set<ResolvedEntry>              _result                 = new LinkedHashSet<ResolvedEntry>();

  /** - */
  @SuppressWarnings("serial")
  private GenericCache<Key, ResolvedEntry> _output2SourceLocations = new GenericCache<Key, ResolvedEntry>() {
                                                                     @Override
                                                                     protected ResolvedEntry create(Key key) {
                                                                       return new ResolvedEntry(
                                                                           key.getOutputDirectory());
                                                                     }
                                                                   };

  /**
   * @param javaProject
   * @throws CoreException
   * @throws JavaModelException
   */
  public Set<ResolvedEntry> resolve(Collection<IJavaProject> javaProjects) throws CoreException, JavaModelException {

    //
    _resolvedJavaProjects.clear();
    _result.clear();
    _output2SourceLocations.clear();

    //
    _mainJavaProjects = javaProjects;

    
    for (IJavaProject javaProject : javaProjects) {
    	 //
        resolveJavaProject(javaProject);
	}
   

    //
    return _result;
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

    //
    if (_resolvedJavaProjects.contains(javaProject)) {
      return;
    }

    //
    _resolvedJavaProjects.add(javaProject);
    
    if (!javaProject.exists()) {
    	// project does not exists in workspace any longer => ignore 
    	return;
    }
    
    //
    IJavaProject oldCurrentJavaProject = _currentJavaProject; 
    
    //
    _currentJavaProject = javaProject;

    // build the project first
    if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
      javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
    }

    //
    for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
      handleClasspathEntry(classpathEntry, javaProject);
    }
    
    //
    _currentJavaProject = oldCurrentJavaProject;
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
  private void handleClasspathEntry(IClasspathEntry classpathEntry, IJavaProject javaProject) throws CoreException,
      JavaModelException {

    //
    if (!isVisible(classpathEntry)) {
      return;
    }
    
    //
    if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
        addLibraryEntry(classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
      addSourceEntry(classpathEntry, javaProject);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
      if (!classpathEntry.getPath().toString().startsWith("org.eclipse.jdt.launching.JRE_CONTAINER")) {

        //
        IClasspathContainer classpathContainer = JavaCore.getClasspathContainer(classpathEntry.getPath(), javaProject);

        //
        for (IClasspathEntry iClasspathEntry : classpathContainer.getClasspathEntries()) {
          handleClasspathEntry(iClasspathEntry, javaProject);
        }
      }
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {

      //
      IProject project = ResourcesPlugin.getWorkspace().getRoot()
          .getProject(classpathEntry.getPath().toPortableString());

      //
      resolveJavaProject(JavaCore.create(project));
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {

      //
      System.out.println("CPE_VARIABLE: " + classpathEntry);

    }
  }

  private boolean isVisible(IClasspathEntry classpathEntry) {
	  if (classpathEntry.isExported() || classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
		  return true;
	  }
	  
	  return _mainJavaProjects.contains(_currentJavaProject);
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   * @throws CoreException
   */
  private void addLibraryEntry(IClasspathEntry classpathEntry) throws CoreException {

    IPath library = classpathEntry.getPath();
    library = makeAbsolute(library);
    ResolvedEntry entry = new ResolvedEntry(library);
    _result.add(entry);

    IPath librarySource = classpathEntry.getSourceAttachmentPath();
    if (librarySource != null) {
      librarySource = makeAbsolute(librarySource);
      entry.getSources().add(librarySource);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   */
  private void addSourceEntry(IClasspathEntry classpathEntry, IJavaProject javaProject) throws CoreException {
    Assert.isNotNull(classpathEntry);
    Assert.isNotNull(javaProject);

    IPath source = classpathEntry.getPath();
    source = makeAbsolute(source);

    IPath classes = classpathEntry.getOutputLocation() != null ? classpathEntry.getOutputLocation() : javaProject
        .getOutputLocation();
    classes = makeAbsolute(classes);

    //
    ResolvedEntry resolvedEntry = _output2SourceLocations.getOrCreate(new Key(javaProject.getProject().getName(),
        classes));

    if (resolvedEntry.getSources().isEmpty()) {

      // resolvedEntry.setAnalyze(javaProject.equals(_mainJavaProject));
      resolvedEntry.setAnalyze(true);
      resolvedEntry.setProjectName(String.format("%s <%s>", javaProject.getElementName(), classes.lastSegment()));
      _result.add(resolvedEntry);
    }

    //
    resolvedEntry.getSources().add(source);
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
	 */
  public static class Key {

    /** - */
    private IPath  _outputDirectory;

    /** - */
    private String _module;

    /**
     * <p>
     * Creates a new instance of type {@link Key}.
     * </p>
     * 
     * @param module
     * @param outputDirectory
     */
    public Key(String module, IPath outputDirectory) {
      _module = module;
      _outputDirectory = outputDirectory;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public IPath getOutputDirectory() {
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

    @Override
    public String toString() {
      return "Key [_outputDirectory=" + _outputDirectory + ", _module=" + _module + "]";
    }
  }
}
