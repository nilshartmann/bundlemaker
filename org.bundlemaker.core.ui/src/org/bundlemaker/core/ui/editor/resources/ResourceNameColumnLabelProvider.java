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

class ResourceNameColumnLabelProvider extends ColumnLabelProvider {

  ResourceNameColumnLabelProvider() {
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof BundleMakerPath) {
      return getImageForBundleMakerPath((BundleMakerPath) element);
    }

    return null;
  }

  /**
   * @param element
   * @return
   */
  private Image getImageForBundleMakerPath(BundleMakerPath path) {
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

  public String getText(Object element) {
    if (element instanceof IFileBasedContent) {
      IFileBasedContent content = (IFileBasedContent) element;
      return String.format("%s [%s]", content.getName(), content.getVersion());
    }

    BundleMakerPath path = (BundleMakerPath) element;
    return path.getLabel();
  }
}