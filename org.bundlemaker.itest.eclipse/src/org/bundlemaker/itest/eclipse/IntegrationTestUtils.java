package org.bundlemaker.itest.eclipse;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Mkdir;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.eclipse.core.runtime.CoreException;

public class IntegrationTestUtils {

  /**
   * <p>
   * </p>
   */
  public static IBundleMakerProjectDescription createProjectDescription(
      IBundleMakerProjectDescription projectDescription) throws CoreException {

    // step 1:
    projectDescription.clear();

    // step 2: add the JRE
    projectDescription.setJre("jdk16");

    // step 3: add the source and classes
    File sourceDirectory = new File(System.getProperty("user.dir"), "eclipse/source");

    File classesDir = new File(System.getProperty("user.dir"), "eclipse/classes");
    File[] jarFiles = classesDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    List<String> classes = new LinkedList<String>();
    for (File file : jarFiles) {
      classes.add(file.getAbsolutePath());
    }
    projectDescription.addResourceContent("eclipse", "3.6.1", classes,
        Arrays.asList(new String[] { sourceDirectory.getAbsolutePath() }));

    // step 4: process the class path entries
    File libsDir = new File(System.getProperty("user.dir"), "eclipse/libs");
    jarFiles = libsDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    for (File externalJar : jarFiles) {
      projectDescription.addResourceContent(externalJar.getAbsolutePath());
    }

    // return the result
    return projectDescription;
  }

  /**
   * <p>
   * </p>
   * 
   * @param directory
   */
  public static void delete(File directory) {

    Delete delete = new Delete();
    delete.setProject(new Project());
    delete.setDir(directory);
    delete.setQuiet(true);
    delete.execute();

    Mkdir mkdir = new Mkdir();
    mkdir.setProject(new Project());
    mkdir.setDir(directory);
    mkdir.execute();
  }
}
