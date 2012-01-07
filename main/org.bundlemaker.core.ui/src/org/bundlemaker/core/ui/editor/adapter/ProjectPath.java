/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.adapter;

import org.bundlemaker.core.projectdescription.file.VariablePath;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectPath {

  private final VariablePath _path;

  private boolean            _source;

  /**
   * @param path
   * @param source
   */
  public ProjectPath(VariablePath path, boolean source) {
    super();
    _path = path;
    _source = source;
  }

  /**
   * @return the source
   */
  public boolean isSource() {
    return _source;
  }

  public boolean isBinary() {
    return !_source;
  }

  /**
   * @param source
   *          the source to set
   */
  public void setSource(boolean source) {
    _source = source;
  }

  public String getTitle() {
    return _path.getUnresolvedPath().toString();
  }

  /**
   * @return the path
   */
  public VariablePath getPath() {
    return _path;
  }

}
