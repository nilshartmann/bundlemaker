package org.bundlemaker.core.exporter.manifest.internal.importresolver;

import java.util.List;

import org.bundlemaker.core.exporter.manifest.internal.CurrentModule;
import org.bundlemaker.core.modules.IModule;

import com.springsource.util.osgi.manifest.ImportPackage;
import com.springsource.util.osgi.manifest.RequireBundle;
import com.springsource.util.osgi.manifest.Resolution;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ImportResolver extends AbstractImportResolver {

	/** - */
	private boolean useRequireBundle = true;

	/**
	 * <p>
	 * Creates a new instance of type {@link ImportResolver}.
	 * </p>
	 * 
	 * @param currentModule
	 * @param importPackage
	 * @param requireBundle
	 */
	public ImportResolver(CurrentModule currentModule,
			ImportPackage importPackage, RequireBundle requireBundle) {

		// call super constructor
		super(currentModule, importPackage, requireBundle);
	}

	/**
	 * <p>
	 * </p>
	 */
	public void addImportPackageAndRequiredBundle() {

		//
		for (String packageName : getReferencesCache()
				.getReferencedPackageToContainingTypesCache().getMap().keySet()) {

			// rule 1: if a package package contains an unsatisfied type,
			// we will import the package as optional
			if (containsUnsatisfiedTypes(packageName)) {

				if (!useRequireBundle) {
					addImportedPackage(packageName, Resolution.OPTIONAL);
				}
			}

			//
			List<IModule> exportingModules = getExportingModules(packageName);

			//
			if (exportingModules.size() > 1) {

				List<IModule> reduced = reduce(exportingModules,
						getReferencesCache()
								.getReferencedPackageToContainingTypesCache()
								.get(packageName));

				if (!useRequireBundle && reduced.size() == 1) {
					addImportedPackage(packageName);
				} else {
					for (IModule iModule : reduced) {
						addRequireBundle(iModule.getModuleIdentifier()
								.getName());
					}
				}

			} else if (exportingModules.size() == 1) {

				if (!useRequireBundle) {
					addImportedPackage(packageName);
				} else {
					addRequireBundle(exportingModules.get(0)
							.getModuleIdentifier().getName());
				}
			}
		}
	}
}
