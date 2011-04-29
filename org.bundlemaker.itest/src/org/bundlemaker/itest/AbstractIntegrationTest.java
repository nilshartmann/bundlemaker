package org.bundlemaker.itest;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.osgi.exporter.BinaryBundleExporter;
import org.bundlemaker.core.osgi.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.osgi.pde.exporter.TargetPlatformProjectExporter;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.resources.IProject;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractIntegrationTest {

  /** - */
  private static final boolean PARSE = Boolean.getBoolean("parse");

  /** - */
  public String                _projectName;

  /** - */
  private boolean              _exportAsStructure101;

  /** - */
  private boolean              _exportAsBinaryBundles;

  /** - */
  private boolean              _exportAsSimpleReport;

  /** - */
  private boolean              _exportAsPdeProjects;

  /**
   * @param projectName
   * @param exportAsSimpleReport
   * @param exportAsStructure101
   * @param exportAsBinaryBundles
   */
  public AbstractIntegrationTest(String projectName, boolean exportAsSimpleReport, boolean exportAsStructure101,
      boolean exportAsBinaryBundles, boolean exportAsPdeProjects) {

    Assert.assertNotNull(projectName);

    _projectName = projectName;
    _exportAsSimpleReport = exportAsSimpleReport;
    _exportAsStructure101 = exportAsStructure101;
    _exportAsBinaryBundles = exportAsBinaryBundles;
    _exportAsPdeProjects = exportAsPdeProjects;
  }

  /**
   * <p>
   * </p>
   * 
   * @throws Exception
   */
  @Test
  public void integrationTest() throws Exception {

    // delete the project
    if (PARSE) {
      log("Deleting existing project...");
      EclipseProjectUtils.deleteProjectIfExists(_projectName);
    }

    // create simple project
    log("Creating new bundlemaker project...");
    IProject simpleProject = BundleMakerCore.getOrCreateSimpleProjectWithBundleMakerNature(_projectName);

    // get the BundleMaker project
    IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(simpleProject, null);

    // create the project description
    if (PARSE) {
      log("Adding project description...");
      doAddProjectDescription(bundleMakerProject);
    }

    // initialize the project
    log("Initializing project...");
    bundleMakerProject.initialize(new ProgressMonitor());

    // parse the project
    if (PARSE) {

      log("Parsing project...");

      StopWatch stopWatch = new StopWatch();
      stopWatch.start();

      List<? extends IProblem> problems = bundleMakerProject.parse(new ProgressMonitor(), true);

      stopWatch.stop();
      log(stopWatch.getElapsedTime() + "");

      BundleMakerProjectUtils.dumpProblems(problems);
    }

    // open the project
    log("Opening project...");
    bundleMakerProject.open(new ProgressMonitor());

    // EXAMPLE CODE - REMOVE
    if (bundleMakerProject.getState().equals(BundleMakerProjectState.READY)) {

      if (!bundleMakerProject.hasModularizedSystemWorkingCopy("test")) {
        bundleMakerProject.createModularizedSystemWorkingCopy("test");
      }

      IModularizedSystem modularizedSystem = bundleMakerProject.getModularizedSystemWorkingCopy("test");

      for (IModule module : modularizedSystem.getAllModules()) {
        System.out.println(module.getModuleIdentifier());
      }
    }
    // EXAMPLE CODE - REMOVE

    //
    doCheckBundleMakerProject(bundleMakerProject);

    // get the default modularized system
    IModularizedSystem modularizedSystem = bundleMakerProject.getModularizedSystemWorkingCopy(bundleMakerProject
        .getProject().getName());

    //
    doAddTransformations(modularizedSystem);

    // apply the transformation
    log("Applying transformations...");
    modularizedSystem.applyTransformations();

    //
    doPostProcessModularizedSystem(modularizedSystem);

    //
    log("Checking model...");
    doCheckModularizedSystem(modularizedSystem);

    // export to simple report
    if (_exportAsSimpleReport) {
      log("Exporting as simple report...");
      exportAsSimpleReport(bundleMakerProject, modularizedSystem);
    }

    // export to structure 101
    if (_exportAsStructure101) {
      log("Exporting as Structure 101 file...");
      exportAsStructure101(bundleMakerProject, modularizedSystem);
    }

    //
    if (_exportAsBinaryBundles) {
      log("Exporting as binary bundles...");
      exportAsBinaryBundle(bundleMakerProject, modularizedSystem);
    }

    //
    if (_exportAsPdeProjects) {
      log("Exporting as PDE projects...");
      exportAsPdeProjects(bundleMakerProject, modularizedSystem);
    }

    doAdditionalExports(bundleMakerProject, modularizedSystem);
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   */
  protected void doCheckBundleMakerProject(IBundleMakerProject bundleMakerProject) {

  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  protected void doPostProcessModularizedSystem(IModularizedSystem modularizedSystem) {

  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws Exception
   */
  protected abstract void doAddProjectDescription(IBundleMakerProject bundleMakerProject) throws Exception;

  /**
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  protected void doAdditionalExports(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  protected void doAddTransformations(IModularizedSystem modularizedSystem) {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   */
  protected void doCheckModularizedSystem(IModularizedSystem modularizedSystem) {
    //
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportAsBinaryBundle(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    BinaryBundleExporter exporter = new BinaryBundleExporter();
    File templates = new File(System.getProperty("user.dir"), "templates");
    exporter.setTemplateRootDirectory(templates);
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Elapsed time " + stopWatch.getElapsedTime());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportAsStructure101(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Structure101Exporter exporter = new Structure101Exporter();
    exporter.export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Elapsed time " + stopWatch.getElapsedTime());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportAsSimpleReport(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    SimpleReportExporter exporter = new SimpleReportExporter();
    new ModularizedSystemExporterAdapter(exporter).export(modularizedSystem, exporterContext);
    stopWatch.stop();
    System.out.println("Elapsed time " + stopWatch.getElapsedTime());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param modularizedSystem
   * @throws Exception
   */
  private void exportAsPdeProjects(IBundleMakerProject bundleMakerProject, IModularizedSystem modularizedSystem)
      throws Exception {

    //
    File destination = new File(System.getProperty("user.dir"), "destination");
    destination.mkdirs();

    // create the exporter context
    DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(bundleMakerProject, destination,
        modularizedSystem);

    File templateDirectory = new File(System.getProperty("user.dir"), "templates");

    PdePluginProjectModuleExporter pdeExporter = new PdePluginProjectModuleExporter();
    pdeExporter.setUseClassifcationForExportDestination(true);
    pdeExporter.setTemplateRootDirectory(templateDirectory);
    new ModularizedSystemExporterAdapter(pdeExporter).export(modularizedSystem, exporterContext);

    TargetPlatformProjectExporter targetPlatformProjectExporter = new TargetPlatformProjectExporter();
    targetPlatformProjectExporter.setTemplateDirectory(templateDirectory);
    targetPlatformProjectExporter.export(modularizedSystem, exporterContext);
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
  public static String getDefaultVmName() {
    return "org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.6";
  }
}
