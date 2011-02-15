package org.bundlemaker.core.exporter.bundle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.RequireBundle;
import com.springsource.util.osgi.manifest.Resolution;
import com.springsource.util.parser.manifest.ManifestContents;

/**
 */
public class ImportResolver extends AbstractImportResolver {

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

		// call super constructor
		super(modularizedSystem, resourceModule, importPackage, requireBundle,
				manifestTemplate);
	}

	/**
	 * <p>
	 * </p>
	 */
	public void addImportPackageAndRequiredBundle() {

		//
		for (String packageName : getPackageToTypesCache().getMap().keySet()) {

			// rule 1: if a package package contains an unsatisfied type,
			// we will import the package as optional
			if (containsUnsatisfiedTypes(packageName)) {
				addImportedPackage(packageName, Resolution.OPTIONAL);
			}

			//
			List<IModule> exportingModules = getExportingModules(packageName);

			//
			if (exportingModules.size() > 1) {

				List<IModule> reduced = reduce(exportingModules,
						getPackageToTypesCache().get(packageName));

				if (reduced.size() == 1) {
					addImportedPackage(packageName);
				} else {
					for (IModule iModule : reduced) {
						addRequireBundle(iModule.getModuleIdentifier()
								.getName());
					}
				}

			} else if (exportingModules.size() == 1) {
				addImportedPackage(packageName);
			}
		}
	}

	private boolean containsUnsatisfiedTypes(String packageName) {

		//
		Set<String> typeNames = getPackageToTypesCache().get(packageName);

		//
		for (String typeName : typeNames) {
			if (getUnsatisfiedTypes().contains(typeName)) {
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
	private List<IModule> reduce(List<IModule> exportingModules,
			Set<String> typeNames) {

		Assert.isNotNull(exportingModules);
		Assert.isNotNull(typeNames);

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

		if (module == null) {
			return false;
		}

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
		Set<String> types = getPackageToTypesCache().get(packageName);

		for (String type : types) {

			Set<IModule> module = getTypeToModuleCache().get(type);

			if (module != null) {
				result.addAll(module);
			} else {
				System.out.println("No Module for " + type);
			}

		}

		//
		return result;
	}
}
