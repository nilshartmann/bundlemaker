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
package org.bundlemaker.core.internal.common.classpath;

import org.bundlemaker.core.project.BundleMakerCore;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IClasspathContainer;
import org.eclipse.jdt.core.IClasspathEntry;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerClasspathContainer implements IClasspathContainer {

  /**
   * @param bundles
   */
  public BundleMakerClasspathContainer() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jdt.core.IClasspathContainer#getClasspathEntries()
   */
  @Override
  public IClasspathEntry[] getClasspathEntries() {
    return BundleMakerClasspathContainerInitializer.getClasspathEntries();
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
    return BundleMakerCore.BUNDLEMAKER_CONTAINER_PATH;
  }

}
