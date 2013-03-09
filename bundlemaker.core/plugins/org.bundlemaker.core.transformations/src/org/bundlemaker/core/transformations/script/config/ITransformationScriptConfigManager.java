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
package org.bundlemaker.core.transformations.script.config;

import org.bundlemaker.core.transformations.internal.Activator;

/**
 * <b>This API is not fix yet. Do not use it.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public interface ITransformationScriptConfigManager {

  public final static String PREF_QUALIFIER = Activator.PLUGIN_ID;

  public final static String PREF_KEY       = "initialTransformationScripts";

}
