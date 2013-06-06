package org.bundlemaker.core.itestframework.internal;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bundlemaker.core.common.utils.VMInstallUtils;
import org.bundlemaker.core.parser.IParserAwareBundleMakerProject;
import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.BundleMakerCore;
import org.bundlemaker.core.project.IModifiableProjectDescription;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.util.AnalyzeMode;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TestProjectCreator {

  /** TEST_PROJECT_NAME */
  public static final String TEST_PROJECT_NAME                 = "TEST_PROJECT";

  /** TEST_PROJECT_VERSION */
  public static final String TEST_PROJECT_VERSION              = "1.0.0";

  /** - */
  public static final String BUNDLEMAKER_TEST_VM_PROPERTY_NAME = "org.bundlemaker.core.itestframework.vm_install";

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  public static void initializeParseAndOPen(IParserAwareBundleMakerProject bundleMakerProject) {

    try {

      // initialize
      bundleMakerProject.initialize(new ProgressMonitor());

      // parse and open the project
      bundleMakerProject.parseAndOpen(new ProgressMonitor());

      // assert no parse errors
      if (bundleMakerProject.getProblems().size() > 0) {
        StringBuilder builder = new StringBuilder();
        for (IProblem problem : bundleMakerProject.getProblems()) {
          builder.append(problem.getMessage());
          builder.append("\n");
        }
        Assert.fail(builder.toString());
      }

    } catch (CoreException e) {
      Assert.fail(e.getMessage());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static final IParserAwareBundleMakerProject getBundleMakerProject(String testProjectName) {
    try {
      // create simple project
      IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(testProjectName);

      // get the BM project
      return BundleMakerCore.getBundleMakerProject(simpleProject).adaptAs(IParserAwareBundleMakerProject.class);
    } catch (CoreException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
      return null;
    }
  }

  public static void addProjectDescription(IProjectDescriptionAwareBundleMakerProject bundleMakerProject,
      String testProjectName) {

    //
    File testDataDirectory = new File(new File(System.getProperty("user.dir"), "test-data"), testProjectName);

    //
    if (!testDataDirectory.isDirectory()) {

      //
      File file = new File(TestProjectCreator.class.getProtectionDomain().getCodeSource().getLocation().getFile());

      //
      if (file.isFile()) {

        // File myTempDir = Files.createTempDir();
        // myTempDir.mkdirs();
        // File parentDir = new File(myTempDir, testProjectName);
        // String prefix = "test-data/" + testProjectName + "/";
        //
        // try {
        // JarFile jar = new JarFile(file);
        // Enumeration<JarEntry> enumeration = jar.entries();
        // while (enumeration.hasMoreElements()) {
        // JarEntry jarEntry = enumeration.nextElement();
        // if (jarEntry.getName().startsWith("test-data/" + testProjectName + "/")) {
        // InputStream in = new BufferedInputStream(jar.getInputStream(jarEntry));
        // File dest = new File(parentDir, jarEntry.getName().substring(prefix.length()));
        // dest.mkdirs();
        // OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        // byte[] buffer = new byte[2048];
        // for (;;) {
        // int nBytes = in.read(buffer);
        // if (nBytes <= 0)
        // break;
        // out.write(buffer, 0, nBytes);
        // }
        // out.flush();
        // out.close();
        // in.close();
        // }
        // }
        // } catch (IOException e) {
        // e.printStackTrace();
        // throw new RuntimeException(e.getMessage(), e);
        // }
        //
        // testDataDirectory = parentDir;

        //
        throw new RuntimeException(String.format("File '%s' has to be a directory!", file.getAbsolutePath()));

      } else if (file.isDirectory()) {
        testDataDirectory = new File(new File(file, "test-data"), testProjectName);
      }
    }

    Assert.assertTrue(String.format("File '%s' has to be a directory.", testDataDirectory),
        testDataDirectory.isDirectory());

    // create the project description
    addProjectDescription(bundleMakerProject, testDataDirectory, testProjectName);
  }

  public static void addProjectDescription(IProjectDescriptionAwareBundleMakerProject bundleMakerProject, File directory) {
    addProjectDescription(bundleMakerProject, directory, TEST_PROJECT_NAME);
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public static void addProjectDescription(IProjectDescriptionAwareBundleMakerProject bundleMakerProject,
      File directory, String projectName) {

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
    FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(projectDescription, projectName,
        TEST_PROJECT_VERSION, classesPath, sourcesPath);

    // step 4: process the class path entries
    File libsDir = new File(directory, "libs");
    if (libsDir.exists()) {
      File[] jarFiles = libsDir.listFiles();
      for (File externalJar : jarFiles) {
        FileBasedProjectContentProviderFactory.addNewFileBasedContentProvider(projectDescription,
            externalJar.getAbsolutePath(), null, AnalyzeMode.BINARIES_ONLY);
      }
    }

    try {
      //
      projectDescription.save();
    } catch (CoreException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

  public static String getClassesPath(File directory) {
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

  public static String getSourcesPath(File directory) {
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

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  private static String getTestVmName() {

    String configuredTestVmLocation = System.getProperty(BUNDLEMAKER_TEST_VM_PROPERTY_NAME);

    System.out.println("configuredTestVmLocation: " + configuredTestVmLocation);

    IVMInstall vmInstall = null;

    if (configuredTestVmLocation == null || configuredTestVmLocation.trim().isEmpty()
        || !new File(configuredTestVmLocation).isDirectory()) {
      vmInstall = JavaRuntime.getDefaultVMInstall();
    } else {
      System.out.println("Creating Test IVMInstall for location '" + configuredTestVmLocation + "'");
      try {
        vmInstall = VMInstallUtils.getOrCreateIVMInstall("BundleMakerTestJDK", configuredTestVmLocation);
      } catch (CoreException e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
      }
    }

    assertNotNull("No VM available", vmInstall);

    System.out.println("Using Test JDK '" + vmInstall.getName() + "' from " + vmInstall.getInstallLocation());
    return vmInstall.getName();
  }
}
