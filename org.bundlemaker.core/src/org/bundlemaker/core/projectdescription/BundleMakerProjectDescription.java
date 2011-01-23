package org.bundlemaker.core.projectdescription;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.JarInfo;
import org.bundlemaker.core.internal.projectdescription.JarInfoService;
import org.eclipse.core.runtime.Path;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectDescription implements
		IBundleMakerProjectDescription {

	/** - */
	private static NumberFormat FORMATTER = new DecimalFormat("000000");

	/** - */
	private List<FileBasedContent> _fileBasedContent;

	/** - */
	private String _jre;

	/** - */
	private boolean _initialized;

	/** - */
	private int _currentId = 0;

	/**
	 * <p>
	 * Creates a new instance of type {@link BundleMakerProjectDescription}.
	 * </p>
	 */
	public BundleMakerProjectDescription() {

		//
		_fileBasedContent = new ArrayList<FileBasedContent>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<? extends IFileBasedContent> getFileBasedContent() {

		//
		return Collections.unmodifiableList(_fileBasedContent);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IFileBasedContent getFileBasedContent(String id) {

		// file based content
		for (FileBasedContent fileBasedContent : _fileBasedContent) {

			//
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
	 * @param bundlemakerProject
	 */
	public void initialize(IBundleMakerProject bundlemakerProject) {

		// TODO
		if (isValid()) {
			throw new RuntimeException("Invalid description");
		}

		//
		int sourceResourcesCount = 0;
		int binaryResourcesCount = 0;

		//
		for (FileBasedContent fileBasedContent : _fileBasedContent) {
			fileBasedContent.initialize(bundlemakerProject);

			//
			if (fileBasedContent.isResourceContent()) {

				sourceResourcesCount += fileBasedContent
						.getModifiableResourceContent()
						.getModifiableBinaryResources().size();

				binaryResourcesCount += fileBasedContent
						.getModifiableResourceContent()
						.getModifiableSourceResources().size();
			}
		}

		// TODO:
		System.out.println("Source resources to process: " + sourceResourcesCount);
		System.out.println("Binary resources to process: " + binaryResourcesCount);

		//
		_initialized = true;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isValid() {
		return _jre == null;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public String getJRE() {
		return _jre;
	}

	public boolean isInitialized() {
		return _initialized;
	}

	public FileBasedContent addResourceContent(String binaryRoot) {

		return addResourceContent(binaryRoot, null);
	}

	public FileBasedContent addResourceContent(String binaryRoot,
			String sourceRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		//
		return addResourceContent(jarInfo.getName(), jarInfo.getVersion(),
				binaryRoot, sourceRoot);
	}

	public FileBasedContent addResourceContent(String name, String version,
			String binaryRoot) {

		return addResourceContent(name, version, binaryRoot, null);
	}

	public FileBasedContent addResourceContent(String name, String version,
			String binaryRoot, String sourceRoot) {

		return addResourceContent(name, version, new String[] { binaryRoot },
				sourceRoot != null ? new String[] { sourceRoot }
						: new String[] {});
	}

	public FileBasedContent addResourceContent(String name, String version,
			List<String> binaryRoot, List<String> sourceRoot) {

		return addResourceContent(name, version,
				binaryRoot.toArray(new String[0]),
				sourceRoot.toArray(new String[0]));
	}

	public FileBasedContent addTypeContent(String binaryRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		return addTypeContent(jarInfo.getName(), jarInfo.getVersion(),
				new String[] { binaryRoot });
	}

	public FileBasedContent addTypeContent(

	String name, String version, String binaryRoot) {

		return addTypeContent(name, version, new String[] { binaryRoot });
	}

	public FileBasedContent addTypeContent(String name, String version,
			List<String> binaryRoot) {

		return addTypeContent(name, version, binaryRoot.toArray(new String[0]));
	}

	// TODO: analyze source!!
	private FileBasedContent addResourceContent(String name, String version,
			String[] binaryRoot, String[] sourceRoot) {

		// create new file based content
		FileBasedContent fileBasedContent = new FileBasedContent();

		// TODO: THREADING
		_currentId++;

		fileBasedContent.setId(FORMATTER.format(_currentId));
		fileBasedContent.setName(name);
		fileBasedContent.setVersion(version);

		// add the binary roots
		for (String string : binaryRoot) {
			fileBasedContent.getModifiableBinaryPaths().add(new Path(string));
		}

		//
		ResourceContent resourceContent = new ResourceContent();
		fileBasedContent.setResourceContent(resourceContent);

		// add the source roots
		for (String string : sourceRoot) {
			resourceContent.getModifiableSourcePaths().add(new Path(string));
		}

		// add the analyze flag
		resourceContent.setAnalyzeSourceResources(true);

		// add file based content
		_fileBasedContent.add(fileBasedContent);

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
	private FileBasedContent addTypeContent(String name, String version,
			String[] binaryRoot) {

		// create new file based content
		FileBasedContent fileBasedContent = new FileBasedContent();

		//
		// TODO: THREADING
		_currentId++;

		fileBasedContent.setId(FORMATTER.format(_currentId));
		fileBasedContent.setName(name);
		fileBasedContent.setVersion(version);

		// add the binary roots
		for (String string : binaryRoot) {
			fileBasedContent.getModifiableBinaryPaths().add(new Path(string));
		}

		// add file based content
		_fileBasedContent.add(fileBasedContent);

		// return result
		return fileBasedContent;
	}

	public List<FileBasedContent> getModifiableFileBasedContent() {

		//
		return _fileBasedContent;
	}

	public int getCurrentId() {
		return _currentId;
	}

	public void setJre(String jre) {
		_jre = jre;
	}

	public void setCurrentId(int currentId) {
		_currentId = currentId;
	}
}
