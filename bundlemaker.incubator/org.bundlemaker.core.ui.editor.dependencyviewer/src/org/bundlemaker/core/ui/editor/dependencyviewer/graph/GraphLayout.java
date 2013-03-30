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

package org.bundlemaker.core.ui.editor.dependencyviewer.graph;

import static com.google.common.base.Preconditions.checkNotNull;

import com.mxgraph.layout.mxIGraphLayout;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class GraphLayout implements Comparable<GraphLayout> {

  private final String         _title;

  private final mxIGraphLayout _layout;

  public GraphLayout(String title, mxIGraphLayout layout) {
    _title = checkNotNull(title);
    _layout = checkNotNull(layout);
  }

  @Override
  public String toString() {
    return _title;
  }

  public mxIGraphLayout getLayout() {
    return this._layout;
  }

  @Override
  public int compareTo(GraphLayout o) {
    return _title.compareTo(o._title);
  }

}
