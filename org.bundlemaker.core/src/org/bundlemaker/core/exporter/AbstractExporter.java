package org.bundlemaker.core.exporter;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Abstract base class for all exporters.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractExporter implements IModuleExporter {

	/** the modularized system */
	private IModularizedSystem _currentModularizedSystem;

	/** the current module */
	private IResourceModule _currentModule;

	/** the current context */
	private IModuleExporterContext _currentContext;

	/**
	 * <p>
	 * Returns the current {@link IModularizedSystem}.
	 * </p>
	 * 
	 * @return the current {@link IModularizedSystem}
	 */
	protected IModularizedSystem getCurrentModularizedSystem() {
		return _currentModularizedSystem;
	}

	/**
	 * <p>
	 * Returns the current {@link IResourceModule}.
	 * </p>
	 * 
	 * @return the current {@link IResourceModule}
	 */
	protected IResourceModule getCurrentModule() {
		return _currentModule;
	}

	/**
	 * <p>
	 * Returns the current {@link IModuleExporterContext}.
	 * </p>
	 * 
	 * @return the current {@link IModuleExporterContext}
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

		//
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void export(IModularizedSystem modularizedSystem,
			IResourceModule module, IModuleExporterContext context)
			throws CoreException {

		Assert.isNotNull(modularizedSystem);
		Assert.isNotNull(module);
		Assert.isNotNull(context);

		// set attributes
		_currentModularizedSystem = modularizedSystem;
		_currentContext = context;
		_currentModule = module;

		// pre export
		preExportModule();

		// export
		doExport();

		// post export
		postExportModule();
	}

	/**
	 * <p>
	 * This method is called <i>before</i> the module is exported.
	 * </p>
	 * 
	 * @throws CoreException
	 */
	protected void preExportModule() throws CoreException {
		// empty implementation
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	protected void doExport() throws CoreException {
		// empty implementation
	}

	/**
	 * <p>
	 * This method is called <i>after</i> the module is exported.
	 * </p>
	 * 
	 * @throws CoreException
	 */
	protected void postExportModule() throws CoreException {
		// empty implementation
	}
}
