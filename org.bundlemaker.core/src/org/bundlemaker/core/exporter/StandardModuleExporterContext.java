package org.bundlemaker.core.exporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class StandardModuleExporterContext implements IModuleExporterContext {

	/** - */
	private IBundleMakerProject _bundleMakerProject;

	/** - */
	private File _destinationDirectory;

	/** - */
	private IModularizedSystem _modularizedSystem;

	/** - */
	private Map<Object, Object> _additionalAttribute;

	/**
	 * <p>
	 * Creates a new instance of type {@link StandardModuleExporterContext}.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @param destinationDirectory
	 * @param modularizedSystem
	 */
	public StandardModuleExporterContext(IBundleMakerProject bundleMakerProject,
			File destinationDirectory, IModularizedSystem modularizedSystem) {

		//
		_bundleMakerProject = bundleMakerProject;

		//
		_destinationDirectory = destinationDirectory;

		//
		_modularizedSystem = modularizedSystem;

		//
		_additionalAttribute = new HashMap<Object, Object>();
	}

	/**
	 * 
	 */
	@Override
	public IBundleMakerProject getBundleMakerProject() {
		return _bundleMakerProject;
	}

	/**
	 * 
	 */
	@Override
	public File getDestinationDirectory() {
		return _destinationDirectory;
	}

	@Override
	public IModularizedSystem getModularizedSystem() {
		return _modularizedSystem;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public boolean containsAttribute(Object key) {
		return _additionalAttribute.containsKey(key);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	public Object getAttribute(Object key) {
		return _additionalAttribute.get(key);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object put(Object key, Object value) {
		return _additionalAttribute.put(key, value);
	}
}
