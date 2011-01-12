package org.bundlemaker.core.internal.model.projectdescription;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.model.projectdescription.ContentType;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent;
import org.bundlemaker.core.resource.IResourceStandin;
import org.bundlemaker.core.resource.ResourceStandin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;

public class ModifiableResourceContentImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContent
	 * @return
	 */
	public static List<IPath> getSourcePaths(
			ModifiableResourceContent resourceContent) {

		// return an unmodifiable list
		return Collections.unmodifiableList(resourceContent
				.getModifiableSourcePaths());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContent
	 * @param path
	 * @return
	 */
	public static IResourceStandin getBinaryResource(
			ModifiableResourceContent resourceContent, IPath path) {

		for (ResourceStandin resourceStandin : resourceContent
				.getModifiableBinaryResources()) {

			if (new Path(resourceStandin.getPath()).equals(path)) {
				return resourceStandin;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContent
	 * @return
	 */
	public static EList<IResourceStandin> getBinaryResources(
			ModifiableResourceContent resourceContent) {
		return ECollections
				.unmodifiableEList((EList<? extends IResourceStandin>) resourceContent
						.getModifiableBinaryResources());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContent
	 * @param path
	 * @return
	 */
	public static IResourceStandin getSourceResource(
			ModifiableResourceContent resourceContent, IPath path) {

		for (ResourceStandin resourceStandin : resourceContent
				.getModifiableBinaryResources()) {

			if (new Path(resourceStandin.getPath()).equals(path)) {
				return resourceStandin;
			}
		}
		return null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param resourceContent
	 * @return
	 */
	public static EList<IResourceStandin> getSourceResources(
			ModifiableResourceContent resourceContent) {
		return ECollections
				.unmodifiableEList((EList<? extends IResourceStandin>) resourceContent
						.getModifiableSourceResources());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modifiableResourceContentImpl
	 * @param path
	 * @param type
	 * @return
	 */
	public static IResourceStandin getResource(
			ModifiableResourceContent resourceContent, IPath path,
			ContentType type) {

		switch (type) {
		case BINARY: {
			return getBinaryResource(resourceContent, path);
		}
		case SOURCE: {
			return getSourceResource(resourceContent, path);
		}
		default: {
			return null;
		}
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modifiableResourceContentImpl
	 * @param type
	 * @return
	 */
	public static EList<IResourceStandin> getResources(
			ModifiableResourceContent resourceContent, ContentType type) {

		switch (type) {
		case BINARY: {
			return getBinaryResources(resourceContent);
		}
		case SOURCE: {
			return getSourceResources(resourceContent);
		}
		default: {
			return null;
		}
		}
	}
}
