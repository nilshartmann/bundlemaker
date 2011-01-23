package org.bundlemaker.core.modules;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IReferencedModulesQueryResult {

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasErrors();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<String, ITypeModule> getReferencedModulesMap();

	Set<ITypeModule> getReferencedModules();

	boolean hasTypesWithAmbiguousModules();

	Map<String, List<ITypeModule>> getTypesWithAmbiguousModules();

	boolean hasMissingTypes();

	List<String> getMissingTypes();
}