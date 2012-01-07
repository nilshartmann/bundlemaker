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

import org.bundlemaker.core.ui.editor.RootPathHelper;
import org.bundlemaker.core.ui.editor.adapter.ProjectPath;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class ResourceNameColumnLabelProvider extends ColumnLabelProvider {

  public ResourceNameColumnLabelProvider() {
  }

  @Override
  public Image getImage(Object element) {

    if (element instanceof ProjectPath) {
      return RootPathHelper.getImageForPath((ProjectPath) element);
    }

    return null;
  }

  @Override
  public String getText(Object element) {
    return null;
    // if (element instanceof IProjectContentProvider) {
    // IProjectContentProvider content = (IProjectContentProvider) element;
    // return String.format("%s [%s]", content.getName(), content.getVersion());
    // }
    //
    // ProjectPath path = (ProjectPath) element;
    // return RootPathHelper.getLabel(path);
  }
}