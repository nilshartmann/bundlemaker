package org.bundlemaker.core.transformation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.internal.modules.ModularizedSystem;
import org.bundlemaker.core.internal.modules.ResourceModule;
import org.bundlemaker.core.modules.IModuleIdentifier;

public class EmbedModuleTransformation implements ITransformation {

	private IModuleIdentifier _hostModuleIdentifier;

	private List<IModuleIdentifier> _embeddedModulesIdentifiers;

	/**
	 * <p>
	 * Creates a new instance of type {@link EmbedModuleTransformation}.
	 * </p>
	 */
	public EmbedModuleTransformation() {

		//
		_embeddedModulesIdentifiers = new ArrayList<IModuleIdentifier>();
	}

	public IModuleIdentifier getHostModuleIdentifier() {
		return _hostModuleIdentifier;
	}

	public void setHostModuleIdentifier(IModuleIdentifier hostModuleIdentifier) {
		_hostModuleIdentifier = hostModuleIdentifier;
	}

	public List<IModuleIdentifier> getEmbeddedModulesIdentifiers() {
		return _embeddedModulesIdentifiers;
	}

	@Override
	public void apply(ModularizedSystem modularizedSystem) {

		// step 1: define modules variables
		ResourceModule hostModule = null;

		List<ResourceModule> embeddedModules = new LinkedList<ResourceModule>();

		// step 2: fetch host and embedded modules
		for (ResourceModule embeddedModule : modularizedSystem
				.getModifiableResourceModules().values()) {

			// try to fetch host module
			if (embeddedModule.getModuleIdentifier().equals(
					_hostModuleIdentifier)) {

				// set the host module
				hostModule = embeddedModule;
			}

			// try to fetch embedded modules
			else {

				for (IModuleIdentifier embeddedModulesIdentifier : _embeddedModulesIdentifiers) {

					if (embeddedModule.getModuleIdentifier().equals(
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
					embeddedModule.getModuleIdentifier());
		}
	}
}
