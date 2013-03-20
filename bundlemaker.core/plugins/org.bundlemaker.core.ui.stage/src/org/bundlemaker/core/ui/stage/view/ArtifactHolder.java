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

import static com.google.common.base.Preconditions.checkState;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ArtifactHolder {

  private final static Object[]      EMPTY_CHILDREN = new Object[0];

  private final IBundleMakerArtifact _artifact;

  private ArtifactHolder             _parent;

  private List<ArtifactHolder>       _children;

  public ArtifactHolder(IBundleMakerArtifact theArtifact) {
    _artifact = theArtifact;
  }

  /**
   * @param parentHolder
   */
  public void setParent(ArtifactHolder parentHolder) {
    checkState(_parent == null, "Parent already set to: " + _parent);

    _parent = parentHolder;
  }

  public void addChild(ArtifactHolder holder) {
    if (_children == null) {
      _children = new LinkedList<ArtifactHolder>();
    }
    _children.add(holder);
  }

  public boolean hasChildren() {
    return _children != null && !_children.isEmpty();
  }

  public ArtifactHolder getParent() {
    return _parent;
  }

  /**
   * @return the artifact
   */
  public IBundleMakerArtifact getArtifact() {
    return _artifact;
  }

  private Object[] _childrenArray;

  public Object[] getChildren() {
    if (_childrenArray == null && _children != null) {
      _childrenArray = _children.toArray(new ArtifactHolder[_children.size()]);
    } else {
      _childrenArray = EMPTY_CHILDREN;
    }

    return _childrenArray;
  }

  @Override
  public String toString() {
    return _artifact.getName();
  }

}
