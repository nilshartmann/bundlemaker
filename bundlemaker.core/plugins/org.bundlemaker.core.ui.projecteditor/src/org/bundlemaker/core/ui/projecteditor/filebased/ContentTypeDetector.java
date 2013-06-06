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
package org.bundlemaker.core.ui.projecteditor.filebased;

import java.io.File;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bundlemaker.core.common.ResourceType;
import org.bundlemaker.core.project.util.VariablePath;
import org.eclipse.core.runtime.CoreException;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ContentTypeDetector {

  /**
   * A {@link Pattern} matching (file) names containing 'src' or 'source'
   */
  private final Pattern sourceNamePattern = Pattern.compile("(.*\\W)?(src|source)(\\W.*)?");

  public ResourceType detectContentType(VariablePath path) {

    try {
      File file = path.getAsFile();
      return detectContentType(file);
    } catch (CoreException e) {
      return guessContentTypeFromFileName(path.getUnresolvedPath().toString());
    }
  }

  /**
   * Tries to determine the {@link ResourceType} of the specified file.
   * 
   * <p>
   * If the content type cannot be determined, {@link ResourceType#BINARY} is assumed.
   * 
   * @param file
   *          a file or directory
   * 
   * @return the content type. Never null
   */
  public ResourceType detectContentType(File file) {

    ResourceType result = null;

    if (file.isDirectory()) {
      result = detectContentTypeFromDirectory(file);
    }

    if (file.isFile()) {
      result = detectContentTypeFromFile(file);
    }

    if (result == null) {
      // check if file name contains 'source' or 'src'
      result = guessContentTypeFromFileName(file.getAbsolutePath());
    }

    return result;

  }

  /**
   * @param file
   * @return
   */
  protected ResourceType detectContentTypeFromDirectory(File file) {
    ResourceType result = null;

    File[] allFiles = file.listFiles();

    List<File> directories = new LinkedList<File>();

    for (File fileInDirectory : allFiles) {
      if (fileInDirectory.isDirectory()) {
        // save subdirectories in case we need to recursively scan them for files
        directories.add(fileInDirectory);
      } else {
        String fileName = fileInDirectory.getName();
        if (isJavaClassFileName(fileName)) {
          // class file found: this jar is probably an archive containing binaries
          result = ResourceType.BINARY;
          break;
        }

        if (isJavaSourceFileName(fileName)) {
          // class file found: this jar is probably an archive containing binaries
          result = ResourceType.SOURCE;
          break;
        }
      }
    }

    if (result == null) {
      for (File subDirectory : directories) {
        result = detectContentTypeFromDirectory(subDirectory);
        if (result != null) {
          break;
        }
      }
    }

    return result;
  }

  protected boolean isJavaSourceFileName(String fileName) {
    return (fileName != null && fileName.toLowerCase().endsWith(".java"));
  }

  protected boolean isJavaClassFileName(String fileName) {
    return (fileName != null && fileName.toLowerCase().endsWith(".class"));
  }

  /**
   * @param file
   * @return
   */
  protected ResourceType detectContentTypeFromFile(File file) {

    JarFile jarFile = null;
    ResourceType result = ResourceType.BINARY;
    try {
      jarFile = new JarFile(file);

      Enumeration<JarEntry> entries = jarFile.entries();

      while (entries.hasMoreElements()) {
        JarEntry entry = entries.nextElement();

        String fileName = entry.getName();
        if (isJavaClassFileName(fileName)) {
          // class file found: this jar is probably an archive containing binaries
          result = ResourceType.BINARY;
          break;
        }

        if (isJavaSourceFileName(fileName)) {
          // class file found: this jar is probably an archive containing binaries
          result = ResourceType.SOURCE;
          break;
        }
      }

    } catch (Exception ex) {
      // for whatever reason we're not able to read the file:
      // guess content type based on the file name
      result = guessContentTypeFromFileName(file.getAbsolutePath());
    } finally {
      if (jarFile != null) {
        try {
          jarFile.close();
        } catch (Exception ex) {
          // ignore
        }
      }
    }

    return result;

  }

  /**
   * @param name
   * @return
   */
  protected ResourceType guessContentTypeFromFileName(String name) {

    // Create the matcher
    Matcher matcher = sourceNamePattern.matcher(name.toLowerCase());

    //
    boolean sourceFileName = matcher.matches();

    if (sourceFileName) {
      return ResourceType.SOURCE;
    }

    return ResourceType.BINARY;
  }

  public static void main(String[] args) {

    File f = new File("/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/BM-JEdit/libs/jedit-abc.zip");

    ResourceType ct = new ContentTypeDetector().detectContentType(f);

    System.out.println("/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/BM-JEdit/libs/jedit-abc.zip: " + ct);

    ct = new ContentTypeDetector().detectContentType(new File(
        "/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/BM-JEdit/libs/jedit.jar"));

    System.out.println("/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/BM-JEdit/libs/jedit.jar: " + ct);

    ct = new ContentTypeDetector().detectContentType(new File(
        "/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/core/src"));
    System.out.println("/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/core/src: " + ct);

    ct = new ContentTypeDetector().detectContentType(new File(
        "/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/core/bin"));
    System.out.println("/Users/nils/develop/bundlemaker/bundlemaker-ui-workspace/core/bin: " + ct);
    //
    // Pattern p = Pattern.compile("(.*\\W)?(src|source)(\\W.*)?");
    //
    // isMatching(p, "asrca");
    // isMatching(p, "AsrcA");
    // isMatching(p, "/src/aa");
    // isMatching(p, "src/aa");
    // isMatching(p, "src");
    // isMatching(p, "src.jar");
    //
    // isMatching(p, "asourcea");
    // isMatching(p, "AsourceA");
    // isMatching(p, "/source/aa");
    // isMatching(p, "source/aa");
    // isMatching(p, "source");
    // isMatching(p, "source.jar");
  }

  // private static void isMatching(Pattern p, String value) {
  // Matcher m = p.matcher(value.toLowerCase());
  // boolean b = m.matches();
  //
  // System.out.printf("'%s' matches pattern: %s%n", value, b);
  // }
}
