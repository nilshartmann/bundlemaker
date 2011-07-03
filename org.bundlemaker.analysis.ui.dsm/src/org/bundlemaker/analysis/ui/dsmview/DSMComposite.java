/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Kai Lehmann - initial API and implementation
 *     Bundlemaker project team - integration with BundleMaker Analysis UI
 ******************************************************************************/
package org.bundlemaker.analysis.ui.dsmview;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.ui.Analysis;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.IDependency;
import org.bundlemaker.dependencyanalysis.model.DependencyEdge;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.bundlemaker.dependencyanalysis.ui.editor.DependencyPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Slider;
import org.sonar.graph.Dsm;
import org.sonar.graph.Edge;

import de.kupzog.ktable.KTable;
import de.kupzog.ktable.KTableCellDoubleClickListener;
import de.kupzog.ktable.KTableCellSelectionListener;
import de.kupzog.ktable.SWTX;

/**
 * <p>
 * Der Cell-Renderer für KTable
 * 
 * <p>
 * Rendert die Zellen der Dependency Structure Matrix abhängig von der übergebebenen Zelle.
 * 
 * <p>
 * Unterschieden wird zwischen Column und Rowheader in Form von uebergebenen Artefakten sowie den Abhaengigkeiten
 * zwischen den Zellen.
 * 
 * @author Kai Lehmann
 * @author Nils Hartmann
 * 
 */
public class DSMComposite<V, E extends Edge<V>> extends Composite {

  private Dsm<IArtifact>  dsm;

  private DependencyGraph graph;

  private KTable          kTable;

  private IDependency[][] dependencies;

  private DependencyPart  dependencyPart;

  private Slider          _zoomSlider;

  public DSMComposite(Composite parent, DependencyPart dependencyPart) {
    this(parent, SWT.CENTER | SWT.BORDER | SWT.FILL, dependencyPart);
  }

  public DSMComposite(Composite parent, int style, DependencyPart dependencyPart) {
    super(parent, style);

    this.dependencyPart = dependencyPart;
    setLayout(new GridLayout(1, true));
    createKTable();
    createSliders();

    registerListeners();

  }

  private void createSliders() {
    Composite sliderComposite = new Composite(this, SWT.NONE);
    sliderComposite.setLayout(new GridLayout(2, false));
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    sliderComposite.setLayoutData(gridData);

    Label label = new Label(sliderComposite, SWT.NONE);
    label.setText("Zoom:");

    _zoomSlider = new Slider(sliderComposite, SWT.HORIZONTAL);
    _zoomSlider.setMaximum(100);
    _zoomSlider.setMinimum(0);
    _zoomSlider.setIncrement(1);
    _zoomSlider.setPageIncrement(5);
    _zoomSlider.setSelection(50);

  }

  public void setDependencyGraph(DependencyGraph graph) {
    this.graph = graph;
    this.dsm = graph.getDsm();
    if (dsm != null) {
      generateMatrix();

    }
  }

  private void generateMatrix() {
    Object[] vertices = dsm.getVertices();
    IArtifact[] headers = new IArtifact[vertices.length];
    Map<IArtifact, Integer> artifactColumnMap = new HashMap<IArtifact, Integer>();

    dependencies = new IDependency[headers.length][headers.length];

    for (int i = 0; i < vertices.length; i++) {
      headers[i] = (IArtifact) vertices[i];
      artifactColumnMap.put(headers[i], i);
      for (int j = 0; j < headers.length; j++) {
        DependencyEdge dependencyEdge = (DependencyEdge) dsm.getCell(i, j).getEdge();
        dependencies[i][j] = dependencyEdge != null ? dependencyEdge.getDependency() : null;
      }
    }
    if (graph.getIgnoredDependencies() != null) {
      for (IDependency ignoredDependency : graph.getIgnoredDependencies()) {
        int row = artifactColumnMap.get(ignoredDependency.getFrom());
        int col = artifactColumnMap.get(ignoredDependency.getTo());
        dependencies[row][col] = ignoredDependency;
        // System.out.println("IgnoredDependency[" + row + "," + col + "]: " + ignoredDependency.getWeight());
      }
    }

    DSMTableModel tableModel = new DSMTableModel(dependencies, headers);

    kTable.setModel(tableModel);

  }

  private void createKTable() {
    GridData gridData = new GridData();
    gridData.horizontalAlignment = GridData.FILL;
    gridData.verticalAlignment = GridData.FILL;
    gridData.grabExcessHorizontalSpace = true;
    gridData.grabExcessVerticalSpace = true;

    kTable = new KTable(this, SWT.V_SCROLL | SWT.H_SCROLL | SWTX.ALIGN_HORIZONTAL_LEFT | SWTX.ALIGN_VERTICAL_BOTTOM
        | SWT.HIDE_SELECTION);
    kTable.setLayoutData(gridData);

  }

  private void registerListeners() {
    kTable.addCellDoubleClickListener(new KTableCellDoubleClickListener() {

      @Override
      public void fixedCellDoubleClicked(int col, int row, int statemask) {
      }

      @Override
      public void cellDoubleClicked(int col, int row, int statemask) {
        DependencyEdge dependency = (DependencyEdge) dsm.getCell(col - 1, row - 1).getEdge();
        IArtifact from = dependency.getFrom();
        IArtifact to = dependency.getTo();

        Collection<IArtifact> artifacts = new ArrayList<IArtifact>();
        artifacts.addAll(from.getChildren());
        artifacts.addAll(to.getChildren());

        DependencyGraph dependencyGraph = DependencyGraph.calculateDependencyGraph(artifacts);
        Analysis.instance().getContext().setDependencyGraph(dependencyGraph);

      }
    });

    kTable.addCellSelectionListener(new KTableCellSelectionListener() {

      @Override
      public void fixedCellSelected(int arg0, int arg1, int arg2) {
        // Nothing so far

      }

      @Override
      public void cellSelected(int col, int row, int statemask) {
        IDependency dependency = dependencies[col - 1][row - 1];

        if (dependency != null) {
          List<IDependency> dependencies = new ArrayList<IDependency>();
          List<IArtifact> fromArtifacts = new ArrayList<IArtifact>();
          List<IArtifact> toArtifacts = new ArrayList<IArtifact>();

          addArtifacts(dependency.getFrom(), fromArtifacts);
          addArtifacts(dependency.getTo(), toArtifacts);

          for (IArtifact from : fromArtifacts) {
            dependencies.addAll(from.getDependencies(toArtifacts));
          }
          String[] columnNames = { "From: " + dependency.getFrom().getName(), "To: " + dependency.getTo().getName(),
              "Weight: " + dependency.getWeight() };
          dependencyPart.changeTable(dependencies, columnNames);
        }
      }

      private void addArtifacts(IArtifact artifact, List<IArtifact> artifacts) {
        if (artifact.getChildren().isEmpty()) {
          artifacts.add(artifact);
        } else {
          artifacts.addAll(artifact.getChildren());
        }
      }

    });

    _zoomSlider.addListener(SWT.Selection, new Listener() {

      @Override
      public void handleEvent(Event event) {
        int value = _zoomSlider.getSelection() - 50;
        float d = (float) value / 10;

        DSMTableModel dsmTableModel = (DSMTableModel) kTable.getModel();
        dsmTableModel.setZoomFactor(d);
        kTable.redraw();
      }
    });

  }

}
