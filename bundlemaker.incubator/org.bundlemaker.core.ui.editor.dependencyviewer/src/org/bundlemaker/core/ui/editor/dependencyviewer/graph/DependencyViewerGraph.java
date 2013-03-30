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

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.ui.artifact.ArtifactImages;
import org.bundlemaker.core.ui.editor.dependencyviewer.DependencyViewerEditor;
import org.bundlemaker.core.util.collections.GenericCache;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerGraph {

  private final static String                                    BUNDLEMAKER_VERTEX_STYLE        = "BUNDLEMAKER_VERTEX";

  private final static String                                    BUNDLEMAKER_EDGE_STYLE          = "BUNDLEMAKER_EDGE";

  private final static String                                    BUNDLEMAKER_CIRCULAR_EDGE_STYLE = "BUNDLEMAKER_CIRCULAR_EDGE";

  private final Map<IBundleMakerArtifact, Object>                _vertexCache                    = new Hashtable<IBundleMakerArtifact, Object>();

  private final GenericCache<IBundleMakerArtifact, List<Object>> _edgeCache                      = new GenericCache<IBundleMakerArtifact, List<Object>>() {

                                                                                                   @Override
                                                                                                   protected List<Object> create(
                                                                                                       IBundleMakerArtifact key) {
                                                                                                     return new LinkedList<Object>();
                                                                                                   }
                                                                                                 };

  private mxGraphComponent                                       _graphComponent;

  private mxGraph                                                _graph;

  private mxIGraphLayout                                         _graphLayout;

  private Display                                                _display;

  public void create(Frame parentFrame, Display display) {

    _display = display;
    _graph = createGraph();

    registerStyles();

    // Layout
    _graphLayout = new mxCircleLayout(_graph);

    _graphComponent = new mxGraphComponent(_graph);
    _graphComponent.setConnectable(false);
    _graphComponent.setToolTips(true);
    _graphComponent.addMouseWheelListener(_wheelTracker);

    _graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

      @Override
      public void mouseReleased(MouseEvent e) {
        Object cell = _graphComponent.getCellAt(e.getX(), e.getY());

        if (cell != null) {
          Object value = _graph.getModel().getValue(cell);
          if (value instanceof IDependency) {
            final IDependency dependency = (IDependency) value;
            _display.syncExec(new Runnable() {

              @Override
              public void run() {
                Selection
                    .instance()
                    .getDependencySelectionService()
                    .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID,
                        DependencyViewerEditor.DEPENDENCY_VIEWER_EDITOR_ID, dependency);
              }
            });
          }
        }
      }
    });

    parentFrame.setLayout(new BorderLayout());

    parentFrame.add(createToolBar(), BorderLayout.NORTH);

    parentFrame.add(_graphComponent, BorderLayout.CENTER);
  }

  protected JPanel createToolBar() {
    JPanel panel = new JPanel();

    Vector<GraphLayout> layouts = Layouts.createLayouts(_graphComponent);

    final JComboBox comboBox = new JComboBox(layouts);
    comboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        GraphLayout newLayout = (GraphLayout) comboBox.getSelectedItem();
        System.out.println("newLayout: " + newLayout);
        _graphLayout = newLayout.getLayout();
        DependencyViewerGraph.this.layoutGraph();
      }
    });
    panel.add(comboBox);
    // mxImageBundle bundle = new mxImageBundle();
    // JButton button = new JButton(new AbstractAction("Selection") {
    //
    // @Override
    // public void actionPerformed(ActionEvent arg0) {
    // Object[] selectionCells = _graph.getSelectionCells();
    // System.out.println("Selection Cells:");
    // if (selectionCells != null) {
    // for (Object object : selectionCells) {
    // System.out.println(" * " + object);
    // }
    // }
    // }
    //
    // });
    // panel.add(button);

    return panel;

  }

  protected mxGraph createGraph() {
    mxGraph graph = new mxGraph() {

      /*
       * (non-Javadoc)
       * 
       * @see com.mxgraph.view.mxGraph#getToolTipForCell(java.lang.Object)
       */
      @Override
      public String getToolTipForCell(Object cell) {
        Object cellValue = model.getValue(cell);
        if (cellValue instanceof IBundleMakerArtifact) {
          return ((IBundleMakerArtifact) cellValue).getQualifiedName();
        }
        if (cellValue instanceof IDependency) {
          IDependency dependency = (IDependency) cellValue;

          String string = "<html>" + dependency.getFrom().getName() + " -> " + dependency.getTo() + ": "
              + dependency.getWeight();

          IDependency dependencyTo = dependency.getTo().getDependencyTo(dependency.getFrom());
          if (dependencyTo != null) {
            string += "<br/>" + dependencyTo.getFrom().getName() + " -> " + dependencyTo.getTo() + ": "
                + dependencyTo.getWeight();
          }

          return string + "</html>";
        }
        return super.getToolTipForCell(cell);
      }

      @Override
      public String convertValueToString(Object cell) {
        Object result = model.getValue(cell);

        if (result instanceof IBundleMakerArtifact) {
          return ((IBundleMakerArtifact) result).getName();
        }

        return super.convertValueToString(cell);
      }

    };

    // Configure Graph
    graph.setCellsDisconnectable(false);
    graph.setConnectableEdges(false);
    graph.setCellsBendable(false);
    graph.setCellsEditable(false);

    return graph;

  }

  protected void registerStyles() {
    // Styles
    mxStylesheet stylesheet = _graph.getStylesheet();

    Hashtable<String, Object> style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_LABEL);
    style.put(mxConstants.STYLE_IMAGE_ALIGN, mxConstants.ALIGN_LEFT);
    style.put(mxConstants.STYLE_OPACITY, 50);
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_FILLCOLOR, "#FFEAB2");
    style.put(mxConstants.STYLE_STROKECOLOR, "#C37D64");
    style.put(mxConstants.STYLE_STROKEWIDTH, "1");
    style.put(mxConstants.STYLE_FONTSIZE, "12");
    stylesheet.putCellStyle("BUNDLEMAKER_VERTEX", style);

    style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_STROKECOLOR, "#FFEAB2");
    style.put(mxConstants.STYLE_STROKEWIDTH, "1");
    style.put(mxConstants.STYLE_NOLABEL, "1");
    // style.put(mxConstants.STYLE_BENDABLE, "1");
    stylesheet.putCellStyle("BUNDLEMAKER_EDGE", style);

    style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_STROKECOLOR, "#B85E3D");
    style.put(mxConstants.STYLE_STROKEWIDTH, "1");
    style.put(mxConstants.STYLE_NOLABEL, "1");
    stylesheet.putCellStyle("BUNDLEMAKER_CIRCULAR_EDGE", style);

  }

  /**
   * @param effectiveSelectedArtifacts
   */
  public void showArtifacts(List<IBundleMakerArtifact> effectiveSelectedArtifacts) {
    Object parent = _graph.getDefaultParent();

    mxIGraphModel model = _graph.getModel();
    model.beginUpdate();

    try {
      Iterator<Entry<IBundleMakerArtifact, Object>> iterator = _vertexCache.entrySet().iterator();

      while (iterator.hasNext()) {
        Entry<IBundleMakerArtifact, Object> entry = iterator.next();
        IBundleMakerArtifact artifact = entry.getKey();
        if (!effectiveSelectedArtifacts.contains(artifact)) {
          // Artifact is not longer part of selection => remove it...

          // from model
          model.remove(entry.getValue());

          // from vertex edge cache
          iterator.remove();

          // from vertex cache
          List<Object> edges = _edgeCache.remove(artifact);
          if (edges != null) {
            for (Object edge : edges) {
              model.remove(edge);
            }
          }

        }
      }

      for (IBundleMakerArtifact iBundleMakerArtifact : effectiveSelectedArtifacts) {
        System.out.println("Add iBundleMakerArtifact: " + iBundleMakerArtifact);
        if (!_vertexCache.containsKey(iBundleMakerArtifact)) {

          String style = BUNDLEMAKER_VERTEX_STYLE;
          ArtifactImages image = ArtifactImages.forArtifact(iBundleMakerArtifact);
          style += ";image=" + image.getImageUrl();
          Rectangle bounds = image.getImage().getBounds();
          style += ";imageWidth=" + bounds.width;
          style += ";imageWidth=" + bounds.height;
          style += ";imageVerticalAlign=center;fontStyle=1;" + "verticalAlign=top;spacingLeft=" + (bounds.width + 15)
              + ";spacingTop=2;imageAlign=left;align=top;spacingRight=5"; // +

          Object vertex = _graph.insertVertex(parent, null, iBundleMakerArtifact, 10, 10, 10, 10, style);
          _graph.updateCellSize(vertex);
          _vertexCache.put(iBundleMakerArtifact, vertex);
        }
      }

      for (IBundleMakerArtifact from : effectiveSelectedArtifacts) {
        Collection<IDependency> dependenciesTo = from.getDependenciesTo(effectiveSelectedArtifacts);
        Object fromVertex = _vertexCache.get(from);
        for (IDependency iDependency : dependenciesTo) {
          IBundleMakerArtifact to = iDependency.getTo();

          if (from.equals(to)) {
            continue;
          }

          String style = from.getDependenciesFrom(to).isEmpty() ? BUNDLEMAKER_EDGE_STYLE
              : BUNDLEMAKER_CIRCULAR_EDGE_STYLE;

          Object toVertex = _vertexCache.get(to);
          Object newEdge = _graph.insertEdge(parent, null, iDependency, fromVertex, toVertex, style);
          _edgeCache.getOrCreate(to).add(newEdge);
          _edgeCache.getOrCreate(from).add(newEdge);
        }
      }

      layoutGraph();

    } finally {
      model.endUpdate();
    }
  }

  protected void layoutGraph() {
    _graph.getModel().beginUpdate();
    try {
      _graphLayout.execute(_graph.getDefaultParent());
    } finally {
      _graph.getModel().endUpdate();
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
