package org.bundlemaker.core.exporter.bundle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.bundlemaker.core.exporter.AbstractJarFileBundleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.exporter.ManifestUtils;
import org.bundlemaker.core.internal.projectdescription.ResourceContent;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.spi.resource.Resource;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.BundleManifest;
import com.springsource.util.osgi.manifest.BundleManifestFactory;
import com.springsource.util.osgi.manifest.BundleSymbolicName;
import com.springsource.util.osgi.manifest.ExportPackage;
import com.springsource.util.osgi.manifest.ExportedPackage;
import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.ImportedPackage;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class BinaryBundleExporter extends AbstractJarFileBundleExporter {

	/** - */
	private List<HeaderDeclaration> EMPTY_HEADERDECLARATION_LIST = Collections
			.emptyList();

	/** - */
	private BundleManifest _currentBundleManifest;

	/** - */
	private IModularizedSystem _currentModularizedSystem;

	/** - */
	private IResourceModule _currentModule;

	/** - */
	private IModuleExporterContext _currentContext;

	/** - */
	private ManifestContents _currentManifestTemplate;

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
	protected ManifestContents createManifest(
			IModularizedSystem modularizedSystem, IResourceModule module,
			IModuleExporterContext context) throws Exception {

		//
		_currentBundleManifest = BundleManifestFactory.createBundleManifest();
		_currentModularizedSystem = modularizedSystem;
		_currentModule = module;
		_currentContext = context;

		//
		_currentManifestTemplate = getManifestTemplate(modularizedSystem,
				module, context);

		//
		createBundleManifestVersion();
		createBundleSymbolicName();
		createBundleVersion();
		createImportPackage();
		createExportPackage();

		// get the result
		ManifestContents manifestContents = ManifestUtils
				.toManifestContents(_currentBundleManifest);

		// copy the original
		if (module.containsResource("META-INF/MANIFEST.MF", ContentType.BINARY)) {

			List<String> headersToIgnore = Arrays.asList(new String[] {
					Constants.BUNDLE_MANIFESTVERSION,
					Constants.BUNDLE_SYMBOLICNAME, Constants.BUNDLE_VERSION,
					Constants.IMPORT_PACKAGE, Constants.EXPORT_PACKAGE });

			IResource resource = module.getResource("META-INF/MANIFEST.MF",
					ContentType.BINARY);

			Manifest manifest = new Manifest(resource.getInputStream());

			for (Entry<Object, Object> entry : manifest.getMainAttributes()
					.entrySet()) {

				if (!headersToIgnore.contains(entry.getKey())) {
					manifestContents.getMainAttributes().put(
							entry.getKey().toString(),
							manifest.getMainAttributes().getValue(
									entry.getKey().toString()));
				}
			}
		}

		// return the result
		return manifestContents;
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createBundleManifestVersion() {

		//
		_currentBundleManifest.setBundleManifestVersion(2);
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createBundleSymbolicName() {

		// get the BundleSymbolicName 'header'
		BundleSymbolicName bundleSymbolicName = _currentBundleManifest
				.getBundleSymbolicName();

		// set the symbolic name
		bundleSymbolicName.setSymbolicName(_currentModule.getModuleIdentifier()
				.getName());
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
			version = new Version(_currentModule

			.getModuleIdentifier().getVersion());
		} catch (Exception e) {

			//
			version = new Version("1.0.0");
		}

		// get the BundleSymbolicName 'header'
		_currentBundleManifest.setBundleVersion(version);
	}

	/**
	 * <p>
	 * </p>
	 */
	protected void createImportPackage() {

		// get the import package 'header'
		ImportPackage importPackage = _currentBundleManifest.getImportPackage();

		// get the import package template
		String importPackageTemplateHeader = _currentManifestTemplate
				.getMainAttributes().get(TemplateConstants.IMPORT_TEMPLATE);

		// parse the declarations
		List<HeaderDeclaration> importPackageTemplates = importPackageTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.IMPORT_PACKAGE)
				: EMPTY_HEADERDECLARATION_LIST;

		// get all referenced package names
		Set<String> packageNames = _currentModule.getReferencedPackageNames(
				true, false);

		//
		for (String packageName : packageNames) {

			// create the 'ImportedPackage' instance
			ImportedPackage importedPackage = importPackage
					.addImportedPackage(packageName);

			// get the template
			HeaderDeclaration importPackageTemplate = ManifestUtils
					.findMostSpecificDeclaration(importPackageTemplates,
							packageName);

			// assign the template values
			if (importPackageTemplate != null) {

				// add the attributes
				importedPackage.getAttributes().putAll(
						importPackageTemplate.getAttributes());

				// add the directives
				importedPackage.getDirectives().putAll(
						importPackageTemplate.getDirectives());
			}
		}
	}

	protected void createExportPackage() {

		// get the export package 'header'
		ExportPackage exportPackage = _currentBundleManifest.getExportPackage();

		// // get the import package template
		// String importPackageTemplateHeader = _currentManifestTemplate
		// .getMainAttributes().get(TemplateConstants.IMPORT_TEMPLATE);

		// get all referenced package names
		Set<String> packageNames = _currentModule.getContainedPackageNames();

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
