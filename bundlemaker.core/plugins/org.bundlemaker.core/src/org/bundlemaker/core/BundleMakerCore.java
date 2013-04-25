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

import java.util.Collection;

import org.bundlemaker.core.internal.Activator;
import org.bundlemaker.core.internal.BundleMakerProject;
import org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory;
import org.bundlemaker.core.util.EclipseProjectUtils;
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
public final class BundleMakerCore {

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
   * </p>
   * 
   * @return
   */
  public static String getVersion() {
    return Activator.getDefault().getBundleVersion();
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   * @throws CoreException
   */
  public static void clearDependencyStore(IBundleMakerProject bundleMakerProject)
      throws CoreException {

    //
    Assert.isNotNull(bundleMakerProject);

    //
    IPersistentDependencyStoreFactory factory = Activator.getDefault().getPersistentDependencyStoreFactory();
    factory.resetPersistentDependencyStore(bundleMakerProject);
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
  public static IBundleMakerProject getBundleMakerProject(IProject project)
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
    IBundleMakerProject bundleMakerProject = (IBundleMakerProject) Activator.getDefault()
        .getBundleMakerProject(project);

    // create project if necessary
    if (bundleMakerProject == null) {

      // step 1: create the project
      bundleMakerProject = new BundleMakerProject(project);

      // step 2: cache the bundle maker project
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
    addNature(project, BundleMakerCore.NATURE_ID);
  }

  public static void addJavaNature(IProject project) throws CoreException {
    addNature(project, JavaCore.NATURE_ID);
  }

  public static boolean isJavaProject(IProject project) throws CoreException {
    return project.hasNature(JavaCore.NATURE_ID);
  }

  public static void addNature(IProject project, String nature) throws CoreException {
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
  public static Collection<IBundleMakerProject> getBundleMakerProjects() {

    //
    IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
    for (IProject iProject : projects) {
      try {
        if (iProject.exists() && iProject.hasNature(BundleMakerCore.NATURE_ID)) {
          getBundleMakerProject(iProject);
        }
      } catch (CoreException e) {
        //
      }
    }

    //
    return Activator.getDefault().getBundleMakerProjects();
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
    return BundleMakerCore.getBundleMakerProject(project);
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
