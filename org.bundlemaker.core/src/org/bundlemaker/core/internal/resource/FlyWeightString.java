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
package org.bundlemaker.core.internal.resource;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FlyWeightString {

  /** - */
  private String _content;

  /**
   * <p>
   * Creates a new instance of type {@link FlyWeightString}.
   * </p>
   * 
   * @param content
   */
  public FlyWeightString(String content) {
    Assert.isNotNull(content);

    _content = content;
  }

  @Override
  public String toString() {
    return _content;
  }

  @Override
  public int hashCode() {
    return _content.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj.getClass().equals(FlyWeightString.class) || obj.getClass().equals(String.class)))
      return false;
    return _content.equals(obj.toString());
  }
}
