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

import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.model.DependencyEdge;
import org.bundlemaker.dependencyanalysis.model.DependencyGraph;
import org.bundlemaker.dependencyanalysis.ui.editor.DependencyPart;
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

  private DSMComposite<IArtifact, DependencyEdge> dsmComposite;

  @Override
  public void doInit(Composite parent) {
    dsmComposite = new DSMComposite<IArtifact, DependencyEdge>(parent, this);
  }

  @Override
  public void useDependencyGraph(DependencyGraph graph) {
    dsmComposite.setDependencyGraph(graph);
  }

  @Override
  public void doDispose() {
  }

}
