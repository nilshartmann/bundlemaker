package org.bundlemaker.itest.eclipse;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.DefaultModuleExporterContext;
import org.bundlemaker.core.exporter.ModularizedSystemExporterAdapter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.exporter.util.SimpleReportExporter;
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
	public static final String PROJECT_NAME = "eclipse";

	/** - */
	private static final boolean PARSE = Boolean.getBoolean("parse");

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testIntegrationTestEclipse() throws Exception {

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
			bundleMakerProject.saveProjectDescription();
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
					.parse(progressMonitor, true);

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

		//
		exportToStructure101(bundleMakerProject, modularizedSystem);
		exportToSimpleReport(bundleMakerProject, modularizedSystem);

		// //
		// IReferencedModulesQueryResult queryResult = modularizedSystem
		// .getReferencedModules(modularizedSystem
		// .getResourceModule(new ModuleIdentifier("eclipse",
		// "3.6.1")), true);
		//
		// for (ITypeModule module : queryResult.getReferencedModules()) {
		// System.out.println(module.getModuleIdentifier().toString());
		// }
		//
		// for (Entry<IReference, Set<ITypeModule>> entry : queryResult
		// .getReferencesWithAmbiguousModules().entrySet()) {
		// System.out.println(" - " + entry.getKey());
		// System.out.println("   - " + entry.getValue());
		// }
		//
		// System.out
		// .println("*****************************************************");
		//
		// for (Entry<IReference, ITypeModule> entry : queryResult
		// .getReferencedModulesMap().entrySet()) {
		//
		// System.out.println(" - " + entry.getKey());
		// System.out.println("   - " + entry.getValue());
		// }
	}

	private void exportToStructure101(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {
		// create the exporter context
		DefaultModuleExporterContext exporterContext = new DefaultModuleExporterContext(
				bundleMakerProject, new File("c:/temp"), modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

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
