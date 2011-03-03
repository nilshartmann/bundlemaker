package org.bundlemaker.core.modules.modifiable;

import java.util.Map;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModuleIdentifier;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModularizedSystem extends IModularizedSystem {

	/**
	 * <p>
	 * Returns a map with all contained {@link IModifiableResourceModule
	 * IModifiableResourceModules}.
	 * </p>
	 * 
	 * @return
	 */
	Map<IModuleIdentifier, IModifiableResourceModule> getModifiableResourceModulesMap();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param moduleIdentifier
	 * @return
	 */
	IModifiableResourceModule getModifiableResourceModule(
			IModuleIdentifier moduleIdentifier);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param moduleIdentifier
	 * @return
	 */
	IModifiableResourceModule createResourceModule(
			IModuleIdentifier moduleIdentifier);

}
