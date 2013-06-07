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

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.common.utils.VMInstallUtils;
import org.bundlemaker.core.jdt.parser.CoreParserJdt;
import org.bundlemaker.core.project.BundleMakerCore;
import org.bundlemaker.core.project.IProjectContentEntry;
import org.bundlemaker.core.project.IProjectDescriptionAwareBundleMakerProject;
import org.bundlemaker.core.project.VariablePath;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProjectHelper {

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @throws CoreException
   */
  public static void setupAssociatedJavaProject(IProjectDescriptionAwareBundleMakerProject project) throws CoreException {

    try {

      // step 1: get the associated JDT project
      IJavaProject javaProject = getAssociatedJavaProject(project);

      // step 2: 'unset' the class path
      javaProject.setRawClasspath(null, null);

      // step 3: create the entries list
      List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();

      // step 3.1: add the vm path
      IVMInstall vmInstall = VMInstallUtils.getIVMInstall(project.getProjectDescription().getJRE());
      IPath path = JavaRuntime.newJREContainerPath(vmInstall);
      IClasspathEntry classpathEntry = JavaCore.newContainerEntry(path);
      entries.add(classpathEntry);

      // step 3.2: add the binary paths
      for (IProjectContentEntry projectContent : project.getProjectDescription().getContent()) {

        // TODO!!
        IPath sourceRoot = null;
        if (!projectContent.getSourceRootPaths().isEmpty()) {
          sourceRoot = projectContent.getSourceRootPaths().toArray(new VariablePath[0])[0]
              .getResolvedPath();
        }

        // add binary paths
        for (VariablePath iClasspathEntry : projectContent.getBinaryRootPaths()) {
          classpathEntry = JavaCore.newLibraryEntry(iClasspathEntry.getResolvedPath(), sourceRoot, null);
          entries.add(classpathEntry);
        }
      }

      // set the classpath
      javaProject.setRawClasspath(entries.toArray(new IClasspathEntry[0]), null);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static boolean hasAssociatedJavaProject(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {
    return hasAssociatedJavaProject(bundleMakerProject.getProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  public static boolean hasAssociatedJavaProject(IProject bundleMakerProject) {

    // the associatedProjectName
    String associatedProjectName = getAssociatedJavaProjectName(bundleMakerProject);

    //
    if (!ResourcesPlugin.getWorkspace().getRoot().exists(new Path(associatedProjectName))) {
      return false;
    } else {

      try {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(associatedProjectName);
        project.open(IResource.HIDDEN, null);

        IJavaProject javaProject = JavaCore.create(project);

        try {
          javaProject.open(null);
        } catch (JavaModelException e) {
          throw new RuntimeException(e.getMessage());
        }
      } catch (Exception e) {
        return false;
      }

      return true;
    }
  }

  public static IJavaProject getAssociatedJavaProject(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {

    return getAssociatedJavaProject(bundleMakerProject.getProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  public static IJavaProject getAssociatedJavaProject(IProject bundleMakerProject) {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    IJavaProject javaProject = JavaCore.create(associatedProject);

    try {
      javaProject.open(null);
    } catch (JavaModelException e) {
      throw new RuntimeException(e.getMessage());
    }

    return javaProject;
  }

  public static IProject getAssociatedJavaProjectAsProject(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) {

    return getAssociatedJavaProjectAsProject(bundleMakerProject.getProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  public static IProject getAssociatedJavaProjectAsProject(IProject bundleMakerProject) {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    return associatedProject;
  }

  public static IJavaProject newAssociatedJavaProject(IProjectDescriptionAwareBundleMakerProject bundleMakerProject) throws CoreException {
    return newAssociatedJavaProject(bundleMakerProject.getProject());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   * @throws CoreException
   */
  public static IJavaProject newAssociatedJavaProject(IProject bundleMakerProject) throws CoreException {

    IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    IProject associatedProject = root.getProject(getAssociatedJavaProjectName(bundleMakerProject));

    if (associatedProject.exists()) {
      associatedProject.delete(true, null);
    }

    Assert.isTrue(!associatedProject.exists());

    IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(
        getAssociatedJavaProjectName(bundleMakerProject));

    description.setNatureIds(new String[] { JavaCore.NATURE_ID });

    associatedProject.create(description, IResource.HIDDEN, null);
    associatedProject.open(IResource.HIDDEN, null);

    IJavaProject javaProject = JavaCore.create(associatedProject);

    associatedProject.setHidden(true);
    associatedProject.refreshLocal(IResource.DEPTH_INFINITE, null);

    return javaProject;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static IPath makeCanonical(IPath path) {

    String[] segments = path.segments();
    StringBuilder builder = null;
    for (String segment : segments) {
      if (builder == null) {
        builder = new StringBuilder(segment);
      } else {
        builder.append("-" + segment);
      }
    }

    return new Path(builder.toString());
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @return
   */
  private static String getAssociatedJavaProjectName(IProject project) {
    return project.getName() + CoreParserJdt.BUNDLEMAKER_JDT_PROJECT_POSTFIX;
  }

  public static void deleteAssociatedProjectIfNecessary(final IProject resource) {

    try {

      if (resource instanceof IProject && ((IProject) resource).hasNature(BundleMakerCore.NATURE_ID)) {

        // create a new workspace job
        WorkspaceJob workspaceJob = new WorkspaceJob("delete") {

          @Override
          public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

            try {

              IProjectDescriptionAwareBundleMakerProject bundleMakerProject = BundleMakerCore.getBundleMakerProject((IProject) resource);

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
