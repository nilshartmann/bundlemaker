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

package org.bundlemaker.core.ui.view.stage.view;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactStageContentProvider extends ArtifactTreeContentProvider {

  // private final static Object[] EMPTY_CHILDREN = new Object[0];

  private final Map<IBundleMakerArtifact, ArtifactHolder> EMPTY_CACHE    = new HashMap<IBundleMakerArtifact, ArtifactHolder>();

  private Map<IBundleMakerArtifact, ArtifactHolder>       _childrenCache = EMPTY_CACHE;

  public ArtifactStageContentProvider() {
    super(true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
   * java.lang.Object, java.lang.Object)
   */
  @Override
  public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    super.inputChanged(viewer, oldInput, newInput);

    if (newInput instanceof IRootArtifact) {
      IRootArtifact virtualRoot = getVirtualRoot();
      IRootArtifact rootArtifact = (IRootArtifact) newInput;

      ArtifactHolder rootHolder = _childrenCache.remove(rootArtifact);
      _childrenCache.put(virtualRoot, rootHolder);
    }
  }

  @Override
  public boolean hasChildren(Object element) {
    if (!(element instanceof IBundleMakerArtifact)) {
      return super.hasChildren(element);
    }

    ArtifactHolder artifactHolder = _childrenCache.get(element);

    return artifactHolder.hasChildren();
  }

  @Override
  public Object[] getElements(Object inputElement) {
    return super.getElements(inputElement);
  }

  @Override
  public Object[] getChildren(Object parentElement) {
    if (!(parentElement instanceof IBundleMakerArtifact)) {
      return super.getChildren(parentElement);
    }

    ArtifactHolder artifactHolder = _childrenCache.get(parentElement);
    return artifactHolder.getChildren();
  }

  /**
   * @param childrenCache
   *          the childrenCache to set
   */
  public void setChildrenCache(Map<IBundleMakerArtifact, ArtifactHolder> childrenCache) {
    _childrenCache = (childrenCache == null ? EMPTY_CACHE : childrenCache);
  }
}
