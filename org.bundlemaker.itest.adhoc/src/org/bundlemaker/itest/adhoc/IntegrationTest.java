package org.bundlemaker.itest.adhoc;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.ModuleExporterContext;
import org.bundlemaker.core.exporter.SimpleReportExporter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Test;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class IntegrationTest {

	/** - */
	public static final String PROJECT_NAME = "metro";

	/** - */
	private static final boolean PARSE = Boolean.getBoolean("parse");

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAdhocIntegrationTest() throws Exception {

		// delete the project
		if (PARSE) {
			EclipseProjectUtils.deleteProjectIfExists(PROJECT_NAME);
		}

		// create simple project
		IProject simpleProject = BundleMakerCore
				.getOrCreateSimpleProjectWithBundleMakerNature(PROJECT_NAME);

		// get the BundleMaker project
		IBundleMakerProject bundleMakerProject = BundleMakerCore
				.getBundleMakerProject(simpleProject, null);

		// create the project description
		if (PARSE) {
			IntegrationTestUtils.createProjectDescription(bundleMakerProject
					.getProjectDescription());
		}

		// create the progress monitor
		IProgressMonitor progressMonitor = new ProgressMonitor();

		// initialize the project
		bundleMakerProject.initialize(progressMonitor);

		// parse the project
		if (PARSE) {

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();

			List<? extends IProblem> problems = bundleMakerProject
					.parse(progressMonitor);

			stopWatch.stop();
			System.out.println(stopWatch.getElapsedTime());

			BundleMakerProjectUtils.dumpProblems(problems);
		}

		// open the project
		bundleMakerProject.open(progressMonitor);

		// get the working copy
		bundleMakerProject.createModularizedSystemWorkingCopy("test");
		IModularizedSystem modularizedSystem = bundleMakerProject
				.getModularizedSystemWorkingCopy("test");

		// transform
		modularizedSystem.applyTransformations();

		//
		assertModelSetup(modularizedSystem);

		// create the exporter context
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		//
		exportToStructure101(bundleMakerProject, modularizedSystem,
				exporterContext);
		exportToSimpleReport(bundleMakerProject, modularizedSystem,
				exporterContext);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @param modularizedSystem
	 * @throws Exception
	 */
	private void exportToStructure101(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem,
			ModuleExporterContext exporterContext) throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
		exporter.setExcludeModuleSelfReferences(false);
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @param modularizedSystem
	 * @throws Exception
	 */
	private void exportToSimpleReport(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem,
			ModuleExporterContext exporterContext) throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SimpleReportExporter exporter = new SimpleReportExporter();
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 */
	private void assertModelSetup(IModularizedSystem modularizedSystem) {

		// assert
		for (IResourceModule resourceModule : modularizedSystem
				.getResourceModules()) {

			// step 1: assert binary content
			for (IResource resource : resourceModule
					.getResources(ContentType.BINARY)) {

				//
				Assert.assertSame(resourceModule, resource.getResourceModule());

				//
				for (IReference reference : resource.getReferences()) {
					//
					Assert.assertSame(resource, reference.getResource());
				}
			}

			//
			for (IResource resource : resourceModule
					.getResources(ContentType.SOURCE)) {

				//
				Assert.assertSame(resourceModule, resource.getResourceModule());

				//
				for (IReference reference : resource.getReferences()) {
					//
					Assert.assertSame(resource, reference.getResource());
				}
			}
		}
	}
}
