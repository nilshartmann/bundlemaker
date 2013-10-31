/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.project;

import java.util.Map;

import org.bundlemaker.core.common.IGenericAdaptable;
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
public interface IProjectDescriptionAwareBundleMakerProject extends IGenericAdaptable {

  /**
   * <p>
   * Returns the user attributes of this project.
   * </p>
   * 
   * @return the user attributes of this project.
   */
  Map<String, Object> getUserAttributes();

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
   * Returns the name of this project.
   * </p>
   * <p>
   * This is a convenience method for {@link #getProject().getName()}
   * </p>
   * 
   * @return the name of this project.
   */
  String getName();

  /**
   * <p>
   * Returns the {@link IModifiableProjectDescription}.
   * </p>
   * <p>
   * Note that you have to (re-) initialize the {@link IProjectDescriptionAwareBundleMakerProject} in order to put the
   * changed project description into effect: <code><pre>
   * // get the BundleMaker project
   * IBundleMakerProject bundleMakerProject = ...
   * 
   * // get the project description
   * IModifiableBundleMakerProjectDescription projectDescription = getModifiableProjectDescription();
   * 
   * // make changes to the project description
   * projectDescription.setJRE("jdk16");
   * 		
   * // (re-) initialize the project
   * bundleMakerProject.initialize();</pre></code>
   * </p>
   * 
   * @return the {@link IModifiableProjectDescription}.
   * 
   * @precondition none
   */
  public IModifiableProjectDescription getModifiableProjectDescription();

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  public void reloadProjectDescription() throws CoreException;

  /**
   * <p>
   * Allows to modify the {@link IProjectDescription} using the template class {@link IProjectDescriptionModifier}. This
   * method automatically calls {@link IModifiableProjectDescription#save()} after executing
   * {@link IProjectDescriptionModifier#modifyProjectDescription(IModifiableProjectDescription)}.
   * </p>
   * 
   * @param modifier
   * @throws CoreException
   */
  void modifyBundleMakerProjectDescription(IProjectDescriptionModifier modifier) throws CoreException;

  /**
   * <p>
   * Returns the (unmodifiable) IBundleMakerProjectDescription of this
   * {@link IProjectDescriptionAwareBundleMakerProject}.
   * </p>
   * 
   * @return the (unmodifiable) IBundleMakerProjectDescription of this
   *         {@link IProjectDescriptionAwareBundleMakerProject}.
   */
  public IProjectDescription getProjectDescription();

  /**
   * <p>
   * Returns the state this {@link IProjectDescriptionAwareBundleMakerProject} is in.
   * </p>
   * 
   * @return the state this {@link IProjectDescriptionAwareBundleMakerProject} is in.
   * 
   * @precondition none
   */
  BundleMakerProjectState getState();

  /**
   * <p>
   * Adds the specified {@link IBundleMakerProjectChangedListener} to the
   * {@link IProjectDescriptionAwareBundleMakerProject}.
   * </p>
   * 
   * @param listener
   *          the {@link IBundleMakerProjectChangedListener}
   */
  void addBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener);

  /**
   * <p>
   * Removes the specified {@link IBundleMakerProjectChangedListener} from the
   * {@link IProjectDescriptionAwareBundleMakerProject}.
   * </p>
   * 
   * @param listener
   *          the {@link IBundleMakerProjectChangedListener}
   */
  void removeBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener);

  /**
   * <p>
   * Initializes the project. You have to recall this method every time you changed the
   * {@link IProjectDescriptionAwareBundleMakerProject} of this {@link IProjectDescriptionAwareBundleMakerProject}.
   * </p>
   * 
   * @param progressMonitor
   *          the {@link IProgressMonitor} to track the progress.
   * @throws CoreException
   * 
   * @precondition none
   */
  void initialize(IProgressMonitor progressMonitor) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @param progressMonitor
   *          the {@link IProgressMonitor} to track the progress.
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.INITIALIZED | BundleMakerProjectState.READY
   */
  void parseAndOpen(IProgressMonitor progressMonitor) throws CoreException;

  /**
   * <p>
   * Disposes the project.
   * </p>
   */
  void dispose();

  /**
   * <p>
   * <code>
   * bundleMakerProject.modifyBundleMakerProjectDescription(new IProjectDescriptionModifier() {
   * 
   *   public void modifyProjectDescription(IModifiableBundleMakerProjectDescription projectDescription) {
   *   
   *     // clear the project description
   *     projectDescription.clear();
   * 
   *     // add new file based content
   *     FileBasedContentProviderFactory.addNewFileBasedContentProvider(projectDescription, new File(
   *       "hibernate-validator/hibernate-validator-4.2.0.Final.jar").getAbsolutePath());
   * 
   *     // set the JRE
   *     projectDescription.setJre("jdk16");
   *   }
   * });
   * </code>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  static public interface IProjectDescriptionModifier {

    /**
     * <p>
     * Call-back method that allows to modify the project description of this
     * {@link IProjectDescriptionAwareBundleMakerProject}.
     * </p>
     * 
     * @param projectDescription
     *          the modifiable project description
     */
    void modifyProjectDescription(IModifiableProjectDescription projectDescription);
  }
}
