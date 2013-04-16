/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.operations;

import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.ErrorDialogUtil;
import org.bundlemaker.core.ui.internal.Activator;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractUiOperation {

  private final Shell                      _shell;

  private final List<IBundleMakerArtifact> _artifacts;

  /**
   * 
   */
  public AbstractUiOperation(Shell shell, List<IBundleMakerArtifact> artifacts) {
    this._shell = shell;
    this._artifacts = artifacts;
  }

  protected Shell getShell() {
    return this._shell;
  }

  /**
   * @return the artifacts
   */
  public List<IBundleMakerArtifact> getArtifacts() {
    return _artifacts;
  }

  public boolean hasArtifacts() {
    return (_artifacts != null && !_artifacts.isEmpty());
  }

  public void run() {
    try {
      doRun();
    } catch (Exception ex) {
      operationFailed(ex);
    }
  }

  protected abstract void doRun();

  protected void operationFailed(Throwable t) {
    reportError(t.getMessage(), t);
  }

  protected void reportError(String msg, Throwable t) {
    Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, msg, t));
    ErrorDialogUtil.errorDialogWithStackTrace("Execution failed", msg, Activator.PLUGIN_ID, t);

  }

}
