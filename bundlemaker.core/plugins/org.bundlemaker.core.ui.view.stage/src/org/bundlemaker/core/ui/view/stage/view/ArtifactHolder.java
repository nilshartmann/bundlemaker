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

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactHolder {

  private final static Object[]      EMPTY_CHILDREN = new Object[0];

  private List<IBundleMakerArtifact> _children;

  public ArtifactHolder() {
  }

  public void addChild(IBundleMakerArtifact holder) {
    if (_children == null) {
      _children = new LinkedList<IBundleMakerArtifact>();
    }
    _children.add(holder);
  }

  public boolean hasChildren() {
    return _children != null && !_children.isEmpty();
  }

  private Object[] _childrenArray;

  public Object[] getChildren() {
    if (_childrenArray == null && _children != null) {
      _childrenArray = _children.toArray(new IBundleMakerArtifact[_children.size()]);
    } else {
      _childrenArray = EMPTY_CHILDREN;
    }

    return _childrenArray;
  }

}
