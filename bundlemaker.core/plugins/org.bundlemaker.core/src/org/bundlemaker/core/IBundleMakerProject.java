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
package org.bundlemaker.core;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.IProjectDescription;
import org.bundlemaker.core.resource.IModuleResource;
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
   * Returns the {@link BundleMakerProjectDescription}.
   * </p>
   * <p>
   * Note that you have to (re-) initialize the {@link IBundleMakerProject} in order to put the changed project
   * description into effect: <code><pre>
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
   * @return the {@link BundleMakerProjectDescription}.
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
   * Returns the (unmodifiable) IBundleMakerProjectDescription of this {@link IBundleMakerProject}.
   * </p>
   * 
   * @return the (unmodifiable) IBundleMakerProjectDescription of this {@link IBundleMakerProject}.
   */
  public IProjectDescription getProjectDescription();

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
   * Adds the specified {@link IBundleMakerProjectChangedListener} to the {@link IBundleMakerProject}.
   * </p>
   * 
   * @param listener
   *          the {@link IBundleMakerProjectChangedListener}
   */
  void addBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener);

  /**
   * <p>
   * Removes the specified {@link IBundleMakerProjectChangedListener} from the {@link IBundleMakerProject}.
   * </p>
   * 
   * @param listener
   *          the {@link IBundleMakerProjectChangedListener}
   */
  void removeBundleMakerProjectChangedListener(IBundleMakerProjectChangedListener listener);

  /**
   * <p>
   * Initializes the project. You have to recall this method every time you changed the
   * {@link BundleMakerProjectDescription} of this {@link IBundleMakerProject}.
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
   * Returns a list with all {@link IProblem IProblems} that occurred whilst parsing the project content.
   * </p>
   * 
   * @return a list with all {@link IProblem IProblems} that occurred whilst parsing the project content.
   */
  List<IProblem> getProblems();

  /**
   * <p>
   * Disposes the project.
   * </p>
   */
  void dispose();

  /**
   * <p>
   * Returns a list with all binary resources that are contained in this project.
   * </p>
   * 
   * @return a list with all binary resources that are contained in this project.
   */
  List<IModuleResource> getBinaryResources();

  /**
   * <p>
   * Returns a list with all source resources that are contained in this project.
   * </p>
   * 
   * @return a list with all source resources that are contained in this project.
   */
  List<IModuleResource> getSourceResources();

  /**
   * <p>
   * Returns a list with all {@link IModularizedSystem IModularizedSystems} that are defined in this
   * {@link IBundleMakerProject}.
   * </p>
   * 
   * @return a list with all {@link IModularizedSystem IModularizedSystems} that are defined in this
   *         {@link IBundleMakerProject}.
   * @throws CoreException
   */
  Collection<IModularizedSystem> getModularizedSystemWorkingCopies() throws CoreException;

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
  IModularizedSystem createModularizedSystemWorkingCopy(IProgressMonitor progressMonitor, String name)
      throws CoreException;

  /**
   * <p>
   * Returns <code>true</code> if this {@link IBundleMakerProject} contains a working copy of type
   * {@link IModularizedSystem}, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this {@link IBundleMakerProject} contains a working copy of type
   *         {@link IModularizedSystem}, <code>false</code> otherwise.
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.OPENED
   */
  boolean hasModularizedSystemWorkingCopy(String name) throws CoreException;

  /**
   * <p>
   * Returns the working copy of type {@link IModularizedSystem} with the given name.
   * </p>
   * 
   * @return the working copy of type {@link IModularizedSystem} with the given name.
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.OPENED
   */
  IModularizedSystem getModularizedSystemWorkingCopy(String name) throws CoreException;

  /**
   * <p>
   * Returns the default working copy of type {@link IModularizedSystem}.
   * </p>
   * 
   * @return the default working copy of type {@link IModularizedSystem}.
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.OPENED
   */
  IModularizedSystem getModularizedSystemWorkingCopy() throws CoreException;

  /**
   * <p>
   * Deletes the working copy of type {@link IModularizedSystem} with the given name.
   * </p>
   * 
   * @param name
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.OPENED
   */
  void deleteModularizedSystemWorkingCopy(String name) throws CoreException;

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
     * Call-back method that allows to modify the project description of this {@link IBundleMakerProject}.
     * </p>
     * 
     * @param projectDescription
     *          the modifiable project description
     */
    void modifyProjectDescription(IModifiableProjectDescription projectDescription);
  }
}
