package org.bundlemaker.core.content.jdt;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.content.jdt.xml.JdtProjectContentType;
import org.bundlemaker.core.projectdescription.AbstractContentProvider;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
public class JdtProjectContentProvider extends AbstractContentProvider implements IBundleMakerProjectContentProvider {

  /** - */
  private IJavaProject _javaProject;

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
  public List<IBundleMakerProjectContent> getBundleMakerProjectContent(IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    List<IBundleMakerProjectContent> fileBasedContents = new LinkedList<IBundleMakerProjectContent>();

    int counter = 0;

    //
    try {
      IClasspathEntry[] entries = _javaProject.getRawClasspath();

      for (IClasspathEntry classpathEntry : entries) {

        //
        if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
          System.out.println("CPE_LIBRARY: " + classpathEntry);
        } else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {

          IPath source = classpathEntry.getPath();

          IPath classes = classpathEntry.getOutputLocation() != null ? classpathEntry.getOutputLocation()
              : _javaProject.getOutputLocation();

          FileBasedContent fbpContent = new FileBasedContent(this);
          fbpContent.setId(getId() + counter++);
          fbpContent.setName("name");
          fbpContent.setVersion("1.2.3");

          fbpContent.addRootPath(
              new VariablePath(_javaProject.getProject().getLocation().append(classes.removeFirstSegments(1))
                  .toOSString()), ContentType.BINARY);

          fbpContent.addRootPath(
              new VariablePath(_javaProject.getProject().getLocation().append(source.removeFirstSegments(1))
                  .toOSString()), ContentType.SOURCE);

          fbpContent.initialize(bundleMakerProject.getProjectDescription());

          fileBasedContents.add(fbpContent);

        } else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_CONTAINER) {
          System.out.println("CPE_CONTAINER: " + classpathEntry);
        } else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_PROJECT) {
          System.out.println("CPE_PROJECT: " + classpathEntry);
        } else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
          System.out.println("CPE_VARIABLE: " + classpathEntry);
        }

        // FileBasedContent fileBasedContent = new FileBasedContent();
        // counter++;
      }

    } catch (JavaModelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    //
    return fileBasedContents;
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
