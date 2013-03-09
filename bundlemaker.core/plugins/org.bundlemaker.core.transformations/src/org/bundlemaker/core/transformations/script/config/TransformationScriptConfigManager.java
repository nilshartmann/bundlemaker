/*******************************************************************************
 * Copyright (c) 2012 BundleMaker project team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.transformations.script.config;

import org.bundlemaker.core.transformations.internal.Activator;

/**
 * <b>NOTE: This is in EXPERIMENTAL/WORK-AROUND only and will be re-written as soon as I'm implementing a "real" UI for
 * managing initial transformation scripts.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptConfigManager implements ITransformationScriptConfigManager {

  private final static String PREF_QUALIFIER = Activator.PLUGIN_ID;

  private final static String PREF_KEY       = "initialTransformationScripts";

  // /*
  // * (non-Javadoc)
  // *
  // * @see
  // *
  // org.bundlemaker.core.transformations.script.config.ITransformationScriptConfigManager#setInitialTransformationScript
  // * (org.eclipse.core.resources.IProject, org.eclipse.jdt.core.IType)
  // */
  // @Override
  // public void setInitialTransformationScript(IProject eclipseProject, IType scriptType) throws CoreException {
  //
  // if (eclipseProject == null || scriptType == null) {
  // return;
  // }
  //
  // if (!BundleMakerCore.isJavaProject(eclipseProject)) {
  // // nothing to do
  // return;
  // }
  //
  // String typeName = scriptType.getFullyQualifiedName();
  //
  // getPreferences().
  //
  // String initialTransformationScriptPrefs = Platform.getPreferencesService().getString(PREF_QUALIFIER, PREF_KEY,
  // null, getLookupContext(eclipseProject));
  //
  // if (initialTransformationScriptPrefs == null) {
  // // no initial transformation scripts
  // return;
  // }
  //
  // }
  //
  // protected IPreferencesService getPreferences() {
  // return Platform.getPreferencesService();
  // }
  //
  // protected IScopeContext[] getLookupContext(IProject eclipseProject) {
  // ProjectScope projectScope = new ProjectScope(eclipseProject);
  //
  // return new IScopeContext[] { projectScope };
  // }

}
