package org.bundlemaker.itest.adhoc;

import java.io.File;
import java.io.FileFilter;

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

    // step 3: add classes
    File libsDir = new File(System.getProperty("user.dir"), "adhoc-input");
    File[] jarFiles = libsDir.listFiles(new FileFilter() {
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
}
