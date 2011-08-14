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
package org.bundlemaker.analysis.ui.xref.views;

import java.util.Collection;

import org.bundlemaker.analysis.model.IArtifact;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Tree Content provider for IArtifact models.
 * 
 * @author Frank Schlueter
 */
public class ArtifactTreeContentProvider implements ITreeContentProvider {
  private final Object[] EMPTY_ARRAY = new Object[0];

  @Override
  public void dispose() {
  }

  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
  }

  @Override
  @SuppressWarnings("unchecked")
  public Object[] getElements(Object inputElement) {
    if (inputElement instanceof IArtifact) {
      IArtifact artifact = (IArtifact) inputElement;
      Object[] childs = new Object[artifact.getChildren().size()];
      artifact.getChildren().toArray(childs);
      return childs;
    } else if (inputElement instanceof Collection) {
      @SuppressWarnings("rawtypes")
      Collection collection = (Collection) inputElement;
      Object[] elements = new Object[collection.size()];
      collection.toArray(elements);

      return elements;
    }
    return null;
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (parentElement instanceof IArtifact) {
      IArtifact artifact = (IArtifact) parentElement;
      Object[] elements = new Object[artifact.getChildren().size()];
      artifact.getChildren().toArray(elements);

      return elements;
    }
    return EMPTY_ARRAY;
  }

  @Override
  public Object getParent(Object element) {
    if (element instanceof IArtifact) {
      return ((IArtifact) element).getParent();
    }
    return null;
  }

  @Override
  public boolean hasChildren(Object element) {
    if (element instanceof IArtifact) {
      IArtifact artifact = (IArtifact) element;
      return artifact.getChildren().size() > 0;
    }
    return false;
  }
}
