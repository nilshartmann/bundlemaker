/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.jdt.internal.parser;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.parser.CoreParserJdt;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DeleteAssociatedProjectChangeListener implements IResourceChangeListener {

  /**
   * {@inheritDoc}
   */
  public void resourceChanged(IResourceChangeEvent event) {

    // get the resource
    IResource resource = event.getResource();

    switch (event.getType()) {

    case IResourceChangeEvent.PRE_CLOSE:

      // do nothing
      break;

    case IResourceChangeEvent.PRE_DELETE:

      // delete project
      deleteAssociatedProjectIfNecessary(resource);
      break;

    case IResourceChangeEvent.POST_CHANGE:

      // do nothing
      break;

    case IResourceChangeEvent.PRE_BUILD:

      // do nothing
      break;

    case IResourceChangeEvent.POST_BUILD:

      // do nothing
      break;
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param resource
   */
  private void deleteAssociatedProjectIfNecessary(final IResource resource) {

    try {

      if (resource instanceof IProject && ((IProject) resource).hasNature(BundleMakerCore.NATURE_ID)) {

        // create a new workspace job
        WorkspaceJob workspaceJob = new WorkspaceJob("delete") {

          @Override
          public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

            try {

              IBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject((IProject) resource, null);

              if (JdtProjectHelper.hasAssociatedJavaProject(bundleMakerProject)) {

                // get the associated java project
                IProject associatedJavaProject = JdtProjectHelper.getAssociatedJavaProjectAsProject(bundleMakerProject);

                if (!associatedJavaProject.isOpen()) {

                  // simply delete the project
                  associatedJavaProject.open(null);
                }

                associatedJavaProject.delete(true, null);

              }

              //
              return new Status(IStatus.OK, CoreParserJdt.BUNDLE_ID, null);

            } catch (Exception e) {

              // TODO
              return new Status(IStatus.ERROR, CoreParserJdt.BUNDLE_ID, "Could not delete project.");
            }

          }
        };

        // schedule the job
        workspaceJob.schedule();
      }

    } catch (CoreException e) {
      e.printStackTrace();
    }
  }
}
