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
package org.bundlemaker.core.util.jarinfo;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarInfoServiceImpl implements JarInfoService {

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public JarInfo extractJarInfo(File file) {
    try {
      if (file.isFile()) {
        return extractInfoFromFile(file);
      }

      return extractInfoFromPath(file);

      //
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private static JarInfo extractInfoFromPath(File path) {
    String dirName = path.getName();
    int x = dirName.lastIndexOf('_');
    String version = "0.0.0";
    String name = dirName;

    if (x > 0) {
      name = dirName.substring(0, x);
      version = dirName.substring(x + 1);
    }

    return new JarInfo(name, version, false);
  }

  private static JarInfo extractInfoFromFile(File file) throws IOException {
    String name = null;
    String version = null;

    // TRY TO RESOLVE THE NAME
    name = extractName(file);

    // TRY TO RESOLVE THE VERSION
    version = extractVersion(file);

    // return the result
    return new JarInfo(name, version, false);
  }

  private static String extractName(File file) throws IOException {

    //
    String result = null;

    // try to extract the name from the root directory
    JarFile jarFile = new JarFile(file);

    // Try to analyze the jar file
    Manifest manifest = jarFile.getManifest();

    // step 1: check if a symbolic name exists
    if (manifest != null) {

      result = manifest.getMainAttributes().getValue(Constants.BUNDLE_SYMBOLICNAME);

      if (result != null) {
        int end = result.indexOf(';');
        if (end > 0) {
          result = result.substring(0, end);
        }
        return result;
      }
    }

    // try to read maven properties
    Enumeration<JarEntry> entries = jarFile.entries();
    while (entries.hasMoreElements()) {
      JarEntry jarEntry = (JarEntry) entries.nextElement();
      if (jarEntry.getName().endsWith("pom.properties")) {
        Properties properties = new Properties();
        properties.load(jarFile.getInputStream(jarEntry));
        // version=0.9.26
        // groupId=ch.qos.logback
        // artifactId=logback-core
        return properties.getProperty("groupId") + "." + properties.getProperty("artifactId");
      }
    }

    // step 2:
    result = LogicalJarNameResolver.extractName(file.getName());
    if (result != null) {
      return result;
    }

    // step 3:
    result = LogicalJarNameResolver.extractNameFromRootDirectory(jarFile);
    if (result != null) {
      return result;
    }

    // step 4:
    if (manifest != null) {

      // try to extract the name from the main class attribute
      result = LogicalJarNameResolver.extractNameFromMainClassAttribute(manifest);
      if (result != null) {
        return result;
      }

      // try to extract the name from the implementation title
      // attribute
      result = LogicalJarNameResolver.extractNameFromImplementationTitle(manifest);
      if (result != null) {
        return result;
      }
    }

    //
    return result;
  }

  private static String extractVersion(File file) throws IOException {

    String version = null;

    // Try to analyze the jar file

    // get the manifest file
    JarFile jarFile = new JarFile(file);

    // Try to analyze the manifest file

    // get the manifest file
    Manifest manifest = jarFile.getManifest();

    if (manifest != null) {

      // Try Bundle-Version
      version = LogicalJarVersionResolver.extractNameFromBundleVersion(manifest);

      // Try Implementation-Version
      if (version == null) {
        version = LogicalJarVersionResolver.extractNameFromImplementationVersion(manifest);
      }

      // Try Specification
      if (version == null) {
        version = LogicalJarVersionResolver.extractNameFromSpecificationVersion(manifest);
      }
    }

    // Still no version found, try maven pom properties
    if (version == null) {
      // try to read maven pom.properties
      Enumeration<JarEntry> entries = jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry jarEntry = (JarEntry) entries.nextElement();
        if (jarEntry.getName().endsWith("pom.properties")) {
          Properties properties = new Properties();
          properties.load(jarFile.getInputStream(jarEntry));
          version = properties.getProperty("version");
          break;
        }
      }
    }

    File fileToParse = file;
    while (version == null && fileToParse != null) {
      version = LogicalJarVersionResolver.extractVersionFromName(fileToParse.getName());
      if (version == null) {
        fileToParse = fileToParse.getParentFile();
      }
    }

    if (version == null) {

      version = "0.0.0";
    }

    return version;
  }
}
