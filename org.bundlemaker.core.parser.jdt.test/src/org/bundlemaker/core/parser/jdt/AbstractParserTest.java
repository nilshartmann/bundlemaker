package org.bundlemaker.core.parser.jdt;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractParserTest {

  /** - */
  public static final String TEST_PROJECT_NAME = "TEST_PROJECT";

  /**
   * <p>
   * </p>
   */
  @Test
  public void testParser() throws CoreException {

    //
    File testDataDirectory = new File(new File(System.getProperty("user.dir"), "test-data"), this.getClass()
        .getSimpleName());
    Assert.assertTrue(testDataDirectory.isDirectory());

    // delete the project
    log("Deleting existing project...");
    EclipseProjectUtils.deleteProjectIfExists(TEST_PROJECT_NAME);

    // create simple project
    log("Creating new bundlemaker project...");
    IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(TEST_PROJECT_NAME);

    // get the BundleMaker project
    IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject, null);

    // create the project description
    log("Adding project description...");
    addProjectDescription(bundleMakerProject, testDataDirectory);

    // create the progress monitor
    IProgressMonitor progressMonitor = new ProgressMonitor();

    // initialize the project
    log("Initializing project...");
    bundleMakerProject.initialize(progressMonitor);

    // parse the project
    log("Parsing project...");
    List<? extends IProblem> problems = bundleMakerProject.parse(progressMonitor, true);

    BundleMakerProjectUtils.dumpProblems(problems);

    // open the project
    log("Opening project...");
    bundleMakerProject.open(progressMonitor);

    IModularizedSystem modularizedSystem = bundleMakerProject.getModularizedSystemWorkingCopy(TEST_PROJECT_NAME);

    //
    testResult(modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  protected void addProjectDescription(IBundleMakerProject bundleMakerProject, File directory) throws CoreException {

    Assert.assertTrue(directory.isDirectory());

    //
    IBundleMakerProjectDescription projectDescription = bundleMakerProject.getProjectDescription();

    // step 1:
    projectDescription.clear();

    // step 2: add the JRE
    projectDescription.setJre(getDefaultVmName());

    // step 3: add the source and classes
    File classes = new File(directory, "classes");
    Assert.assertTrue(classes.isDirectory());

    File source = new File(directory, "src");
    Assert.assertTrue(source.isDirectory());

    projectDescription.addResourceContent(TEST_PROJECT_NAME, "1.0.0", classes.getAbsolutePath(),
        source.getAbsolutePath());

    // step 4: process the class path entries
    File libsDir = new File(directory, "libs");
    if (libsDir.exists()) {
      File[] jarFiles = libsDir.listFiles();
      for (File externalJar : jarFiles) {
        projectDescription.addResourceContent(externalJar.getAbsolutePath());
      }
    }

    //
    projectDescription.save();
  }

  /**
   * <p>
   * </p>
   */
  protected void log(String message) {
    System.out.println(String.format("[%s] %s", this.getClass().getName(), message));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected static String getDefaultVmName() {
    return JavaRuntime.getDefaultVMInstall().getName();
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @throws CoreException 
   */
  protected abstract void testResult(IModularizedSystem modularizedSystem) throws CoreException;
}
