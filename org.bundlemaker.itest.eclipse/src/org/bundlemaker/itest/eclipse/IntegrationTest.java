package org.bundlemaker.itest.eclipse;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResourceStandin;
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

		//
		IReferencedModulesQueryResult queryResult = modularizedSystem
				.getReferencedModules(modularizedSystem
						.getResourceModule(new ModuleIdentifier("eclipse",
								"3.6.1")), true);

		for (ITypeModule module : queryResult.getReferencedModules()) {
			System.out.println(module.getModuleIdentifier().toString());
		}

		for (Entry<IReference, Set<ITypeModule>> entry : queryResult
				.getReferencesWithAmbiguousModules().entrySet()) {
			System.out.println(" - " + entry.getKey());
			System.out.println("   - " + entry.getValue());
		}

		System.out
				.println("*****************************************************");

		for (Entry<IReference, ITypeModule> entry : queryResult
				.getReferencedModulesMap().entrySet()) {

			System.out.println(" - " + entry.getKey());
			System.out.println("   - " + entry.getValue());
		}
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
			for (IResourceStandin resourceStandin : resourceModule
					.getResources(ContentType.BINARY)) {

				//
				Assert.assertSame(resourceModule,
						resourceStandin.getResourceModule());

				//
				for (IReference reference : resourceStandin.getResource()
						.getReferences()) {
					//
					Assert.assertSame(resourceStandin.getResource(),
							reference.getResource());
				}
			}

			//
			for (IResourceStandin resourceStandin : resourceModule
					.getResources(ContentType.SOURCE)) {

				//
				Assert.assertSame(resourceModule,
						resourceStandin.getResourceModule());
				Assert.assertSame(resourceStandin, resourceStandin
						.getResource().getResourceStandin());

				//
				for (IReference reference : resourceStandin.getResource()
						.getReferences()) {
					//
					Assert.assertSame(resourceStandin.getResource(),
							reference.getResource());
				}
			}
		}
	}
}
