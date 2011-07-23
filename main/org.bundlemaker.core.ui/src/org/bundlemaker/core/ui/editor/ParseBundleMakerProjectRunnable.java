/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor;

import java.lang.reflect.InvocationTargetException;

import org.bundlemaker.core.BundleMakerProjectState;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * A {@link IRunnableWithProgress} that initializes and (re-parses) a bundlemaker project
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ParseBundleMakerProjectRunnable implements IRunnableWithProgress {

  private final IBundleMakerProject _bundleMakerProject;

  /**
   * @param bundleMakerProject
   */
  public ParseBundleMakerProjectRunnable(IBundleMakerProject bundleMakerProject) {
    _bundleMakerProject = bundleMakerProject;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
    try {
      _bundleMakerProject.initialize(monitor);

      // Check if initializing was successful
      BundleMakerProjectState state = _bundleMakerProject.getState();
      if (state == BundleMakerProjectState.INITIALIZED | state == BundleMakerProjectState.READY) {
        // parse the project
        _bundleMakerProject.parseAndOpen(monitor);
      }
    } catch (Exception ex) {
      // Forward exception
      throw new InvocationTargetException(ex);
    }
  }

  /**
   * Initialize and parse the specified project
   * 
   * <p>
   * Errors happening during the operation are reported via Error Log and an Error Dialog
   * 
   * @param bundleMakerProject
   */
  public static void parseProject(IBundleMakerProject bundleMakerProject) {
    // Create runnable
    ParseBundleMakerProjectRunnable runnable = new ParseBundleMakerProjectRunnable(bundleMakerProject);

    // Execute runnable via IProgressService
    try {
      PlatformUI.getWorkbench().getProgressService().busyCursorWhile(runnable);
    } catch (InvocationTargetException ex) {
      // Report Error to error log
      Throwable cause = ex.getCause();
      BundleMakerUiUtils.logError("Error while parsing project: " + cause, cause);

      // Report error to user
      MessageDialog.openError(Display.getCurrent().getActiveShell(), "Could not parse project",
          String.format("Error while parsing project:%n%s%nSee Error Log for details", ex));
    } catch (InterruptedException ex) {
      // ignore. User has canceled the operation
    }

    // Refresh navigator tree
    BundleMakerUiUtils.refreshProjectExplorer(bundleMakerProject);

  }

}
