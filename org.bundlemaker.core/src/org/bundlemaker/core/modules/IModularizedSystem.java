package org.bundlemaker.core.modules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.transformation.ITransformation;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModularizedSystem {

	/**
	 * <p>
	 * Returns the name of the modularized system.
	 * </p>
	 * 
	 * @return
	 */
	String getName();

	/**
	 * <p>
	 * Returns the project description.
	 * </p>
	 * 
	 * @return the project description
	 */
	IBundleMakerProjectDescription getProjectDescription();

	/**
	 * <p>
	 * Returns the (modifiable) transformation list.
	 * </p>
	 * 
	 * @return
	 */
	List<ITransformation> getTransformations();

	/**
	 * <p>
	 * Applies the transformations.
	 * </p>
	 */
	void applyTransformations();

	/**
	 * <p>
	 * Returns all modules ({@link ITypeModule ITypeModules} and
	 * {@link IResourceModule IResourceModules}).
	 * </p>
	 * 
	 * @return
	 */
	Set<ITypeModule> getAllModules();

	/**
	 * <p>
	 * Returns all contained {@link ITypeModule ITypeModules}.
	 * </p>
	 * 
	 * @return
	 */
	Set<ITypeModule> getTypeModules();

	/**
	 * <p>
	 * Returns the {@link ITypeModule} with the given identifier.
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	ITypeModule getTypeModule(IModuleIdentifier identifier);

	/**
	 * <p>
	 * Returns all contained {@link IResourceModule IResourceModules}.
	 * </p>
	 * 
	 * @return
	 */
	Set<IResourceModule> getResourceModules();

	/**
	 * <p>
	 * Returns the {@link IResourceModule} with the given identifier.
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	IResourceModule getResourceModule(IModuleIdentifier identifier);

	/**
	 * <p>
	 * Returns a set with all unassigned resources.
	 * </p>
	 * 
	 * @return
	 */
	Set<IResourceStandin> getUnassignedResources();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	Set<ITypeModule> getContainingModules(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 * @throws AmbiguousModuleDependencyException
	 */
	ITypeModule getContainingModule(String fullyQualifiedName)
			throws AmbiguousModuleDependencyException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @return
	 */
	IReferencedModulesQueryResult getReferencedModules(IResourceModule module);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resource
	 * @return
	 */
	IReferencedModulesQueryResult getReferencedModules(IResource resource);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @param hideContainedTypes
	 * @param includeSourceReferences
	 * @return
	 */
	Set<String> getUnsatisfiedReferencedTypes(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @param hideContainedTypes
	 * @param includeSourceReferences
	 * @return
	 */
	Set<String> getUnsatisfiedReferencedPackages(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	Map<String, Set<ITypeModule>> getAmbiguousPackages();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	ITypeModule getExecutionEnvironmentTypeModule();
}