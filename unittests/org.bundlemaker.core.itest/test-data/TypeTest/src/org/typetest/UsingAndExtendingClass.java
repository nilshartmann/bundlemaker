/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.typetest;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class UsingAndExtendingClass extends TypeClass {

  private TypeClass usage;
  
  /**
   * @param usage the usage to set
   */
  public void setUsage(TypeClass usage) {
    this.usage = usage;
  }
  /**
   * @return the usage
   */
  public TypeClass getUsage() {
    return usage;
  }
  
}
