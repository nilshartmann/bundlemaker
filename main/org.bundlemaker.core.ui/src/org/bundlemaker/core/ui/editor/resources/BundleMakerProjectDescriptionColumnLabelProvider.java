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
package org.bundlemaker.core.ui.editor.resources;

import org.bundlemaker.core.projectdescription.IFileBasedContent;
import org.bundlemaker.core.projectdescription.IRootPath;
import org.bundlemaker.core.ui.internal.CenterImageLabelProvider;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

class BundleMakerProjectDescriptionColumnLabelProvider extends CenterImageLabelProvider implements ILabelProvider {

  private final int _column;

  /**
   * @param column
   */
  BundleMakerProjectDescriptionColumnLabelProvider(int column) {
    _column = column;
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof IFileBasedContent) {
      return getImageForFileBasedContent((IFileBasedContent) element);
    }

    if (element instanceof IRootPath) {
      return getImageForBundleMakerPath((IRootPath) element);
    }

    return null;
  }

  /**
   * @param element
   * @return
   */
  private Image getImageForBundleMakerPath(IRootPath path) {
    if (_column != 0) {
      // Icons are shown in the left column only
      return null;
    }

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
   * @param element
   * @return
   */
  private Image getImageForFileBasedContent(IFileBasedContent content) {
    Image image = null;

    switch (_column) {
    case 1:
      if (content.getAnalyzeMode().isAnalyze()) {
        image = UIImages.CHECKED.getImage();
      } else {
        image = UIImages.UNCHECKED.getImage();
      }
      break;
    case 2:
      if (content.isResourceContent()) {
        if (content.isAnalyzeSourceResources()) {
          image = UIImages.CHECKED.getImage();
        } else {
          image = UIImages.UNCHECKED.getImage();
        }
      }
      break;
    default:
      break;
    }

    return image;
  }

  @Override
  public String getText(Object element) {
    if (_column > 0) {
      return null;
    }

    if (element instanceof IFileBasedContent) {
      IFileBasedContent content = (IFileBasedContent) element;
      return String.format("%s [%s]", content.getName(), content.getVersion());
    }

    IRootPath path = (IRootPath) element;
    return String.valueOf(path.getUnresolvedPath());
  }
}