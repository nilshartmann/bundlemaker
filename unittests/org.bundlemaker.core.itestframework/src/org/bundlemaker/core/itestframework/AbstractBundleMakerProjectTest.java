package org.bundlemaker.core.itestframework;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ModelTransformer;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerProjectTest {

  /** TEST_PROJECT_VERSION */
  public static final String  TEST_PROJECT_VERSION = "1.0.0";

  /** - */
  private String              _testProjectName;

  /** - */
  private IBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String getTestProjectName() {
    return _testProjectName;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IBundleMakerProject getBundleMakerProject() {
    return _bundleMakerProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Before
  public void before() throws CoreException {

    //
    _testProjectName = computeTestProjectName();

    // delete the project
    log("Deleting existing project...");
    EclipseProjectUtils.deleteProjectIfExists(_testProjectName);

    // create simple project
    log("Creating new bundlemaker project...");
    IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(_testProjectName);

    // get the BM project
    _bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject, null);

    //
    ModelTransformer.invalidateCache();
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @After
  public void after() throws CoreException {

    // dispose the project
    _bundleMakerProject.dispose();

    // delete the project
    EclipseProjectUtils.deleteProjectIfExists(_testProjectName);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  protected void addProjectDescription() throws CoreException {
    //
    File testDataDirectory = new File(new File(System.getProperty("user.dir"), "test-data"), getTestProjectName());
    Assert.assertTrue(testDataDirectory.isDirectory());

    // create the project description
    log("Adding project description...");
    addProjectDescription(_bundleMakerProject, testDataDirectory);
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
    IModifiableBundleMakerProjectDescription projectDescription = bundleMakerProject.getModifiableProjectDescription();

    // step 1:
    projectDescription.clear();

    // step 2: add the JRE
    projectDescription.setJre(getDefaultVmName());

    // step 3: add the source and classes
    File classes = null;
    if (new File(directory, "classes").isDirectory()) {
      classes = new File(directory, "classes");
    } else if (new File(directory, "classes.zip").isFile()) {
      classes = new File(directory, "classes.zip");
    } else {
      Assert.fail("No classes found!");
    }

    File sources = null;
    if (new File(directory, "src").isDirectory()) {
      sources = new File(directory, "src");
    } else if (new File(directory, "src.zip").isFile()) {
      sources = new File(directory, "src.zip");
    } else {
      Assert.fail("No classes found!");
    }

    projectDescription.addResourceContent(_testProjectName, TEST_PROJECT_VERSION, classes.getAbsolutePath(),
        sources.getAbsolutePath());

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
  protected String computeTestProjectName() {
    return this.getClass().getSimpleName();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected static String getDefaultVmName() {
    IVMInstall defaultVMInstall = JavaRuntime.getDefaultVMInstall();
    assertNotNull("No default VM available", defaultVMInstall);
    return defaultVMInstall.getName();
  }
}
