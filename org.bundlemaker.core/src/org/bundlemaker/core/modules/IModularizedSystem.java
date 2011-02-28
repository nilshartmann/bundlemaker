package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;
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
	 * </p>
	 * 
	 * @return
	 */
	IBundleMakerProject getBundleMakerProject();

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
	 * </p>
	 * 
	 * @return
	 */
	IModule getExecutionEnvironment();

	/**
	 * <p>
	 * Returns all modules ({@link IModule ITypeModules} and
	 * {@link IResourceModule IResourceModules}).
	 * </p>
	 * 
	 * @return
	 */
	Set<IModule> getAllModules();

	/**
	 * <p>
	 * Returns all contained {@link IModule ITypeModules}.
	 * </p>
	 * 
	 * @return
	 */
	Collection<IModule> getNonResourceModules();

	/**
	 * <p>
	 * Returns the {@link IModule} with the given identifier.
	 * </p>
	 * 
	 * @param identifier
	 * @return
	 */
	IModule getNonResourceModule(IModuleIdentifier identifier);

	/**
	 * <p>
	 * Returns all contained {@link IResourceModule IResourceModules}.
	 * </p>
	 * 
	 * @return
	 */
	Collection<IResourceModule> getResourceModules();

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
	Set<IResource> getUnassignedResources();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	Set<IModule> getContainingModules(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	Set<IType> getReferencingTypes(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 * @throws AmbiguousDependencyException
	 */
	IModule getContainingModule(String fullyQualifiedName)
			throws AmbiguousDependencyException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 * @throws AmbiguousDependencyException
	 */
	IType getType(String fullyQualifiedName)
			throws AmbiguousDependencyException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @return
	 */
	IReferencedModulesQueryResult getReferencedModules(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences);

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
	Map<String, Set<IModule>> getAmbiguousPackages();

	// TODO
	Collection<IType> getTypeReferencesTransitiveClosure(String typeName,
			IQueryFilter<IType> filter);

	// TODO
	Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName,
			IQueryFilter<IType> filter);

	// TODO
	Collection<IResource> getResourceReferencesTransitiveClosure(
			IResource resource, ContentType contentType,
			IQueryFilter<IType> queryFilter);

	// TODO
	Collection<IResource> getResourceIsReferencedTransitiveClosure(
			IResource resource, ContentType contentType,
			IQueryFilter<IResource> queryFilter);
}