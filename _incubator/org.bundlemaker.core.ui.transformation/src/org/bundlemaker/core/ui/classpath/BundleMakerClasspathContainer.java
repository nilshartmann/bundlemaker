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

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.ui.transformation.Activator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.JavaCore;
import org.osgi.framework.Bundle;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerClasspathContainer implements IClasspathContainer {

  private final Bundle[]    _bundles;

  private IClasspathEntry[] _entries;

  /**
   * @param bundles
   */
  public BundleMakerClasspathContainer(Bundle[] bundles) {
    _bundles = bundles;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
   */
  @Override
  public IClasspathEntry[] getClasspathEntries() {

    if (_entries == null) {
      _entries = populateClasspathEntries();
    }

    return _entries;
  }

  /**
   * @return
   */
  private IClasspathEntry[] populateClasspathEntries() {

    List<IClasspathEntry> entries = new LinkedList<IClasspathEntry>();

    for (Bundle bundle : _bundles) {
      String location = bundle.getLocation();

      if (location.startsWith("reference:")) {
        location = location.substring("reference:".length());
      }

      if (!location.startsWith("file:")) {
        System.err.println("Location kein file: " + location);
        continue;
      }

      location = location.substring("file:".length());

      File file = new File(location);
      if (file.isDirectory()) {
        File binDirectory = new File(file, "bin");
        if (binDirectory.isDirectory()) {
          file = binDirectory;
        }
      }

      IPath path = new Path(file.getAbsolutePath());

      IClasspathEntry entry = JavaCore.newLibraryEntry(path, null, null, new IAccessRule[0], null, false);
      entries.add(entry);
    }

    return entries.toArray(new IClasspathEntry[0]);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.IClasspathContainer#getDescription()
   */
  @Override
  public String getDescription() {
    return "BundleMaker Libraries";
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.IClasspathContainer#getKind()
   */
  @Override
  public int getKind() {
    return K_APPLICATION;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.IClasspathContainer#getPath()
   */
  @Override
  public IPath getPath() {
    return Activator.BUNDLEMAKER_CONTAINER_PATH;
  }

}
