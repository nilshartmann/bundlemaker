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

import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.projectdescription.BundleMakerProjectDescription;
import org.bundlemaker.core.internal.store.IDependencyStore;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.projectdescription.IBundleMakerProjectDescription;
import org.bundlemaker.core.resource.IResource;
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

  /** the bundle make directory name */
  public static final String BUNDLEMAKER_DIRECTORY_NAME = ".bundlemaker";

  /** the project description file name */
  public static final String PROJECT_DESCRIPTION_NAME   = "projectdescription.xml";

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
   * Returns the {@link BundleMakerProjectDescription}.
   * </p>
   * <p>
   * Note that you have to (re-) initialize the {@link IBundleMakerProject} in order to put the changed project
   * description into effect: <code><pre>
   * // get the BundleMaker project
   * IBundleMakerProject bundleMakerProject = ...
   * 
   * // get the project description
   * BundleMakerProjectDescription projectDescription = bundleMakerProject.getProjectDescription();
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
  public IBundleMakerProjectDescription getProjectDescription();

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
   * @precondition BundleMakerProjectState.INITIALIZED | BundleMakerProjectState.PARSED | BundleMakerProjectState.OPENED
   */
  void parseAndOpen(IProgressMonitor progressMonitor) throws CoreException;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IProblem> getProblems();

  /**
   * <p>
   * </p>
   */
  void dispose();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IResource> getBinaryResources();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<IResource> getSourceResources();

  /**
   * <p>
   * </p>
   * 
   * @return
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
  IModularizedSystem createModularizedSystemWorkingCopy(String name) throws CoreException;

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
   * Deletes the working copy of type {@link IModularizedSystem} with the given name.
   * </p>
   * 
   * @param name
   * @throws CoreException
   * 
   * @precondition BundleMakerProjectState.OPENED
   */
  void deleteModularizedSystemWorkingCopy(String name) throws CoreException;
}
