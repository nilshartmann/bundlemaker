package org.bundlemaker.itest.spring;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.ModuleExporterContext;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.resource.IReference;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceStandin;
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

		// // // add the 'removeResourcesTransformation'
		// // RemoveResourcesTransformation removeResourcesTransformation =
		// // TransformationFactory.eINSTANCE
		// // .createRemoveResourcesTransformation();
		// // removeResourcesTransformation
		// // .addResourceSet("testng", "5.8",
		// // new String[] { "com/thoughtworks/qdox/model/*",
		// // "com/thoughtworks/qdox/model/util/*",
		// // "com/thoughtworks/qdox/*",
		// // "com/thoughtworks/qdox/parser/*",
		// // "com/thoughtworks/qdox/parser/structs/*",
		// // "com/thoughtworks/qdox/directorywalker/*",
		// // "com/thoughtworks/qdox/parser/impl/*",
		// // "bsh/commands/*", "bsh/*", "bsh/servlet/*",
		// // "bsh/org/objectweb/asm/*", "bsh/util/*",
		// // "bsh/classpath/*", "bsh/collection/*",
		// // "bsh/reflect/*" }, null);
		// // modularizedSystem.getTransformations().add(
		// // removeResourcesTransformation);

		// apply the transformation
		modularizedSystem.applyTransformations();

		// export to structure 101
		exportToStructure101(bundleMakerProject, modularizedSystem);

		// //
		// // //
		// // assertEquals(112, modularizedSystem.getResourceModules().size());
		// // assertEquals(1, modularizedSystem.getTypeModules().size());
		// // assertEquals(113, modularizedSystem.getAllModules().size());
		// //
		// // AmbiguousPackages ambiguousPackages = new AmbiguousPackages(
		// // modularizedSystem);
		// //
		// // Map<ITypeModule, String[]> moduleMap =
		// // ambiguousPackages.getModuleMap();
		// // for (ITypeModule module : moduleMap.keySet()) {
		// //
		// // // dump
		// // System.out.println(" - "
		// // + ModelUtils.toString(module.getModuleIdentifier()));
		// // System.out.println("   " + Arrays.asList(moduleMap.get(module)));
		// // }
		// //
		// // // for (IResourceModule resourceModule : modularizedSystem
		// // // .getResourceModules()) {
		// // //
		// // // if (!resourceModule.getResources(ContentType.SOURCE).isEmpty())
		// {
		// // //
		// // // System.out.println(ModelUtils.toString(resourceModule
		// // // .getModuleIdentifier()));
		// // //
		// // // IReferencedModules referencedModules = modularizedSystem
		// // // .getReferencedModules(resourceModule);
		// // //
		// // // for (ITypeModule typeModule : referencedModules
		// // // .getReferencedModulesMap().values()) {
		// // // System.out.println(" - "
		// // // + ModelUtils.toString(typeModule
		// // // .getModuleIdentifier()));
		// // // }
		// // //
		// // // for (Entry<String, IModuleList> entry : referencedModules
		// // // .getTypesWithAmbiguousModules()) {
		// // // System.out.println(" ~ " + entry.getKey());
		// // // for (ITypeModule typeModule : entry.getValue().getModules()) {
		// // // System.out.println("   ~~ "
		// // // + ModelUtils.toString(typeModule
		// // // .getModuleIdentifier()));
		// // // }
		// // // }
		// // // }
		// // // }
		// //
		// // // EMap<String, IModuleList> ambiguousPackages = modularizedSystem
		// // // .getAmbiguousPackages();
		// // //
		// // // for (final Entry<String, IModuleList> ambiguousPackage :
		// // // ambiguousPackages) {
		// // //
		// // // System.out.println(" - " + ambiguousPackage.getKey());
		// // //
		// // // for (ITypeModule typeModule : ambiguousPackage.getValue()
		// // // .getModules()) {
		// // //
		// // // System.out
		// // // .println("   - "
		// // // + ModelUtils.toString(typeModule
		// // // .getModuleIdentifier()));
		// // //
		// // // System.out.println("     "
		// // // + typeModule.getContainedTypes(new IQueryFilter() {
		// // // public boolean matches(String content) {
		// // // Pattern pattern = Pattern
		// // // .compile(ambiguousPackage.getKey()
		// // // .replace(".", "\\.")
		// // // + "\\.\\w*");
		// // // return pattern.matcher(content).matches();
		// // // }
		// // // }));
		// // // }
		// // // }
		// //

		//
		// // Create PdeExporterConfiguration
		// PdeExporterConfiguration pdeExporterConfiguration = new
		// PdeExporterConfiguration();
		// pdeExporterConfiguration.setUseClassifcationForExportDestination(true);
		// pdeExporterConfiguration
		// .setDependencyDescriptionStyle(PdeExporterConfiguration.STRICT_IMPORT_PACKAGE);
		// exporterContext.put(PdeExporterConfiguration.KEY,
		// pdeExporterConfiguration);
		//
		// exporterContext
		// .put(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY,
		// new File(
		// "R:/environments/bundlemaker2-environment/workspace/org.bundlemaker.itest.spring/templates"));
		//
		// // new PdePluginProjectModuleExporter().export(modularizedSystem,
		// // exporterContext);
		// //
		// // new TargetPlatformProjectExporter().export(modularizedSystem,
		// // exporterContext);
		//
		// new Structure101Exporter().export(modularizedSystem,
		// exporterContext);
		//
		// new SimpleReportExporter().export(modularizedSystem,
		// exporterContext);
		//
		// // ModifiableModularizedSystem system = (ModifiableModularizedSystem)
		// // modularizedSystem;
		// //
		// // StopWatch stopWatch = new StopWatch();
		// // stopWatch.start();
		// //
		// system.getContainingModules("org.springframework.jca.context.BootstrapContextAwareProcessor");
		// // stopWatch.stop();
		// // System.out.println(stopWatch.getElapsedTime());
		// //
		// // stopWatch = new StopWatch();
		// // stopWatch.start();
		// // IResourceModule resourceModule =
		// //
		// system.getResourceModule(ModelUtils.createModuleIdentifier("Spring",
		// // "2.5.6"));
		// // system.getUnsatisfiedReferencedTypes(resourceModule, true, true);
		// // stopWatch.stop();
		// // System.out.println(stopWatch.getElapsedTime());
		// //
		// // stopWatch = new StopWatch();
		// // stopWatch.start();
		// // resourceModule =
		// //
		// system.getResourceModule(ModelUtils.createModuleIdentifier("Spring",
		// // "2.5.6"));
		// // system.getUnsatisfiedReferencedPackages(resourceModule, true,
		// true);
		// // stopWatch.stop();
		// // System.out.println(stopWatch.getElapsedTime());
		//
		// // delete the destination directory
		// // File destinationDirectory = new
		// // File(System.getProperty("user.dir"),
		// // "destination");
		//
		// // File destinationDirectory = new File("D:/temp/temp");
		// // IntegrationTestUtils.delete(destinationDirectory);
		// //
		// // // create the exporter context
		// // StandardModuleExporterContext context = new
		// // StandardModuleExporterContext(
		// // bundleMakerProject, destinationDirectory, modularizedSystem);
		// // context.put(IModuleExporterContext.TEMPLATE_DIRECTORY,
		// // new File(System.getProperty("user.dir"), "templates"));
		// //
		// // // export the modules
		// // new StandardBundlorBasedBinaryBundleExporter().export(
		// // modularizedSystem, context);
		// // // new Structure101Exporter().export(modularizedSystem, context);
		// // new PdePluginProjectModuleExporter().export(modularizedSystem,
		// // context);
	}

	private void exportToStructure101(IBundleMakerProject bundleMakerProject,
			IModularizedSystem modularizedSystem) throws Exception {
		// create the exporter context
		ModuleExporterContext exporterContext = new ModuleExporterContext(
				bundleMakerProject, new File("c:/temp"), modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());
	}

	private void checkResourceModel(IBundleMakerProject bundleMakerProject) {

		// check each file content entry
		for (IFileBasedContent fileBasedContent : bundleMakerProject
				.getProjectDescription().getFileBasedContent()) {

			//
			Assert.assertNotNull(fileBasedContent.getResourceContent());

			// step 1: assert binary content
			for (IResourceStandin resourceStandin : fileBasedContent
					.getResourceContent().getBinaryResources()) {

				// assert the resource is set
				Assert.assertNotNull(resourceStandin.getRoot() + "/"
						+ resourceStandin.getPath(),
						resourceStandin.getResource());

				// get the resource
				IResource resource = resourceStandin.getResource();

				// assert the back reference
				Assert.assertEquals(resource.getResourceStandin(),
						resourceStandin);

				// assert that the binary resources don't have any references
				Assert.assertEquals(0, resource.getReferences().size());

				// additional asserts if the resource is a class
				if (resourceStandin.getPath().endsWith(".class")) {

					// TODO
					// Assert.assertEquals(resourceStandin.getPath(), 1,
					// resourceStandin.getResource().getContainedTypes()
					// .size());

					for (IType type : resourceStandin.getResource()
							.getContainedTypes()) {

						Assert.assertNotNull(type.getBinaryResource());
						Assert.assertTrue(type.hasBinaryResource());
						Assert.assertEquals(resourceStandin.getResource(),
								type.getBinaryResource());

						for (IReference reference : type.getReferences()) {

							Assert.assertNotNull(reference
									.getFullyQualifiedName());
							Assert.assertFalse(reference
									.hasAssociatedResource());
							Assert.assertTrue(reference.hasAssociatedType());
							Assert.assertEquals(type, reference.getType());

						}
					}
				}
			}

			// step 2: assert source content
			for (IResourceStandin resourceStandin : fileBasedContent
					.getResourceContent().getSourceResources()) {

				// assert the resource is set
				Assert.assertNotNull(resourceStandin.getRoot() + "/"
						+ resourceStandin.getPath(),
						resourceStandin.getResource());

				// get the resource
				IResource sourceResource = resourceStandin.getResource();

				// assert the back reference
				Assert.assertEquals(sourceResource.getResourceStandin(),
						resourceStandin);

				// assert that the binary resources don't have any references
				// Assert.assertEquals(0, resource.getReferences().size());

				// additional asserts if the resource is a class
				if (sourceResource.getPath().endsWith(".java")) {

					// TODO
					// Assert.assertTrue(sourceResource.getPath(),
					// sourceResource.getContainedTypes().size() > 0);
					if (sourceResource.getContainedTypes().size() == 0) {
						System.out.println(sourceResource.getPath());
					}

					// TODO
					// Assert.assertEquals(resourceStandin.getPath(), 1,
					// resourceStandin.getResource().getContainedTypes()
					// .size());

					for (IType type : sourceResource.getContainedTypes()) {

						Assert.assertNotNull(type.getSourceResource());
						Assert.assertTrue(type.hasSourceResource());
						Assert.assertEquals(sourceResource,
								type.getSourceResource());

						for (IReference reference : type.getReferences()) {

							Assert.assertNotNull(reference
									.getFullyQualifiedName());
							Assert.assertFalse(reference
									.hasAssociatedResource());
							Assert.assertTrue(reference.hasAssociatedType());
							Assert.assertEquals(type, reference.getType());

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
