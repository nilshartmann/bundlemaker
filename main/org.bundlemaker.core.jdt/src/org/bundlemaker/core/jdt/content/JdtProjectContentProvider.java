package org.bundlemaker.core.jdt.content;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.content.xml.JdtProjectContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
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
public class JdtProjectContentProvider extends AbstractContentProvider implements IProjectContentProvider {

  /** - */
  private IJavaProject _javaProject;

  /** - */
  private EntryHelper  _entryHelper;

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
   * {@inheritDoc}
   * 
   * @throws CoreException
   */
  @Override
  public List<IProjectContentEntry> getBundleMakerProjectContent(IBundleMakerProject bundleMakerProject)
      throws CoreException {

    // build the project first
    if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
      _javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
    }

    // create instance of entry helper
    _entryHelper = new EntryHelper(bundleMakerProject.getProjectDescription(), this, _javaProject);

    //
    for (IClasspathEntry classpathEntry : _javaProject.getRawClasspath()) {
      handleClasspathEntry(classpathEntry);
    }

    //
    return _entryHelper.getFileBasedContents();
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
  private void handleClasspathEntry(IClasspathEntry classpathEntry) throws CoreException, JavaModelException {
    //
    if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
      _entryHelper.addLibraryEntry(classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
      _entryHelper.addSourceEntry(classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {

      if (!classpathEntry.getPath().toString().startsWith("org.eclipse.jdt.launching.JRE_CONTAINER")) {

        IClasspathContainer classpathContainer = JavaCore.getClasspathContainer(classpathEntry.getPath(), _javaProject);

        for (IClasspathEntry iClasspathEntry : classpathContainer.getClasspathEntries()) {
          handleClasspathEntry(iClasspathEntry);
        }
      }

    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
      System.out.println("CPE_PROJECT: " + classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
      System.out.println("CPE_VARIABLE: " + classpathEntry);
    }
  }

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
}
