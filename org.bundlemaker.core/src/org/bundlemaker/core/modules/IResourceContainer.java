package org.bundlemaker.core.modules;

import java.util.Set;

import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResourceStandin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IResourceContainer extends ITypeContainer {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceType
	 * @param conentType
	 * @return
	 */
	boolean containsResource(String resourceType, ContentType conentType);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param path
	 * @param conentType
	 * @return
	 */
	IResourceStandin getResource(String path, ContentType conentType);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param conentType
	 * @return
	 */
	Set<IResourceStandin> getResources(ContentType conentType);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param hideContainedTypes
	 * @param includeSourceReferences
	 * @return
	 */
	Set<String> getReferencedTypes(boolean hideContainedTypes,
			boolean includeSourceReferences);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param hideContainedTypes
	 * @param includeSourceReferences
	 * @return
	 */
	Set<String> getReferencedPackages(boolean hideContainedTypes,
			boolean includeSourceReferences);

}