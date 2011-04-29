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
package org.bundlemaker.core;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.util.EclipseProjectUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * <p>
 * Core support for bundle maker projects.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclasses by clients.
 */
public final class BundleMakerCore {

  /** the nature id */
  public static final String BUNDLE_ID                  = "org.bundlemaker.core";

  /** the nature id */
  public static final String NATURE_ID                  = "org.bundlemaker.core.bundlemakernature";

  /** the bundle make directory name */
  public static final String BUNDLEMAKER_DIRECTORY_NAME = ".bundlemaker";

  /** the project description file name */
  public static final String PROJECT_DESCRIPTION_NAME   = "bundlemaker.xml";

  /** the project description path */
  public static final IPath  PROJECT_DESCRIPTION_PATH   = new Path(PROJECT_DESCRIPTION_NAME);

  /**
   * <p>
   * Creates a bundle maker project for the given {@link IProject}. The specified project must have the bundle maker
   * nature.
   * </p>
   * 
   * @param project
   * @return
   * @throws CoreException
   */
  public static IBundleMakerProject getBundleMakerProject(IProject project, IProgressMonitor progressMonitor)
      throws CoreException {
    Assert.isNotNull(project);

    // check if nature exists
    if (!project.exists()) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, BundleMakerCore.BUNDLE_ID, "Project '" + project.getName()
          + "' has to exist."));
    }

    // check if nature exists
    if (!project.hasNature(NATURE_ID)) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, BundleMakerCore.BUNDLE_ID, "Project '" + project.getName()
          + "' must have nature '" + NATURE_ID + "'."));
    }

    // // try to get project from cache
    BundleMakerProject bundleMakerProject = (BundleMakerProject) Activator.getDefault().getBundleMakerProject(project);

    // create project if necessary
    if (bundleMakerProject == null) {

      // step 1: create the project
      bundleMakerProject = new BundleMakerProject(project);

      // // step 2: initialize
      // bundleMakerProject.initialize(progressMonitor);

      // step 3: cache the bundle maker project
      Activator.getDefault().cacheBundleMakerProject(project, bundleMakerProject);
    }

    // return result
    return bundleMakerProject;
  }

  /**
   * <p>
   * Create a simple project with the bundle maker nature.
   * </p>
   * 
   * @param projectName
   * @return
   * @throws CoreException
   */
  public static IProject getOrCreateSimpleProjectWithBundleMakerNature(String projectName) throws CoreException {

    // create the bundle maker project
    IProject project = EclipseProjectUtils.getOrCreateSimpleProject(projectName);

    // add the bundle maker nature
    BundleMakerCore.addBundleMakerNature(project);

    // return the newly created project
    return project;
  }

  /**
   * <p>
   * Adds the bundle maker nature to the given project.
   * </p>
   * 
   * @param project
   *          the project
   * @throws CoreException
   */
  public static void addBundleMakerNature(IProject project) throws CoreException {

    if (!project.hasNature(BundleMakerCore.NATURE_ID)) {

      // get the project description
      IProjectDescription description = project.getDescription();

      // set the new nature
      String[] prevNatures = description.getNatureIds();
      String[] newNatures = new String[prevNatures.length + 1];
      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = BundleMakerCore.NATURE_ID;
      description.setNatureIds(newNatures);

      // set the new description
      project.setDescription(description, null);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param simpleProjectName
   * @return
   * @throws CoreException
   */
  public static IBundleMakerProject getBundleMakerProject(String simpleProjectName) throws CoreException {

    // get the project
    IProject project = EclipseProjectUtils.getProject(simpleProjectName);

    // get the bundle maker project
    return BundleMakerCore.getBundleMakerProject(project, null);
  }
}
