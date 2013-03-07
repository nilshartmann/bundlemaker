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
package com.example;

import java.util.Comparator;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class Class2 implements Interface1 {

  @Override
  public Class1 getClass1() {
    return null;
  }
  
  public Comparator<?> createComparator() {
    return new Comparator<Class2>() {

      @Override
      public int compare(Class2 arg0, Class2 arg1) {
        return 0;
      }
      
    }; 
  }
  
  public static class StaticInnerClass2 {
    public void sayHi() {
      System.out.println("Hi!");
    }
  }
  
  
  
  class InnerClass2 {
    public void sayWhoo() {
      System.out.println("whoo");
    }
  }
  
  
  
  

}
