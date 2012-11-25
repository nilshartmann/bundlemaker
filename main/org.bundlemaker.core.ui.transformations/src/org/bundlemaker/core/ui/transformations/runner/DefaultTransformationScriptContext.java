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
package org.bundlemaker.core.ui.transformations.runner;

import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.transformations.script.ITransformationScriptContext;
import org.bundlemaker.core.transformations.script.ITransformationScriptLogger;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DefaultTransformationScriptContext implements ITransformationScriptContext {

  private final IProgressMonitor            _progressMonitor;

  private final ITransformationScriptLogger _logger;

  private final IRootArtifact               _rootArtifact;

  DefaultTransformationScriptContext(IProgressMonitor progressMonitor, ITransformationScriptLogger logger,
      IRootArtifact rootArtifact) {
    this._progressMonitor = progressMonitor;
    this._logger = logger;
    this._rootArtifact = rootArtifact;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.ITransformationScriptContext#getProgressMonitor()
   */
  @Override
  public IProgressMonitor getProgressMonitor() {
    return _progressMonitor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.ITransformationScriptContext#stopIfCanceled()
   */
  @Override
  public void interruptIfCanceled() throws InterruptedException {
    if (_progressMonitor.isCanceled()) {
      throw new InterruptedException("Script execution has been canceled");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.ITransformationScriptContext#getLogger()
   */
  @Override
  public ITransformationScriptLogger getLogger() {
    return _logger;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.transformations.script.ITransformationScriptContext#getRootArtifact()
   */
  @Override
  public IRootArtifact getRootArtifact() {
    return _rootArtifact;
  }

}
