/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.IProblem;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.ui.artifact.CommonNavigatorUtils;
import org.bundlemaker.core.ui.artifact.configuration.IArtifactModelConfigurationProvider;
import org.bundlemaker.core.ui.event.Events;
import org.bundlemaker.core.ui.internal.Activator;
import org.bundlemaker.core.ui.internal.BundleMakerUiUtils;
import org.bundlemaker.core.ui.preferences.BundleMakerPreferences;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.progress.IProgressService;

/**
 * Parses and opens a specified BundleMaker Project and executes all required pre- and post-actions for the UI (like
 * fireing events, switching perspective etc)
 * 
 * <p>
 * After opening the project the appropriate events are fired, the perspective will be switched etc.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerProjectOpener {

  public static void openProject(final IBundleMakerProject bundleMakerProject) {

    if (bundleMakerProject == null) {
      return;
    }

    // ask user if the perspective should be opened
    if (!BundleMakerPerspectiveHelper
        .openBundleMakerPerspectiveIfWanted(BundleMakerPreferences.PREF_SWITCH_TO_PERSPECTIVE_ON_PROJECT_OPEN)) {
      // BundleMaker perspective not open; make sure that at least the Project Explorer is visible
      CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    }

    // TODO: As an exception?
    Activator.getDefault().initFilters();

    boolean success = ParseBundleMakerProjectRunnable.parseProject(bundleMakerProject);

    List<IProblem> problems = bundleMakerProject.getProblems();
    for (IProblem iProblem : problems) {
      System.out.println("Problem: " + iProblem);
    }

    if (!success) {
      return;
    }

    // Select default modularized system in common navigator
    try {

      IWorkbench wb = PlatformUI.getWorkbench();
      IProgressService ps = wb.getProgressService();
      ps.run(false, false, new IRunnableWithProgress() {

        @Override
        public void run(IProgressMonitor pm) {

          try {
            selectDefaultModularizedSystemArtifact(bundleMakerProject, pm);

          } catch (CoreException ex) {
            BundleMakerUiUtils.logError("Error while creating BundleMaker model:" + ex, ex);
          }
        }
      });

    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Events.instance().fireProjectOpened(bundleMakerProject);

    // Re-activate common navigator make selections via context menu work
    CommonNavigatorUtils.activateCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
  }

  private static IBundleMakerArtifact selectDefaultModularizedSystemArtifact(IBundleMakerProject bundleMakerProject,
      IProgressMonitor monitor)
      throws CoreException {

    IProject eclipseProject = bundleMakerProject.getProject();

    // get the common navigator
    CommonNavigator commonNavigator = CommonNavigatorUtils
        .findCommonNavigator(CommonNavigatorUtils.PROJECT_EXPLORER_VIEW_ID);
    if (commonNavigator == null) {
      return null;
    }

    // get "root" BundleMakerArtifact
    IBundleMakerArtifact defaultModularizedSystemArtifact = getDefaultModularizedSystemArtifact(bundleMakerProject,
        monitor);

    // Expand Eclipse Project project in tree (i.e. make Artifacts node visible)
    commonNavigator.getCommonViewer().expandToLevel(eclipseProject, 1);

    // Expand Tree to BundleMaker artifact (no idea why two steps are neccessary)
    commonNavigator.getCommonViewer().expandToLevel(defaultModularizedSystemArtifact, 1);

    // Select root artifact in tree
    StructuredSelection newSelection = new StructuredSelection(defaultModularizedSystemArtifact);
    commonNavigator.selectReveal(newSelection);

    return defaultModularizedSystemArtifact;
  }

  /**
   * <p>
   * </p>
   * 
   * @param bundleMakerProject
   * @param monitor
   * @return
   * @throws CoreException
   */
  protected static IBundleMakerArtifact getDefaultModularizedSystemArtifact(IBundleMakerProject bundleMakerProject,
      IProgressMonitor monitor)
      throws CoreException {

    IArtifactModelConfigurationProvider artifactModelConfigurationProvider = Activator.getDefault()
        .getArtifactModelConfigurationProvider();

    IModularizedSystem modularizedSystem = bundleMakerProject.getModularizedSystemWorkingCopy();

    //
    return modularizedSystem.getAnalysisModel(artifactModelConfigurationProvider
        .getArtifactModelConfiguration(), monitor);
  }
}
