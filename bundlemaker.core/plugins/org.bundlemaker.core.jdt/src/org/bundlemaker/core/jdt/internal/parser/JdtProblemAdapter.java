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
package org.bundlemaker.core.jdt.internal.parser;

import org.bundlemaker.core.parser.IProblem;
import org.bundlemaker.core.project.IProjectContentResource;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtProblemAdapter implements IProblem {

  /** - */
  private org.eclipse.jdt.core.compiler.IProblem _compilerProblem;

  /** - */
  private IProjectContentResource                           _resourceKey;

  /**
   * <p>
   * Creates a new instance of type {@link JdtProblemAdapter}.
   * </p>
   * 
   * @param resourceKey
   * @param compilerProblem
   */
  public JdtProblemAdapter(IProjectContentResource resourceKey, org.eclipse.jdt.core.compiler.IProblem compilerProblem) {

    // assert
    Assert.isNotNull(resourceKey);
    Assert.isNotNull(compilerProblem);

    // the resource key
    _resourceKey = resourceKey;

    // set the comiler problem
    _compilerProblem = compilerProblem;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IProjectContentResource getResource() {
    return _resourceKey;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isError() {
    return _compilerProblem.isError();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSourceLineNumber() {
    return _compilerProblem.getSourceLineNumber();
  }

  /**
   * {@inheritDoc}
   */
  public int getSourceEnd() {
    return _compilerProblem.getSourceEnd();
  }

  /**
   * {@inheritDoc}
   */
  public int getSourceStart() {
    return _compilerProblem.getSourceStart();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMessage() {
    return _compilerProblem.getMessage();
  }

  @Override
  public String toString() {
    return _compilerProblem.toString();
  }
}
