package org.bundlemaker.core.projectdescription;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IBundleMakerProjectDescription {

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	IBundleMakerProject getBundleMakerProject();

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	List<? extends IFileBasedContent> getFileBasedContent();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param id
	 * @return
	 */
	IFileBasedContent getFileBasedContent(String id);

	/**
	 * @return
	 */
	String getJRE();

	void setJre(String jre);

	void addTypeContent(String name, String version, List<String> binaryRoot);

	void addTypeContent(String name, String version, String binaryRoot);

	void addTypeContent(String binaryRoot);

	void addResourceContent(String name, String version,
			List<String> binaryRoot, List<String> sourceRoot);

	void addResourceContent(String name, String version, String binaryRoot,
			String sourceRoot);

	void addResourceContent(String name, String version, String binaryRoot);

	void addResourceContent(String binaryRoot, String sourceRoot);

	void addResourceContent(String binaryRoot);

	void removeContent(String id);

	void clear();
}
