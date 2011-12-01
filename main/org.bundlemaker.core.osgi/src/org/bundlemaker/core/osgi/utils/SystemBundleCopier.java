package org.bundlemaker.core.osgi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.bundlemaker.core.util.FileUtils;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.BundleActivator;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SystemBundleCopier {

  /**
   * <p>
   * </p>
   *
   * @param destinationDirectory
   * @throws FileNotFoundException
   * @throws IOException
   */
  public static void copySystemBundle(File destinationDirectory) throws FileNotFoundException, IOException {

    Assert.isNotNull(destinationDirectory);
    Assert.isTrue(destinationDirectory.isDirectory(), "destinationDirectory.isDirectory()");

    URL url = BundleActivator.class.getProtectionDomain().getCodeSource().getLocation();

    String file = BundleActivator.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    String fileName = file.substring(file.lastIndexOf("/") + 1);

    FileUtils.copy(url.openStream(), new FileOutputStream(new File(destinationDirectory, fileName)), new byte[1024]);
  }
}
