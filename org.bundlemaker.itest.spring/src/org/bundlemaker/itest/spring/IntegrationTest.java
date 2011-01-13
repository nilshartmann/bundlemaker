package org.bundlemaker.itest.spring;

import java.io.File;
import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.exporter.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.exporter.StandardModuleExporterContext;
import org.bundlemaker.core.exporter.pde.exporter.PdeExporterAttributes;
import org.bundlemaker.core.exporter.pde.exporter.PdePluginProjectModuleExporter;
import org.bundlemaker.core.exporter.pde.exporter.TargetPlatformProjectExporter;
import org.bundlemaker.core.exporter.structure101.Structure101Exporter;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.util.BundleMakerProjectUtils;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.bundlemaker.core.util.ProgressMonitor;
import org.bundlemaker.core.util.StopWatch;
import org.bundlemaker.core.util.StopWatchProxy;
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
	private static final boolean PARSE = true;

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

		// get the default modularized system
		IModularizedSystem modularizedSystem = bundleMakerProject
				.getModularizedSystemWorkingCopy(IBundleMakerProject.DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID);

		// add the transformations
		IntegrationTestUtils.addEmbedAntTransformation(modularizedSystem);
		IntegrationTestUtils
				.addModularizeSpringTransformation(modularizedSystem);

		// // add the 'removeResourcesTransformation'
		// RemoveResourcesTransformation removeResourcesTransformation =
		// TransformationFactory.eINSTANCE
		// .createRemoveResourcesTransformation();
		// removeResourcesTransformation
		// .addResourceSet("testng", "5.8",
		// new String[] { "com/thoughtworks/qdox/model/*",
		// "com/thoughtworks/qdox/model/util/*",
		// "com/thoughtworks/qdox/*",
		// "com/thoughtworks/qdox/parser/*",
		// "com/thoughtworks/qdox/parser/structs/*",
		// "com/thoughtworks/qdox/directorywalker/*",
		// "com/thoughtworks/qdox/parser/impl/*",
		// "bsh/commands/*", "bsh/*", "bsh/servlet/*",
		// "bsh/org/objectweb/asm/*", "bsh/util/*",
		// "bsh/classpath/*", "bsh/collection/*",
		// "bsh/reflect/*" }, null);
		// modularizedSystem.getTransformations().add(
		// removeResourcesTransformation);

		// apply the transformation
		modularizedSystem.applyTransformations();

		modularizedSystem = (IModularizedSystem) StopWatchProxy
				.newInstance(modularizedSystem);
		//
		// //
		// assertEquals(112, modularizedSystem.getResourceModules().size());
		// assertEquals(1, modularizedSystem.getTypeModules().size());
		// assertEquals(113, modularizedSystem.getAllModules().size());
		//
		// AmbiguousPackages ambiguousPackages = new AmbiguousPackages(
		// modularizedSystem);
		//
		// Map<ITypeModule, String[]> moduleMap =
		// ambiguousPackages.getModuleMap();
		// for (ITypeModule module : moduleMap.keySet()) {
		//
		// // dump
		// System.out.println(" - "
		// + ModelUtils.toString(module.getModuleIdentifier()));
		// System.out.println("   " + Arrays.asList(moduleMap.get(module)));
		// }
		//
		// // for (IResourceModule resourceModule : modularizedSystem
		// // .getResourceModules()) {
		// //
		// // if (!resourceModule.getResources(ContentType.SOURCE).isEmpty()) {
		// //
		// // System.out.println(ModelUtils.toString(resourceModule
		// // .getModuleIdentifier()));
		// //
		// // IReferencedModules referencedModules = modularizedSystem
		// // .getReferencedModules(resourceModule);
		// //
		// // for (ITypeModule typeModule : referencedModules
		// // .getReferencedModulesMap().values()) {
		// // System.out.println(" - "
		// // + ModelUtils.toString(typeModule
		// // .getModuleIdentifier()));
		// // }
		// //
		// // for (Entry<String, IModuleList> entry : referencedModules
		// // .getTypesWithAmbiguousModules()) {
		// // System.out.println(" ~ " + entry.getKey());
		// // for (ITypeModule typeModule : entry.getValue().getModules()) {
		// // System.out.println("   ~~ "
		// // + ModelUtils.toString(typeModule
		// // .getModuleIdentifier()));
		// // }
		// // }
		// // }
		// // }
		//
		// // EMap<String, IModuleList> ambiguousPackages = modularizedSystem
		// // .getAmbiguousPackages();
		// //
		// // for (final Entry<String, IModuleList> ambiguousPackage :
		// // ambiguousPackages) {
		// //
		// // System.out.println(" - " + ambiguousPackage.getKey());
		// //
		// // for (ITypeModule typeModule : ambiguousPackage.getValue()
		// // .getModules()) {
		// //
		// // System.out
		// // .println("   - "
		// // + ModelUtils.toString(typeModule
		// // .getModuleIdentifier()));
		// //
		// // System.out.println("     "
		// // + typeModule.getContainedTypes(new IQueryFilter() {
		// // public boolean matches(String content) {
		// // Pattern pattern = Pattern
		// // .compile(ambiguousPackage.getKey()
		// // .replace(".", "\\.")
		// // + "\\.\\w*");
		// // return pattern.matcher(content).matches();
		// // }
		// // }));
		// // }
		// // }
		//
		// create the exporter context
		StandardModuleExporterContext exporterContext = new StandardModuleExporterContext(
				bundleMakerProject, new File("D:/temp"), modularizedSystem);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Structure101Exporter exporter = new Structure101Exporter();
		exporter.export(modularizedSystem, exporterContext);
		stopWatch.stop();
		System.out.println("Dauer " + stopWatch.getElapsedTime());

		exporterContext.put(
				PdeExporterAttributes.BUNDLE_DEPENDENCY_DESCRIPTION,
				PdeExporterAttributes.STRICT_IMPORT_PACKAGE);

		exporterContext
				.put(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY,
						new File(
								"R:/environments/bundlemaker2-environment/workspace/org.bundlemaker.itest.spring/templates"));

		new PdePluginProjectModuleExporter().export(modularizedSystem,
				exporterContext);

		new TargetPlatformProjectExporter().export(modularizedSystem,
				exporterContext);

		// ModifiableModularizedSystem system = (ModifiableModularizedSystem)
		// modularizedSystem;
		//
		// StopWatch stopWatch = new StopWatch();
		// stopWatch.start();
		// system.getContainingModules("org.springframework.jca.context.BootstrapContextAwareProcessor");
		// stopWatch.stop();
		// System.out.println(stopWatch.getElapsedTime());
		//
		// stopWatch = new StopWatch();
		// stopWatch.start();
		// IResourceModule resourceModule =
		// system.getResourceModule(ModelUtils.createModuleIdentifier("Spring",
		// "2.5.6"));
		// system.getUnsatisfiedReferencedTypes(resourceModule, true, true);
		// stopWatch.stop();
		// System.out.println(stopWatch.getElapsedTime());
		//
		// stopWatch = new StopWatch();
		// stopWatch.start();
		// resourceModule =
		// system.getResourceModule(ModelUtils.createModuleIdentifier("Spring",
		// "2.5.6"));
		// system.getUnsatisfiedReferencedPackages(resourceModule, true, true);
		// stopWatch.stop();
		// System.out.println(stopWatch.getElapsedTime());

		// delete the destination directory
		// File destinationDirectory = new
		// File(System.getProperty("user.dir"),
		// "destination");

		// File destinationDirectory = new File("D:/temp/temp");
		// IntegrationTestUtils.delete(destinationDirectory);
		//
		// // create the exporter context
		// StandardModuleExporterContext context = new
		// StandardModuleExporterContext(
		// bundleMakerProject, destinationDirectory, modularizedSystem);
		// context.put(IModuleExporterContext.TEMPLATE_DIRECTORY,
		// new File(System.getProperty("user.dir"), "templates"));
		//
		// // export the modules
		// new StandardBundlorBasedBinaryBundleExporter().export(
		// modularizedSystem, context);
		// // new Structure101Exporter().export(modularizedSystem, context);
		// new PdePluginProjectModuleExporter().export(modularizedSystem,
		// context);
	}
}
