package org.bundlemaker.core.util;

import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResource;
import org.bundlemaker.core.spi.resource.ResourceStandin;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class TransformationUtils {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param target
	 * @param toAdd
	 */
	// TODO MOVE
	public static void addAll(Set<IResource> target,
			List<IResource> toAdd) {

		for (IResource IResource : toAdd) {
			if (IResource instanceof ResourceStandin) {
				target.add((ResourceStandin) IResource);
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceModule
	 * @param resourceStandins
	 * @param binary
	 */
	// TODO MOVE
	public static void removeAll(ResourceModule resourceModule,
			List<IResource> resourceStandins, ContentType binary) {

		removeAll(
				resourceModule.getSelfContainer().getModifiableResourcesSet(
						binary), resourceStandins);

		// TODO!
		// for (ModifiableResourceContainer resourceContainer : resourceModule
		// .getModifiableContainedResourceContainers().values()) {
		//
		// removeAll(resourceContainer.getModifiableResources(binary),
		// resourceStandins);
		// }
	}

	// /**
	// * <p>
	// * </p>
	// *
	// * @param moduleIdentifier
	// * @return
	// */
	// public static ModifiableResourceModule findResourceModule(
	// List<ModifiableResourceModule> modules,
	// IModuleIdentifier moduleIdentifier) {
	//
	// for (ModifiableResourceModule resourceModule : modules) {
	// if (ModelUtils.equals(resourceModule.getModuleIdentifier(),
	// moduleIdentifier)) {
	// return resourceModule;
	// }
	// }
	//
	// return null;
	// }

	private static void removeAll(Set<IResource> target,
			List<IResource> toRemove) {

		for (IResource IResource : toRemove) {
			target.remove(IResource);
		}
	}
}
