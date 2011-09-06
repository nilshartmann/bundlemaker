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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.analysis.model.dependencies.DependencyEdge;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.sonar.graph.Dsm;

/**
 * <p>
 * Die DSM-View
 * 
 * <p>
 * Stellt den selektierten Abhaengigkeitsgraphen in Form einer Dependency Structure Matrix dar.
 * 
 * 
 * @author Kai Lehmann
 * 
 */
public class DSMView extends DependencyPart {

  //
  private static final boolean                    FEATURE_USE_NEW_DSM = Boolean.getBoolean("bm.useNewDsm");

  /**
   * This is used as the DSMView's providerId for the xxxSelectionServices
   */
  public static String                            ID                  = "org.bundlemaker.analysis.ui.dsmview.DSMView";

  private DSMComposite<IArtifact, DependencyEdge> dsmComposite;

  @Override
  public void doDispose() {
    if (FEATURE_USE_NEW_DSM) {
      /**
       * @todo dispose _dsmViewWidget
       * 
       */
    } else {
      dsmComposite.dispose();
    }
  }

  @Override
  protected void doInit(Composite composite) {
    if (FEATURE_USE_NEW_DSM) {
      doInitNew(composite);
    } else {
      doInitOld(composite);
    }
  }

  @Override
  protected void useArtifacts(List<IArtifact> artifacts) {
    super.useArtifacts(artifacts);
    useDependencyGraph(getDependencyGraphForCurrentArtifacts());
  }

  // /**
  // * <p>
  // * Will be invoked when this DependencyPart is going to be opened
  // * </p>
  // *
  // * <p>
  // * Subclasses can implemented this method to update their displays
  // *
  // */
  // @Override
  // public void onShow() {
  // System.out.println("DSMView - on show");
  // useDependencyGraph(getDependencyGraphForCurrentArtifacts());
  // }

  protected void useDependencyGraph(DependencyGraph graph) {

    if (FEATURE_USE_NEW_DSM) {
      useDependencyGraphNew(graph);
    } else {
      useDependencyGraphOld(graph);
    }
  }

  /************ START - OLD DsmView ***************************/

  public void doInitOld(Composite parent) {
    dsmComposite = new DSMComposite<IArtifact, DependencyEdge>(parent, this);
  }

  public void useDependencyGraphOld(DependencyGraph graph) {
    dsmComposite.setDependencyGraph(graph);
  }

  /************ STOP - OLD DsmView ***************************/

  /************ START - NEW DsmView ***************************/

  private DsmViewWidget   _dsmViewWidget;

  private DependencyGraph _graph;

  private Dsm<IArtifact>  _dsm;

  private IDependency[][] _dependencies;

  public void doInitNew(Composite parent) {
    _dsmViewWidget = new DsmViewWidget(new DsmViewModel(), parent);
  }

  public void useDependencyGraphNew(DependencyGraph graph) {

    this._graph = graph;

    this._dsm = graph.getDsm();

    if (_dsm != null) {
      generateMatrix();
    } else {
      MessageBox msgBox = new MessageBox(Display.getCurrent().getActiveShell());
      msgBox.setMessage("Too many artifacts for DSM view.");
      msgBox.open();
    }
  }

  private void generateMatrix() {
    Object[] vertices = _dsm.getVertices();
    IArtifact[] headers = new IArtifact[vertices.length];
    Map<IArtifact, Integer> artifactColumnMap = new HashMap<IArtifact, Integer>();

    _dependencies = new IDependency[headers.length][headers.length];

    for (int i = 0; i < vertices.length; i++) {
      headers[i] = (IArtifact) vertices[i];
      artifactColumnMap.put(headers[i], i);
      for (int j = 0; j < headers.length; j++) {
        DependencyEdge dependencyEdge = (DependencyEdge) _dsm.getCell(i, j).getEdge();
        _dependencies[j][i] = dependencyEdge != null ? dependencyEdge.getDependency() : null;
      }
    }

    // if (_graph.getIgnoredDependencies() != null) {
    // for (IDependency ignoredDependency : _graph.getIgnoredDependencies()) {
    // int row = artifactColumnMap.get(ignoredDependency.getFrom());
    // int col = artifactColumnMap.get(ignoredDependency.getTo());
    // _dependencies[row][col] = ignoredDependency;
    // // System.out.println("IgnoredDependency[" + row + "," + col + "]: " + ignoredDependency.getWeight());
    // }
    // }

    DsmViewModel tableModel = new DsmViewModel(headers, _dependencies);

    _dsmViewWidget.setModel(tableModel);
  }

  /************ END - NEW DsmView ***************************/
}
