/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.tutorial.dependencyviewer;

import org.bundlemaker.analysis.model.IDependency;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class DependencyViewerContentProvider extends ArrayContentProvider implements
    IGraphEntityRelationshipContentProvider {

  private final static Object[]       EMPTY_RELATION = new Object[0];

  private final DependencyViewerModel _model;

  public DependencyViewerContentProvider(DependencyViewerModel model) {
    this._model = model;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider#getRelationships(java.lang.Object,
   * java.lang.Object)
   */
  @Override
  public Object[] getRelationships(Object source, Object dest) {

    System.out.println("getRelationships source: " + source + ", dest: " + dest);

    if (source == dest) {
      return EMPTY_RELATION;
    }

    IBundleMakerArtifact from = (IBundleMakerArtifact) source;
    IBundleMakerArtifact to = (IBundleMakerArtifact) dest;

    if (!_model.getArtifacts().contains(to)) {
      return EMPTY_RELATION;
    }

    return from.getDependencies(to).toArray(new IDependency[0]);
  }

  // /*
  // * (non-Javadoc)
  // *
  // * @see org.eclipse.zest.core.viewers.IGraphEntityContentProvider#getConnectedTo(java.lang.Object)
  // */
  // @Override
  // public Object[] getConnectedTo(Object entity) {
  //
  // IBundleMakerArtifact artifact = (IBundleMakerArtifact) entity;
  //
  // System.out.println(" getConnectedTo '" + artifact.getQualifiedName() + "'");
  // Collection<? extends IDependency> dependencies = artifact.getDependencies(_selectedArtifacts);
  //
  // List<IBundleMakerArtifact> connectedTo = new LinkedList<IBundleMakerArtifact>();
  //
  // for (IDependency iDependency : dependencies) {
  // IBundleMakerArtifact to = iDependency.getTo();
  // connectedTo.add(to);
  // }
  //
  // return connectedTo.toArray(new IBundleMakerArtifact[0]);
  // }
  //

}
