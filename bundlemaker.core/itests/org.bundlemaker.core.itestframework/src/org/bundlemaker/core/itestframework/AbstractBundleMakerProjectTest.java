package org.bundlemaker.core.itestframework;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.spi.IModifiableProjectDescription;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.JdkCreator;
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
  
  public static final String BUNDLEMAKER_TEST_VM_PROPERTY_NAME = "org.bundlemaker.core.itestframework.vm_install";


  /** TEST_PROJECT_VERSION */
  public static final String  TEST_PROJECT_VERSION = "1.0.0";

  /** - */
  private IBundleMakerProject _bundleMakerProject;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getTestProjectName() {
    return computeTestProjectName();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final IBundleMakerProject getBundleMakerProject() {
    
    //
    if (_bundleMakerProject == null) {
      
      try {
        // create simple project
        log("Creating new bundlemaker project...");
        IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(getTestProjectName());

        // get the BM project
        _bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject);
      } catch (CoreException e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
      }
    }
    
    //
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

    // // delete the project
    // log("Deleting existing project...");
    // EclipseProjectUtils.deleteProjectIfExists(_testProjectName);
  }

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @After
  public void after() throws CoreException {

//    // dispose the project
//    if (_bundleMakerProject != null) {
//      _bundleMakerProject.dispose();
//    }
//
//    // delete the project
//    EclipseProjectUtils.deleteProjectIfExists(_testProjectName);
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
    Assert.assertTrue(String.format("File '%s' has to be a directory.", testDataDirectory),
        testDataDirectory.isDirectory());

    // create the project description
    log("Adding project description...");
    addProjectDescription(getBundleMakerProject(), testDataDirectory);
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
    IModifiableProjectDescription projectDescription = bundleMakerProject.getModifiableProjectDescription();

    // step 1:
    projectDescription.clear();

    // step 2: add the JRE
    projectDescription.setJre(getTestVmName());

    // step 3: add the source and classes
    String classesPath = getClassesPath(directory);
    String sourcesPath = getSourcesPath(directory);
    
 
    //
    FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(projectDescription, getTestProjectName(),
        TEST_PROJECT_VERSION, classesPath, sourcesPath);

    // step 4: process the class path entries
    File libsDir = new File(directory, "libs");
    if (libsDir.exists()) {
      File[] jarFiles = libsDir.listFiles();
      for (File externalJar : jarFiles) {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(projectDescription,
            externalJar.getAbsolutePath(), null, getLibraryAnalyzeMode());
      }
    }

    //
    projectDescription.save();
  }
  
  protected String getClassesPath(File directory) {
    File classes = null;
    if (new File(directory, "classes").isDirectory()) {
      classes = new File(directory, "classes");
    } else if (new File(directory, "classes.zip").isFile()) {
      classes = new File(directory, "classes.zip");
    } else {
      Assert.fail("No classes found!");
    }

    return classes.getAbsolutePath();
  }
  
  protected String getSourcesPath(File directory) {
    File sources = null;
    if (new File(directory, "src").isDirectory()) {
      sources = new File(directory, "src");
    } else if (new File(directory, "src.zip").isFile()) {
      sources = new File(directory, "src.zip");
    } else {
      // Assert.fail("No classes found!");
    }
    
    return (sources == null ? null : sources.getAbsolutePath());
    

  }

  protected AnalyzeMode getLibraryAnalyzeMode() {
    return AnalyzeMode.BINARIES_ONLY;
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
   * @throws CoreException 
   */
  protected static String getTestVmName() throws CoreException {
    
    String configuredTestVmLocation = System.getProperty(BUNDLEMAKER_TEST_VM_PROPERTY_NAME);
    
    System.out.println("configuredTestVmLocation: " + configuredTestVmLocation);

    IVMInstall vmInstall = null;
    
    if (configuredTestVmLocation == null || configuredTestVmLocation.trim().isEmpty()) {
     vmInstall = JavaRuntime.getDefaultVMInstall(); 
    } else {
      System.out.println("Creating Test IVMInstall for location '" + configuredTestVmLocation + "'");
      vmInstall = JdkCreator.getOrCreateIVMInstall("BundleMakerTestJDK", configuredTestVmLocation);
    }
    
    assertNotNull("No VM available", vmInstall);
    return vmInstall.getName();
  }
}
