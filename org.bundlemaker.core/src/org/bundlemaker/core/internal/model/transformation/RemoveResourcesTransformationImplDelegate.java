package org.bundlemaker.core.internal.model.transformation;

import java.util.List;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.transformation.RemoveResourcesTransformation;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.util.ModelUtils;
import org.bundlemaker.core.util.TransformationUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class RemoveResourcesTransformationImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param removeResourcesTransformation
	 * @param modularizedSystem
	 */
	public static void apply(
			RemoveResourcesTransformation removeResourcesTransformation,
			ModularizedSystem modularizedSystem) {

		//
		for (ResourceSet resourceSet : removeResourcesTransformation
				.getResourcesToRemove()) {

			//
			ResourceModule resourceModule = modularizedSystem
					.getModifiableResourceModule(resourceSet
							.getModuleIdentifier());

			List<IResourceStandin> resourceStandinsToMove = resourceSet
					.getMatchingResources(resourceModule, ContentType.BINARY);

			TransformationUtils.removeAll(resourceModule,
					resourceStandinsToMove, ContentType.BINARY);

			resourceStandinsToMove = resourceSet.getMatchingResources(
					resourceModule, ContentType.SOURCE);

			TransformationUtils.removeAll(resourceModule,
					resourceStandinsToMove, ContentType.SOURCE);

		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param name
	 * @param version
	 * @param includes
	 * @param excludes
	 */
	public static void addResourceSet(
			RemoveResourcesTransformation transformation, String name,
			String version, String[] includes, String[] excludes) {

		//
		addResourceSet(transformation,
				ModelUtils.createModuleIdentifier(name, version), includes,
				excludes);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fromModuleIdentifier
	 * @param includes
	 * @param excludes
	 */
	public static void addResourceSet(
			RemoveResourcesTransformation transformation,
			IModuleIdentifier fromModuleIdentifier, String[] includes,
			String[] excludes) {

		//
		ResourceSet resourceSet = TransformationFactory.eINSTANCE
				.createResourceSet();

		//
		resourceSet.setModuleIdentifier(fromModuleIdentifier);

		//
		if (includes != null) {
			for (String include : includes) {
				resourceSet.getIncludes().add(include);
			}
		}

		//
		if (excludes != null) {
			for (String exclude : excludes) {
				resourceSet.getExcludes().add(exclude);
			}
		}

		//
		transformation.getResourcesToRemove().add(resourceSet);
	}
}
