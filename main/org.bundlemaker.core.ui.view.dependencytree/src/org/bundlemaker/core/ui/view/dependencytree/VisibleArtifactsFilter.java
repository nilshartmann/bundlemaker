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
package org.bundlemaker.core.ui.view.dependencytree;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.ui.artifact.tree.ArtifactTreeContentProvider.VirtualRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * <p>
 * Filter for IArtifact trees. This filter gets a white list of IArtifacts given in the constructor. Only these
 * artifacts and their parent nodes are displayed in the tree, everything else is hidden.
 * </p>
 * 
 * @author Frank Schlueter
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VisibleArtifactsFilter extends ViewerFilter {

  private Set<IBundleMakerArtifact> _artifacts = new HashSet<IBundleMakerArtifact>();

  public VisibleArtifactsFilter(Collection<IBundleMakerArtifact> visibleArtifacts) {

    //
    this._artifacts.addAll(visibleArtifacts);

    //
    for (IBundleMakerArtifact dependency : visibleArtifacts) {
      IBundleMakerArtifact parent = dependency.getParent();
      while (parent != null) {
        this._artifacts.add((IBundleMakerArtifact) parent);
        parent = parent.getParent();
      }
    }
  }

  @Override
  public boolean select(Viewer viewer, Object parentElement, Object element) {
    if (element instanceof VirtualRoot) {
      return true;
    } else if (_artifacts.contains(element)) {
      return true;
    } else {
      return false;
    }
  }

}
