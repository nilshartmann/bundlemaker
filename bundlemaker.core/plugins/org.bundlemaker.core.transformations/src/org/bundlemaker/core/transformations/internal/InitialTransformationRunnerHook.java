/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.internal;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.hook.IBundleMakerProjectHook;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.transformations.script.runner.TransformationScriptRunner;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class InitialTransformationRunnerHook implements IBundleMakerProjectHook {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.hook.IBundleMakerProjectHook#modularizedSystemCreated(org.bundlemaker.core.modules.
   * IModularizedSystem)
   */
  @Override
  public void modularizedSystemCreated(IProgressMonitor progressMonitor, IModularizedSystem modularizedSystem)
      throws Exception {

    IProject eclipseProject = modularizedSystem.getBundleMakerProject().getProject();

    if (!BundleMakerCore.isJavaProject(eclipseProject)) {
      // nothing to do
      return;
    }

    // Read stored names of transformation scripts from the Project's preferences
    ProjectScope projectScope = new ProjectScope(eclipseProject);

    String initialTransformationScriptPrefs = Platform.getPreferencesService().getString(Activator.PLUGIN_ID,
        "initialTransformationScripts", null, new IScopeContext[] { projectScope });

    if (initialTransformationScriptPrefs == null) {
      // no initial transformation scripts
      return;
    }

    // Create Java project
    IJavaProject javaProject = JavaCore.create(eclipseProject);

    // Run each script
    String[] initialTransformationScriptNames = initialTransformationScriptPrefs.split(",");

    for (String scriptName : initialTransformationScriptNames) {
      IType type = javaProject.findType(scriptName);

      TransformationScriptRunner runner = new TransformationScriptRunner(modularizedSystem, type);

      runner.runScript(progressMonitor);
    }

  }
}
