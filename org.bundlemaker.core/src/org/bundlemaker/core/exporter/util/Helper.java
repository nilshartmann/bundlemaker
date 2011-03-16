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
package org.bundlemaker.core.exporter.util;

import java.io.ByteArrayInputStream;

import org.bundlemaker.core.util.EclipseProjectUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class Helper {

  /**
   * <p>
   * </p>
   * 
   * @param projectName
   * @return
   */
  public static String getUniqueProjectName(String projectName) {
    //
    String newProjectName = projectName;

    // if the project name exists, add a post-fix
    if (doesNonGeneratedProjectExist(newProjectName)) {

      //
      int postfix = 0;

      //
      while (doesNonGeneratedProjectExist(newProjectName)) {

        //
        postfix++;

        //
        newProjectName = String.format("%s (%s)", projectName, postfix);
      }

      //
      projectName = newProjectName;
    }

    return projectName;
  }

  /**
   * <p>
   * </p>
   * 
   * @param projectName
   * @return
   * @throws CoreException
   */
  public static IProject deleteAndCreateProject(String projectName, IPath location) throws CoreException {

    // create project
    IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

    // delete the project if exists
    if (project.exists()) {
      project.delete(true, true, null);
    }

    // create the project if not exists
    if (!project.exists()) {

      // create the description
      IProjectDescription desc = project.getWorkspace().newProjectDescription(project.getName());

      //
      if (location != null) {
        desc.setLocation(location);
      }

      //
      project.create(desc, null);

      //
      if (!project.isOpen()) {
        project.open(null);
      }

      // add the 'bundlemakergenerated' flag
      IFile file = project.getFile(".bundlemakergenerated");
      if (!file.exists()) {
        file.create(new ByteArrayInputStream(new byte[0]), true, null);
      } else {
        file.setContents(new ByteArrayInputStream(new byte[0]), IFile.FORCE, null);
      }
    }

    // return the result
    return project;
  }

  /**
   * <p>
   * </p>
   * 
   * @param projectName
   * @return
   */
  private static boolean doesNonGeneratedProjectExist(String projectName) {

    try {

      boolean result = EclipseProjectUtils.exists(projectName)
          && !EclipseProjectUtils.exists(new Path(projectName + "/.bundlemakergenerated"));

      //
      EclipseProjectUtils.getOrCreateSimpleProject(projectName);

      //
      return result;

    } catch (CoreException e) {

      //
      return true;
    }
  }
}
