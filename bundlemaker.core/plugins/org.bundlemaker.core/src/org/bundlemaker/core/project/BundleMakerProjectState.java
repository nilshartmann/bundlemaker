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

/**
 * <p>
 * </p>
 * <ul>
 * <li><b><i>CREATED:</i></b> The project has been created, but no project description has been set up yet.</li>
 * <li><b><i>INITIALIZED:</i></b> The project description has been set up.</li>
 * <li><b><i>READY:</i></b> The project has been parsed. Note that the parsing of a project has to be started manually
 * by the user as it's a long-running operation.</li>
 * <li><b><i>DIRTY:</i></b> The project is dirty (because the project description has changed) and needs to be reparsed.
 * </li>
 * <li><b><i>DISPOSED:</i></b> The project has been disposed.</li>
 * </ul>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noextend This enum is not intended to be extended by clients.
 */
public enum BundleMakerProjectState {

  /** the CREATED state */
  CREATED,

  /** the INITIALIZED state */
  INITIALIZED,

  /** the READY state */
  READY,

  /** the DIRTY state */
  DIRTY,

  /** the DISPOSED state */
  DISPOSED;
}
