/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor;

import org.eclipse.core.runtime.IPath;

public class BundleMakerPath {
  private final IPath   _path;

  private final boolean _binary;

  private final boolean _folder;

  private boolean       _analyzeSources;

  public BundleMakerPath(IPath path, boolean binary, boolean folder) {
    super();
    _path = path;
    _binary = binary;
    _folder = folder;
    _analyzeSources = (binary == false);
  }

  public boolean isFolder() {
    return _folder;
  }

  public String getLabel() {
    return _path.toString();
  }

  public boolean isBinary() {
    return _binary;
  }

  /**
   * @return the analyzeSources
   */
  public boolean isAnalyzeSources() {
    return _analyzeSources;
  }

  /**
   * @param analyzeSources
   *          the analyzeSources to set
   */
  public void setAnalyzeSources(boolean analyzeSources) {
    _analyzeSources = analyzeSources;
  }

  /**
   * @return the path
   */
  public IPath getPath() {
    return _path;
  }

}