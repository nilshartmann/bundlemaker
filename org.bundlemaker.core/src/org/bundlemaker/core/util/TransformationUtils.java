package org.bundlemaker.core.util;

import java.util.List;
import java.util.Set;

import org.bundlemaker.core.modules.ResourceModule;
import org.bundlemaker.core.projectdescription.ContentType;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.ResourceStandin;

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
	public static void addAll(Set<IResourceStandin> target,
			List<IResourceStandin> toAdd) {

		for (IResourceStandin iResourceStandin : toAdd) {
			if (iResourceStandin instanceof ResourceStandin) {
				target.add((ResourceStandin) iResourceStandin);
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
			List<IResourceStandin> resourceStandins, ContentType binary) {

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

	private static void removeAll(Set<IResourceStandin> target,
			List<IResourceStandin> toRemove) {

		for (IResourceStandin iResourceStandin : toRemove) {
			target.remove(iResourceStandin);
		}
	}
}
