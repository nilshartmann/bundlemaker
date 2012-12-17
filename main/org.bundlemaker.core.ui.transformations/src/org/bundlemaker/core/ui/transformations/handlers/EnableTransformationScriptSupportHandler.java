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

import java.util.List;

import org.bundlemaker.core.transformations.support.TransformationScriptSupport;
import org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

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
      // shouldn't happen according to enablement policy defined in plugin.xml
      return;
    }

    IAdaptable adaptable = selectedObjects.get(0);

    IResource resource = (IResource) adaptable.getAdapter(IResource.class);

    if (resource == null) {
      // shouldn't happen according to enablement policy defined in plugin.xml
      return;
    }

    // get project
    IProject eclipseProject = resource.getProject();

    if (eclipseProject == null) {
      return;
    }

    // Enable Transformation Script Support
    TransformationScriptSupport.enableTransformationScriptSupport(eclipseProject,
        PreferenceConstants.getDefaultJRELibrary());

    // Open the editor with the sample transformation script
    IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
    if (activeWorkbenchWindow != null) {
      IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
      IFile file = TransformationScriptSupport.findSampleTransformationScript(eclipseProject);

      if (file != null) {
        IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
        if (desc != null)
          activePage.openEditor(new FileEditorInput(file), desc.getId());
      }
    }
  }
}
