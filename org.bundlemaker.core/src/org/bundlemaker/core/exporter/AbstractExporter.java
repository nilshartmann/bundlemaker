package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExporter implements IModuleExporter {

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
	protected IModularizedSystem getCurrentModularizedSystem() {
		return _currentModularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected IResourceModule getCurrentModule() {
		return _currentModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	protected IModuleExporterContext getCurrentContext() {
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

	@Override
	public final void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws CoreException {

		_currentModularizedSystem = modularizedSystem;
		_currentContext = context;
		_currentModule = module;

		preExportModule();

		doExport();

		postExportModule();

	}

	protected void preExportModule() throws CoreException {

	}

	protected void doExport() throws CoreException {

	}

	protected void postExportModule() throws CoreException {

	}
}
