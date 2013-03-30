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

import java.util.Collections;
import java.util.Vector;

import javax.swing.JLabel;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class Layouts {
  /**
   * Creates a layout instance for the given identifier.
   */
  public static Vector<GraphLayout> createLayouts(final mxGraphComponent graphComponent) {

    Vector<GraphLayout> layouts = new Vector<GraphLayout>();
    mxGraph graph = graphComponent.getGraph();

    mxHierarchicalLayout hierarchicalLayout = new mxHierarchicalLayout(graph);
    layouts.add(new GraphLayout("Vertical Hierarchical", hierarchicalLayout));
    layouts.add(new GraphLayout("Horizontal Hierarchical", new mxHierarchicalLayout(graph, JLabel.WEST)));
    // layouts.add(new GraphLayout("Vertical Tree", new mxCompactTreeLayout(graph, false)));
    // layouts.add(new GraphLayout("Horizontal Tree", new mxCompactTreeLayout(graph, true)));
    // layouts.add(new GraphLayout("parallelEdges", new mxParallelEdgeLayout(graph)));
    // layouts.add(new GraphLayout("placeEdgeLabels", new mxEdgeLabelLayout(graph)));
    // layouts.add(new GraphLayout("organicLayout", new mxOrganicLayout(graph)));
    // layouts.add(new GraphLayout("Vertical Partition", new mxPartitionLayout(graph, false) {
    // @Override
    // public mxRectangle getContainerSize() {
    // return graphComponent.getLayoutAreaSize();
    // }
    // }));
    // layouts.add(new GraphLayout("Horizontal Partition", new mxPartitionLayout(graph, true) {
    // /**
    // * Overrides the empty implementation to return the size of the graph control.
    // */
    // @Override
    // public mxRectangle getContainerSize() {
    // return graphComponent.getLayoutAreaSize();
    // }
    // }));

    // layouts.add(new GraphLayout("Vertical Stack", new mxStackLayout(graph, false) {
    // @Override
    // public mxRectangle getContainerSize() {
    // return graphComponent.getLayoutAreaSize();
    // }
    // }));
    //
    // layouts.add(new GraphLayout("Horizontal Stack", new mxStackLayout(graph, true) {
    // /**
    // * Overrides the empty implementation to return the size of the graph control.
    // */
    // @Override
    // public mxRectangle getContainerSize() {
    // return graphComponent.getLayoutAreaSize();
    // }
    // }));

    mxCircleLayout circleLayout = new mxCircleLayout(graph);
    circleLayout.setResetEdges(true);
    layouts.add(new GraphLayout("Circle Layout", circleLayout));

    Collections.sort(layouts);

    return layouts;
  }
}
