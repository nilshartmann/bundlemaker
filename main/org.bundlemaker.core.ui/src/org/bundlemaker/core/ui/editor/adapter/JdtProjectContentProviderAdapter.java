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
package org.bundlemaker.core.ui.editor.adapter;

import org.bundlemaker.core.content.jdt.JdtProjectContentProvider;
import org.bundlemaker.core.ui.internal.UIImages;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class JdtProjectContentProviderAdapter implements IWorkbenchAdapter {
  private final static Object[] NO_CHILDREN = new Object[0];

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getChildren(java.lang.Object)
   */
  @Override
  public Object[] getChildren(Object o) {
    return NO_CHILDREN;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getImageDescriptor(java.lang.Object)
   */
  @Override
  public ImageDescriptor getImageDescriptor(Object object) {
    return UIImages.JDT_PROJECT_CONTENT_PROVIDER.getImageDescriptor();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.ui.model.IWorkbenchAdapter#getLabel(java.lang.Object)
   */
  @Override
  public String getLabel(Object o) {
    return getJdtProjectContentProvider(o).getJavaProject().getProject().getName();
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

  private JdtProjectContentProvider getJdtProjectContentProvider(Object o) {
    return (JdtProjectContentProvider) o;
  }

}
