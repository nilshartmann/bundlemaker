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
package org.bundlemaker.core.projectdescription;

import java.util.List;

import org.bundlemaker.core.IBundleMakerProject;

/**
 * <p>
 * Defines the interface of a bundle maker project description.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IProjectDescription {

  /**
   * <p>
   * Returns the containing bundle maker project.
   * </p>
   * 
   * @return the containing bundle maker project.
   */
  IBundleMakerProject getBundleMakerProject();

  /**
   * <p>
   * Returns an <b>unmodifiable</b> list.
   * </p>
   * 
   * @return
   */
  List<? extends IProjectContentProvider> getContentProviders();

  /**
   * <p>
   * Returns a (read-only) list with all the defined {@link IProjectContentEntry}.
   * </p>
   * 
   * @return a (read-only) list with all the defined {@link IProjectContentEntry}.
   */
  List<? extends IProjectContentEntry> getContent();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  IProjectContentEntry getProjectContentEntry(String identifier);

  /**
   * <p>
   * Returns the name of the associated JRE.
   * </p>
   * 
   * @return the name of the associated JRE.
   */
  String getJRE();
}
