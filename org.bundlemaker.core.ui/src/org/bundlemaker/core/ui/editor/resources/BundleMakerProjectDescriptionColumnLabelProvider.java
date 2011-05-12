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
import org.bundlemaker.core.ui.editor.BundleMakerPath;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

class BundleMakerProjectDescriptionColumnLabelProvider extends ColumnLabelProvider {

  private final int _column;

  /**
   * @param column
   */
  public BundleMakerProjectDescriptionColumnLabelProvider(int column) {
    _column = column;
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof IFileBasedContent) {
      return getImageForFileBasedContent((IFileBasedContent) element);
    }

    if (element instanceof BundleMakerPath) {
      return getImageForBundleMakerPath((BundleMakerPath) element);
    }

    return super.getImage(element);
  }

  /**
   * @param element
   * @return
   */
  private Image getImageForBundleMakerPath(BundleMakerPath path) {
    if (_column != 0) {
      // Icons are shown in the left column only
      return null;
    }

    if (path.isFolder()) {
      if (path.isBinary()) {
        return UIImages.BINARY_FOLDER.getImage();
      }
      return UIImages.SOURCE_FOLDER.getImage();
    }
    if (path.isBinary()) {
      return UIImages.BINARY_ARCHIVE.getImage();
    }
    return UIImages.SOURCE_ARCHIVE.getImage();
  }

  /**
   * @param element
   * @return
   */
  private Image getImageForFileBasedContent(IFileBasedContent content) {
    if (_column > 0) {
      if (!content.isResourceContent()) {
        return null;
      }
      // TODO: ResourceContent ohne Sources sollte isAnalyzeSourceResources() false haben ???
      if (content.isAnalyzeSourceResources()) {
        return UIImages.CHECKED.getImage();
      }
      return UIImages.UNCHECKED.getImage();
    }
    return null;
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

    BundleMakerPath path = (BundleMakerPath) element;
    return path.getLabel();
  }
}