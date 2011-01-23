package org.bundlemaker.core.exporter.pde.exporter;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.exporter.AbstractExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.bundlor.StandardBundlorBasedBinaryBundleExporter;
import org.bundlemaker.core.exporter.pde.Activator;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IReferencedModulesQueryResult;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.springsource.bundlor.util.MatchUtils;
import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;
import com.springsource.util.parser.manifest.RecoveringManifestParser;

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
	public void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws Exception {

		// get a non-existing project name
		String projectName = getUniqueProjectName(module.getModuleIdentifier()
				.getName());

		// delete and create project
		IPath location = null;

		if (getConfiguration(context).isUseClassifcationForExportDestination()) {

			Path destinationDirectoryPath = new Path(context
					.getDestinationDirectory().getAbsolutePath());

			location = destinationDirectoryPath.append(
					module.getClassification()).append(projectName);
		}

		//
		IProject project = deleteAndCreateProject(projectName, location);

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

		File templateDirectory = (File) context
				.getAttribute(StandardBundlorBasedBinaryBundleExporter.TEMPLATE_DIRECTORY);

		File template = new File(templateDirectory, String.format(
				"%s.template", module.getModuleIdentifier().toString()));

		if (template.exists()) {

			// MatchUtils
			RecoveringManifestParser manifestParser = new RecoveringManifestParser();

			//
			ManifestContents templateManifest = manifestParser
					.parse(new FileReader(template));

			//
			String requireBundle = templateManifest.getMainAttributes().get(
					Constants.REQUIRE_BUNDLE);

			List<HeaderDeclaration> requireBundleDeclarations = parseTemplate(requireBundle);

			HeaderDeclaration declaration = findMostSpecificDeclaration(
					requireBundleDeclarations, "blub");

			System.out.println(declaration);
		}

		// *********************************************************

		//
		IBundleProjectService bundleProjectService = Activator
				.getBundleProjectService();

		//
		IBundleProjectDescription bundleProjectDescription = bundleProjectService
				.getDescription(project);

		//
		bundleProjectDescription.setSymbolicName(module.getModuleIdentifier()
				.getName());

		bundleProjectDescription.setBundleVersion(new Version(module
				.getModuleIdentifier().getVersion()));

		if (getConfiguration(context).equals(
				PdeExporterConfiguration.STRICT_IMPORT_PACKAGE)) {

			// import packages
			addImportPackages(module, modularizedSystem, bundleProjectService,
					bundleProjectDescription);

		} else if (getConfiguration(context).equals(
				PdeExporterConfiguration.STRICT_REQUIRE_BUNDLE)) {

			// require bundles
			addRequireBundle(module, modularizedSystem, bundleProjectService,
					bundleProjectDescription);
		}

		// export packages
		Set<String> containedPackages = module.getContainedPackages();
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
		for (IResourceStandin resourceStandin : module
				.getResources(ContentType.SOURCE)) {

			if (!resourceStandin.getPath().startsWith("META-INF")) {

				//
				File targetFile = new File(srcFolder.getRawLocation().toFile(),
						resourceStandin.getPath());
				targetFile.getParentFile().mkdirs();

				File inputFile = new File(resourceStandin.getRoot(),
						resourceStandin.getPath());

				//
				FileUtils.copyFile(inputFile, targetFile);
			}
		}
	}

	private void addRequireBundle(IResourceModule module,
			IModularizedSystem modularizedSystem,
			IBundleProjectService bundleProjectService,
			IBundleProjectDescription bundleProjectDescription) {

		//
		IReferencedModulesQueryResult referencedModules = modularizedSystem
				.getReferencedModules(module);

		List<IRequiredBundleDescription> requiredBundleDescriptions = new LinkedList<IRequiredBundleDescription>();

		//
		for (ITypeModule typeModule : referencedModules.getReferencedModules()) {

			if (!typeModule.equals(modularizedSystem
					.getExecutionEnvironmentTypeModule())) {

				IRequiredBundleDescription requiredBundleDescription = bundleProjectService
						.newRequiredBundle(typeModule.getModuleIdentifier()
								.getName(), null, false, true);

				requiredBundleDescriptions.add(requiredBundleDescription);
			}
		}

		bundleProjectDescription.setRequiredBundles(requiredBundleDescriptions
				.toArray(new IRequiredBundleDescription[0]));
	}

	private void addImportPackages(IResourceModule module,
			IModularizedSystem modularizedSystem,
			IBundleProjectService bundleProjectService,
			IBundleProjectDescription bundleProjectDescription) {

		Set<String> referencedPackages = module.getReferencedPackages(true,
				true);

		List<IPackageImportDescription> importDescriptions = new LinkedList<IPackageImportDescription>();

		for (String referencedPackage : referencedPackages) {

			IPackageImportDescription importDescription = bundleProjectService
					.newPackageImport(referencedPackage, null, false);

			importDescriptions.add(importDescription);
		}

		bundleProjectDescription.setPackageImports(importDescriptions
				.toArray(new IPackageImportDescription[0]));
	}

	private List<HeaderDeclaration> parseTemplate(String template) {

		if (template != null && !template.isEmpty()) {
			return HeaderParserFactory
					.newHeaderParser(new SimpleParserLogger()).parseHeader(
							template);
		} else {
			return new ArrayList<HeaderDeclaration>(0);
		}
	}

	private HeaderDeclaration findMostSpecificDeclaration(
			List<HeaderDeclaration> declarations, String packageName) {

		HeaderDeclaration match = null;
		int matchSpecificity = -1;

		for (HeaderDeclaration headerDeclaration : declarations) {
			for (String stem : headerDeclaration.getNames()) {
				int m = MatchUtils.rankedMatch(packageName, stem);
				if (m > matchSpecificity) {
					match = headerDeclaration;
					matchSpecificity = m;
				}
			}
		}
		return match;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param context
	 * @return
	 */
	private PdeExporterConfiguration getConfiguration(
			IModuleExporterContext context) {

		// get the result
		PdeExporterConfiguration result = (PdeExporterConfiguration) context
				.getAttribute(PdeExporterConfiguration.KEY);

		// return the result
		return result;
	}
}
