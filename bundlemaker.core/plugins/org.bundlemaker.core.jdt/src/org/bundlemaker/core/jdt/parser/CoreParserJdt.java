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
package org.bundlemaker.core.jdt.parser;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;

/**
 */
public class CoreParserJdt {
  
  /** the bundle maker JDT project postfix */
  public static final String BUNDLEMAKER_JDT_PROJECT_POSTFIX = "$bundlemakerJdt";

  /** - */
  public static final String BUNDLE_ID          = "org.bundlemaker.core.jdt";

  /** the EXTENSION_POINT_ID */
  public static final String EXTENSION_POINT_ID = "org.bundlemaker.core.jdt.jdtsourceparserhook";

  /**
   * Returns the default compiler options. The returned map contains at least
   * the source- and binary compliance level for the compiler set to java1.6
   *  
   * @param compilerOptions The available compiler options or null
   * @return
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static Map getCompilerOptionsWithComplianceLevel(Map compilerOptions) {
    Map result = new Hashtable();
    
    // Add passed options if available
    if (compilerOptions != null) {
      result.putAll(compilerOptions);
    }
    
      result.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_7);
      result.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_7);
      result.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_7);
    
    return result;
  }
}
