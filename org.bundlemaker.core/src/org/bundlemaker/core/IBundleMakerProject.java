package org.bundlemaker.core;

import java.util.List;

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.model.projectdescription.modifiableprojectdescription.ModifiableBundleMakerProjectDescription;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.parser.store.IDependencyStore;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * Represents a bundle maker project.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IBundleMakerProject {

	/** DEFAULT_WORKING_COPY_ID */
	static final String DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID = "DEFAULT_MODULARIZED_SYSTEM_WORKING_COPY_ID";

	/** the bundle make directory name */
	public static final String BUNDLEMAKER_DIRECTORY_NAME = ".bundlemaker";

	/** the project description file name */
	public static final String PROJECT_DESCRIPTION_NAME = "projectdescription.xml";

	/**
	 * <p>
	 * Returns the associated <code>IProject</code>.
	 * </p>
	 * 
	 * @return the associated <code>IProject</code>.
	 * 
	 * @precondition none
	 */
	IProject getProject();

	/**
	 * <p>
	 * Returns the {@link ModifiableBundleMakerProjectDescription}.
	 * </p>
	 * <p>
	 * Note that you have to (re-) initialize the {@link IBundleMakerProject} in
	 * order to put the changed project description into effect: <code><pre>
	 * // get the BundleMaker project
	 * IBundleMakerProject bundleMakerProject = ...
	 * 
	 * // get the project description
	 * ModifiableBundleMakerProjectDescription projectDescription = bundleMakerProject.getProjectDescription();
	 * 
	 * // make changes to the project description
	 * projectDescription.setJRE("jdk16");
	 * 		
	 * // (re-) initialize the project
	 * projectDescription.initialize();</pre></code>
	 * </p>
	 * 
	 * @return the {@link ModifiableBundleMakerProjectDescription}.
	 * 
	 * @precondition none
	 */
	public ModifiableBundleMakerProjectDescription getProjectDescription();

	/**
	 * <p>
	 * Saves the {@link ModifiableBundleMakerProjectDescription}.
	 * </p>
	 * <p>
	 * The project description is saved internally in the xml file
	 * <code>"&lt;project-directory&gt;/.bundlemaker/projectdescription.xml"</code>
	 * . Note that it's not intended to directly modify this file.
	 * </p>
	 * 
	 * @throws CoreException
	 * 
	 * @precondition none
	 */
	public void saveProjectDescription() throws CoreException;

	/**
	 * <p>
	 * Returns the state this {@link IBundleMakerProject} is in.
	 * </p>
	 * 
	 * @return the state this {@link IBundleMakerProject} is in.
	 * 
	 * @precondition none
	 */
	BundleMakerProjectState getState();

	/**
	 * <p>
	 * Initializes the project. You have to recall this method every time you
	 * changed the {@link ModifiableBundleMakerProjectDescription} of this
	 * {@link IBundleMakerProject}.
	 * </p>
	 * 
	 * @param progressMonitor
	 *            the {@link IProgressMonitor} to track the progress.
	 * @throws CoreException
	 * 
	 * @precondition none
	 */
	void initialize(IProgressMonitor progressMonitor) throws CoreException;

	/**
	 * <p>
	 * Parses the {@link BundleMakerProject} and stores the analyzed
	 * dependencies in the underlying {@link IDependencyStore}.
	 * </p>
	 * 
	 * @param progressMonitor
	 *            the {@link IProgressMonitor} to track the progress.
	 * @throws CoreException
	 * 
	 * @precondition BundleMakerProjectState.INITIALIZED |
	 *               BundleMakerProjectState.PARSED |
	 *               BundleMakerProjectState.OPENED
	 */
	List<? extends IProblem> parse(IProgressMonitor progressMonitor)
			throws CoreException;

	/**
	 * <p>
	 * Opens the {@link BundleMakerProject}. Opening a
	 * {@link IBundleMakerProject} forces the project to read all the
	 * dependencies from the underlying dependency database and complete the set
	 * up of the internal data model.
	 * </p>
	 * 
	 * @throws CoreException
	 */
	void open(IProgressMonitor progressMonitor) throws CoreException;

	/**
	 * <p>
	 * Creates a new working copy of type {@link IModularizedSystem}.
	 * </p>
	 * 
	 * @return a new working copy of type {@link IModularizedSystem}.
	 * @throws CoreException
	 * 
	 * @precondition BundleMakerProjectState.OPENED
	 */
	void createModularizedSystemWorkingCopy(String name) throws CoreException;

	/**
	 * <p>
	 * Returns <code>true</code> if this {@link IBundleMakerProject} contains a
	 * working copy of type {@link IModularizedSystem}, <code>false</code>
	 * otherwise.
	 * </p>
	 * 
	 * @return <code>true</code> if this {@link IBundleMakerProject} contains a
	 *         working copy of type {@link IModularizedSystem},
	 *         <code>false</code> otherwise.
	 * @throws CoreException
	 * 
	 * @precondition BundleMakerProjectState.OPENED
	 */
	boolean hasModularizedSystemWorkingCopy(String name) throws CoreException;

	/**
	 * <p>
	 * Returns the working copy of type {@link IModularizedSystem} with the
	 * given name.
	 * </p>
	 * 
	 * @return the working copy of type {@link IModularizedSystem} with the
	 *         given name.
	 * @throws CoreException
	 * 
	 * @precondition BundleMakerProjectState.OPENED
	 */
	IModularizedSystem getModularizedSystemWorkingCopy(String name)
			throws CoreException;

	/**
	 * <p>
	 * Deletes the working copy of type {@link IModularizedSystem} with the
	 * given name.
	 * </p>
	 * 
	 * @param name
	 * @throws CoreException
	 * 
	 * @precondition BundleMakerProjectState.OPENED
	 */
	void deleteModularizedSystemWorkingCopy(String name) throws CoreException;
}
