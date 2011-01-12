package org.bundlemaker.core.internal.model.projectdescription;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.JarInfo;
import org.bundlemaker.core.internal.projectdescription.JarInfoService;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableFileBasedContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableResourceContent;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableprojectdescriptionFactory;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class ModifiableBundleMakerProjectDescriptionImplDelegate {

	/** - */
	private static NumberFormat FORMATTER = new DecimalFormat("000000");

	/**
	 * <p>
	 * </p>
	 * 
	 * @param description
	 * @param bundlemakerProject
	 */
	public static void initialize(
			ModifiableBundleMakerProjectDescription description,
			BundleMakerProject bundlemakerProject) {

		// TODO
		if (description.isValid()) {
			throw new RuntimeException("Invalid description");
		}

		//
		for (ModifiableFileBasedContent fileBasedContent : description
				.getModifiableFileBasedContent()) {
			fileBasedContent.initialize(bundlemakerProject);
		}

		//
		description.setInitialized(true);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param description
	 * @param id
	 * @return
	 */
	public static IFileBasedContent getFileBasedContent(
			ModifiableBundleMakerProjectDescription description, String id) {

		//
		for (ModifiableFileBasedContent fileBasedContent : description
				.getModifiableFileBasedContent()) {

			if (fileBasedContent.getId().equals(id)) {
				return fileBasedContent;
			}
		}

		//
		return null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param description
	 * @return
	 */
	public static List<IFileBasedContent> getFileBasedContent(
			ModifiableBundleMakerProjectDescription description) {

		return Collections
				.unmodifiableList((List<? extends IFileBasedContent>) description
						.getModifiableFileBasedContent());

	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param id
	 * @param binaryRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String binaryRoot) {

		return addResourceContent(projectDescription, binaryRoot, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modifiableBundleMakerProjectDescription
	 * @param id
	 * @param binaryRoot
	 * @param sourceRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription modifiableBundleMakerProjectDescription,
			String binaryRoot, String sourceRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		//
		return addResourceContent(modifiableBundleMakerProjectDescription,
				jarInfo.getName(), jarInfo.getVersion(), binaryRoot, sourceRoot);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param id
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, String binaryRoot) {

		return addResourceContent(projectDescription, name, version,
				binaryRoot, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @param sourceRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, String binaryRoot, String sourceRoot) {

		return addResourceContent(projectDescription, name, version,
				new String[] { binaryRoot },
				sourceRoot != null ? new String[] { sourceRoot }
						: new String[] {});
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @param sourceRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, List<String> binaryRoot,
			List<String> sourceRoot) {

		return addResourceContent(projectDescription, name, version,
				binaryRoot.toArray(new String[0]),
				sourceRoot.toArray(new String[0]));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param binaryRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addTypeContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String binaryRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		return addTypeContent(projectDescription, jarInfo.getName(),
				jarInfo.getVersion(), new String[] { binaryRoot });
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addTypeContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, String binaryRoot) {

		return addTypeContent(projectDescription, name, version,
				new String[] { binaryRoot });
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @return
	 */
	public static ModifiableFileBasedContent addTypeContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, List<String> binaryRoot) {

		return addTypeContent(projectDescription, name, version,
				binaryRoot.toArray(new String[0]));
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param modifiableBundleMakerProjectDescription
	 */
	public static boolean isValid(
			ModifiableBundleMakerProjectDescription modifiableBundleMakerProjectDescription) {

		// TODO
		return modifiableBundleMakerProjectDescription.getJRE() == null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @param sourceRoot
	 * @return
	 */
	// TODO: analyze source!!
	private static ModifiableFileBasedContent addResourceContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, String[] binaryRoot,
			String[] sourceRoot) {

		// create new file based content
		ModifiableFileBasedContent fileBasedContent = ModifiableprojectdescriptionFactory.eINSTANCE
				.createModifiableFileBasedContent();

		//
		// TODO: THREADING
		projectDescription.setCurrentId(projectDescription.getCurrentId() + 1);
		fileBasedContent.setId(FORMATTER.format(projectDescription
				.getCurrentId()));
		fileBasedContent.setName(name);
		fileBasedContent.setVersion(version);

		// add the binary roots
		fileBasedContent.getModifiableBinaryPathNames().addAll(
				Arrays.asList(binaryRoot));

		//
		ModifiableResourceContent resourceContent = ModifiableprojectdescriptionFactory.eINSTANCE
				.createModifiableResourceContent();
		fileBasedContent.setModifiableResourceContent(resourceContent);

		// add the source roots
		resourceContent.getModifiableSourcePathNames().addAll(
				Arrays.asList(sourceRoot));

		// add the analyze flag
		resourceContent.setAnalyzeSourceResources(true);

		// add file based content
		projectDescription.getModifiableFileBasedContent()
				.add(fileBasedContent);

		// return result
		return fileBasedContent;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param projectDescription
	 * @param name
	 * @param version
	 * @param binaryRoot
	 * @param sourceRoot
	 * @return
	 */
	private static ModifiableFileBasedContent addTypeContent(
			ModifiableBundleMakerProjectDescription projectDescription,
			String name, String version, String[] binaryRoot) {

		// create new file based content
		ModifiableFileBasedContent fileBasedContent = ModifiableprojectdescriptionFactory.eINSTANCE
				.createModifiableFileBasedContent();

		//
		// TODO: THREADING
		projectDescription.setCurrentId(projectDescription.getCurrentId() + 1);
		fileBasedContent.setId(FORMATTER.format(projectDescription
				.getCurrentId()));
		fileBasedContent.setName(name);
		fileBasedContent.setVersion(version);

		// add the binary roots
		fileBasedContent.getModifiableBinaryPathNames().addAll(
				Arrays.asList(binaryRoot));

		// add file based content
		projectDescription.getModifiableFileBasedContent()
				.add(fileBasedContent);

		// return result
		return fileBasedContent;
	}
}
