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
package org.bundlemaker.core.internal.store;

import org.bundlemaker.core.IBundleMakerProject;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Factory to create instances of type {@link IPersistentDependencyStore} for a given {@link IBundleMakerProject}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IPersistentDependencyStoreFactory {

  /**
   * <p>
   * Returns a {@link IPersistentDependencyStore} for the specified {@link IBundleMakerProject}. Multiple calls to this
   * method will return the identical {@link IPersistentDependencyStore}.
   * </p>
   * 
   * @param project
   * @return
   */
  public IPersistentDependencyStore getPersistentDependencyStore(IBundleMakerProject project);

  /**
   * <p>
   * Resets the {@link IPersistentDependencyStore} for the specified {@link IBundleMakerProject}.
   * </p>
   * 
   * @param project
   * @throws CoreException
   */
  public void resetPersistentDependencyStore(IBundleMakerProject project) throws CoreException;
}
