package org.bundlemaker.core.exporter.bundle;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.exporter.ManifestUtils;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.util.GenericCache;
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
import com.springsource.util.parser.manifest.ManifestContents;

/**
 */
public abstract class AbstractImportResolver {

	/** - */
	private static final Object IMPORT_TEMPLATE = "Import-Template";

	/** - */
	private static final Object REQUIRE_BUNDLE_TEMPLATE = "RequiredBundle-Template";

	/** - */
	private List<HeaderDeclaration> EMPTY_HEADERDECLARATION_LIST = Collections
			.emptyList();

	/** - */
	private IModularizedSystem _modularizedSystem;

	/** - */
	private IResourceModule _resourceModule;

	/** - */
	private ImportPackage _importPackage;

	/** - */
	private RequireBundle _requireBundle;

	/** - */
	private ManifestContents _manifestTemplate;

	/** - */
	private GenericCache<String, Set<IModule>> _typeToModuleCache;

	/** - */
	private GenericCache<String, Set<String>> _packageToTypesCache;

	/** - */
	private Set<String> _unsatisfiedTypes;

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
	 * @param importPackage
	 * @param requireBundle
	 * @param manifestTemplate
	 */
	public AbstractImportResolver(IModularizedSystem modularizedSystem,
			IResourceModule resourceModule, ImportPackage importPackage,
			RequireBundle requireBundle, ManifestContents manifestTemplate) {

		Assert.isNotNull(modularizedSystem);
		Assert.isNotNull(resourceModule);
		Assert.isNotNull(importPackage);
		Assert.isNotNull(requireBundle);
		Assert.isNotNull(manifestTemplate);

		//
		_modularizedSystem = modularizedSystem;
		_resourceModule = resourceModule;
		_importPackage = importPackage;
		_requireBundle = requireBundle;
		_manifestTemplate = manifestTemplate;

		//
		_typeToModuleCache = new GenericCache<String, Set<IModule>>() {
			@Override
			protected Set<IModule> create(String key) {
				return new HashSet<IModule>();
			}
		};

		//
		_packageToTypesCache = new GenericCache<String, Set<String>>() {
			@Override
			protected Set<String> create(String key) {
				return new HashSet<String>();
			}
		};

		//
		_unsatisfiedTypes = new HashSet<String>();

		//
		initializeCaches();
		initTemplates();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IModularizedSystem getModularizedSystem() {
		return _modularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IResourceModule getResourceModule() {
		return _resourceModule;
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
	public ManifestContents getManifestTemplate() {
		return _manifestTemplate;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public GenericCache<String, Set<IModule>> getTypeToModuleCache() {
		return _typeToModuleCache;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public GenericCache<String, Set<String>> getPackageToTypesCache() {
		return _packageToTypesCache;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Set<String> getUnsatisfiedTypes() {
		return _unsatisfiedTypes;
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
	 */
	private void initializeCaches() {

		//
		for (String typeName : _resourceModule.getReferencedTypeNames(true,
				false, false)) {

			// get the package type
			String packageName = typeName.contains(".") ? typeName.substring(0,
					typeName.lastIndexOf('.')) : "";

			// add to the package to type cache
			_packageToTypesCache.getOrCreate(packageName).add(typeName);

			// get the modules
			Set<IModule> modules = _modularizedSystem
					.getContainingModules(typeName);

			// add to the type caches
			if (modules.isEmpty()) {
				_unsatisfiedTypes.add(typeName);
			} else {
				_typeToModuleCache.getOrCreate(typeName).addAll(modules);
			}
		}
	}

	private void initTemplates() {

		// get the import package template
		String importPackageTemplateHeader = _manifestTemplate
				.getMainAttributes().get(IMPORT_TEMPLATE);

		_importPackageTemplates = importPackageTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.IMPORT_PACKAGE)
				: EMPTY_HEADERDECLARATION_LIST;

		// get the require bundle template
		String requiredBundleTemplateHeader = _manifestTemplate
				.getMainAttributes().get(REQUIRE_BUNDLE_TEMPLATE);

		_requireBundleTemplates = requiredBundleTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.REQUIRE_BUNDLE)
				: EMPTY_HEADERDECLARATION_LIST;

	}

}
