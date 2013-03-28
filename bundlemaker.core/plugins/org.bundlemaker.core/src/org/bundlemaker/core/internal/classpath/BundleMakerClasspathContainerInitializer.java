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
package org.bundlemaker.core.internal.classpath;

import java.io.File;
import java.net.URL;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.internal.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerClasspathContainerInitializer extends ClasspathContainerInitializer {

  /**
   * Name of the bundles that define the Classpath Container (as plain JARs)
   */
  private final static String[]    BUNDLEMAKER_LIBRARY_BUNDLES = new String[] { "org.bundlemaker.core", //
      "org.bundlemaker.com.tinkerpop.blueprints.blueprints-core", //
      "org.bundlemaker.core.transformations", //
      "org.bundlemaker.core.ui", //
      "org.eclipse.core.resources", //
      "org.eclipse.core.runtime",
      "org.eclipse.equinox.common"//
                                                               };

  private static IClasspathEntry[] _classpathEntries;

  /**
   * Eclipse install location used to convert relative plug-in paths to absolute ones
   */
  private static IPath             _eclipseInstallLocation;

  public BundleMakerClasspathContainerInitializer() {
    // required no-arg constructor
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.ClasspathContainerInitializer#initialize(org.eclipse.core.runtime.IPath,
   * org.eclipse.jdt.core.IJavaProject)
   */
  @Override
  public void initialize(IPath containerPath, IJavaProject javaProject) throws CoreException {

    JavaCore.setClasspathContainer(BundleMakerCore.BUNDLEMAKER_CONTAINER_PATH, new IJavaProject[] { javaProject },
        new IClasspathContainer[] { new BundleMakerClasspathContainer() }, null);

  }

  /**
   * @return
   */
  static IClasspathEntry[] getClasspathEntries() {
    if (_classpathEntries == null) {
      _classpathEntries = determineBundleMakerLibraryClasspathEntries();
    }

    return _classpathEntries;
  }

  /**
   * @return
   */
  private static IClasspathEntry[] determineBundleMakerLibraryClasspathEntries() {

    Map<String, Bundle> installedBundles = getInstalledBundles();

    final List<IClasspathEntry> classpathEntries = new LinkedList<IClasspathEntry>();

    for (String bundleMakerLibraryBundleName : BUNDLEMAKER_LIBRARY_BUNDLES) {

      Bundle bundle = installedBundles.get(bundleMakerLibraryBundleName);
      if (bundle == null) {
        // TODO
        System.err.println("[BundleMakerClassPathContainerInitializer] No bundle found with name '"
            + bundleMakerLibraryBundleName + "'");
        continue;
      }

      IPath bundlePath = getBundlePath(bundle);

      if (bundlePath == null) {
        continue;
      }

      IPath sourceBundlePath = getSourceLocation(bundle, bundlePath);

      IClasspathEntry entry = JavaCore.newLibraryEntry(bundlePath, sourceBundlePath, null, new IAccessRule[0], null,
          false);
      classpathEntries.add(entry);
    }

    return classpathEntries.toArray(new IClasspathEntry[0]);

  }

  private static synchronized IPath eclipseInstallLocation() {

    if (_eclipseInstallLocation == null) {

      // Try Eclipe Location Service
      Location location = null;

      ServiceTracker<Location, Location> serviceTracker;
      try {
        serviceTracker = new ServiceTracker<Location, Location>(
            Activator.getContext(),
            Activator.getContext().createFilter(Location.ECLIPSE_HOME_FILTER),
            null);
        serviceTracker.open();
        location = serviceTracker.getService();
        serviceTracker.close();
      } catch (InvalidSyntaxException e) {
        // ignore
      }

      String locationString;

      if (location != null && location.isSet()) {
        // Use url from Location service
        URL url = location.getURL();
        locationString = url.toString();
      } else {
        // Fallback: use System property
        locationString = System.getProperty("eclipse.home.location");
      }

      if (locationString == null) {
        throw new IllegalStateException("Could not determine Eclipse home location.");
      }

      // Convert URL to Path
      if (locationString.startsWith("file:")) {
        locationString = locationString.substring("file:".length());
      }

      _eclipseInstallLocation = new Path(locationString);
    }

    return _eclipseInstallLocation;
  }

  private static IPath getBundlePath(Bundle bundle) {
    String location = bundle.getLocation();

    if (location.startsWith("reference:")) {
      location = location.substring("reference:".length());
    }

    if (!location.startsWith("file:")) {
      System.err.println("Location kein file: " + location);
      return null;
    }

    location = location.substring("file:".length());

    File file = new File(location);
    if (file.isDirectory()) {
      File binDirectory = new File(file, "bin");
      if (binDirectory.isDirectory()) {
        file = binDirectory;
      }
    }

    IPath bundlePath;

    if (file.isAbsolute()) {
      bundlePath = new Path(file.getAbsolutePath());
    } else {
      String path = file.getPath();
      bundlePath = eclipseInstallLocation().append(path);
    }

    return bundlePath;

  }

  private static IPath getSourceLocation(Bundle bundle, IPath bundlePath) {
    // TODO For now this seems to be enough but we could make the algorithm more sophisticated

    IPath sourcePath = null;

    File bundleFile = new File(bundlePath.toOSString());
    if ("bin".equals(bundleFile.getName())) {
      // project
      sourcePath = bundlePath.removeLastSegments(1).append("src");
    } else {
      IPath parentPath = bundlePath.removeLastSegments(1);

      String sourceJarName = bundle.getSymbolicName() + ".source_" + bundle.getVersion() + ".jar";

      sourcePath = parentPath.append(sourceJarName);
    }

    File sourceFile = new File(sourcePath.toOSString());
    if (sourceFile.exists()) {
      return sourcePath;
    }

    // source location couldn't be determined
    return null;
  }

  private static Map<String, Bundle> getInstalledBundles() {
    BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();

    Bundle[] allBundles = bundleContext.getBundles();

    Map<String, Bundle> result = new Hashtable<String, Bundle>();

    for (Bundle bundle : allBundles) {

      result.put(bundle.getSymbolicName(), bundle);
    }

    return result;

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.ClasspathContainerInitializer#getDescription(org.eclipse.core.runtime.IPath,
   * org.eclipse.jdt.core.IJavaProject)
   */
  @Override
  public String getDescription(IPath containerPath, IJavaProject project) {
    return "BundleMaker Libraries";
  }

}
