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
package org.bundlemaker.core.analysis.ui.commands;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.ui.Activator;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Abstract base class for BundleMaker-based command handlers.
 * 
 * <p>
 * This class provides access to the IArtifact objects, that are selected in the navigator view
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public abstract class AbstractBundleMakerHandler extends AbstractHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
   */
  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {

    ISelection selection = HandlerUtil.getCurrentSelection(event);
    if (selection instanceof IStructuredSelection) {
      // Get selected elements
      IStructuredSelection structuredSelection = (IStructuredSelection) selection;

      List<IArtifact> selectedArtifacts = new LinkedList<IArtifact>();
      Iterator<?> iterator = structuredSelection.iterator();
      // Convert elements to Artifacts
      while (iterator.hasNext()) {
        Object element = iterator.next();

        IArtifact artifact = (IArtifact) element;
        selectedArtifacts.add(artifact);
      }

      // Invoke execution method
      try {
        execute(selectedArtifacts);
      } catch (Exception ex) {
        reportError(Activator.PLUGIN_ID, "Error while executing command: " + ex, ex);
      }
    }

    // execute() methods always must return null
    return null;
  }

  protected void reportError(String pluginId, String message, Throwable ex) {
    Status errorStatus = new Status(IStatus.ERROR, pluginId, message, ex);
    Activator.getDefault().getLog().log(errorStatus);
  }

  /**
   * Will be invoked when the command is executed. Subclasses must override this method to provide their execution logic
   * 
   * @param selectedArtifacts
   *          The {@link IArtifact} objects that are selected in the tree. Never null.
   * @return
   */
  protected abstract void execute(List<IArtifact> selectedArtifacts) throws Exception;

}
