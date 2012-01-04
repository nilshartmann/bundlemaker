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

import java.util.List;

import org.bundlemaker.core.projectdescription.IModifiableProjectDescription;
import org.bundlemaker.core.projectdescription.IProjectContentProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ProjectDescriptionAdapter implements IWorkbenchAdapter {

  /**
   * @param parent
   */
  public ProjectDescriptionAdapter() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren(Object o) {
    IModifiableProjectDescription projectDescription = (IModifiableProjectDescription) o;
    List<? extends IProjectContentProvider> fileBasedContent = projectDescription.getContentProviders();

    return fileBasedContent.toArray(new IProjectContentProvider[0]);

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
   */
  @Override
  public ImageDescriptor getImageDescriptor(Object object) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
   */
  @Override
  public String getLabel(Object o) {
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getParent(java.lang.Object)
   */
  @Override
  public Object getParent(Object o) {
    return null;
  }

}
