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
package org.bundlemaker.core.internal.projectdescription.file;

import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogicalJarVersionResolver {

  /** - */
  private static final String VERSION_PATTERN_STRING = "(\\d+\\.){1,3}\\w*";

  /** - */
  private static Pattern      VERSION_PATTERN        = Pattern.compile(VERSION_PATTERN_STRING);

  /**
   * <p>
   * Returns the version from the Bundle-Version manifest attribute if (correctly) set
   * </p>
   * 
   * @param manifest
   * @return
   */
  public static String extractNameFromBundleVersion(Manifest manifest) {
    // get the 'Implementation-Title' attribute
    String result = manifest.getMainAttributes().getValue("Bundle-Version");

    //
    return returnIfValid(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifest
   * @return
   */
  public static String extractNameFromImplementationVersion(Manifest manifest) {

    // get the 'Implementation-Title' attribute
    String result = manifest.getMainAttributes().getValue("Implementation-Version");

    if (result != null && result.startsWith("\"") && result.endsWith("\"")) {
      result = result.substring(1, result.length() - 1);
    }

    //
    return returnIfValid(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifest
   * @return
   */
  public static String extractNameFromSpecificationVersion(Manifest manifest) {

    // get the 'Implementation-Title' attribute
    String result = manifest.getMainAttributes().getValue("Specification-Version");

    //
    return returnIfValid(result);
  }

  /**
   * <p>
   * </p>
   * 
   * @param inputStr
   * @return
   */
  public static String extractVersionFromName(CharSequence inputStr) {

    Matcher matcher = VERSION_PATTERN.matcher(inputStr);

    if (matcher.find()) {

      String result = matcher.group();
      if (result.endsWith(".jar") || result.endsWith(".zip")) {
        result = result.substring(0, result.length() - 4);
      }

      if (result.length() > 1) {
        return result;
      }

      return null;
    }

    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  private static String returnIfValid(String string) {

    //
    if (string == null) {
      return null;
    }

    //
    if (isValidVersion(string)) {
      return string;
    }

    //
    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param string
   * @return
   */
  private static boolean isValidVersion(String string) {

    // Compile and use regular expression
    Matcher matcher = VERSION_PATTERN.matcher(string);
    return matcher.matches();
  }
}
