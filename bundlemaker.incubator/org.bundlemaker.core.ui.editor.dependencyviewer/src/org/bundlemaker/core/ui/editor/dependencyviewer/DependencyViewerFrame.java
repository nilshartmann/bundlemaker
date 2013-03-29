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

package org.bundlemaker.core.ui.editor.dependencyviewer;

import java.awt.Frame;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.view.mxGraph;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerFrame {

  private mxGraphComponent _graphComponent;

  private mxGraph          _graph;

  private mxIGraphLayout   _graphLayout;

  public void create(Frame parentFrame) {
    _graph = new mxGraph();

    _graph.setCellsDisconnectable(false);
    _graph.setConnectableEdges(false);
    _graph.setCellsBendable(false);
    _graph.setCellsEditable(false);

    _graphLayout = new mxCircleLayout(_graph);

    _graphComponent = new mxGraphComponent(_graph);
    _graphComponent.setConnectable(false);
    _graphComponent.addMouseWheelListener(_wheelTracker);
    parentFrame.add(_graphComponent);
  }

  Map<IBundleMakerArtifact, Object> vertexCache = new Hashtable<IBundleMakerArtifact, Object>();

  /**
   * @param effectiveSelectedArtifacts
   */
  public void showArtifacts(List<IBundleMakerArtifact> effectiveSelectedArtifacts) {
    Object parent = _graph.getDefaultParent();

    mxIGraphModel model = _graph.getModel();
    model.beginUpdate();

    try {
      Iterator<Entry<IBundleMakerArtifact, Object>> iterator = vertexCache.entrySet().iterator();

      while (iterator.hasNext()) {
        Entry<IBundleMakerArtifact, Object> entry = iterator.next();
        if (!effectiveSelectedArtifacts.contains(entry.getKey())) {
          model.remove(entry.getValue());
          iterator.remove();
        }
      }

      for (IBundleMakerArtifact iBundleMakerArtifact : effectiveSelectedArtifacts) {
        System.out.println("Add iBundleMakerArtifact: " + iBundleMakerArtifact);
        if (!vertexCache.containsKey(iBundleMakerArtifact)) {
          Object vertex = _graph.insertVertex(parent, null, iBundleMakerArtifact.getName(), 10, 10, 10, 10);
          _graph.updateCellSize(vertex);
          vertexCache.put(iBundleMakerArtifact, vertex);
        }
      }

      for (IBundleMakerArtifact from : effectiveSelectedArtifacts) {
        Collection<IDependency> dependenciesTo = from.getDependenciesTo(effectiveSelectedArtifacts);
        Object fromVertex = vertexCache.get(from);
        for (IDependency iDependency : dependenciesTo) {
          IBundleMakerArtifact to = iDependency.getTo();

          if (from.equals(to)) {
            continue;
          }

          String direction = from.getName() + " -> " + to.getName();

          if (!from.getDependenciesFrom(to).isEmpty()) {
            direction = "";
          }

          Object toVertex = vertexCache.get(to);
          _graph.insertEdge(parent, null, direction, fromVertex, toVertex);
        }
      }

      // _graph.insertEdge(parent, null, "Hello->World", v1, v2);
      // _graph.insertEdge(parent, null, "World->BundleMaker", v2, v3);
      // _graph.insertEdge(parent, null, "BundleMaker->Hello", v3, v1);

      _graphLayout.execute(_graph.getDefaultParent());

    } finally {
      model.endUpdate();
    }
  }

  MouseWheelListener _wheelTracker = new MouseWheelListener() {
                                     @Override
                                     public void mouseWheelMoved(MouseWheelEvent e) {
                                       if (e.getSource() instanceof mxGraphOutline || e.isControlDown()) {
                                         if (e.getWheelRotation() < 0) {
                                           _graphComponent.zoomIn();
                                         } else {
                                           _graphComponent.zoomOut();
                                         }

                                       }
                                     }
                                   };

}
