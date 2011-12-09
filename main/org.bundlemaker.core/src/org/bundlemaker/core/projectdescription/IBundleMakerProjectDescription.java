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
public interface IBundleMakerProjectDescription {

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
   * Returns a list with all the defined {@link IFileBasedContent}.
   * </p>
   * 
   * @return a list with all the defined {@link IFileBasedContent}.
   */
  List<? extends IFileBasedContent> getFileBasedContent();

  /**
   * <p>
   * Returns the {@link IFileBasedContent} with the specified identifier.
   * </p>
   * 
   * @param id
   *          the identifier
   * @return the {@link IFileBasedContent} with the specified identifier.
   */
  // TODO: REMOVE
  IFileBasedContent getFileBasedContent(String id);

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<? extends IFileBasedContentProvider> getFileBasedContentProviders();

  /**
   * <p>
   * Returns the name of the associated JRE.
   * </p>
   * 
   * @return the name of the associated JRE.
   */
  String getJRE();
}
