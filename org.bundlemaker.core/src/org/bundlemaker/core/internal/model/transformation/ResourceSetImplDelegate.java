package org.bundlemaker.core.internal.model.transformation;

import java.util.Set;

import org.apache.tools.ant.types.selectors.SelectorUtils;
import org.bundlemaker.core.model.module.IModuleIdentifier;
import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.transformation.ResourceSet;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ResourceSetImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSet
	 * @param resourceModule
	 * @param contentType
	 * @return
	 */
	// TODO : -> matches(ResourceStandin)
	public static EList<IResourceStandin> getMatchingResources(
			ResourceSet resourceSet, IResourceModule resourceModule,
			ContentType contentType) {

		IModuleIdentifier moduleIdentifier = resourceSet.getModuleIdentifier();

		if (!(resourceModule.getModuleIdentifier().getName()
				.equals(moduleIdentifier.getName()) && resourceModule
				.getModuleIdentifier().getVersion()
				.equals(moduleIdentifier.getVersion()))) {

			return ECollections.emptyEList();
		}

		EList<IResourceStandin> result = new BasicEList<IResourceStandin>();

		//
		Set<IResourceStandin> resourceStandins = resourceModule
				.getResources(contentType);

		for (IResourceStandin resourceStandin : resourceStandins) {

			if (isIncluded(resourceSet, resourceStandin)) {
				result.add(resourceStandin);
			}
		}

		// TODO: Contained Containers!

		return result;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceSet
	 * @param resourceStandin
	 * @return
	 */
	private static boolean isIncluded(ResourceSet resourceSet,
			IResourceStandin resourceStandin) {

		boolean included = false;

		for (String include : resourceSet.getIncludes()) {
			if (SelectorUtils.matchPath(FileUtils.normalize(include),
					FileUtils.normalize(resourceStandin.getPath()))) {
				included = true;
				break;
			}
		}

		if (!included) {
			return false;
		}

		for (String exclude : resourceSet.getExcludes()) {
			if (SelectorUtils.matchPath(FileUtils.normalize(exclude),
					FileUtils.normalize(resourceStandin.getPath()))) {
				return false;
			}
		}

		return true;

	}
}
