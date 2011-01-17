package org.bundlemaker.core.projectdescription;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleMakerProjectDescription {

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

	String getJRE();
}
