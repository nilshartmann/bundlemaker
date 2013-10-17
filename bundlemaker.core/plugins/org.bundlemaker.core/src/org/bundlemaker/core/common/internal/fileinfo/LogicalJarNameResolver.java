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
package org.bundlemaker.core.common.internal.fileinfo;

import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogicalJarNameResolver {

  /**
   * <p>
   * </p>
   * 
   * @param manifest
   * @return
   */
  public static String extractNameFromImplementationTitle(Manifest manifest) {

    // get the 'Implementation-Title' attribute
    String result = manifest.getMainAttributes().getValue("Implementation-Title");

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
  public static String extractNameFromMainClassAttribute(Manifest manifest) {

    // get the main class attribute
    String result = manifest.getMainAttributes().getValue("Main-Class");

    if (result != null && result.indexOf(".") != -1) {

      result = result.substring(0, result.lastIndexOf("."));

      return returnIfValid(result);
    }

    return null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param jarFile
   * @return
   */
  public final static String extractNameFromRootDirectory(JarFile jarFile) {

    // get all entries
    Enumeration<JarEntry> enumeration = jarFile.entries();

    String[] commonPath = null;

    while (enumeration.hasMoreElements()) {
      JarEntry jarEntry = (JarEntry) enumeration.nextElement();

      if (!jarEntry.getName().endsWith(".class")) {
        continue;
      }

      int index = jarEntry.getName().lastIndexOf('/');
      String directory = index != -1 ? jarEntry.getName().substring(0, index) : jarEntry.getName();

      //
      if (!"META-INF".equals(directory)) {

        if (commonPath == null) {

          commonPath = directory.split("/");

        } else {

          String[] directoryPath = directory.split("/");

          boolean stillOnCommonPath = true;

          for (int i = 0; i < commonPath.length; i++) {

            if (i < directoryPath.length && stillOnCommonPath) {
              if (!directoryPath[i].equals(commonPath[i])) {
                commonPath[i] = null;
                stillOnCommonPath = false;
              }
            } else {
              commonPath[i] = null;
            }

          }
        }
      }
    }

    //
    if (commonPath != null) {

      StringBuilder stringBuilder = new StringBuilder();

      int count = 1;

      for (int i = 0; i < commonPath.length; i++) {
        if (commonPath[i] != null) {
          stringBuilder.append(commonPath[i]);
          if (i + 1 < commonPath.length && commonPath[i + 1] != null) {
            stringBuilder.append(".");
            count++;
          }
        }
      }

      // return the result
      String result = stringBuilder.toString();
      result = result.trim();
      if (!result.isEmpty() && count >= 2) {
        return result;
      }
    }

    return null;
  }

  /**
   * <p>
   * Extracts the name from the input string. This methods first searches for a possible version string in the input
   * name. If a version is found, the substring from 0 till the beginning of the version string will be returned.
   * </p>
   * 
   * @param inputStr
   * @return
   */
  public static String extractName(String inputStr) {

    // version pattern
    String patternStr = "(\\d+\\.){1,3}\\w*";

    // Compile and use regular expression
    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(inputStr);

    //
    String result = inputStr.toString();

    //
    if (matcher.find()) {
      String group = matcher.group();
      if (group.length() > 1) {
        result = result.substring(0, result.indexOf(group));
      }
    }

    //
    if (result.endsWith(".jar") || result.endsWith(".zip")) {
      result = result.substring(0, result.length() - 4);
    }

    if (result.endsWith("-") || result.endsWith("_")) {
      result = result.substring(0, result.length() - 1);
    }

    //
    return returnIfValid(result);
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
    if (isValidSymbolicName(string)) {
      return string;
    }

    //
    return null;
  }

  /**
   * @param string
   * @return
   */
  private static boolean isValidSymbolicName(String string) {

    // version pattern
    String patternStr = "([\\w\\-])*(\\.([\\w\\-])*)*";

    // Compile and use regular expression
    Pattern pattern = Pattern.compile(patternStr);
    Matcher matcher = pattern.matcher(string);
    return matcher.matches();
  }
}
