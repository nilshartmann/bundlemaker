package org.bundlemaker.core.jdt.content;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.content.xml.JdtProjectContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
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
  private IJavaProject       _javaProject;

  /** - */
  private EntryHelper        _entryHelper;

  /** - */
  private List<IJavaProject> _alreadyResolved = new LinkedList<IJavaProject>();

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

    // create instance of entry helper
    _entryHelper = new EntryHelper(bundleMakerProject.getProjectDescription(), this);

    //
    resolveJavaProject(_javaProject);

    //
    return _entryHelper.getFileBasedContents();
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
    if (_alreadyResolved.contains(javaProject)) {
      return;
    }

    //
    _alreadyResolved.add(javaProject);

    // build the project first
    if (!ResourcesPlugin.getWorkspace().isAutoBuilding()) {
      javaProject.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, null);
    }

    //
    for (IClasspathEntry classpathEntry : javaProject.getRawClasspath()) {
      handleClasspathEntry(classpathEntry, javaProject, AnalyzeMode.BINARIES_AND_SOURCES);
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
      _entryHelper.addLibraryEntry(classpathEntry);
    }

    //
    else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
      _entryHelper.addSourceEntry(classpathEntry, javaProject);
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

}
