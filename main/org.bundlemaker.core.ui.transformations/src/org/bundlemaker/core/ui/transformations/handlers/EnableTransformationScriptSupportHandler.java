/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.handlers;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.transformations.support.TransformationScriptSupport;
import org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.viewers.ISelection;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class EnableTransformationScriptSupportHandler extends AbstractBundleMakerHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * org.eclipse.jface.viewers.ISelection)
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    List<IAdaptable> selectedObjects = getSelectedObject(selection, IAdaptable.class);
    if (selectedObjects.size() < 1) {
      return;
    }

    List<IProject> handledProjects = new LinkedList<IProject>();

    for (IAdaptable adaptable : selectedObjects) {

      IResource resource = (IResource) adaptable.getAdapter(IResource.class);

      if (resource == null) {
        continue;
      }

      // get project
      IProject eclipseProject = resource.getProject();

      if (eclipseProject != null && !handledProjects.contains(eclipseProject)) {
        TransformationScriptSupport.enableTransformationScriptSupport(eclipseProject,
            PreferenceConstants.getDefaultJRELibrary());

        // remember project to not handle it twice
        handledProjects.add(eclipseProject);

      }
    }

    // TODO open editor
  }

}
