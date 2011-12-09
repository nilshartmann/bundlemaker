package org.bundlemaker.core.itest.core;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IFileBasedContentProvider;
import org.bundlemaker.core.projectdescription.modifiable.FileBasedContent;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JDTFileBaseContentProvider implements IFileBasedContentProvider {

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
   */
  @Override
  public List<IFileBasedContent> getFileBaseContent() {

    //
    List<IFileBasedContent> fileBasedContents = new LinkedList<IFileBasedContent>();

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

          FileBasedContent basedContent = new FileBasedContent();
          basedContent.setSourcePaths(new String[] { source.toOSString() });
          basedContent.setBinaryPaths(new String[] { classes.toOSString() });
          basedContent.setId(_id + "#" + counter);
          fileBasedContents.add(basedContent);

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
