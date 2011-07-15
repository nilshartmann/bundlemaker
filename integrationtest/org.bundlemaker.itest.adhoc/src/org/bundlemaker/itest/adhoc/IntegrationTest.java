package org.bundlemaker.itest.adhoc;

import java.io.File;
import java.io.FileFilter;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.itest.AbstractIntegrationTest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IntegrationTest extends AbstractIntegrationTest {

  /**
   * <p>
   * </p>
   */
  public IntegrationTest() {

    // call super constructor
    super("ad-hoc", true, true, true, true);
  }

  @Override
  protected void doAddProjectDescription(IBundleMakerProject bundleMakerProject) throws Exception {

    // step 1:
    bundleMakerProject.getModifiableProjectDescription().clear();

    // step 2: add the JRE
    bundleMakerProject.getModifiableProjectDescription().setJre(AbstractIntegrationTest.getDefaultVmName());

    // step 3: add classes
    File libsDir = new File(System.getProperty("user.dir"), "adhoc-input");
    File[] jarFiles = libsDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return !(pathname.getName().contains(".svn") || pathname.getName().contains(".SVN"));
      }
    });
    for (File externalJar : jarFiles) {
      bundleMakerProject.getModifiableProjectDescription().addResourceContent(externalJar.getAbsolutePath());
    }

    bundleMakerProject.getModifiableProjectDescription().save();
  }
}
