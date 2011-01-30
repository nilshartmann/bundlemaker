package org.bundlemaker.core.modules;

import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.resource.IReference;

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
	Map<IReference, ITypeModule> getReferencedModulesMap();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<ITypeModule> getReferencedModules();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasReferencesWithAmbiguousModules();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<IReference, Set<ITypeModule>> getReferencesWithAmbiguousModules();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<String, Set<ITypeModule>> getReferencedTypesWithAmbiguousModules();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	boolean hasUnsatisfiedReferences();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<IReference> getUnsatisfiedReferences();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Set<String> getUnsatisfiedReferencedTypes();
}