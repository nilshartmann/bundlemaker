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
package org.bundlemaker.core.util;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JavaTypeUtils {

  /**
   * <p>
   * </p>
   * 
   * @param fullQualifiedName
   * @return
   */
  public static boolean isLocalOrAnonymousTypeName(String fullQualifiedName) {
    Assert.isNotNull(fullQualifiedName);

    return fullQualifiedName.matches(".*\\$\\d.*");
  }

  /**
   * <p>
   * </p>
   * 
   * @param fullQualifiedName
   * @return
   */
  public static String getEnclosingNonLocalAndNonAnonymousTypeName(String fullQualifiedName) {
    Assert.isNotNull(fullQualifiedName);

    // local or anonymous?
    if (isLocalOrAnonymousTypeName(fullQualifiedName)) {

      String[] parts = fullQualifiedName.split("\\$\\d");
      return parts[0];

    } else {
      return fullQualifiedName;
    }
  }

  public static String convertToFullyQualifiedName(String classFilePath) {

    String fullyQualifiedName = classFilePath.substring(0, classFilePath.length() - ".class".length());

    if (fullyQualifiedName.startsWith("/")) {
      fullyQualifiedName = fullyQualifiedName.substring(1);
    }

    fullyQualifiedName = fullyQualifiedName.replace('/', '.');

    return fullyQualifiedName;
  }

  public static String convertFromFullyQualifiedName(String fullyQualifiedName) {

    String result = fullyQualifiedName.replace('.', '/');

    return result + ".class";
  }
}
