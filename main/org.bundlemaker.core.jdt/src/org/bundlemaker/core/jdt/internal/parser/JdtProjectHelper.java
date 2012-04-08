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

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.jdt.parser.CoreParserJdt;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.util.JdkCreator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
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
  public static void setupAssociatedJavaProject(IBundleMakerProject project) throws CoreException {

    try {
      System.out.println("setupAssociatedJavaProject(" + project + ")");

      // step 1: get the associated JDT project
      IJavaProject javaProject = getAssociatedJavaProject(project);

      // step 2: 'unset' the class path
      javaProject.setRawClasspath(null, null);

      // step 3: create the entries list
      List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();

      // step 3.1: add the vm path
      IVMInstall vmInstall = JdkCreator.getIVMInstall(project.getProjectDescription().getJRE());
      IPath path = JavaRuntime.newJREContainerPath(vmInstall);
      IClasspathEntry classpathEntry = JavaCore.newContainerEntry(path);
      entries.add(classpathEntry);

      // step 3.2: add the binary paths
      for (IProjectContentEntry projectContent : project.getProjectDescription().getContent()) {

        // TODO!!
        IPath sourceRoot = null;
        if (!((FileBasedContent) projectContent).getSourceRootPaths().isEmpty()) {
          sourceRoot = ((FileBasedContent) projectContent).getSourceRootPaths().toArray(new VariablePath[0])[0]
              .getResolvedPath();
        }

        // add binary paths
        for (VariablePath iClasspathEntry : ((FileBasedContent) projectContent).getBinaryRootPaths()) {
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

  public static boolean hasAssociatedJavaProject(IBundleMakerProject bundleMakerProject) {
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

  public static IJavaProject getAssociatedJavaProject(IBundleMakerProject bundleMakerProject) {

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

  public static IProject getAssociatedJavaProjectAsProject(IBundleMakerProject bundleMakerProject) {

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

  public static IJavaProject newAssociatedJavaProject(IBundleMakerProject bundleMakerProject) throws CoreException {
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

}
