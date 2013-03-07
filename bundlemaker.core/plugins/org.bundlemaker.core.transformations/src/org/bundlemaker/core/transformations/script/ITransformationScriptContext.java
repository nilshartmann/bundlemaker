/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.script;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface ITransformationScriptContext {

  /**
   * Returns the {@link IProgressMonitor} instance that can be used to report progress
   * 
   * @return
   */
  public IProgressMonitor getProgressMonitor();

  /**
   * Stops the execution of the script if it has been canceled by the user.
   * 
   * @throws InterruptedException
   */
  public void interruptIfCanceled() throws InterruptedException;

  /**
   * Returns the {@link ITransformationScriptLogger} that can be used to add messages to the Script Console
   * 
   * @return
   */
  public ITransformationScriptLogger getLogger();

  /**
   * Gets the {@link IRootArtifact} that this script is running on
   * 
   * @return
   */
  public IRootArtifact getRootArtifact();

}
