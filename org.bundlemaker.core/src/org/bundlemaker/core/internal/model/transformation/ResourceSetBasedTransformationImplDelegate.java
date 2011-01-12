package org.bundlemaker.core.internal.model.transformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.module.ModifiableModuleIdentifier;
import org.bundlemaker.core.model.module.ModuleFactory;
import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.model.transformation.ResourceSetBasedModuleDefinition;
import org.bundlemaker.core.model.transformation.ResourceSetBasedTransformation;
import org.bundlemaker.core.model.transformation.TransformationFactory;
import org.bundlemaker.core.modules.ITypeModule;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.util.ModelUtils;
import org.bundlemaker.core.util.TransformationUtils;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSetBasedTransformationImplDelegate {

	public static ResourceSetBasedModuleDefinition addModuleDefinition(
			ResourceSetBasedTransformation resourceSetBasedTransformation,
			String name, String version) {

		return addModuleDefinition(resourceSetBasedTransformation, name,
				version, new HashMap<String, Object>());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSetBasedTransformationImpl
	 * @param name
	 * @param version
	 * @return
	 */
	public static ResourceSetBasedModuleDefinition addModuleDefinition(
			ResourceSetBasedTransformation resourceSetBasedTransformation,
			String name, String version, Map<String, Object> userAttibutes) {

		Assert.isNotNull(name);
		Assert.isNotNull(version);

		// TODO: check if duplicate

		// create
		ResourceSetBasedModuleDefinition moduleDefinition = TransformationFactory.eINSTANCE
				.createResourceSetBasedModuleDefinition();
		ModifiableModuleIdentifier targetIdentifier = ModuleFactory.eINSTANCE
				.createModifiableModuleIdentifier();
		targetIdentifier.setName(name);
		targetIdentifier.setVersion(version);
		moduleDefinition.setModuleIdentifier(targetIdentifier);
		moduleDefinition.getUserAttributes().putAll(userAttibutes);

		// add
		resourceSetBasedTransformation.getModuleDefinitions().add(
				moduleDefinition);

		// return result
		return moduleDefinition;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSetBasedTransformationImpl
	 * @param description
	 * @param modules
	 */
	public static void apply(ResourceSetBasedTransformation transformation,
			ModularizedSystem modularizedSystem) {

		System.out.println("ResourceSetBasedTransformation start");

		//
		List<ResourceSetBasedModuleDefinition> moduleDefinitions = transformation
				.getModuleDefinitions();

		//
		for (ResourceSetBasedModuleDefinition moduleDefinition : moduleDefinitions) {

			// get the target module
			IModuleIdentifier targetModuleIdentifier = moduleDefinition
					.getModuleIdentifier();

			// log
			System.out.println("Creating module '"
					+ ModelUtils.toString(targetModuleIdentifier) + "'...");

			ResourceModule targetResourceModule = modularizedSystem
					.getModifiableResourceModule(targetModuleIdentifier);

			// create a new one if necessary
			if (targetResourceModule == null) {

				targetResourceModule = modularizedSystem
						.createResourceModule(ModelUtils
								.createModuleIdentifier(
										targetModuleIdentifier.getName(),
										targetModuleIdentifier.getVersion()));
			}

			// set classifications
			if (moduleDefinition.getClassification() != null) {
				targetResourceModule.setClassification(moduleDefinition
						.getClassification());
			}

			// set user attributes
			targetResourceModule.getUserAttributes().putAll(
					moduleDefinition.getUserAttributes());

			//
			for (ResourceSet resourceSet : moduleDefinition.getResourceSets()) {

				ResourceModule originResourceModule = modularizedSystem
						.getModifiableResourceModule(resourceSet
								.getModuleIdentifier());

				// origin resource module does not exist
				if (originResourceModule == null) {

					for (ITypeModule typeModule : modularizedSystem
							.getAllModules()) {

						System.out.println(" - "
								+ ModelUtils.toString(typeModule
										.getModuleIdentifier()));
					}

					throw new RuntimeException(String.format(
							"Module '%s' does not exist.",
							ModelUtils.toString(resourceSet
									.getModuleIdentifier())));
				}

				// handle custom ResourceSetProcessor
				if (transformation.getResourceSetProcessor() != null) {
					transformation.getResourceSetProcessor().processResources(
							originResourceModule, targetResourceModule,
							resourceSet);
				}

				//
				else {
					processResources(originResourceModule,
							targetResourceModule, resourceSet);
				}
			}
		}

		System.out.println("ResourceSetBasedTransformation stop");
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param originResourceModule
	 * @param targetResourceModule
	 * @param resourceSet
	 */
	private static void processResources(ResourceModule originResourceModule,
			ResourceModule targetResourceModule, ResourceSet resourceSet) {

		List<IResourceStandin> resourceStandinsToMove = resourceSet
				.getMatchingResources(originResourceModule, ContentType.BINARY);

		TransformationUtils.addAll(targetResourceModule.getSelfContainer()
				.getModifiableResourcesSet(ContentType.BINARY),
				resourceStandinsToMove);

		TransformationUtils.removeAll(originResourceModule,
				resourceStandinsToMove, ContentType.BINARY);

		resourceStandinsToMove = resourceSet.getMatchingResources(
				originResourceModule, ContentType.SOURCE);

		TransformationUtils.addAll(targetResourceModule.getSelfContainer()
				.getModifiableResourcesSet(ContentType.SOURCE),
				resourceStandinsToMove);

		TransformationUtils.removeAll(originResourceModule,
				resourceStandinsToMove, ContentType.SOURCE);
	}
}
