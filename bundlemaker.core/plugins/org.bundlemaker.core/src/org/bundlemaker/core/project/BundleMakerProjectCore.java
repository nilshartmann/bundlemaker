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
package org.bundlemaker.core.project;

import java.util.Collection;

import org.bundlemaker.core.common.Activator;
import org.bundlemaker.core.common.Constants;
import org.bundlemaker.core.common.utils.EclipseProjectUtils;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.project.internal.BundleMakerProjectCache;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaCore;

/**
 * <p>
 * Core support for bundle maker projects.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This class is not intended to be subclasses by clients.
 */
public final class BundleMakerProjectCore {

  /** the nature id */
  public static final String BUNDLE_ID                  = "org.bundlemaker.core";

  /** the nature id */
  public static final String NATURE_ID                  = "org.bundlemaker.core.bundlemakernature";

  /** bundlemaker classpath container */
  public static final IPath  BUNDLEMAKER_CONTAINER_PATH = new Path("org.bundlemaker.core.classpath"); //$NON-NLS-1$

  /** the bundle make directory name */
  public static final String BUNDLEMAKER_DIRECTORY_NAME = ".bundlemaker";

  /** the project description file name */
  public static final String PROJECT_DESCRIPTION_NAME   = "bundlemaker.json";

  /** the project description path */
  public static final IPath  PROJECT_DESCRIPTION_PATH   = new Path(PROJECT_DESCRIPTION_NAME);

  /**
   * <p>
   * Returns the version of the bundle '<code>org.bundlemaker.core</code>'.
   * </p>
   * 
   * @return the version of the bundle '<code>org.bundlemaker.core</code>'.
   */
  public static String getBundleMakerVersion() {
    return Activator.getDefault().getBundleVersion();
  }

  /**
   * <p>
   * Creates a bundle maker project for the given {@link IProject}. The specified project must have the bundle maker
   * nature.
   * </p>
   * <p>
   * You can use {@link #isBundleMakerProject(IProject)} to check if the project is BundleMaker project
   * 
   * @param project
   * @return
   * @throws CoreException
   */
  public static IProjectDescriptionAwareBundleMakerProject getProjectDescriptionAwareBundleMakerProject(IProject project)
      throws CoreException {
    Assert.isNotNull(project);

    // check if nature exists
    if (!project.exists()) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, BUNDLE_ID, "Project '" + project.getName()
          + "' has to exist."));
    }

    // check if nature exists
    if (!project.hasNature(NATURE_ID)) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, BUNDLE_ID, "Project '" + project.getName()
          + "' must have nature '" + NATURE_ID + "'."));
    }

    // // try to get project from cache
    IProjectDescriptionAwareBundleMakerProject bundleMakerProject = BundleMakerProjectCache.instance()
        .getBundleMakerProject(project);

    // create project if necessary
    if (bundleMakerProject == null) {

      // step 1: create the project
      bundleMakerProject = new BundleMakerProject(project);

      // step 2: cache the bundle maker project
      BundleMakerProjectCache.instance().cacheBundleMakerProject(project, bundleMakerProject);
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
    addBundleMakerNature(project);

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
    addNature(project, NATURE_ID);
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @throws CoreException
   */
  public static void addJavaNature(IProject project) throws CoreException {
    addNature(project, JavaCore.NATURE_ID);
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   * @throws CoreException
   */
  public static boolean isJavaProject(IProject project) throws CoreException {
    return project.hasNature(JavaCore.NATURE_ID);
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @param nature
   * @throws CoreException
   */
  public static void addNature(IProject project, String nature) throws CoreException {

    //
    if (!project.hasNature(nature)) {

      // get the project description
      IProjectDescription description = project.getDescription();

      // set the new nature
      String[] prevNatures = description.getNatureIds();
      String[] newNatures = new String[prevNatures.length + 1];
      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = nature;
      description.setNatureIds(newNatures);

      // set the new description
      project.setDescription(description, null);
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws CoreException
   */
  public static Collection<? extends IProjectDescriptionAwareBundleMakerProject> getBundleMakerProjects() {

    //
    IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    for (IProject iProject : projects) {
      try {
        if (iProject.exists() && iProject.hasNature(NATURE_ID)) {
          getProjectDescriptionAwareBundleMakerProject(iProject);
        }
      } catch (CoreException e) {
        //
      }
    }

    //
    return BundleMakerProjectCache.instance().getBundleMakerProjects();
  }

  /**
   * <p>
   * Creates a bundle maker project for the given {@link IProject}. The specified project must have the bundle maker
   * nature.
   * </p>
   * <p>
   * You can use {@link #isBundleMakerProject(IProject)} to check if the project is BundleMaker project
   * 
   * @param project
   * @return
   * @throws CoreException
   */
  public static IProjectDescriptionAwareBundleMakerProject getBundleMakerProject(IProject project)
      throws CoreException {
    Assert.isNotNull(project);

    // check if nature exists
    if (!project.exists()) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, Constants.BUNDLE_ID_BUNDLEMAKER_CORE, "Project '"
          + project.getName()
          + "' has to exist."));
    }

    // check if nature exists
    if (!project.hasNature(NATURE_ID)) {
      // TODO: I18N
      throw new CoreException(new Status(IStatus.ERROR, Constants.BUNDLE_ID_BUNDLEMAKER_CORE, "Project '"
          + project.getName()
          + "' must have nature '" + NATURE_ID + "'."));
    }

    // // try to get project from cache
    IProjectDescriptionAwareBundleMakerProject bundleMakerProject = BundleMakerProjectCache.instance()
        .getBundleMakerProject(project);

    // create project if necessary
    if (bundleMakerProject == null) {

      // step 1: create the project
      bundleMakerProject = new BundleMakerProject(project);

      // step 2: cache the bundle maker project
      BundleMakerProjectCache.instance().cacheBundleMakerProject(project, bundleMakerProject);
    }

    // return result
    return bundleMakerProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @param simpleProjectName
   * @return
   * @throws CoreException
   */
  public static IProjectDescriptionAwareBundleMakerProject getProjectDescriptionAwareBundleMakerProject(
      String simpleProjectName)
      throws CoreException {

    // get the project
    IProject project = EclipseProjectUtils.getProject(simpleProjectName);

    // get the bundle maker project
    return getProjectDescriptionAwareBundleMakerProject(project);
  }

  /**
   * <p>
   * Returns <code>true</code> if the specified {@link IProject} is a BundleMaker project.
   * </p>
   * 
   * @param project
   *          the project to test
   * @return
   * @throws CoreException
   */
  public static boolean isBundleMakerProject(IProject project) throws CoreException {

    //
    if (project == null) {
      return false;
    }

    // check if project exists
    if (!project.exists()) {
      return false;
    }

    // check if nature exists
    if (!project.hasNature(NATURE_ID)) {
      return false;
    }

    // returns true
    return true;
  }
}
