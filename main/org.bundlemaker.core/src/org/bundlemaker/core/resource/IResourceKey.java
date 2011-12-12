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
package org.bundlemaker.core.resource;

import org.bundlemaker.core.projectdescription.IBundleMakerProjectContent;

/**
 * <p>
 * An {@link IResourceKey} implements an identifier for a resource. It contains a <code>contentId</code>, a
 * <code>root</code> directory or archive file, and a <code>path</code>.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IResourceKey extends IContentProvider {

  /**
   * <p>
   * Returns the identifier of the {@link IBundleMakerProjectContent} that defines the resource.
   * </p>
   * 
   * @return the identifier of the {@link IBundleMakerProjectContent} that defines the resource.
   */
  String getContentId();

  /**
   * <p>
   * Returns the root directory or archive file that contains the resource (e.g. 'c:/dev/classes.zip' or
   * 'c:/dev/source'). Note that resource paths are always slash-delimited ('/').
   * </p>
   * 
   * @return the root directory or archive file that contains the resource.
   */
  String getRoot();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  long getTimestamp();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  byte[] getHashvalue();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasHashvalue();
}
