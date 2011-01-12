package org.bundlemaker.core.internal.model.transformation;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.transformation.EmbedModuleTransformation;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.ModularizedSystem;
import org.bundlemaker.core.modules.ResourceContainer;
import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.util.ModelUtils;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class EmbedModuleTransformationImplDelegate {

	public static void apply(EmbedModuleTransformation transformation,
			ModularizedSystem modularizedSystem) {

		// step 1: define modules variables
		ResourceModule hostModule = null;
		List<ResourceModule> embeddedModules = new LinkedList<ResourceModule>();

		// step 2: fetch host and embedded modules
		for (ResourceModule embeddedModule : modularizedSystem
				.getModifiableResourceModules()) {

			// try to fetch host module
			if (ModelUtils.equals(embeddedModule.getModuleIdentifier(),
					transformation.getHostModuleIdentifier())) {

				// set the host module
				hostModule = embeddedModule;
			}

			// try to fetch embedded modules
			else {
				for (IModuleIdentifier embeddedModulesIdentifier : transformation
						.getEmbeddedModulesIdentifiers()) {

					if (ModelUtils.equals(embeddedModule.getModuleIdentifier(),
							embeddedModulesIdentifier)) {
						embeddedModules.add(embeddedModule);
					}
				}
			}
		}

		// step 3: add the embedded modules as container
		for (ResourceModule embeddedModule : embeddedModules) {

			// // get the module name
			// String embeddedModuleName = ModelUtils.toString(embeddedModule
			// .getModuleIdentifier());
			//
			// // get the resource container
			// ResourceContainer embeddedContainer = embeddedModule
			// .getSelfContainer().getModifiableResourcesSet(contentType);
			//
			// // embed the container
			// hostModule.getModifiableContainedResourceContainers().put(
			// embeddedModuleName, embeddedContainer);
			//
			// // embed all embedded containers
			// if (embeddedModule.getModifiableContainedResourceContainers()
			// .size() > 0) {
			//
			// // add the map
			// hostModule.getModifiableContainedResourceContainers().putAll(
			// embeddedModule
			// .getModifiableContainedResourceContainers());
			// }

			// remove as top level module
			modularizedSystem.getModifiableResourceModules().remove(
					embeddedModule);
		}
	}
}
