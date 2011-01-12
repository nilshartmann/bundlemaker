package org.bundlemaker.core.internal.model.projectdescription;

import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.IResourceContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.resource.ResourceStandin;
import org.bundlemaker.core.resource.StringCache;
import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModifiableFileBasedContentImplDelegate {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fileBasedContent
	 * @param bundleMakerProject
	 * @throws CoreException
	 */
	public static void initialize(ModifiableFileBasedContent fileBasedContent,
			BundleMakerProject bundleMakerProject) {

		// return if content already is initialized
		if (fileBasedContent.isInitialized()) {
			return;
		}

		// add the binary paths
		for (String binaryPath : fileBasedContent
				.getModifiableBinaryPathNames()) {
			fileBasedContent.getModifiableBinaryPaths().add(
					new Path(binaryPath));
		}

		if (fileBasedContent.isResourceContent()) {

			// add the source paths
			for (String sourcePath : fileBasedContent
					.getModifiableResourceContent()
					.getModifiableSourcePathNames()) {

				fileBasedContent.getModifiableResourceContent()
						.getModifiableSourcePaths().add(new Path(sourcePath));
			}

			// add the content
			for (IPath root : fileBasedContent.getBinaryPaths()) {
				try {
					List<String> children = FileUtils.getAllChildren(root
							.toFile());

					addResources(fileBasedContent.getId(), root.toFile()
							.getAbsolutePath(), children, fileBasedContent
							.getModifiableResourceContent()
							.getModifiableBinaryResources(),
							bundleMakerProject.DEFAULT_STRING_CACHE);

				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (IPath root : fileBasedContent.getResourceContent()
					.getSourcePaths()) {

				try {
					List<String> children = FileUtils.getAllChildren(root
							.toFile());

					addResources(fileBasedContent.getId(), root.toFile()
							.getAbsolutePath(), children, fileBasedContent
							.getModifiableResourceContent()
							.getModifiableSourceResources(),
							bundleMakerProject.DEFAULT_STRING_CACHE);

				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// set initialized
		fileBasedContent.setInitialized(true);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fileBasedContent
	 * @return
	 */
	public static List<IPath> getBinaryPaths(
			ModifiableFileBasedContent fileBasedContent) {

		// return an unmodifiable list
		return Collections.unmodifiableList(fileBasedContent
				.getModifiableBinaryPaths());
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fileBasedContent
	 * @return
	 */
	public static boolean isResourceContent(
			ModifiableFileBasedContent fileBasedContent) {

		//
		return fileBasedContent.getModifiableResourceContent() != null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param fileBasedContent
	 * @return
	 */
	public static IResourceContent getResourceContent(
			ModifiableFileBasedContent fileBasedContent) {

		//
		return fileBasedContent.getModifiableResourceContent();
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param stringCache
	 * 
	 * @param content
	 * @param contentDescription
	 * @param isSource
	 */
	private static void addResources(String contentId, String root,
			List<String> paths, List<ResourceStandin> resources,
			StringCache stringCache) {

		for (String path : paths) {
			ResourceStandin resourceProxy = new ResourceStandin(contentId,
					root, normalize(path), stringCache);
			resources.add(resourceProxy);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param path
	 * @return
	 */
	private static String normalize(String path) {
		return path.replace('\\', '/');
	}
}
