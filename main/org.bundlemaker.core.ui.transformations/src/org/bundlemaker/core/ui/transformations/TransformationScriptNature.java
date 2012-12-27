/*******************************************************************************
 * Copyright (c) 2012 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations;

import org.bundlemaker.core.transformations.support.TransformationScriptSupport;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.ui.PreferenceConstants;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TransformationScriptNature implements IProjectNature {

  /** - */
  private static final String NATURE_ID = "org.bundlemaker.core.ui.transformations.transformationScriptNature";

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @param monitor
   * @throws CoreException
   */
  public static void addTransformationScriptNature(IProject project, IProgressMonitor monitor) throws CoreException {

    //
    if (monitor != null && monitor.isCanceled()) {
      throw new OperationCanceledException();
    }

    if (!TransformationScriptNature.hasNature(project)) {
      IProjectDescription description = project.getDescription();
      String[] prevNatures = description.getNatureIds();
      String[] newNatures = new String[prevNatures.length + 1];
      System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
      newNatures[prevNatures.length] = NATURE_ID;
      description.setNatureIds(newNatures);
      project.setDescription(description, monitor);
    } else {
      if (monitor != null) {
        monitor.worked(1);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @return
   */
  public static boolean hasNature(IProject project) {
    try {
      if (!project.hasNature(NATURE_ID)) {
        return false;
      }
    } catch (CoreException ex) {
      return false;
    }
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param project
   * @param monitor
   * @throws CoreException
   */
  public static void removeTransformationScriptNature(IProject project, IProgressMonitor monitor) throws CoreException {

    //
    if (monitor != null && monitor.isCanceled()) {
      throw new OperationCanceledException();
    }

    //
    if (hasNature(project)) {
      IProjectDescription description = project.getDescription();
      String[] prevNatures = description.getNatureIds();
      String[] newNatures = new String[prevNatures.length - 1];
      int k = 0;
      head: for (int i = 0; i < prevNatures.length; i++) {
        if (prevNatures[i].equals(NATURE_ID)) {
          continue head;
        }
        newNatures[k++] = prevNatures[i];
      }
      description.setNatureIds(newNatures);
      project.setDescription(description, monitor);
    } else {
      if (monitor != null) {
        monitor.worked(1);
      }
    }
  }

  /** the associated bundle maker project */
  private IProject _project;

  /**
   * {@inheritDoc}
   */
  @Override
  public IProject getProject() {
    return _project;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setProject(IProject value) {
    _project = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void configure() throws CoreException {
    TransformationScriptSupport.enableTransformationScriptSupport(_project, PreferenceConstants.getDefaultJRELibrary());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deconfigure() throws CoreException {

  }
}
