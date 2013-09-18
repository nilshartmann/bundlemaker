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
package org.bundlemaker.core.jtype.internal;

import org.bundlemaker.core.jtype.ReferenceType;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class ReferenceKey {

  /** - */
  private String        fullyQualifiedName;

  /** - */
  private ReferenceType referenceType;

  /**
   * <p>
   * Creates a new instance of type {@link ReferenceKey}.
   * </p>
   * 
   * @param fullyQualifiedName
   * @param referenceType
   */
  public ReferenceKey(String fullyQualifiedName, ReferenceType referenceType) {
    this.fullyQualifiedName = fullyQualifiedName;
    this.referenceType = referenceType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fullyQualifiedName == null) ? 0 : fullyQualifiedName.hashCode());
    result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ReferenceKey other = (ReferenceKey) obj;
    if (fullyQualifiedName == null) {
      if (other.fullyQualifiedName != null)
        return false;
    } else if (!fullyQualifiedName.equals(other.fullyQualifiedName))
      return false;
    if (referenceType != other.referenceType)
      return false;
    return true;
  }

}
