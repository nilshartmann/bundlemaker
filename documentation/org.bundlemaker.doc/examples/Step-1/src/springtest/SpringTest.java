package springtest;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.osgi.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.osgi.pde.exporter.TargetPlatformProjectExporter;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.launching.JavaRuntime;
import org.junit.Test;

public class SpringTest {

	@Test
	public void test() throws Exception {

    // (1) delete project if exists
    EclipseProjectUtils.deleteProjectIfExists("spring-example");

    // (2) create simple project
    IProject simpleProject = BundleMakerCore
      .getOrCreateSimpleProjectWithBundleMakerNature("spring-example");

    // (3) get the BundleMaker project
    IBundleMakerProject bundleMakerProject = BundleMakerCore
      .getBundleMakerProject(simpleProject, null);

    // (4) set the project description
    setProjectDescription(bundleMakerProject);

    // (5) initialize the project
    bundleMakerProject.initialize(new ProgressMonitor());

    // (6) parse and dump the problems
    List<? extends IProblem> problems = bundleMakerProject.parse(
      new ProgressMonitor(), true);

    BundleMakerProjectUtils.dumpProblems(problems);

    // (7) open the project
    bundleMakerProject.open(new ProgressMonitor());

	  // (8) get the default modularized system
	  IModularizedSystem modularizedSystem = bundleMakerProject
	    .getModularizedSystemWorkingCopy(bundleMakerProject
	        .getProject().getName());

	  // (9)
	  modularizedSystem.applyTransformations();

	  // (10) create the destination directory
	  File destination = new File(System.getProperty("user.dir"), 
	    "destination");
	  destination.mkdirs();

	  // (11) create the exporter context
	  DefaultModuleExporterContext exporterContext = 
	    new DefaultModuleExporterContext(bundleMakerProject, 
	      destination, modularizedSystem);

	  // (12) export plug-in projects
	  new ModularizedSystemExporterAdapter(new PdePluginProjectModuleExporter()).export(
	    modularizedSystem, exporterContext);

	  // (13) export target platform project
	  TargetPlatformProjectExporter targetPlatformProjectExporter = new TargetPlatformProjectExporter();
	  targetPlatformProjectExporter.export(modularizedSystem, exporterContext);   
	}

  /**
   * Helper method that creates a bundlemaker project description.
   *
   * @param bundleMakerProject 
   * @throws CoreException
   */            
  private void setProjectDescription(IBundleMakerProject bundleMakerProject)
      throws CoreException {

    // (4-1) get the project description instance
    IBundleMakerProjectDescription projectDescription = bundleMakerProject
      .getProjectDescription();

    // (4-2) remove any content
    projectDescription.clear();

    // (4-3) set the name of the associated JRE
    projectDescription.setJre(
      JavaRuntime.getDefaultVMInstall().getName());

    // (4-4) add a content entry for the spring 
    // framework that contains sources and binary files 
    File classesZip = new File(System.getProperty("user.dir"),
      "spring/classes.zip");

    File sourceDirectory = new File(System.getProperty("user.dir"),
      "spring/source.zip");

    projectDescription
      .addResourceContent("Spring", "2.5.6",
        classesZip.getAbsolutePath(),
         sourceDirectory.getAbsolutePath());

    // (4-5) add content entries for all archive
    // files in the 'spring/libs' folder
    File libsDir = new File(System.getProperty("user.dir"), 
      "spring/libs");
    
    for (File externalJar : libsDir.listFiles()) {
    projectDescription
      .addResourceContent(externalJar.getAbsolutePath());
    }

    // (4-6) finally we have to save the project description
    projectDescription.save();
  }
}
