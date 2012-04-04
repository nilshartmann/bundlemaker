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

import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.UIImages;
import org.bundlemaker.core.ui.editor.adapter.ProjectPath;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class RootPathHelper {

  public static String getLabel(VariablePath rootPath) {
    return String.valueOf(rootPath.getUnresolvedPath());
  }

  public static String getLabel(ProjectPath projectPath) {
    return getLabel(projectPath.getPath());
  }

  /**
   * @param element
   * @return
   */
  public static Image getImageForPath(VariablePath path, boolean isBinaryPath) {
    boolean isFolder;
    try {
      isFolder = path.getAsFile().isDirectory();
    } catch (CoreException ex) {
      return UIImages.UNKNOWN_OBJECT.getImage();
    }

    if (isFolder) {
      if (isBinaryPath) {
        return UIImages.BINARY_FOLDER.getImage();
      }
      return UIImages.SOURCE_FOLDER.getImage();
    }
    if (isBinaryPath) {
      return UIImages.BINARY_ARCHIVE.getImage();
    }
    return UIImages.SOURCE_ARCHIVE.getImage();

  }

  /**
   * @param path
   * @return
   */
  public static ImageDescriptor getImageDescriptorForPath(VariablePath path, boolean isBinaryPath) {
    boolean isFolder;
    try {
      isFolder = path.getAsFile().isDirectory();
    } catch (CoreException ex) {
      return UIImages.UNKNOWN_OBJECT.getImageDescriptor();
    }

    if (isFolder) {
      if (isBinaryPath) {
        return UIImages.BINARY_FOLDER.getImageDescriptor();
      }
      return UIImages.SOURCE_FOLDER.getImageDescriptor();
    }
    if (isBinaryPath) {
      return UIImages.BINARY_ARCHIVE.getImageDescriptor();
    }
    return UIImages.SOURCE_ARCHIVE.getImageDescriptor();
  }

  public static IProjectContentProvider getOwningFileBasedContent(TreePath treePath) {
    IProjectContentProvider lastSegment = (IProjectContentProvider) treePath.getParentPath().getLastSegment();
    return lastSegment;
  }

  /**
   * @param path
   * @param isBinaryPath
   * @return
   */
  public static Image getImageForPath(ProjectPath path) {
    return getImageForPath(path.getPath(), path.isBinary());
  }

}
