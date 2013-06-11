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
package org.bundlemaker.core.resource;

import java.util.Collection;
import java.util.List;

import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
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
public interface IModuleAwareBundleMakerProject extends IProjectDescriptionAwareBundleMakerProject {

  /**
   * <p>
   * Returns a list with all {@link IModularizedSystem IModularizedSystems} that are defined in this
   * {@link IModuleAwareBundleMakerProject}.
   * </p>
   * 
   * @return a list with all {@link IModularizedSystem IModularizedSystems} that are defined in this
   *         {@link IModuleAwareBundleMakerProject}.
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
   * Returns <code>true</code> if this {@link IModuleAwareBundleMakerProject} contains a working copy of type
   * {@link IModularizedSystem}, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this {@link IModuleAwareBundleMakerProject} contains a working copy of type
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
}
