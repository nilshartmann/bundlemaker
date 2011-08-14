/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor;

import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.projectdescription.modifiable.IModifiableFileBasedContent;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class RootPathHelper {

  public static String getLabel(IRootPath rootPath) {
    return String.valueOf(rootPath.getUnresolvedPath());
  }

  /**
   * @param element
   * @return
   */
  public static Image getImageForPath(IRootPath path) {
    boolean isFolder;
    try {
      isFolder = path.getAsFile().isDirectory();
    } catch (CoreException ex) {
      return UIImages.UNKNOWN_OBJECT.getImage();
    }

    if (isFolder) {
      if (path.isBinaryPath()) {
        return UIImages.BINARY_FOLDER.getImage();
      }
      return UIImages.SOURCE_FOLDER.getImage();
    }
    if (path.isBinaryPath()) {
      return UIImages.BINARY_ARCHIVE.getImage();
    }
    return UIImages.SOURCE_ARCHIVE.getImage();

  }

  /**
   * @param path
   * @return
   */
  public static ImageDescriptor getImageDescriptorForPath(IRootPath path) {
    boolean isFolder;
    try {
      isFolder = path.getAsFile().isDirectory();
    } catch (CoreException ex) {
      return UIImages.UNKNOWN_OBJECT.getImageDescriptor();
    }

    if (isFolder) {
      if (path.isBinaryPath()) {
        return UIImages.BINARY_FOLDER.getImageDescriptor();
      }
      return UIImages.SOURCE_FOLDER.getImageDescriptor();
    }
    if (path.isBinaryPath()) {
      return UIImages.BINARY_ARCHIVE.getImageDescriptor();
    }
    return UIImages.SOURCE_ARCHIVE.getImageDescriptor();
  }

  public static IModifiableFileBasedContent getOwningFileBasedContent(TreePath treePath) {
    IModifiableFileBasedContent lastSegment = (IModifiableFileBasedContent) treePath.getParentPath().getLastSegment();
    return lastSegment;
  }

}
