/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core;

import org.bundlemaker.core.resource.IResourceKey;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class DefaultProblemImpl implements IProblem {
  
  private final IResourceKey _resourceKey;
  private final String _message;
  
  /**
   * @param resourceKey
   * @param message
   */
  public DefaultProblemImpl(IResourceKey resourceKey, String message) {
    _resourceKey = resourceKey;
    _message = message;
  }

  /* (non-Javadoc)
   * @see org.bundlemaker.core.IProblem#getResource()
   */
  @Override
  public IResourceKey getResource() {
    return _resourceKey;
  }

  @Override
  public String getMessage() {
    // source not available
    return this._message;
  }

  @Override
  public boolean isError() {
    return true;
  }

  @Override
  public int getSourceLineNumber() {
    // source not available
    return -1;
  }

  @Override
  public int getSourceStart() {
    // source not available
    return -1;
  }

  @Override
  public int getSourceEnd() {
    // source not available
    return -1;
  }

}
