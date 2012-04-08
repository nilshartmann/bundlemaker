package org.bundlemaker.core.jdt.content;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;

public class EntryHelper {

  /** - */
  IProjectDescription                _projectDescription;

  /** - */
  private IProjectContentProvider    _provider;

  /** - */
  private IJavaProject               _javaProject;

  /** - */
  private int                        _counter = 0;

  /** - */
  private List<IProjectContentEntry> _fileBasedContents;

  /**
   * <p>
   * Creates a new instance of type {@link EntryHelper}.
   * </p>
   * 
   * @param provider
   * @param javaProject
   */
  public EntryHelper(IProjectDescription projectDescription, IProjectContentProvider provider, IJavaProject javaProject) {
    Assert.isNotNull(projectDescription);
    Assert.isNotNull(provider);
    Assert.isNotNull(javaProject);

    //
    _projectDescription = projectDescription;
    _provider = provider;
    _javaProject = javaProject;

    //
    _fileBasedContents = new LinkedList<IProjectContentEntry>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   * @throws CoreException
   */
  public void addLibraryEntry(IClasspathEntry classpathEntry) throws CoreException {

    IPath classes = classpathEntry.getPath();
    classes = makeAbsolute(classes);

    // IPath source = classpathEntry.getSourceAttachmentPath();
    // source = makeAbsolute(source);

    // TODO
    createFileBasedContent(classpathEntry.getPath().lastSegment(), "1.2.3", classes, null);
  }

  /**
   * <p>
   * </p>
   * 
   * @param classpathEntry
   */
  public void addSourceEntry(IClasspathEntry classpathEntry) throws CoreException {

    IPath source = classpathEntry.getPath();
    source = makeAbsolute(source);

    IPath classes = classpathEntry.getOutputLocation() != null ? classpathEntry.getOutputLocation() : _javaProject
        .getOutputLocation();
    classes = makeAbsolute(classes);

    createFileBasedContent(_javaProject.getProject().getName(), "1.0.0", classes, source);
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
  private void createFileBasedContent(String contentName, String contentVersion, IPath binaryPath, IPath sourcePath)
      throws CoreException {

    Assert.isNotNull(contentName);
    Assert.isNotNull(contentVersion);
    Assert.isNotNull(binaryPath);

    System.out.println("binaryPath: " + binaryPath);
    System.out.println("sourcePath: " + sourcePath);

    FileBasedContent result = new FileBasedContent(_provider);
    result.setId(_provider.getId() + _counter++);
    result.setName(contentName);
    result.setVersion(contentVersion);

    result.addRootPath(new VariablePath(binaryPath.toOSString()), ContentType.BINARY);

    if (sourcePath != null) {
      result.addRootPath(new VariablePath(sourcePath.toOSString()), ContentType.SOURCE);
    }

    //
    result.initialize(_projectDescription);

    //
    _fileBasedContents.add(result);
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
}
