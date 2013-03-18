package org.bundlemaker.core.itestframework.internal;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class TestFrameworkActivator implements BundleActivator {

  /**
   * {@inheritDoc}
   */
  @Override
  public void start(BundleContext context) throws Exception {

    //
    if (isMacOs()) {
      // Without the org.eclipse.jdt.launching.macosx bundle there are no
      // JREs available on MacOs
      installMacOsLaunchingBundle(context);
    }

  }

  /**
   * Installs the bundle org.eclipse.jdt.launching.macosx if running on MacOs
   * 
   * @param context
   * @throws Exception
   */
  private void installMacOsLaunchingBundle(BundleContext context) throws Exception {
    String osgiFramework = System.getProperty("osgi.framework");
    URI uri = new URI(osgiFramework);
    File osgiFrameworkBundle = new File(uri);
    File pluginsDir = osgiFrameworkBundle.getParentFile();

    File macosLaunchingBundle = findMacOsLaunchingBundle(pluginsDir);
    if (macosLaunchingBundle == null) {
      System.err.println("WARN! No MacOs Launching Bundle found!");
      return;
    }

    System.out.printf("Installing MacOs launching bundle '%s'%n", macosLaunchingBundle);
    Bundle installedBundle = context.installBundle(macosLaunchingBundle.toURI().toString());
    System.out.printf("Starting MacOs launching bundle '%s'%n", installedBundle);
    installedBundle.start();

  }

  /**
   * @param pluginsDir
   * @return
   */
  private File findMacOsLaunchingBundle(File pluginsDir) {
    File[] plugins = pluginsDir.listFiles(new FileFilter() {

      @Override
      public boolean accept(File file) {
        String name = file.getName();
        if (name.startsWith("org.eclipse.jdt.launching.macosx_")) {
          return true;
        }
        return false;
      }
    });

    if (plugins.length > 0) {
      return plugins[0];
    }

    return null;
  }

  /**
   * @return
   */
  private boolean isMacOs() {
    String osgiOs = System.getProperty("osgi.os");
    return "macosx".equals(osgiOs);
  }

  @Override
  public void stop(BundleContext context) throws Exception {

  }

}
