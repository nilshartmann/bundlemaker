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

public class MemoryUtils {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static String getMemoryUsage() {
    long totalMem = Runtime.getRuntime().totalMemory();
    long freeMem = Runtime.getRuntime().freeMemory();
    return "Memory used: " + (totalMem - freeMem) / (1024 * 1024) + " MB (total: " + totalMem / (1024 * 1024) + " MB )";
  }
}
