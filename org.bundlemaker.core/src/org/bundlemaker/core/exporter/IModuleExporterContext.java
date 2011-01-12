package org.bundlemaker.core.exporter;

import java.io.File;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.IModularizedSystem;

/**
 * <p>
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IModuleExporterContext {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IBundleMakerProject getBundleMakerProject();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	File getDestinationDirectory();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IModularizedSystem getModularizedSystem();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	boolean containsAttribute(Object key);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param key
	 * @return
	 */
	Object getAttribute(Object key);
}
