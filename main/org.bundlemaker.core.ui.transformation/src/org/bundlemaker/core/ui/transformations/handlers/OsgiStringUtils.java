/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformations.handlers;

import java.util.Dictionary;

import org.osgi.framework.Bundle;

/**
 * @see http://git.eclipse.org/c/gemini.blueprint/org.eclipse.gemini.blueprint.git/tree/core/src/main/java/org/eclipse/
 *      gemini/blueprint/util/OsgiStringUtils.java
 */
public class OsgiStringUtils {
  private static final String NULL_STRING = "null";

  /**
   * Returns the bundle name and symbolic name - useful when logging bundle info.
   * 
   * @param bundle
   *          OSGi bundle (can be null)
   * @return the bundle name and symbolic name
   */
  public static String nullSafeNameAndSymName(Bundle bundle) {
    if (bundle == null)
      return NULL_STRING;

    Dictionary<String, String> dict = bundle.getHeaders();

    if (dict == null)
      return NULL_STRING;

    StringBuilder buf = new StringBuilder();
    String name = dict.get(org.osgi.framework.Constants.BUNDLE_NAME);
    if (name == null)
      buf.append(NULL_STRING);
    else
      buf.append(name);
    buf.append(" (");
    String sname = dict.get(org.osgi.framework.Constants.BUNDLE_SYMBOLICNAME);

    if (sname == null)
      buf.append(NULL_STRING);
    else
      buf.append(sname);

    buf.append(")");

    return buf.toString();
  }
}
