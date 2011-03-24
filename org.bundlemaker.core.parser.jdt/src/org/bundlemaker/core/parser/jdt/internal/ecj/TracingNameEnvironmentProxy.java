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
package org.bundlemaker.core.parser.jdt.internal.ecj;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
@SuppressWarnings("restriction")
public class TracingNameEnvironmentProxy implements INameEnvironment {

  /** the name environment */
  private INameEnvironment _nameEnvironment;

  /** the set of requested types */
  private Set<String>      _requestedTypes;

  /**
   * <p>
   * </p>
   * 
   * @param nameEnvironment
   */
  public TracingNameEnvironmentProxy(INameEnvironment nameEnvironment) {
    Assert.isNotNull(nameEnvironment);

    // set the name environment
    this._nameEnvironment = nameEnvironment;

    // created the requested types map
    _requestedTypes = new HashSet<String>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<String> getRequestedTypes() {
    return _requestedTypes;
  }

  /**
   * <p>
   * </p>
   */
  public void resetRequestedTypes() {
    _requestedTypes.clear();
  }

  /**
   * {@inheritDoc}
   */
  public void cleanup() {
    _nameEnvironment.cleanup();
  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[] typeName, char[][] packageName) {

    //
    NameEnvironmentAnswer answer = _nameEnvironment.findType(typeName, packageName);

    //
    if (answer != null) {
      _requestedTypes.add(NameEnvironmentUtils.getAsString(typeName, packageName, '.'));
    }

    //
    return answer;
  }

  /**
   * {@inheritDoc}
   */
  public NameEnvironmentAnswer findType(char[][] compoundTypeName) {

    NameEnvironmentAnswer answer = _nameEnvironment.findType(compoundTypeName);

    if (answer != null) {
      _requestedTypes.add(NameEnvironmentUtils.getAsString(compoundTypeName, '.'));
    }

    return answer;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isPackage(char[][] parentPackageName, char[] packageName) {
    return _nameEnvironment.isPackage(parentPackageName, packageName);
  }
}
