package org.bundlemaker.itest.spring;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.ModuleExporterContext;
import org.bundlemaker.core.exporter.bundle.BinaryBundleExporter;
import org.bundlemaker.core.exporter.bundlor.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.exporter.pde.exporter.PdeExporterConfiguration;
import org.bundlemaker.core.exporter.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.exporter.pde.exporter.TargetPlatformProjectExporter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
import org.bundlemaker.core.modules.query.TypeQueryFilters;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
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
	public static final String PROJECT_NAME = "spring";

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

			List<? extends IProblem> problems = bundleMakerProject
					.parse(progressMonitor);

			stopWatch.stop();
			System.out.println(stopWatch.getElapsedTime());

			BundleMakerProjectUtils.dumpProblems(problems);
		}

		// open the project
		bundleMakerProject.open(progressMonitor);

		// check the model
		checkResourceModel(bundleMakerProject);

		// get the default modularized system
		IModularizedSystem modularizedSystem = bundleMakerProject
				.getModularizedSystemWorkingCopy(IBundleMakerProject.DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID);

		// add the transformations
		IntegrationTestUtils.addEmbedAntTransformation(modularizedSystem);
		IntegrationTestUtils
				.addModularizeSpringTransformation(modularizedSystem);

		// apply the transformation
		modularizedSystem.applyTransformations();

		// check the model
		checkTypeModel(modularizedSystem);

		//
		checkModularizedSystem(modularizedSystem);

		//
		System.out
				.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
		Collection<IType> types = modularizedSystem.getIsUsedBy(
				"org.springframework.context.ApplicationContext",
				TypeQueryFilters.TRUE_QUERY_FILTER);

		for (IType iType : types) {
			System.out.println(iType.getFullyQualifiedName() + " : "
					+ iType.getModule().getModuleIdentifier());
		}
		System.out
				.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

		// // export to simple report
		// exportToSimpleReport(bundleMakerProject, modularizedSystem);
		//
		// // export to structure 101
		// exportToStructure101(bundleMakerProject, modularizedSystem);

		// // export to binary bundle
		// exportToBinaryBundle(bundleMakerProject, modularizedSystem);
		//
		// // exportToPdeProjects
		// exportToPdeProjects(bundleMakerProject, modularizedSystem);
	}

	private void checkTypeModel(IModularizedSystem modularizedSystem) {

		// //
		// ITypeModule executionEnvironment = modularizedSystem
		// .getExecutionEnvironmentTypeModule();
		//
		// //
		// for (IType type : executionEnvironment.getContainedTypes()) {
		// Assert.assertTrue(
		// type.getFullyQualifiedName(),
		// ((ModularizedSystem) modularizedSystem)._typeToModuleListMap
		// .containsKey(type.getFullyQualifiedName()));
		// }
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 */
	private void checkModularizedSystem(IModularizedSystem modularizedSystem) {

		Collection<IResourceModule> resourceModules = modularizedSystem
				.getResourceModules();

		Assert.assertEquals(112, resourceModules.size());

		IResourceModule resourceModule = modularizedSystem
				.getResourceModule(new ModuleIdentifier("Spring-Core", "2.5.6"));

		Assert.assertNotNull(resourceModule);

		Set<String> typeNames = resourceModule.getContainedTypeNames();
		Assert.assertEquals(212, typeNames.size());

		int externalBinaryReferencesCount = resourceModule.getAllReferences(
				true, false, false).size();

		int externalBinaryAndSourceReferencesCount = resourceModule
				.getAllReferences(true, true, false).size();

		int binaryReferencesCount = resourceModule.getAllReferences(false,
				false, false).size();

		int binaryAndSourceReferencesCount = resourceModule.getAllReferences(
				false, true, false).size();

		System.out.println(externalBinaryReferencesCount);
		System.out.println(externalBinaryAndSourceReferencesCount);
		System.out.println(binaryReferencesCount);
		System.out.println(binaryAndSourceReferencesCount);

		Set<IReference> externalBinaryReferences = resourceModule
				.getAllReferences(true, false, false);

		for (IReference reference : resourceModule.getAllReferences(true, true,
				false)) {

			if (reference.isCompileTimeReference()
					&& !reference.isRuntimeReference()) {

				/** TODO **/

				if (reference.hasAssociatedType()) {
					System.out.println(reference);
					System.out.println(reference.getType().getSourceResource()
							.getPath());
					System.out.println(reference.getType().getBinaryResource()
							.getPath());
				}
			}

			// if (!externalBinaryReferences.contains(reference)) {
			// System.out.println(" - " + reference);
			// } else {
			// System.out.println(" + " + reference);
			// }
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @param modularizedSystem
	 * @throws Exception
	 */
	private void exportToPdeProjects(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {

		// Create PdeExporterConfiguration
		PdeExporterConfiguration pdeExporterConfiguration = new PdeExporterConfiguration();
		pdeExporterConfiguration.setUseClassifcationForExportDestination(true);
		pdeExporterConfiguration
				.setDependencyDescriptionStyle(PdeExporterConfiguration.STRICT_IMPORT_PACKAGE);

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		exporterContext.put(PdeExporterConfiguration.KEY,
				pdeExporterConfiguration);

		exporterContext
				.put(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY,
						new File(
								"R:/environments/bundlemaker2-environment/workspace/org.bundlemaker.itest.spring/templates"));

		new PdePluginProjectModuleExporter().export(modularizedSystem,
				exporterContext);

		new TargetPlatformProjectExporter().export(modularizedSystem,
				exporterContext);

	}

	private void exportToStructure101(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

	private void exportToBinaryBundle(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {

		//
		File destination = new File(System.getProperty("user.dir"),
				"destination");
		destination.mkdirs();

		// create the exporter context
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		BinaryBundleExporter exporter = new BinaryBundleExporter();
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
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, destination, modularizedSystem);

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
	 * @param bundleMakerProject
	 */
	private void checkResourceModel(IBundleMakerProject bundleMakerProject) {

		// check each file content entry
		for (IFileBasedContent fileBasedContent : bundleMakerProject
				.getProjectDescription().getFileBasedContent()) {

			//
			Assert.assertNotNull(fileBasedContent.getResourceContent());

			//
			Map<String, IType> typeMap = new HashMap<String, IType>();

			// step 1: assert binary content
			for (IResource resource : fileBasedContent.getResourceContent()
					.getBinaryResources()) {

				// assert that the binary resources don't have any references
				Assert.assertEquals(0, resource.getReferences().size());

				// additional asserts if the resource is a class
				if (resource.getPath().endsWith(".class")) {

					// TODO
					// Assert.assertEquals(resourceStandin.getPath(), 1,
					// resourceStandin.getResource().getContainedTypes()
					// .size());

					for (IType type : resource.getContainedTypes()) {

						Assert.assertNotNull(type.getBinaryResource());
						Assert.assertTrue(type.hasBinaryResource());
						Assert.assertEquals(resource, type.getBinaryResource());

						//
						typeMap.put(type.getFullyQualifiedName(), type);

						Map<String, IReference> referenceMap = new HashMap<String, IReference>();

						for (IReference reference : type.getReferences()) {

							Assert.assertNotNull(reference
									.getFullyQualifiedName());
							Assert.assertFalse(reference
									.hasAssociatedResource());
							Assert.assertTrue(reference.hasAssociatedType());
							Assert.assertEquals(type, reference.getType());

							//
							Assert.assertFalse(String.format(
									"Duplicate reference '%s' -> '%s'",
									reference, referenceMap.get(reference)),
									referenceMap.containsKey(reference
											.getFullyQualifiedName()));

							referenceMap.put(reference.getFullyQualifiedName(),
									reference);

						}
					}
				}
			}

			// step 2: assert source content
			for (IResource resource : fileBasedContent.getResourceContent()
					.getSourceResources()) {

				// assert that the binary resources don't have any references
				// Assert.assertEquals(0, resource.getReferences().size());

				// additional asserts if the resource is a class
				if (resource.getPath().endsWith(".java")) {

					// TODO
					// Assert.assertTrue(sourceResource.getPath(),
					// sourceResource.getContainedTypes().size() > 0);
					if (resource.getContainedTypes().size() == 0) {
						System.out.println(resource.getPath());
					}

					// TODO
					// Assert.assertEquals(resourceStandin.getPath(), 1,
					// resourceStandin.getResource().getContainedTypes()
					// .size());

					for (IType type : resource.getContainedTypes()) {

						Assert.assertNotNull(type.getSourceResource());
						Assert.assertTrue(type.hasSourceResource());
						Assert.assertEquals(resource, type.getSourceResource());

						Assert.assertTrue(
								String.format(
										"Type '%s' is not contained in the binary type map.",
										type.getFullyQualifiedName()), typeMap
										.containsKey(type
												.getFullyQualifiedName()));

						Assert.assertSame(
								String.format(
										"Type '%s' is not contained in the binary type map.",
										type.getFullyQualifiedName()), type,
								typeMap.get(type.getFullyQualifiedName()));

						Map<String, IReference> referenceMap = new HashMap<String, IReference>();

						for (IReference reference : type.getReferences()) {

							Assert.assertNotNull(reference
									.getFullyQualifiedName());
							Assert.assertFalse(reference
									.hasAssociatedResource());
							Assert.assertTrue(reference.hasAssociatedType());
							Assert.assertEquals(type, reference.getType());

							//
							Assert.assertFalse(String.format(
									"Duplicate reference '%s' -> '%s'",
									reference, referenceMap.get(reference)),
									referenceMap.containsKey(reference
											.getFullyQualifiedName()));

							referenceMap.put(reference.getFullyQualifiedName(),
									reference);
						}
					}
				}
			}
		}
	}

	public static boolean isLocalOrAnonymousType(String fullQualifiedName) {
		return fullQualifiedName.matches(".*\\$\\d.*");
	}
}
