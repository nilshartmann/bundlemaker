package org.bundlemaker.core.internal.common.fileinfo;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.framework.Constants;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultFileBasedContentInfoResolver implements IFileBasedProjectContentInfoResolver {

  /**
   * The pattern to identify source module names and extract it's associated binary names if possible
   */
  final static Pattern _pattern = Pattern.compile("(.*)[\\._-](src|source|sources)[-_]?.*");

  /** - */
  private String       _name;

  /** - */
  private String       _version;

  /** - */
  private boolean      _isSource;

  /** - */
  private JarFile      _jarFile;

  /** - */
  private Manifest     _manifest;

  /** - */
  private File         _file;

  private String       _binaryName;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean resolve(File file) {

    //
    try {

      //
      _file = file;
      _jarFile = new JarFile(file);
      _manifest = _jarFile.getManifest();

      //
      _name = extractName();
      if (_name == null) {
        return false;
      }

      _binaryName = extractAssociatedBinaryName(_name);

      _version = extractVersion();
      if (_version == null) {
        return false;
      }

      //
      _isSource = extractIsSource();

      //
      return true;
    }

    //
    catch (IOException e) {
      return false;
    }
  }

  /**
   * @param name
   * @return
   */
  public static String extractAssociatedBinaryName(String name) {
    Matcher matcher = _pattern.matcher(name);

    if (!matcher.matches() || matcher.groupCount() != 2) {
      // cannot determine...
      return name;
    }

    return matcher.group(1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return _name;
  }

  public String getBinaryName() {
    return _binaryName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVersion() {
    return _version;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSource() {
    return _isSource;
  }

  /**
   * Returns true if the given value matches the source name pattern
   * 
   * @param value
   * @return
   */
  public static boolean isSourcePattern(String value) {
    Matcher matcher = _pattern.matcher(value);
    boolean result = matcher.matches();
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  private boolean extractIsSource() {
    return isSourcePattern(_file.getName());
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws IOException
   */
  private String extractName() throws IOException {

    //
    String result = null;

    // step 1: check if a symbolic name exists
    if (_manifest != null) {

      result = _manifest.getMainAttributes().getValue(Constants.BUNDLE_SYMBOLICNAME);

      if (result != null) {
        int end = result.indexOf(';');
        if (end > 0) {
          result = result.substring(0, end);
        }
        return result;
      }
    }

    // try to read maven properties
    Enumeration<JarEntry> entries = _jarFile.entries();
    while (entries.hasMoreElements()) {
      JarEntry jarEntry = (JarEntry) entries.nextElement();
      if (jarEntry.getName().endsWith("pom.properties")) {
        Properties properties = new Properties();
        properties.load(_jarFile.getInputStream(jarEntry));
        // version=0.9.26
        // groupId=ch.qos.logback
        // artifactId=logback-core
        return properties.getProperty("groupId") + "." + properties.getProperty("artifactId");
      }
    }

    // step 2:
    result = LogicalJarNameResolver.extractName(_file.getName());
    if (result != null) {
      return result;
    }

    // step 3:
    result = LogicalJarNameResolver.extractNameFromRootDirectory(_jarFile);
    if (result != null) {
      return result;
    }

    // step 4:
    if (_manifest != null) {

      // try to extract the name from the main class attribute
      result = LogicalJarNameResolver.extractNameFromMainClassAttribute(_manifest);
      if (result != null) {
        return result;
      }

      // try to extract the name from the implementation title
      // attribute
      result = LogicalJarNameResolver.extractNameFromImplementationTitle(_manifest);
      if (result != null) {
        return result;
      }
    }

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param file
   * @return
   * @throws IOException
   */
  private String extractVersion() throws IOException {

    String version = null;

    // Try to analyze the manifest file

    // get the manifest file
    Manifest manifest = _jarFile.getManifest();

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
      Enumeration<JarEntry> entries = _jarFile.entries();
      while (entries.hasMoreElements()) {
        JarEntry jarEntry = (JarEntry) entries.nextElement();
        if (jarEntry.getName().endsWith("pom.properties")) {
          Properties properties = new Properties();
          properties.load(_jarFile.getInputStream(jarEntry));
          version = properties.getProperty("version");
          break;
        }
      }
    }

    File fileToParse = _file;
    while (version == null && fileToParse != null) {
      version = LogicalJarVersionResolver.extractVersionFromName(fileToParse.getName());
      if (version == null) {
        fileToParse = fileToParse.getParentFile();
      }
    }

    // Determine from Filename

    return (version == null ? "0.0.0" : version);
  }
}
