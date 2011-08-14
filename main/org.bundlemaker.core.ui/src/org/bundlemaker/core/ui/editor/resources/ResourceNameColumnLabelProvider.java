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
import org.bundlemaker.core.ui.editor.RootPathHelper;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

class ResourceNameColumnLabelProvider extends ColumnLabelProvider {

  ResourceNameColumnLabelProvider() {
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof IRootPath) {
      return RootPathHelper.getImageForPath((IRootPath) element);
    }

    return null;
  }

  @Override
  public String getText(Object element) {
    if (element instanceof IFileBasedContent) {
      IFileBasedContent content = (IFileBasedContent) element;
      return String.format("%s [%s]", content.getName(), content.getVersion());
    }

    IRootPath path = (IRootPath) element;
    return RootPathHelper.getLabel(path);
  }
}