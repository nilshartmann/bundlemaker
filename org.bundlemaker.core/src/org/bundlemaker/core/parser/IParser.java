package org.bundlemaker.core.parser;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.model.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Defines the common interface to parse a {@link BundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IParser {

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void parseBundleMakerProjectStart(
			IBundleMakerProject bundleMakerProject);

	/**
	 * <p>
	 * </p>
	 * 
	 * @param content
	 * @param directories
	 * @param cache
	 * @param _progressMonitor 
	 * 
	 * @return
	 * @throws CoreException
	 */
	public List<IProblem> parse(IFileBasedContent content,
			List<IDirectory> directories, IResourceCache cache, IProgressMonitor _progressMonitor)
			throws CoreException;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void parseBundleMakerProjectStop(
			IBundleMakerProject bundleMakerProject);
}
