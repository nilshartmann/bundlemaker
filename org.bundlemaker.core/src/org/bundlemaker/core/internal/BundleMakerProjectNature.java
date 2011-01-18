package org.bundlemaker.core.internal;

import java.io.IOException;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.model.internal.projectdescription.EProjectDescription;
import org.bundlemaker.core.model.internal.projectdescription.ProjectdescriptionFactory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

/**
 * <p>
 * Implementation of the bundle maker project nature.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleMakerProjectNature implements IProjectNature {

	/** the associated bundle maker project */
	private IProject project;

	/**
	 * {@inheritDoc}
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setProject(IProject value) {
		project = value;
	}

	/**
	 * {@inheritDoc}
	 */
	public void configure() throws CoreException {
		createFolder(project
				.getFolder(BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME));
		addConfigurationFile();
	}

	/**
	 * {@inheritDoc}
	 */
	public void deconfigure() throws CoreException {
		IFolder folder = project
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

		// TODO

		try {
			EProjectDescription projectDescription = ProjectdescriptionFactory.eINSTANCE
					.createEProjectDescription();

			URI uri = URI.createPlatformResourceURI(
					project.getFullPath()
							.append(BundleMakerCore.BUNDLEMAKER_DIRECTORY_NAME)
							.append(BundleMakerCore.PROJECT_DESCRIPTION_NAME)
							.toString(), true);

			Resource resource = new XMLResourceImpl(uri);
			resource.getContents().add(projectDescription);
			resource.save(null);

		} catch (IOException e) {

			// TODO: MSG
			throw new CoreException(new Status(IStatus.ERROR,
					BundleMakerCore.BUNDLE_ID, ""));
		}
	}
}
