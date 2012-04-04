/*******************************************************************************
 * Copyright (c) 2011 Nils Hartmann
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.editor.adapter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.bundlemaker.core.projectdescription.file.FileBasedContent;
import org.bundlemaker.core.projectdescription.file.FileBasedContentProvider;
import org.bundlemaker.core.projectdescription.file.VariablePath;
import org.bundlemaker.core.ui.UIImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentProviderAdapter implements IWorkbenchAdapter {

  private FileBasedContentProvider getFileBasedContentProvider(Object o) {
    return (FileBasedContentProvider) o;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren(Object o) {

    FileBasedContent fileBasedContent = getFileBasedContentProvider(o).getFileBasedContent();

    List<Object> result = new LinkedList<Object>();

    addAsProjectPaths(result, fileBasedContent.getBinaryRootPaths(), false);
    addAsProjectPaths(result, fileBasedContent.getSourceRootPaths(), true);

    return result.toArray(new Object[0]);
  }

  private void addAsProjectPaths(List<Object> target, Set<VariablePath> paths, boolean source) {
    for (VariablePath variablePath : paths) {
      target.add(new ProjectPath(variablePath, source));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
   */
  @Override
  public ImageDescriptor getImageDescriptor(Object object) {
    return UIImages.RESOURCE_CONTENT.getImageDescriptor();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
   */
  @Override
  public String getLabel(Object o) {
    FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) o;

    return String.format("%s [%s]", fileBasedContentProvider.getFileBasedContent().getName(), fileBasedContentProvider
        .getFileBasedContent().getVersion());
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
   */
  @Override
  public Object getParent(Object o) {
    // TODO Auto-generated method stub
    return null;
  }

}
