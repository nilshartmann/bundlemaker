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
package org.bundlemaker.core.ui.classpath;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.ui.transformation.Activator;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerClasspathContainerInitializer extends ClasspathContainerInitializer {

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

    System.out.println("Initialize " + containerPath);
    IProject project = javaProject.getProject();

    Bundle[] bundles = getBundleMakerLibraryBundles();

    IPluginModelBase model = PluginRegistry.findModel(project);
    JavaCore.setClasspathContainer(Activator.BUNDLEMAKER_CONTAINER_PATH, new IJavaProject[] { javaProject },
        new IClasspathContainer[] { new BundleMakerClasspathContainer(bundles) }, null);

    // TODO Auto-generated method stub

  }

  /**
   * @return
   */
  private Bundle[] getBundleMakerLibraryBundles() {
    List<Bundle> libraryBundles = new LinkedList<Bundle>();

    BundleContext bundleContext = Activator.getDefault().getBundle().getBundleContext();

    Bundle[] allBundles = bundleContext.getBundles();

    for (Bundle bundle : allBundles) {
      if (isBundleMakerLibraryBundle(bundle)) {
        libraryBundles.add(bundle);
      }
    }

    return libraryBundles.toArray(new Bundle[0]);
  }

  private final static String[] BUNDLEMAKER_LIBRARY_BUNDLES = new String[] { //
                                                            "org.bundlemaker.core", //
      "org.bundlemaker.core.transformations", //
      "org.bundlemaker.core.ui", //
      "org.bundlemaker.core.ui.transformation", //
      "org.eclipse.core.resources", //
      "org.eclipse.core.runtime" //
                                                            };

  /**
   * @param bundle
   * @return
   */
  private boolean isBundleMakerLibraryBundle(Bundle bundle) {

    String name = bundle.getSymbolicName();

    for (String bundleMakerLibraryBundleName : BUNDLEMAKER_LIBRARY_BUNDLES) {
      if (name.equalsIgnoreCase(bundleMakerLibraryBundleName)) {
        return true;
      }
    }

    return false;
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
