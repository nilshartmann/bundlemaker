package org.bundlemaker.core.internal;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Implementation of the bundle maker project nature.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectNature implements IProjectNature {

	/** the associated bundle maker project */
	private IProject _project;

	/**
	 * {@inheritDoc}
	 */
	public IProject getProject() {
		return _project;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setProject(IProject value) {
		_project = value;
	}

	/**
	 * {@inheritDoc}
	 */
	public void configure() throws CoreException {
		createFolder(_project
				.getFolder(BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME));
		addConfigurationFile();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deconfigure() throws CoreException {
		IFolder folder = _project
				.getFolder(BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME);
		folder.delete(true, null);
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param folder
	 * @throws CoreException
	 */
	private static void createFolder(IFolder folder) throws CoreException {

		IContainer parent = folder.getParent();

		if (parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}

		if (!folder.exists()) {
			folder.create(false, true, null);
		}
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @throws CoreException
	 */
	private void addConfigurationFile() throws CoreException {
		ProjectDescriptionStore.saveProjectDescription(_project,
				new BundleMakerProjectDescription());
	}
}
