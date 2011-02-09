package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * Abstract base class for exporters. This implementation's
 * {@link #export(IModularizedSystem, IModuleExporterContext)} method simply
 * calls the
 * {@link AbstractExporter#export(IModularizedSystem, IResourceModule, IModuleExporterContext)}
 * method for each contained {@link IResourceModule}: <code><pre>
 * for (IResourceModule resourceModule : modularizedSystem.getResourceModules()) {
 *   if (canExport(modularizedSystem, resourceModule, context)) {
 *     export(modularizedSystem, resourceModule, context);
 *   }
 * }
 * </pre></code>
 * </p>
 * <p>
 * Clients may implement subclass this class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExporter implements IModuleExporter,
		IModularizedSystemExporter {

	/** - */
	private IModularizedSystem _currentModularizedSystem;

	/** - */
	private IResourceModule _currentModule;

	/** - */
	private IModuleExporterContext _currentContext;

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IModularizedSystem getCurrentModularizedSystem() {
		return _currentModularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IResourceModule getCurrentModule() {
		return _currentModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public IModuleExporterContext getCurrentContext() {
		return _currentContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canExport(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context) {

		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception {

		_currentModularizedSystem = modularizedSystem;
		_currentContext = context;

		// simply call export() for each contained
		for (IResourceModule resourceModule : modularizedSystem
				.getResourceModules()) {

			// export if possible
			if (canExport(modularizedSystem, resourceModule, context)) {

				_currentModule = resourceModule;

				export(modularizedSystem, resourceModule, context);
			}
		}
	}
}
