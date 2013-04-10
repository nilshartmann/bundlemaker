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
package org.bundlemaker.core.ui.handler;

import java.util.List;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.ui.utils.BundleMakerProjectOpener;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ISelection;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ParseBundleMakerProjectHandler extends AbstractBundleMakerHandler implements IHandler {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.handler.AbstractBundleMakerHandler#execute(org.eclipse.core.commands.ExecutionEvent,
   * org.eclipse.jface.viewers.ISelection)
   */
  @Override
  protected void execute(ExecutionEvent event, ISelection selection) throws Exception {

    // get the selected resource
    List<IResource> selectedObjects = getSelectedObject(selection, IResource.class);

    // check if there's at least one resource
    if (selectedObjects.isEmpty()) {
      return;
    }

    // grab resource and get the project
    IResource selectedResource = selectedObjects.get(0);
    IProject project = selectedResource.getProject();

    // get the BundleMaker project
    IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject(project);

    // clear dependency store
    if (clearPersistentDependencyStore()) {
      BundleMakerCore.clearDependencyStore(bundleMakerProject);
    }

    // open the BundleMaker project
    BundleMakerProjectOpener.openProject(bundleMakerProject);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  protected boolean clearPersistentDependencyStore() {
    return false;
  }
}
