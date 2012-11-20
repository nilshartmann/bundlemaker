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
package org.bundlemaker.core.util.jarinfo;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarInfo {

  /** - */
  private String  _name;

  /** - */
  private String  _version;

  /** - */
  private boolean _isSource = false;

  /**
   * <p>
   * Creates a new instance of type {@link JarInfo}.
   * </p>
   * 
   * @param name
   * @param version
   */
  public JarInfo(String name, String version, boolean isSource) {
    Assert.isNotNull(name);
    Assert.isNotNull(version);

    _name = name;
    _version = version;
    _isSource = isSource;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getName() {
    return _name;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "JarInfo [name=" + _name + ", version=" + _version + "]";
  }
}
