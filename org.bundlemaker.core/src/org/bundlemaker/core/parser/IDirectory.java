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
package org.bundlemaker.core.parser;

import java.util.List;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDirectory {

  /**
   * <p>
   * Returns the {@link IFileBasedContent} that defines this directory entry.
   * </p>
   * 
   * @return
   */
  public IFileBasedContent getFileBasedContent();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public IPath getDirectoryName();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IDirectoryFragment> getBinaryDirectoryFragments();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getBinaryContentCount();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean hasSourceContent();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public List<IDirectoryFragment> getSourceDirectoryFragments();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getSourceContentCount();
}
