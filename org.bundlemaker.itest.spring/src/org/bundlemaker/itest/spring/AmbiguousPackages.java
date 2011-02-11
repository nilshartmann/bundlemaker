package org.bundlemaker.itest.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AmbiguousPackages {

	/** - */
	private org.bundlemaker.core.modules.IModularizedSystem _modularizedSystem;

	/** - */
	private Map<String, Set<IModule>> _packageMap;

	/** - */
	private Map<IModule, String[]> _moduleMap;

	/**
	 * <p>
	 * Creates a new instance of type {@link AmbiguousPackages}.
	 * </p>
	 * 
	 * @param modularizedSystem
	 */
	public AmbiguousPackages(IModularizedSystem modularizedSystem) {
		super();

		//
		_modularizedSystem = modularizedSystem;

		//
		init();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Map<String, Set<IModule>> getPackageMap() {
		return _packageMap;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Map<IModule, String[]> getModuleMap() {
		return _moduleMap;
	}

	/**
	 * <p>
	 * </p>
	 */
	private void init() {

		//
		_packageMap = _modularizedSystem.getAmbiguousPackages();

		//
		_moduleMap = new HashMap<IModule, String[]>();

		//
		for (final String packageName : _packageMap.keySet()) {

			//
			Set<IModule> moduleList = _packageMap.get(packageName);

			//
			for (IModule typeModule : moduleList) {

				//
				if (_moduleMap.containsKey(typeModule)) {

					String[] oldPackages = _moduleMap.get(typeModule);
					String[] newPackages = new String[oldPackages.length + 1];
					System.arraycopy(oldPackages, 0, newPackages, 0,
							oldPackages.length);
					newPackages[newPackages.length - 1] = packageName;
					_moduleMap.put(typeModule, newPackages);
				}

				//
				else {

					_moduleMap.put(typeModule, new String[] { packageName });
				}

			}
		}
	}

	// System.out.println(typeModule
	// .getContainedTypes(new IQueryFilter() {
	// public boolean matches(String content) {
	// Pattern pattern = Pattern.compile(packageName
	// .replace(".", "\\.") + "\\.\\w*");
	// return pattern.matcher(content).matches();
	// }
	// }));
}
