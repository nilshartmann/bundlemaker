package org.bundlemaker.core.modules;

import java.util.Map;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ITypeModule extends ITypeContainer {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IModuleIdentifier getModuleIdentifier();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	IPath getClassification();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<String, Object> getUserAttributes();
}
