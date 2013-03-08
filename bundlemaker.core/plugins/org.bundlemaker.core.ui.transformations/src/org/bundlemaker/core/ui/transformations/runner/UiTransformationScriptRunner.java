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

import java.lang.reflect.InvocationTargetException;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.transformations.script.runner.TransformationScriptRunner;
import org.bundlemaker.core.ui.transformations.Activator;
import org.bundlemaker.core.ui.transformations.console.TransformationScriptConsoleFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class UiTransformationScriptRunner extends TransformationScriptRunner implements IRunnableWithProgress {

  private final Shell _shell;

  public UiTransformationScriptRunner(Shell shell, IModularizedSystem modularizedSystem, IType transformationScriptType) {
    super(modularizedSystem, transformationScriptType);

    this._shell = shell;
  }

  public UiTransformationScriptRunner(Shell shell, IBundleMakerArtifact artifact, IType transformationScriptType) {
    super(artifact.getModularizedSystem(), transformationScriptType);

    this._shell = shell;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
   */
  @Override
  public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
    try {
      // Make sure the console with output is visible
      TransformationScriptConsoleFactory.showConsole();

      runScript(monitor);
    } catch (InterruptedException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new InvocationTargetException(ex);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.transformations.script.runner.TransformationScriptRunner#handleScriptException(java.lang.Exception
   * )
   */
  @Override
  protected void handleScriptException(final Exception ex) {

    //
    Activator
        .getDefault()
        .getLog()
        .log(
            new Status(Status.ERROR, Activator.PLUGIN_ID,
                String.format("Execution of transformation script failed with %s: %s", ex.getClass().getSimpleName(),
                    ex.getMessage()), ex));

    //
    _shell.getDisplay().asyncExec(new Runnable() {

      @Override
      public void run() {

        getLogger().log("Execution of transformation script failed with Exception: " + ex, ex);

        MessageDialog
            .openError(_shell, "Transformation Script failed",
                "Execution of transformation script failed with Exception:\n\n" + ex
                    + "\n\nSee Error Log for more details");
      }
    });

  }
}
