package org.bundlemaker.core.exporter.pde.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.pde.Activator;
import org.bundlemaker.core.exporter.util.Helper;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.pde.core.project.IBundleClasspathEntry;
import org.eclipse.pde.core.project.IBundleProjectDescription;
import org.eclipse.pde.core.project.IBundleProjectService;
import org.eclipse.pde.core.project.IPackageExportDescription;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.osgi.framework.Version;

/**
 * h
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PdePluginProjectModuleExporter extends AbstractExporter {

	/** - */
	private static final String SRC_DIRECTORY_NAME = "src";

	/** - */
	private static final String BIN_DIRECTORY_NAME = "bin";

	/** - */
	private PdeManifestStyle _dependencyDescriptionStyle = PdeManifestStyle.STRICT_REQUIRE_BUNDLE;

	/** - */
	private boolean _useClassifcationForExportDestination;

	public PdeManifestStyle getDependencyDescriptionStyle() {
		return _dependencyDescriptionStyle;
	}

	public void setDependencyDescriptionStyle(
			PdeManifestStyle dependencyDescriptionStyle) {
		_dependencyDescriptionStyle = dependencyDescriptionStyle;
	}

	public boolean isUseClassifcationForExportDestination() {
		return _useClassifcationForExportDestination;
	}

	public void setUseClassifcationForExportDestination(
			boolean useClassifcationForExportDestination) {
		_useClassifcationForExportDestination = useClassifcationForExportDestination;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		//
		// TODO: Convenience method, e.g. 'isSourceModule' ...
		return !module.getResources(ContentType.SOURCE).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doExport() throws CoreException {

		// get a non-existing project name
		String projectName = Helper.getUniqueProjectName(getCurrentModule()
				.getModuleIdentifier().getName());

		// delete and create project
		IPath location = null;

		if (isUseClassifcationForExportDestination()) {

			Path destinationDirectoryPath = new Path(getCurrentContext()
					.getDestinationDirectory().getAbsolutePath());

			location = destinationDirectoryPath.append(
					getCurrentModule().getClassification()).append(projectName);
		}

		//
		IProject project = Helper.deleteAndCreateProject(projectName, location);

		// add java and plug-nature
		IProjectDescription description = project.getDescription();
		description.setNatureIds(new String[] { JavaCore.NATURE_ID,
				IBundleProjectDescription.PLUGIN_NATURE });
		project.setDescription(description, null);

		// 'clean' the java project
		IJavaProject javaProject = JavaCore.create(project);
		javaProject.setRawClasspath(new IClasspathEntry[] { JavaRuntime
				.getDefaultJREContainerEntry() }, null);
		javaProject.save(null, true);

		// **************************************

		// File templateDirectory = (File) getCurrentContext()
		// .getAttribute(Pde.TEMPLATE_DIRECTORY);
		//
		// File template = new File(templateDirectory, String.format(
		// "%s.template", module.getModuleIdentifier().toString()));
		//
		// if (template.exists()) {
		//
		// // MatchUtils
		// RecoveringManifestParser manifestParser = new
		// RecoveringManifestParser();
		//
		// //
		// ManifestContents templateManifest = manifestParser
		// .parse(new FileReader(template));
		//
		// //
		// String requireBundle = templateManifest.getMainAttributes().get(
		// Constants.REQUIRE_BUNDLE);
		//
		// List<HeaderDeclaration> requireBundleDeclarations = ManifestUtils
		// .parseManifestValue(requireBundle);
		//
		// HeaderDeclaration declaration = ManifestUtils
		// .findMostSpecificDeclaration(requireBundleDeclarations,
		// "blub");
		//
		// System.out.println(declaration);
		// }

		// *********************************************************

		//
		IBundleProjectService bundleProjectService = Activator
				.getBundleProjectService();

		//
		IBundleProjectDescription bundleProjectDescription = bundleProjectService
				.getDescription(project);

		//
		bundleProjectDescription.setSymbolicName(getCurrentModule()
				.getModuleIdentifier().getName());

		bundleProjectDescription.setBundleVersion(new Version(
				getCurrentModule().getModuleIdentifier().getVersion()));

		if (getDependencyDescriptionStyle().equals(
				PdeManifestStyle.STRICT_IMPORT_PACKAGE)) {

			// import packages
			addImportPackages(getCurrentModule(),
					getCurrentModularizedSystem(), bundleProjectService,
					bundleProjectDescription);

		} else if (getDependencyDescriptionStyle().equals(
				PdeManifestStyle.STRICT_REQUIRE_BUNDLE)) {

			// require bundles
			addRequireBundle(getCurrentModule(), getCurrentModularizedSystem(),
					bundleProjectService, bundleProjectDescription);
		}

		// export packages
		Set<String> containedPackages = getCurrentModule()
				.getContainedPackageNames();
		List<IPackageExportDescription> exportDescriptions = new LinkedList<IPackageExportDescription>();
		for (String containedPackage : containedPackages) {
			IPackageExportDescription exportDescription = bundleProjectService
					.newPackageExport(containedPackage, null, true, null);
			exportDescriptions.add(exportDescription);
		}
		bundleProjectDescription.setPackageExports(exportDescriptions
				.toArray(new IPackageExportDescription[0]));

		// set source dir
		IBundleClasspathEntry bundleClasspathEntry = bundleProjectService
				.newBundleClasspathEntry(new Path(SRC_DIRECTORY_NAME),
						new Path(BIN_DIRECTORY_NAME), null);

		//
		bundleProjectDescription
				.setBundleClassath(new IBundleClasspathEntry[] { bundleClasspathEntry });

		//
		bundleProjectDescription.apply(null);

		IFolder srcFolder = project.getFolder(SRC_DIRECTORY_NAME);

		// copy the source
		for (IResource resourceStandin : getCurrentModule().getResources(
				ContentType.SOURCE)) {

			if (!resourceStandin.getPath().startsWith("META-INF")) {

				//
				File targetFile = new File(srcFolder.getRawLocation().toFile(),
						resourceStandin.getPath());
				targetFile.getParentFile().mkdirs();

				File inputFile = new File(resourceStandin.getRoot(),
						resourceStandin.getPath());

				try {
					//
					FileUtils.copy(resourceStandin.getInputStream(),
							new FileOutputStream(targetFile), new byte[1024]);
				} catch (Exception e) {
					// TODO
					e.printStackTrace();
					throw new CoreException(new Status(IStatus.ERROR, "asd", "asd"));
				}
			}
		}
	}

	private void addRequireBundle(IResourceModule module,
			IModularizedSystem modularizedSystem,
			IBundleProjectService bundleProjectService,
			IBundleProjectDescription bundleProjectDescription) {

		//
		IReferencedModulesQueryResult referencedModules = modularizedSystem
				.getReferencedModules(module, true, true);

		List<IRequiredBundleDescription> requiredBundleDescriptions = new LinkedList<IRequiredBundleDescription>();

		//
		for (IModule typeModule : referencedModules.getReferencedModules()) {

			if (!typeModule.equals(modularizedSystem.getExecutionEnvironment())) {

				IRequiredBundleDescription requiredBundleDescription = bundleProjectService
						.newRequiredBundle(typeModule.getModuleIdentifier()
								.getName(), null, false, true);

				requiredBundleDescriptions.add(requiredBundleDescription);
			}
		}

		bundleProjectDescription.setRequiredBundles(requiredBundleDescriptions
				.toArray(new IRequiredBundleDescription[0]));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @param modularizedSystem
	 * @param bundleProjectService
	 * @param bundleProjectDescription
	 */
	private void addImportPackages(IResourceModule module,
			IModularizedSystem modularizedSystem,
			IBundleProjectService bundleProjectService,
			IBundleProjectDescription bundleProjectDescription) {

		Set<String> referencedPackages = module.getReferencedPackageNames(true,
				true, true);

		List<IPackageImportDescription> importDescriptions = new LinkedList<IPackageImportDescription>();

		for (String referencedPackage : referencedPackages) {

			IPackageImportDescription importDescription = bundleProjectService
					.newPackageImport(referencedPackage, null, false);

			importDescriptions.add(importDescription);
		}

		bundleProjectDescription.setPackageImports(importDescriptions
				.toArray(new IPackageImportDescription[0]));
	}
}
