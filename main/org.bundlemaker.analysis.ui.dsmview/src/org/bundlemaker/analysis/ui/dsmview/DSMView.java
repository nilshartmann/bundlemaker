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

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.dependencies.DependencyEdge;
import org.bundlemaker.analysis.model.dependencies.DependencyGraph;
import org.bundlemaker.analysis.ui.editor.DependencyPart;
import org.eclipse.swt.widgets.Composite;

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
  public static String                            ID = "org.bundlemaker.analysis.ui.dsmview.DSMView";

  private static DSMView                          _instance;

  private DSMComposite<IArtifact, DependencyEdge> dsmComposite;

  @Override
  public void doInit(Composite parent) {
    _instance = this;
    dsmComposite = new DSMComposite<IArtifact, DependencyEdge>(parent, this);
  }

  @Override
  public void useDependencyGraph(DependencyGraph graph) {
    dsmComposite.setDependencyGraph(graph);
  }

  @Override
  public void doDispose() {
  }

  public static void showTab() {
    if (_instance != null) {
      _instance.selectViewTab();
    }

  }

}
