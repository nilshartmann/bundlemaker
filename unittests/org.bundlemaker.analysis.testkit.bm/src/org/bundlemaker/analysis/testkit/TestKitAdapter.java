package org.bundlemaker.analysis.testkit;

import java.io.File;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.testkit.framework.ITestKitAdapter;
import org.bundlemaker.analysis.testkit.framework.ITimeStampAwareTestKitAdapter;
import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProviderFactory;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TestKitAdapter implements ITestKitAdapter, ITimeStampAwareTestKitAdapter {

  /** TEST_PROJECT_VERSION */
  private static final String          TEST_PROJECT_VERSION = "1.0.0";

  /** - */
  private IBundleMakerProject          _bundleMakerProject;

  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  /** - */
  private IRootArtifact                _rootArtifact;

  /** - */
  private String                       _timestamp;

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTimeStamp(String timestamp) {
    _timestamp = timestamp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init() throws Exception {

    // delete the project
    log("Deleting existing project...");
    EclipseProjectUtils.deleteProjectIfExists(getTestProjectName());

    // create simple project
    log("Creating new bundlemaker project...");
    IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(getTestProjectName());

    // get the BM project
    _bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject, null);

    //
    addProjectDescription();

    //
    _bundleMakerProject.initialize(new ProgressMonitor());
    _bundleMakerProject.parseAndOpen(new ProgressMonitor());

    _modularizedSystem = (IModifiableModularizedSystem) _bundleMakerProject
        .createModularizedSystemWorkingCopy("Original");

    _modularizedSystem.getTransformations().add(
        new GroupTransformation(new ModuleIdentifier("jedit", "1.0.0"), new Path("group1/group2")));

    _modularizedSystem.applyTransformations(null);

    //
    _rootArtifact = _modularizedSystem.getArtifactModel(ArtifactModelConfiguration.AGGREGATE_INNER_TYPES_CONFIGURATION);

    //
    ModularizedSystemChecker.check(_modularizedSystem, _timestamp);

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() throws Exception {

    // dispose the project
    _bundleMakerProject.dispose();

    // delete the project
    EclipseProjectUtils.deleteProjectIfExists(getTestProjectName());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifact getRoot() {
    return _rootArtifact.getRoot();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final String getTestProjectName() {
    return "jedit";
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
    IModifiableProjectDescription projectDescription = bundleMakerProject.getModifiableProjectDescription();

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

    FileBasedContentProviderFactory.addNewFileBasedContentProvider(projectDescription, getTestProjectName(),
        TEST_PROJECT_VERSION, classes.getAbsolutePath(), sources.getAbsolutePath());

    // step 4: process the class path entries
    File libsDir = new File(directory, "libs");
    if (libsDir.exists()) {
      File[] jarFiles = libsDir.listFiles();
      for (File externalJar : jarFiles) {
        FileBasedContentProviderFactory.addNewFileBasedContentProvider(projectDescription,
            externalJar.getAbsolutePath());
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
}
