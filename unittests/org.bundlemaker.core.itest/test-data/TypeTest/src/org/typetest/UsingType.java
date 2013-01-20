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
public class UsingType {
  
  private TypeClass typeClass;
  private TypeEnum typeEnum;
  private TypeInterface typeInterface;
  private TypeInterfaceImpl typeInterfaceImpl;
  /**
   * @return the typeClass
   */
  public TypeClass getTypeClass() {
    return typeClass;
  }
  /**
   * @param typeClass the typeClass to set
   */
  public void setTypeClass(TypeClass typeClass) {
    this.typeClass = typeClass;
  }
  /**
   * @return the typeEnum
   */
  public TypeEnum getTypeEnum() {
    return typeEnum;
  }
  /**
   * @param typeEnum the typeEnum to set
   */
  public void setTypeEnum(TypeEnum typeEnum) {
    this.typeEnum = typeEnum;
  }
  /**
   * @return the typeInterface
   */
  public TypeInterface getTypeInterface() {
    return typeInterface;
  }
  /**
   * @param typeInterface the typeInterface to set
   */
  public void setTypeInterface(TypeInterface typeInterface) {
    this.typeInterface = typeInterface;
  }
  /**
   * @return the typeInterfaceImpl
   */
  public TypeInterfaceImpl getTypeInterfaceImpl() {
    return typeInterfaceImpl;
  }
  /**
   * @param typeInterfaceImpl the typeInterfaceImpl to set
   */
  public void setTypeInterfaceImpl(TypeInterfaceImpl typeInterfaceImpl) {
    this.typeInterfaceImpl = typeInterfaceImpl;
  }

}
