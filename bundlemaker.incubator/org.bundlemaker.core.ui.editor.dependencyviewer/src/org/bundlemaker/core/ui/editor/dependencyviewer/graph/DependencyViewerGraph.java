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

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.selection.Selection;
import org.bundlemaker.core.selection.stage.ArtifactStageChangedEvent;
import org.bundlemaker.core.selection.stage.IArtifactStage;
import org.bundlemaker.core.selection.stage.IArtifactStageChangeListener;
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
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerGraph {

  private final static String                     BUNDLEMAKER_VERTEX_STYLE        = "BUNDLEMAKER_VERTEX";

  private final static String                     BUNDLEMAKER_EDGE_STYLE          = "BUNDLEMAKER_EDGE";

  private final static String                     BUNDLEMAKER_CIRCULAR_EDGE_STYLE = "BUNDLEMAKER_CIRCULAR_EDGE";

  private final Map<IBundleMakerArtifact, Object> _vertexCache                    = new Hashtable<IBundleMakerArtifact, Object>();

  private final EdgeCache                         _edgeCache                      = new EdgeCache();

  private mxGraphComponent                        _graphComponent;

  private mxGraph                                 _graph;

  private mxIGraphLayout                          _graphLayout;

  private Display                                 _display;

  private UnstageAction                           _unstageAction;

  /**
   * Should the graph be re-layouted after artifacts have been added or removed?
   */
  private boolean                                 _doLayoutAfterArtifactsChange   = true;

  public void create(Frame parentFrame, Display display) {

    _display = display;
    _graph = createGraph();

    registerStyles();

    // Layout
    _graphLayout = new mxCircleLayout(_graph);

    _graphComponent = new mxGraphComponent(_graph);
    _graphComponent.setConnectable(false);
    _graphComponent.setToolTips(true);
    _graphComponent.addMouseWheelListener(new ZoomMouseWheelListener());

    // Populate the frame
    parentFrame.setLayout(new BorderLayout());
    parentFrame.add(createToolBar(), BorderLayout.NORTH);
    parentFrame.add(_graphComponent, BorderLayout.CENTER);
  }

  protected JPanel createToolBar() {
    JPanel comboBoxPanel = new JPanel();

    // Layout Selector
    Vector<GraphLayout> layouts = Layouts.createLayouts(_graphComponent);
    final JComboBox comboBox = new JComboBox(layouts);
    comboBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        GraphLayout newLayout = (GraphLayout) comboBox.getSelectedItem();
        _graphLayout = newLayout.getLayout();
        DependencyViewerGraph.this.layoutGraph();
      }
    });
    comboBoxPanel.add(comboBox);

    // UnstageButton
    _unstageAction = new UnstageAction();
    comboBoxPanel.add(new JButton(_unstageAction));

    return comboBoxPanel;

  }

  public void dispose() {
    if (_unstageAction != null) {
      _unstageAction.dispose();
    }
  }

  protected mxGraph createGraph() {
    mxGraph graph = new mxGraph() {

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

    // listener for cell selection changes
    graph.getSelectionModel().addListener(mxEvent.CHANGE, new mxIEventListener() {

      @Override
      public void invoke(Object sender, mxEventObject evt) {
        cellSelectionChanged();
      }
    });

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

    // Base style for an Artifact
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

    // base style for a uni-directional dependency
    style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_STROKECOLOR, "#FFEAB2");
    style.put(mxConstants.STYLE_STROKEWIDTH, "1");
    style.put(mxConstants.STYLE_NOLABEL, "1");
    stylesheet.putCellStyle("BUNDLEMAKER_EDGE", style);

    // base style for a circular dependency
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
          style += ";imageVerticalAlign=center;fontStyle=1;verticalAlign=top;spacingLeft=" + (bounds.width + 15)
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

      if (_doLayoutAfterArtifactsChange) {
        layoutGraph();
      }

    } finally {
      model.endUpdate();
    }
  }

  /**
   * Handles changes of the cell (edges or vertex) selection
   */
  protected void cellSelectionChanged() {

    Object[] cells = _graph.getSelectionCells();

    final List<IBundleMakerArtifact> selectedArtifacts = new LinkedList<IBundleMakerArtifact>();
    final List<IDependency> selectedDependencies = new LinkedList<IDependency>();

    // collect selected artifacts and dependencies
    for (Object cell : cells) {

      Object value = _graph.getModel().getValue(cell);

      if (value instanceof IDependency) {
        IDependency dependency = (IDependency) value;
        selectedDependencies.add(dependency);
      } else if (value instanceof IBundleMakerArtifact) {
        IBundleMakerArtifact bundleMakerArtifact = (IBundleMakerArtifact) value;
        selectedArtifacts.add(bundleMakerArtifact);
      }
    }

    // propagate selected dependencies
    if (!selectedDependencies.isEmpty()) {
      runInSwt(new Runnable() {

        @Override
        public void run() {
          Selection
              .instance()
              .getDependencySelectionService()
              .setSelection(Selection.MAIN_DEPENDENCY_SELECTION_ID, DependencyViewerEditor.DEPENDENCY_VIEWER_EDITOR_ID,
                  selectedDependencies);
        }
      });
    }

    //
    _unstageAction.setUnstageCandidates(selectedArtifacts);

  }

  /**
   * (Re-)layouts the whole graph.
   * 
   * <p>
   * This methods uses the currently selected layout. It executes the layout regardless of the current
   * {@link #_doLayoutAfterArtifactsChange} setting
   */
  protected void layoutGraph() {
    _graph.getModel().beginUpdate();
    try {
      _graphLayout.execute(_graph.getDefaultParent());
    } finally {
      _graph.getModel().endUpdate();
    }
  }

  /**
   * @return the {@link IArtifactStage}
   */
  protected IArtifactStage getArtifactStage() {
    return Selection.instance().getArtifactStage();
  }

  /**
   * Runs the specified {@link Runnable} on the SWT Thread
   */
  protected void runInSwt(final Runnable runnable) {
    _display.asyncExec(runnable);
  }

  class UnstageAction extends AbstractAction implements Runnable, IArtifactStageChangeListener {

    private static final long          serialVersionUID = 1L;

    private List<IBundleMakerArtifact> _unstageCandidates;

    public UnstageAction() {
      super("Unstage");

      getArtifactStage().addArtifactStageChangeListener(this);

      refreshEnablement();
    }

    /**
     * @param selectedArtifacts
     */
    public void setUnstageCandidates(List<IBundleMakerArtifact> selectedArtifacts) {
      _unstageCandidates = selectedArtifacts;

      refreshEnablement();
    }

    public void dispose() {
      getArtifactStage().removeArtifactStageChangeListener(this);
      _unstageCandidates = null;
    }

    protected void refreshEnablement() {
      if (getArtifactStage().getAddMode().isAutoAddMode()) {
        setEnabled(false);
        return;
      }

      setEnabled(_unstageCandidates != null && _unstageCandidates.size() > 0);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

      // Changes in Stage are processed in SWT, so make sure, we're on the SWT thread
      runInSwt(this);
    }

    @Override
    public void run() {
      try {
        DependencyViewerGraph.this._doLayoutAfterArtifactsChange = false;

        getArtifactStage().removeStagedArtifacts(_unstageCandidates);
      } finally {
        DependencyViewerGraph.this._doLayoutAfterArtifactsChange = true;

      }
    }

    @Override
    public void artifactStateChanged(ArtifactStageChangedEvent event) {
      refreshEnablement();
    }
  }

  /**
   * Zoom in/out using the mouse wheel while pressing the ctrl-key
   */
  class ZoomMouseWheelListener implements MouseWheelListener {
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

  class EdgeCache extends GenericCache<IBundleMakerArtifact, List<Object>> {

    private static final long serialVersionUID = 1L;

    @Override
    protected List<Object> create(IBundleMakerArtifact key) {
      return new LinkedList<Object>();
    }
  };

}
