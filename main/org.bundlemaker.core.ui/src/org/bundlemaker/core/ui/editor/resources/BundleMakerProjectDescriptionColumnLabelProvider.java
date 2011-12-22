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

import org.bundlemaker.core.projectdescription.AnalyzeMode;
import org.bundlemaker.core.projectdescription.IProjectContentEntry;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.editor.RootPathHelper;
import org.bundlemaker.core.ui.internal.CenterImageLabelProvider;
import org.bundlemaker.core.ui.internal.UIImages;
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

    if (element instanceof IProjectContentEntry) {
      return getImageForFileBasedContent((IProjectContentEntry) element);
    }

    if (element instanceof VariablePath) {
      return getImageForBundleMakerPath((VariablePath) element);
    }

    return null;
  }

  /**
   * @param element
   * @return
   */
  private Image getImageForBundleMakerPath(VariablePath path) {
    if (_column != 0) {
      // Icons are shown in the left column only
      return null;
    }

    // TODO: FIX ME
    return RootPathHelper.getImageForPath(path, true);

  }

  /**
   * @param element
   * @return
   */
  private Image getImageForFileBasedContent(IProjectContentEntry content) {
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
      AnalyzeMode analyzeMode = content.getAnalyzeMode();
      if (analyzeMode == AnalyzeMode.BINARIES_AND_SOURCES) {
        image = UIImages.CHECKED.getImage();
      } else if (analyzeMode == AnalyzeMode.BINARIES_ONLY) {
        image = UIImages.UNCHECKED.getImage();
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

    if (element instanceof IProjectContentEntry) {
      IProjectContentEntry content = (IProjectContentEntry) element;
      return String.format("%s [%s]", content.getName(), content.getVersion());
    }

    VariablePath path = (VariablePath) element;
    return RootPathHelper.getLabel(path);
  }
}