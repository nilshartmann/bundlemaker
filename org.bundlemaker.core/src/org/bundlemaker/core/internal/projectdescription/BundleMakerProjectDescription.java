package org.bundlemaker.core.internal.projectdescription;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.Assert;
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

	/** - */
	private IBundleMakerProject _bundleMakerProject;

	/**
	 * <p>
	 * Creates a new instance of type {@link BundleMakerProjectDescription}.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public BundleMakerProjectDescription(IBundleMakerProject bundleMakerProject) {

		//
		_fileBasedContent = new ArrayList<FileBasedContent>();
		_bundleMakerProject = bundleMakerProject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBundleMakerProject getBundleMakerProject() {
		return _bundleMakerProject;
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

	@Override
	public void removeContent(String id) {

		for (Iterator<FileBasedContent> iterator = _fileBasedContent.iterator(); iterator
				.hasNext();) {

			FileBasedContent content = (FileBasedContent) iterator.next();

			if (content.getId().equals(id)) {
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public void clear() {

		//
		_fileBasedContent.clear();

		//
		_currentId = 0;

		//
		_initialized = false;

		//
		_jre = null;
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

				binaryResourcesCount += fileBasedContent
						.getModifiableResourceContent()
						.getModifiableBinaryResources().size();

				sourceResourcesCount += fileBasedContent
						.getModifiableResourceContent()
						.getModifiableSourceResources().size();
			}
		}

		// TODO:
		System.out.println("Source resources to process: "
				+ sourceResourcesCount);
		System.out.println("Binary resources to process: "
				+ binaryResourcesCount);

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

	@Override
	public void addResourceContent(String binaryRoot) {

		addResourceContent(binaryRoot, null);
	}

	@Override
	public void addResourceContent(String binaryRoot, String sourceRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		//
		addResourceContent(jarInfo.getName(), jarInfo.getVersion(), binaryRoot,
				sourceRoot);
	}

	@Override
	public void addResourceContent(String name, String version,
			String binaryRoot) {

		addResourceContent(name, version, binaryRoot, null);
	}

	@Override
	public void addResourceContent(String name, String version,
			String binaryRoot, String sourceRoot) {

		addResourceContent(name, version, new String[] { binaryRoot },
				sourceRoot != null ? new String[] { sourceRoot }
						: new String[] {});
	}

	@Override
	public void addResourceContent(String name, String version,
			List<String> binaryRoot, List<String> sourceRoot) {

		addResourceContent(name, version, binaryRoot.toArray(new String[0]),
				sourceRoot.toArray(new String[0]));
	}

	@Override
	public void addTypeContent(String binaryRoot) {

		// get the jar info
		JarInfo jarInfo = JarInfoService.extractJarInfo(new File(binaryRoot));

		addTypeContent(jarInfo.getName(), jarInfo.getVersion(),
				new String[] { binaryRoot });
	}

	@Override
	public void addTypeContent(String name, String version, String binaryRoot) {

		addTypeContent(name, version, new String[] { binaryRoot });
	}

	@Override
	public void addTypeContent(String name, String version,
			List<String> binaryRoot) {

		addTypeContent(name, version, binaryRoot.toArray(new String[0]));
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

	@Override
	public void setJre(String jre) {
		_jre = jre;
	}

	public void setCurrentId(int currentId) {
		_currentId = currentId;
	}
}
