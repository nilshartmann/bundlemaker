/*******************************************************************************
 * Copyright (c) 2013 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/

package org.bundlemaker.core.ui.stage.view;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStageContentProvider implements ITreeContentProvider {

  private final static Object[] EMPTY_CHILDREN = new Object[0];

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

  }

  @Override
  public void dispose() {

  }

  @Override
  public boolean hasChildren(Object element) {
    ArtifactHolder holder = (ArtifactHolder) element;
    return holder.hasChildren();
  }

  @Override
  public Object getParent(Object element) {
    return ((ArtifactHolder) element).getParent();
  }

  @Override
  public Object[] getElements(Object inputElement) {
    if (inputElement instanceof ArtifactHolder) {
      return ((ArtifactHolder) inputElement).getChildren();
    }
    return EMPTY_CHILDREN;
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    return ((ArtifactHolder) parentElement).getChildren();
  }
}
