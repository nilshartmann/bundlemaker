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
package org.bundlemaker.core;

import org.bundlemaker.core.resource.IProjectContentResource;

/**
 * <p>
 * Common interface for problems and errors that occur while parsing the content of a {@link IBundleMakerProject}.
 * </p>
 * <p>
 * Clients may implement this interface.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IProblem {

  /**
   * <p>
   * Returns {@link IProjectContentResource} of the associated resource.
   * </p>
   * 
   * @return the {@link IProjectContentResource} of the associated resource.
   */
  IProjectContentResource getResource();

  /**
   * <p>
   * Returns <code>true</code> if this problem is an error, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if this problem is an error, <code>false</code> otherwise.
   */
  boolean isError();

  /**
   * <p>
   * Returns the line number in source where the problem begins or -1 if unknown
   * </p>
   * 
   * @return the line number in source where the problem begins
   */
  int getSourceLineNumber();

  /**
   * <p>
   * Returns the message for this problem.
   * </p>
   * 
   * @return the message for this problem.
   */
  String getMessage();

  /**
   * <p>
   * Returns the start position of the problem (inclusive), or -1 if unknown.
   * </p>
   * 
   * @return the start position of the problem (inclusive), or -1 if unknown.
   */
  int getSourceStart();

  /**
   * <p>
   * Answer the end position of the problem (inclusive), or -1 if unknown.
   * </p>
   * 
   * @return the end position of the problem (inclusive), or -1 if unknown
   */
  int getSourceEnd();

  /**
   * <p>
   * </p>
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   */
  public static class DefaultProblem implements IProblem {

    private final IProjectContentResource _resourceKey;

    private final String                  _message;

    /**
     * @param resourceKey
     * @param message
     */
    public DefaultProblem(IProjectContentResource resourceKey, String message) {
      _resourceKey = resourceKey;
      _message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bundlemaker.core.IProblem#getResource()
     */
    @Override
    public IProjectContentResource getResource() {
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

}
