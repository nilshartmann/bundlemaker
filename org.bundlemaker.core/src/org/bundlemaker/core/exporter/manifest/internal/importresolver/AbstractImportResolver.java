package org.bundlemaker.core.exporter.manifest.internal.importresolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.exporter.manifest.internal.AbstractResolver;
import org.bundlemaker.core.exporter.manifest.internal.CurrentModule;
import org.bundlemaker.core.exporter.manifest.internal.ManifestConstants;
import org.bundlemaker.core.exporter.manifest.internal.ManifestUtils;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Constants;

import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.VersionRange;
import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.ImportedPackage;
import com.springsource.util.osgi.manifest.RequireBundle;
import com.springsource.util.osgi.manifest.RequiredBundle;
import com.springsource.util.osgi.manifest.RequiredBundle.Visibility;
import com.springsource.util.osgi.manifest.Resolution;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;

/**
 */
public abstract class AbstractImportResolver extends AbstractResolver {

	/** - */
	private ReferencesCache _referencesCache;

	/** - */
	private ImportPackage _importPackage;

	/** - */
	private RequireBundle _requireBundle;

	/** - */
	private List<HeaderDeclaration> _importPackageTemplates;

	/** - */
	private List<HeaderDeclaration> _requireBundleTemplates;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modularizedSystem
	 * @param resourceModule
	 * @param manifestTemplate
	 * @param importPackage
	 * @param requireBundle
	 */
	public AbstractImportResolver(CurrentModule currentModule,
			ImportPackage importPackage, RequireBundle requireBundle) {

		super(currentModule);

		Assert.isNotNull(importPackage);
		Assert.isNotNull(requireBundle);

		//
		_importPackage = importPackage;
		_requireBundle = requireBundle;

		//
		// TODO
		_referencesCache = new ReferencesCache(
				currentModule.getModularizedSystem(),
				currentModule.getResourceModule(), true, true);

		//
		initTemplates();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected final ReferencesCache getReferencesCache() {
		return _referencesCache;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public ImportPackage getImportPackage() {
		return _importPackage;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public RequireBundle getRequireBundle() {
		return _requireBundle;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public List<HeaderDeclaration> getImportPackageTemplates() {
		return _importPackageTemplates;
	}

	/**
	 * <p>
	 * </p>
	 */
	public abstract void addImportPackageAndRequiredBundle();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 */
	public final void addImportedPackage(String packageName) {

		//
		addImportedPackage(packageName, null, null, null, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 * @param resolution
	 */
	public final void addImportedPackage(String packageName,
			Resolution resolution) {

		//
		addImportedPackage(packageName, resolution, null, null, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 * @param resolution
	 * @param packageVersion
	 */
	public final void addImportedPackage(String packageName,
			Resolution resolution, VersionRange packageVersion) {

		//
		addImportedPackage(packageName, resolution, packageVersion, null, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 */
	public final void addImportedPackage(String packageName,
			Resolution resolution, VersionRange packageVersion,
			String bundleSymbolicName, VersionRange bundleVersion) {

		// step 1: create the 'ImportedPackage' instance
		ImportedPackage importedPackage = _importPackage
				.addImportedPackage(packageName);

		// step 2: set the attributes
		if (resolution != null) {
			importedPackage.setResolution(resolution);
		}
		if (packageVersion != null) {
			importedPackage.setVersion(packageVersion);
		}
		if (bundleSymbolicName != null) {
			importedPackage.setBundleSymbolicName(bundleSymbolicName);
		}
		if (bundleVersion != null) {
			importedPackage.setBundleVersion(bundleVersion);
		}

		// finally: the template ALWAYS overrides the computed values
		HeaderDeclaration importPackageTemplate = ManifestUtils
				.findMostSpecificDeclaration(_importPackageTemplates,
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

	/**
	 * <p>
	 * </p>
	 */
	public final void addRequireBundle(String bundleSymbolicName,
			VersionRange bundleVersion, Resolution resolution,
			Visibility visibility) {

		// step 1: create the 'RequiredBundle' instance
		RequiredBundle requiredBundle = _requireBundle
				.addRequiredBundle(bundleSymbolicName);

		// step 2: set the attributes
		if (bundleVersion != null) {
			requiredBundle.setBundleVersion(bundleVersion);
		}
		if (resolution != null) {
			requiredBundle.setResolution(resolution);
		}
		if (visibility != null) {
			requiredBundle.setVisibility(visibility);
		}

		// finally: the template ALWAYS overrides the computed values
		HeaderDeclaration requireBundleTemplate = ManifestUtils
				.findMostSpecificDeclaration(_requireBundleTemplates,
						bundleSymbolicName);

		// assign the template values
		if (requireBundleTemplate != null) {

			// add the attributes
			requiredBundle.getAttributes().putAll(
					requireBundleTemplate.getAttributes());

			// add the directives
			requiredBundle.getDirectives().putAll(
					requireBundleTemplate.getDirectives());
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleSymbolicName
	 */
	public final void addRequireBundle(String bundleSymbolicName) {

		addRequireBundle(bundleSymbolicName, null, null, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleSymbolicName
	 * @param bundleVersion
	 */
	public final void addRequireBundle(String bundleSymbolicName,
			VersionRange bundleVersion) {

		addRequireBundle(bundleSymbolicName, bundleVersion, null, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 * @return
	 */
	protected boolean containsUnsatisfiedTypes(String packageName) {

		//
		Set<String> typeNames = getReferencesCache()
				.getReferencedPackageToContainingTypesCache().get(packageName);

		//
		for (String typeName : typeNames) {
			if (getReferencesCache().getUnsatisfiedTypes().contains(typeName)) {
				return true;
			}
		}

		//
		return false;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param exportingModules
	 * @param set
	 * @return
	 */
	protected List<IModule> reduce(List<IModule> exportingModules,
			Set<String> typeNames) {

		Assert.isNotNull(exportingModules);
		Assert.isNotNull(typeNames);

		//
		for (IModule module : exportingModules) {
			if (module.containsAll(typeNames)) {
				List<IModule> result = new LinkedList<IModule>();
				result.add(module);
				return result;
			}
		}

		//
		return exportingModules;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 * @return
	 */
	protected List<IModule> getExportingModules(String packageName) {

		//
		List<IModule> result = new ArrayList<IModule>();

		//
		Set<String> types = getReferencesCache()
				.getReferencedPackageToContainingTypesCache().get(packageName);

		for (String type : types) {

			Set<IModule> module = getReferencesCache()
					.getReferenceTypeToExportingModuleCache().get(type);

			if (module != null) {
				result.addAll(module);
			} else {
				System.out.println("No Module for " + type);
			}

		}

		//
		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 */
	private void initTemplates() {

		// get the import package template
		String importPackageTemplateHeader = getManifestTemplate()
				.getMainAttributes().get(
						ManifestConstants.HEADER_IMPORT_TEMPLATE);

		_importPackageTemplates = importPackageTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.IMPORT_PACKAGE)
				: ManifestConstants.EMPTY_HEADERDECLARATION_LIST;

		// get the require bundle template
		String requiredBundleTemplateHeader = getManifestTemplate()
				.getMainAttributes().get(
						ManifestConstants.HEADER_REQUIRE_BUNDLE_TEMPLATE);

		_requireBundleTemplates = requiredBundleTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.REQUIRE_BUNDLE)
				: ManifestConstants.EMPTY_HEADERDECLARATION_LIST;

	}

}
