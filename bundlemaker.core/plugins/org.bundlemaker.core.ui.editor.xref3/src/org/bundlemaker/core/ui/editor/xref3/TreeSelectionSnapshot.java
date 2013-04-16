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

package org.bundlemaker.core.ui.editor.xref3;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;

class TreeSelectionSnapshot {
  /**
   * 
   */

  private final List<TreeSelection> _treeSelections = new LinkedList<TreeSelectionSnapshot.TreeSelection>();

  /**
   * 
   */
  public TreeSelectionSnapshot() {
  }

  public void reselect() {

    for (TreeSelection treeSelection : _treeSelections) {
      treeSelection.reselect();
    }

  }

  class TreeSelection {
    private final TreeViewer _source;

    private final ISelection _selection;

    TreeSelection(TreeViewer viewer, ISelection selection) {
      this._source = viewer;
      this._selection = selection;
    }

    public void reselect() {
      _source.setSelection(_selection);
    }
  }

  /**
   * @param treeViewer
   * @param b
   */
  public void addSelection(TreeViewer treeViewer, boolean currentTree) {

    TreeSelection selection = new TreeSelection(treeViewer, treeViewer.getSelection());

    if (!currentTree) {
      _treeSelections.add(0, selection);
    } else {
      // current tree selection must be last in order to select it last if it's re-selected
      _treeSelections.add(selection);
    }

  }

}