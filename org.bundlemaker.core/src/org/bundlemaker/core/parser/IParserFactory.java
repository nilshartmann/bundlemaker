package org.bundlemaker.core.parser;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * A parser factory is responsible for creating project parser.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IParserFactory {

	/**
	 * <p>
	 * This method is called immediately after a {@link IParserFactory} has been
	 * created.
	 * </p>
	 */
	public void initialize();

	/**
	 * <p>
	 * This method is called before a {@link IParserFactory} will be destroyed.
	 * </p>
	 */
	public void dispose();

	/**
	 * <p>
	 * Initializes the {@link IParserFactory} for the specified project.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void initialize(IBundleMakerProject bundleMakerProject)
			throws CoreException;

	/**
	 * <p>
	 * Returns <code>true</code>, if this {@link IParserFactory} already has
	 * been initialized.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 * @return <code>true</code>, if this {@link IParserFactory} already has
	 *         been initialized.
	 */
	public boolean isInitialized(IBundleMakerProject bundleMakerProject);

	/**
	 * <p>
	 * Creates a new instance of type {@link IParser} for a given
	 * {@link IBundleMakerProject}.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 *            the {@link IBundleMakerProject}
	 * @return the newly created {@link IParser}
	 * @throws CoreException
	 */
	public IParser createParser(IBundleMakerProject bundleMakerProject)
			throws CoreException;

	/**
	 * <p>
	 * Disposes the {@link IParserFactory} for the specified project.
	 * </p>
	 * 
	 * @param bundleMakerProject
	 */
	public void dispose(IBundleMakerProject bundleMakerProject);
}
