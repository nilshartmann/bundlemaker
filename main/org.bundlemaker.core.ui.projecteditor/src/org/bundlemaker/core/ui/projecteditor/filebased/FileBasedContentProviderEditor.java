/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.projecteditor.filebased;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class FileBasedContentProviderEditor implements IProjectContentProviderEditor {

  /*
   * (non-Javadoc)
   * 
   * @see org.bundlemaker.core.ui.projecteditor.provider.IProjectContentProviderEditor#getElements(org.bundlemaker.core.
   * IBundleMakerProject, org.bundlemaker.core.projectdescription.IProjectContentProvider)
   */
  @Override
  public Object[] getElements(IBundleMakerProject project, IProjectContentProvider provider) {

    return null;
    // if (!(provider instanceof FileBasedContentProvider)) {
    // return null;
    // }
    //
    // FileBasedContentProvider fileBasedContentProvider = (FileBasedContentProvider) provider;
    //
    //
    //
    // // TODO Auto-generated method stub
    // return null;
  }

  class Parent implements IWorkbenchAdapter {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
     */
    @Override
    public Object[] getChildren(Object o) {
      // TODO Auto-generated method stub
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
     */
    @Override
    public ImageDescriptor getImageDescriptor(Object object) {
      // TODO Auto-generated method stub
      return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
     */
    @Override
    public String getLabel(Object o) {
      // TODO Auto-generated method stub
      return null;
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

}
