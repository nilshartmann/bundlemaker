package org.bundlemaker.core.exporter.bundle;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ManifestUtils;
import org.bundlemaker.core.exporter.ModuleExporterUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.osgi.manifest.BundleSymbolicName;
import com.springsource.util.osgi.manifest.ExportPackage;
import com.springsource.util.osgi.manifest.ExportedPackage;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleManifestCreator {

	/** - */
	private IResourceModule _resourceModule;

	/** - */
	private BundleManifest _newBundleManifest;

	/** - */
	private ManifestContents _manifestTemplate;

	/** - */
	private IModularizedSystem _modularizedSystem;

	/** - */
	private IModuleExporterContext _context;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceModule
	 * @param manifestTemplate
	 * @param modularizedSystem
	 * @param context
	 */
	public BundleManifestCreator(IResourceModule resourceModule,
			ManifestContents manifestTemplate,
			IModularizedSystem modularizedSystem, IModuleExporterContext context) {

		Assert.isNotNull(resourceModule);
		Assert.isNotNull(manifestTemplate);
		Assert.isNotNull(modularizedSystem);
		Assert.isNotNull(context);

		_resourceModule = resourceModule;
		_manifestTemplate = manifestTemplate;
		_modularizedSystem = modularizedSystem;
		_context = context;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param module
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public ManifestContents createManifest() throws Exception {

		// the existing bundle manifest resource
		IResource existingManifestResource = _resourceModule.getResource(
				"META-INF/MANIFEST.MF", ContentType.BINARY);

		// the existing bundle manifest
		ManifestContents existingManifest = ManifestUtils
				.readManifestContents(existingManifestResource);

		// return immediately if manifest already is a bundle manifest
		if (isBundleManifest(existingManifest)
				&& !ModuleExporterUtils.requiresRepackaging(_resourceModule,
						ContentType.BINARY)) {

			// return the existing manifest
			return existingManifest;
		}

		// create a new bundle manifest
		_newBundleManifest = BundleManifestFactory.createBundleManifest();

		// set the header
		createBundleManifestVersion();
		createBundleSymbolicName();
		createBundleVersion();
		createImportPackageAndRequiredBundle();
		createExportPackage();

		// get the new manifest contents
		ManifestContents newManifestContents = ManifestUtils
				.toManifestContents(_newBundleManifest);

		// copy the original headers
		List<String> headersToIgnore = Arrays.asList(new String[] {
				Constants.BUNDLE_MANIFESTVERSION,
				Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
				Constants.IMPORT_PACKAGE, Constants.EXPORT_PACKAGE });

		// iterate over the exiting main attributes
		for (Entry<String, String> entry : existingManifest.getMainAttributes()
				.entrySet()) {

			// copy if not ignored
			if (!headersToIgnore.contains(entry.getKey())) {

				//
				newManifestContents.getMainAttributes().put(
						entry.getKey().toString(),
						existingManifest.getMainAttributes().get(
								entry.getKey().toString()));
			}
		}

		//
		System.out.println(newManifestContents.getMainAttributes());

		// return the result
		return newManifestContents;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param existingManifest
	 * @return
	 */
	protected boolean isBundleManifest(ManifestContents existingManifest) {

		//
		return existingManifest.getMainAttributes().containsKey(
				Constants.BUNDLE_SYMBOLICNAME);
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createBundleManifestVersion() {

		// set the bundle manifest version to '2'
		_newBundleManifest.setBundleManifestVersion(2);
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createBundleSymbolicName() {

		// get the BundleSymbolicName 'header'
		BundleSymbolicName bundleSymbolicName = _newBundleManifest
				.getBundleSymbolicName();

		// set the symbolic name
		bundleSymbolicName.setSymbolicName(_resourceModule
				.getModuleIdentifier().getName());
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createBundleVersion() {

		//
		Version version = null;

		try {

			//
			version = new Version(_resourceModule.getModuleIdentifier()
					.getVersion());
		} catch (Exception e) {

			//
			version = new Version("1.0.0");
		}

		// get the BundleSymbolicName 'header'
		_newBundleManifest.setBundleVersion(version);
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createImportPackageAndRequiredBundle() {

		ImportResolver importResolver = new ImportResolver(_modularizedSystem,
				_resourceModule, _newBundleManifest.getImportPackage(),
				_newBundleManifest.getRequireBundle(), _manifestTemplate);

		importResolver.addImportPackageAndRequiredBundle();
	}

	protected void createExportPackage() {

		// get the export package 'header'
		ExportPackage exportPackage = _newBundleManifest.getExportPackage();

		// // get the import package template
		// String importPackageTemplateHeader = _currentManifestTemplate
		// .getMainAttributes().get(TemplateConstants.IMPORT_TEMPLATE);

		// get all referenced package names
		Set<String> packageNames = _resourceModule.getContainedPackageNames();

		//
		for (String packageName : packageNames) {

			// create the 'ExportPackage' instance
			ExportedPackage exportedPackage = exportPackage
					.addExportedPackage(packageName);

			// // get the template
			// HeaderDeclaration importPackageTemplate = ManifestUtils
			// .findMostSpecificDeclaration(importPackageTemplates,
			// packageName);

			// // assign the template values
			// if (importPackageTemplate != null) {
			//
			// // add the attributes
			// importedPackage.getAttributes().putAll(
			// importPackageTemplate.getAttributes());
			//
			// // add the directives
			// importedPackage.getDirectives().putAll(
			// importPackageTemplate.getDirectives());
			// }
		}
	}
}
