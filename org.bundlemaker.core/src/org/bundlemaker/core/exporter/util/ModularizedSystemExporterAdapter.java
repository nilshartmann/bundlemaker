package org.bundlemaker.core.exporter.util;

import org.bundlemaker.core.exporter.IModularizedSystemExporter;
import org.bundlemaker.core.exporter.IModuleExporter;
import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModularizedSystemExporterAdapter implements
		IModularizedSystemExporter {

	/** - */
	private IModuleExporter _moduleExporter;

	/** - */
	private IModularizedSystem _currentModularizedSystem;

	/** - */
	private IResourceModule _currentModule;

	/** - */
	private IModuleExporterContext _currentContext;

	/**
	 * <p>
	 * Creates a new instance of type {@link ModularizedSystemExporterAdapter}.
	 * </p>
	 * 
	 * @param moduleExporter
	 */
	public ModularizedSystemExporterAdapter(IModuleExporter moduleExporter) {
		Assert.isNotNull(moduleExporter);

		_moduleExporter = moduleExporter;
	}

	public IModuleExporter getModuleExporter() {
		return _moduleExporter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void export(IModularizedSystem modularizedSystem,
			IModuleExporterContext context) throws Exception {

		_currentModularizedSystem = modularizedSystem;
		_currentContext = context;
		_currentContext = preExportModules();

		// simply call export() for each contained
		for (IResourceModule resourceModule : _currentModularizedSystem
				.getResourceModules()) {

			//
			_currentModule = resourceModule;

			// export if possible
			if (_moduleExporter.canExport(_currentModularizedSystem,
					_currentModule, _currentContext)) {
				_moduleExporter.export(_currentModularizedSystem,
						_currentModule, _currentContext);
			} else {
				handleNonExportableModule();
			}
		}

		postExportModules();
	}

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

	protected IModuleExporterContext preExportModules() throws Exception {
		return _currentContext;
	}

	protected void postExportModules() throws Exception {
	}

	protected void handleNonExportableModule() {

	}
}
