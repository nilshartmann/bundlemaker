package org.bundlemaker.core.internal.projectdescription;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.osgi.framework.Constants;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JarInfoService {

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   */
  public static JarInfo extractJarInfo(File file) {

    String name = null;
    String version = null;

    try {

      // TRY TO RESOLVE THE NAME
      name = extractName(file);

      // TRY TO RESOLVE THE VERSION
      version = extractVersion(file);

      // return the result
      return new JarInfo(name, version);
    }

    //
    catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
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
        return result;
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

    File fileToParse = file;
    while (version == null && fileToParse != null) {
      version = LogicalJarVersionResolver.extractVersionFromName(fileToParse.getName());
      if (version == null) {
        fileToParse = fileToParse.getParentFile();
      }
    }

    // Try to analyze the jar file
    if (version == null) {

      // get the manifest file
      JarFile jarFile = new JarFile(file);
      Manifest manifest = jarFile.getManifest();

      if (manifest != null) {

        version = LogicalJarVersionResolver.extractNameFromImplementationVersion(manifest);

        if (version == null) {
          version = LogicalJarVersionResolver.extractNameFromSpecificationVersion(manifest);
        }
      }
    }

    if (version == null) {

      version = "0.0.0";
    }

    return version;
  }
}
