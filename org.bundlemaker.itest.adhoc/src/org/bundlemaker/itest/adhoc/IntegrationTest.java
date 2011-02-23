package org.bundlemaker.itest.adhoc;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.exporter.util.BinaryBundleExporter;
import org.bundlemaker.core.exporter.util.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.util.SimpleReportExporter;
import org.bundlemaker.core.modules.IModularizedSystem;
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
	public static final String PROJECT_NAME = "ad-hoc";

	/** - */
	private static final boolean PARSE = Boolean.getBoolean("parse");

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIntegrationTestSpring() throws Exception {

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

			List<? extends IProblem> problems = bundleMakerProject.parse(
					progressMonitor, true);

			stopWatch.stop();
			System.out.println(stopWatch.getElapsedTime());

			BundleMakerProjectUtils.dumpProblems(problems);
		}

		// open the project
		bundleMakerProject.open(progressMonitor);

		// get the default modularized system
		IModularizedSystem modularizedSystem = bundleMakerProject
				.getModularizedSystemWorkingCopy(IBundleMakerProject.DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID);

		// apply the transformation
		modularizedSystem.applyTransformations();

		// export to simple report
		exportToSimpleReport(bundleMakerProject, modularizedSystem);

		// export to structure 101
		exportToStructure101(bundleMakerProject, modularizedSystem);

		//
		exportToBinaryBundle(bundleMakerProject, modularizedSystem);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @param modularizedSystem
	 * @throws Exception
	 */
	private void exportToBinaryBundle(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		BinaryBundleExporter exporter = new BinaryBundleExporter();
		new ModularizedSystemExporterAdapter(exporter).export(
				modularizedSystem, exporterContext);
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
	private void exportToStructure101(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
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
			IModularizedSystem modularizedSystem) throws Exception {

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		SimpleReportExporter exporter = new SimpleReportExporter();
		new ModularizedSystemExporterAdapter(exporter).export(
				modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

}
