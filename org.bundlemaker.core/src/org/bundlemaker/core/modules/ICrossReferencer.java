package org.bundlemaker.core.modules;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.bundlemaker.core.modules.query.IQueryFilter;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.resource.IType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ICrossReferencer {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fullyQualifiedName
	 * @return
	 */
	@Deprecated
	Set<IType> getReferencingTypes(String fullyQualifiedName);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param module
	 * @return
	 */
	@Deprecated
	IReferencedModulesQueryResult getReferencedModules(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resource
	 * @return
	 */
	@Deprecated
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
	@Deprecated
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
	@Deprecated
	Set<String> getUnsatisfiedReferencedPackages(IResourceModule module,
			boolean hideContainedTypes, boolean includeSourceReferences);

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	@Deprecated
	Map<String, Set<IModule>> getAmbiguousPackages();

	@Deprecated
	Map<String, Set<IType>> getAmbiguousTypes();

	// TODO
	@Deprecated
	Collection<IType> getTypeReferencesTransitiveClosure(String typeName,
			IQueryFilter<IType> filter);

	// TODO
	@Deprecated
	Collection<IType> getTypeIsReferencedTransitiveClosure(String typeName,
			IQueryFilter<IType> filter);

	// TODO
	@Deprecated
	Collection<IResource> getResourceReferencesTransitiveClosure(
			IResource resource, ContentType contentType,
			IQueryFilter<IType> queryFilter);

	// TODO
	@Deprecated
	Collection<IResource> getResourceIsReferencedTransitiveClosure(
			IResource resource, ContentType contentType,
			IQueryFilter<IResource> queryFilter);
}