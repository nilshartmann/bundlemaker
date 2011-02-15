package org.bundlemaker.core.exporter.bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IType;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Constants;

import com.springsource.bundlor.util.SimpleParserLogger;
import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.RequireBundle;
import com.springsource.util.osgi.manifest.parse.HeaderDeclaration;
import com.springsource.util.osgi.manifest.parse.HeaderParserFactory;
import com.springsource.util.parser.manifest.ManifestContents;

public class ImportResolver {

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
	public ImportResolver(IModularizedSystem modularizedSystem,
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
	 */
	public void addImportPackageAndRequiredBundle() {

		//
		for (String packageName : _packageToTypesCache.getMap().keySet()) {

			//
			List<IModule> exportingModules = getExportingModules(packageName);

			//
			if (exportingModules.size() > 1) {

				List<IModule> reduced = reduce(exportingModules,
						_packageToTypesCache.get(packageName));

				// Import package
				System.out.println("Multiple Exporter reduced to " + reduced);

			} else if (exportingModules.size() == 1) {

				// Import package
				System.out.println("One Exporter");
			}
		}

		// handle the unsatisfied types
		for (String typeName : _unsatisfiedTypes) {

			// Import package
			System.out.println("Unsatisfied " + typeName);
		}

		// for (String packageName : packageNames) {
		//
		// // create the 'ImportedPackage' instance
		// ImportedPackage importedPackage = importPackage
		// .addImportedPackage(packageName);
		//
		// // get the template
		// HeaderDeclaration importPackageTemplate = ManifestUtils
		// .findMostSpecificDeclaration(importPackageTemplates,
		// packageName);
		//
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
		// }
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param exportingModules
	 * @param set
	 * @return
	 */
	private List<IModule> reduce(List<IModule> exportingModules,
			Set<String> typeNames) {

		//
		for (IModule module : exportingModules) {
			if (containsAll(module, typeNames)) {
				List<IModule> result = new LinkedList<IModule>();
				result.add(module);
				return result;
			}
		}

		//
		return exportingModules;
	}

	// MOVE TO
	private boolean containsAll(IModule module, Set<String> typeNames) {

		try {
			for (String typeName : typeNames) {
				if (module.getType(typeName) == null) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param packageName
	 * @return
	 */
	private List<IModule> getExportingModules(String packageName) {

		//
		List<IModule> result = new ArrayList<IModule>();

		//
		Set<String> types = _packageToTypesCache.get(packageName);

		for (String type : types) {
			result.addAll(_typeToModuleCache.get(type));
		}

		//
		return result;
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
				.getMainAttributes().get(TemplateConstants.IMPORT_TEMPLATE);

		_importPackageTemplates = importPackageTemplateHeader != null ? HeaderParserFactory
				.newHeaderParser(new SimpleParserLogger()).parsePackageHeader(
						importPackageTemplateHeader, Constants.IMPORT_PACKAGE)
				: EMPTY_HEADERDECLARATION_LIST;

	}

}
