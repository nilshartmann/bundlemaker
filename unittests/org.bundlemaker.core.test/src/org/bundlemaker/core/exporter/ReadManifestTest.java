package org.bundlemaker.core.exporter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.osgi.framework.internal.core.FrameworkProperties;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ResolverError;
import org.eclipse.osgi.service.resolver.State;
import org.eclipse.osgi.service.resolver.StateObjectFactory;
import org.junit.Test;
import org.osgi.framework.BundleException;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ReadManifestTest {

  /** - */
  public static StateObjectFactory _factory = StateObjectFactory.defaultFactory;

  /** - */
  private static long              COUNTER  = 1l;

  /**
   * <p>
   * </p>
   * 
   * @throws CoreException
   */
  @Test
  public void test() throws Exception {
    State state = resolve();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   * @throws BundleException
   * @throws IOException
   * @throws FileNotFoundException
   */
  private State resolve() throws FileNotFoundException, IOException, BundleException {

    // TODO
    FrameworkProperties.setProperty("osgi.resolver.usesMode", "ignore");

    // step 1: create new state
    State state = StateObjectFactory.defaultFactory.createState(true);

    // for (BundleDescription bundleDescription :
    // getAllBundleDescriptions(this._configuration
    // .isPreferProjects())) {

    BundleDescription bundleDescription = parsePluginDirectory(new File(System.getProperty("user.dir"),
        "test-environment/libs/catalina_0.0.0"));

    BundleDescription copy = StateObjectFactory.defaultFactory.createBundleDescription(bundleDescription);
    // copy.setUserObject(bundleDescription.getUserObject());
    if (!state.addBundle(copy)) {
      // TODO: NLS
      throw new RuntimeException("Could not add bundle '" + bundleDescription + "' to state!");
    }
    // }

    // // set the platform properties
    // Properties platformProperties =
    // this._configuration.getConfigurationProperties();
    // state.setPlatformProperties(platformProperties);

    // resolve the state
    state.resolve();

    // log errors if any
    BundleDescription[] bundleDescriptions = state.getBundles();
    // boolean allStatesResolved = true;

    for (BundleDescription description : bundleDescriptions) {
      String resolverErrors = dumpResolverErrors(description, true);
      if (resolverErrors != null && !resolverErrors.trim().equals("")) {
        System.out.println(resolverErrors);
      }
    }
    // return the state
    return state;
  }

  public static String dumpResolverErrors(BundleDescription description, boolean dumpHeader) {

    StringBuffer stringBuffer = new StringBuffer();
    State state = description.getContainingState();
    ResolverError[] errors = state.getResolverErrors(description);
    if (!description.isResolved() || ((errors != null) && (errors.length != 0))) {
      if ((errors != null) && (errors.length == 1) && (errors[0].getType() == ResolverError.SINGLETON_SELECTION)) {
        stringBuffer.append("Not using '");
        stringBuffer.append(description.getName());
        stringBuffer.append("' -- another version resolved\n");
      } else {
        if (dumpHeader) {
          stringBuffer.append("Could not resolve '");
          // stringBuffer.append(getBundleInfo(description));
          stringBuffer.append(description.getSymbolicName());
          stringBuffer.append("_");
          stringBuffer.append(description.getVersion());
          stringBuffer.append("' (Location: ");
          stringBuffer.append(description.getLocation());
          stringBuffer.append("):\n");
        }
        if (errors != null) {
          if (errors.length > 0) {
            for (int i = 0; i < errors.length; i++) {
              stringBuffer.append("  ");
              stringBuffer.append(errors[i]);
              if (i + 1 < errors.length) {
                stringBuffer.append("\n");
              }
            }
          }
        }
      }
    }
    return stringBuffer.toString();
  }

  private static BundleDescription parsePluginJarFile(File file) {

    try {
      // create jar file
      JarFile jarFile = new JarFile(file);

      // support for plugins based on the osgi bundle model
      Manifest manifest = jarFile.getManifest();
      if ((manifest != null) && isBundleManifest(manifest)) {
        return createBundleDescription(manifest, file.getAbsolutePath(), file);
      }
    } catch (Exception e) {
      throw new RuntimeException("Exception while parsing plugin jar '" + file.getName() + "'!", e);
    }

    // throw FileParserException since jar is no valid plugin jar
    // TODO: Konfigurierbar machen!!
    // throw new FileParserException("Could not parse plugin jar '" +
    // file.getAbsolutePath()
    // + "' since it contains neither a Bundle-Manifest nor a plugin.xml!");
    return null;
  }

  private static BundleDescription parsePluginDirectory(File directory) throws FileNotFoundException, IOException,
      BundleException {

    // support for plugins based on the osgi bundle model
    File bundleManifestFile = new File(directory, "META-INF/MANIFEST.MF");
    if (bundleManifestFile.isFile()) {
      Manifest manifest = new Manifest(new FileInputStream(bundleManifestFile));

      if (isBundleManifest(manifest)) {
        return createBundleDescription(manifest, directory.getAbsolutePath(), directory);
      }
    }

    // throw FileParserException since directory is no valid plugin
    // directory
    // throw new FileParserException("Could not parse plugin directory '" +
    // directory.getAbsolutePath()
    // + "' since it contains neither a Bundle-Manifest nor a plugin.xml!");
    return null;
  }

  /**
   * Returns whether or not the specified manifest is a bundle manifest.
   * 
   * @param manifest
   *          the manifest to test.
   * @return whether or not the specified manifest is a bundle manifest.
   */
  private static boolean isBundleManifest(Manifest manifest) {
    return manifest.getMainAttributes().getValue("Bundle-SymbolicName") != null;
  }

  private static BundleDescription createBundleDescription(Manifest manifest, String path, Object source)
      throws BundleException {

    long counter = isSystemBundle(manifest) ? 0 : COUNTER++;

    Properties manifestProperties = convertManifest(manifest);
    BundleDescription bundleDescription = _factory.createBundleDescription(null, manifestProperties, path, counter);

    bundleDescription.setUserObject(null);
    return bundleDescription;
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifest
   * @return
   */
  @SuppressWarnings("rawtypes")
  private static Properties convertManifest(Manifest manifest) {
    Attributes attributes = manifest.getMainAttributes();
    Iterator iter = attributes.keySet().iterator();
    Properties result = new Properties();
    while (iter.hasNext()) {
      Attributes.Name key = (Attributes.Name) iter.next();
      result.put(key.toString(), attributes.get(key));
    }
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param manifest
   * @return
   */
  private static boolean isSystemBundle(Manifest manifest) {
    String isSystemBundle = manifest.getMainAttributes().getValue("Eclipse-SystemBundle");
    return "true".equals(isSystemBundle);
  }
}
