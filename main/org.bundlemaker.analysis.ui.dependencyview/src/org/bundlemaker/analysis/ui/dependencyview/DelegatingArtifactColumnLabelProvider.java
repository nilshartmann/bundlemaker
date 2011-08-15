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
package org.bundlemaker.analysis.ui.dependencyview;

import org.bundlemaker.analysis.ui.DefaultArtifactLabelProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DelegatingArtifactColumnLabelProvider extends ColumnLabelProvider {

  private static final DefaultArtifactLabelProvider _defaultArtifactLabelProvider = new DefaultArtifactLabelProvider();

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getImage(java.lang.Object)
   */
  @Override
  public Image getImage(Object element) {
    return _defaultArtifactLabelProvider.getImage(preprocessElement(element));
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.jface.viewers.ColumnLabelProvider#getText(java.lang.Object)
   */
  @Override
  public String getText(Object element) {
    return _defaultArtifactLabelProvider.getText(preprocessElement(element));
  }

  /**
   * Override in subclasses to return another element that is passed to the artifact label provider
   * 
   * @param element
   * @return
   */
  protected Object preprocessElement(Object element) {
    return element;
  }

}
