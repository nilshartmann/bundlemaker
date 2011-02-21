package org.bundlemaker.core.exporter.manifest.internal;

import org.bundlemaker.core.exporter.IModuleExporterContext;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

import com.springsource.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractResolver {

	/** - */
	private CurrentModule _currentModule;

	/**
	 * <p>
	 * Creates a new instance of type {@link AbstractResolver}.
	 * </p>
	 * 
	 * @param currentModule
	 */
	public AbstractResolver(CurrentModule currentModule) {

		Assert.isNotNull(currentModule);

		//
		_currentModule = currentModule;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final IModularizedSystem getModularizedSystem() {
		return _currentModule.getModularizedSystem();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final IResourceModule getResourceModule() {
		return _currentModule.getResourceModule();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final ManifestContents getManifestTemplate() {
		return _currentModule.getManifestTemplate();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final IModuleExporterContext getModuleExporterContext() {
		return _currentModule.getContext();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public final CurrentModule getCurrentModule() {
		return _currentModule;
	}
}