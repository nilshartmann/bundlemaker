package org.bundlemaker.core.itest.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.FileBasedContentFactory;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.FileASTRequestor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JDTFileBaseContentProvider implements IBundleMakerProjectContentProvider {

  /** - */
  private IJavaProject _javaProject;

  /** - */
  private String       _id;

  /**
   * <p>
   * Creates a new instance of type {@link JDTFileBaseContentProvider}.
   * </p>
   * 
   * @param javaProject
   */
  public JDTFileBaseContentProvider(IJavaProject javaProject) {
    Assert.assertNotNull(javaProject);

    _javaProject = javaProject;
  }

  @Override
  public String getId() {
    return _id;
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   */
  public void setId(String id) {
    _id = id;
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

    //
    try {
      int counter = 0;

      IClasspathEntry[] entries = _javaProject.getRawClasspath();

      for (IClasspathEntry classpathEntry : entries) {

        //
        if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
          System.out.println("CPE_LIBRARY: " + classpathEntry);
        } else if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
          IPath source = classpathEntry.getPath();
          IPath classes = classpathEntry.getOutputLocation() != null ? classpathEntry.getOutputLocation()
              : _javaProject.getOutputLocation();

          FileBasedContentProvider contentProvider = FileBasedContentFactory.addContent("name", "1.2.3", _javaProject
              .getProject().getLocation().append(classes.removeFirstSegments(1)).toOSString(), _javaProject
              .getProject().getLocation().append(source.removeFirstSegments(1)).toOSString());

          contentProvider.getFileBasedContent().initialize(bundleMakerProject.getProjectDescription());
          
          fileBasedContents.add(contentProvider.getFileBasedContent());

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
}
