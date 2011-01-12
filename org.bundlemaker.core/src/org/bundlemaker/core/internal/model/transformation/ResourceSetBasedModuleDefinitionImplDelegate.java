package org.bundlemaker.core.internal.model.transformation;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.module.ModifiableModuleIdentifier;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.util.ModelUtils;

public class ResourceSetBasedModuleDefinitionImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSetBasedModuleDefinition
	 * @param fromModuleIdentifier
	 * @param includes
	 * @param excludes
	 */
	public static void addResourceSet(
			ResourceSetBasedModuleDefinition resourceSetBasedModuleDefinition,
			IModuleIdentifier fromModuleIdentifier, String[] includes,
			String[] excludes) {

		ResourceSet resourceSet = TransformationFactory.eINSTANCE
				.createResourceSet();

		resourceSet.setModuleIdentifier(fromModuleIdentifier);

		if (includes != null) {
			for (String include : includes) {
				resourceSet.getIncludes().add(include);
			}
		}

		if (excludes != null) {
			for (String exclude : excludes) {
				resourceSet.getExcludes().add(exclude);
			}
		}

		resourceSetBasedModuleDefinition.getResourceSets().add(resourceSet);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSetBasedModuleDefinition
	 * @param fromName
	 * @param fromVersion
	 * @param includes
	 * @param excludes
	 */
	public static void addResourceSet(
			ResourceSetBasedModuleDefinition resourceSetBasedModuleDefinition,
			String fromName, String fromVersion, String[] includes,
			String[] excludes) {

		IModuleIdentifier originIdentifier = ModelUtils.createModuleIdentifier(
				fromName, fromVersion);

		addResourceSet(resourceSetBasedModuleDefinition, originIdentifier,
				includes, excludes);
	}
}
