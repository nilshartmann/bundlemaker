/*******************************************************************************
 * Copyright (c) 2012 BundleMaker project team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.hook;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A hook that can be used to customize the BundleMaker project creation.
 * 
 * <p>
 * <b>NOTE!</b> This hook is BundleMaker internal only, and <b>should not be implemented, instantiated or registered at
 * the service registry!</b>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface IBundleMakerProjectHook {

  /**
   * Invoked when a new IModularized system has been created. The BasicTransformation already has been run on the
   * specified instance
   * 
   * @param modularizedSystem
   */
  void modularizedSystemCreated(IProgressMonitor progressMonitor, IModularizedSystem modularizedSystem)
      throws Exception;

}
