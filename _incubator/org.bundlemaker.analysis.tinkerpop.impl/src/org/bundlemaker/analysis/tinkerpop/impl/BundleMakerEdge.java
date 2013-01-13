/*******************************************************************************
 * Copyright (c) 2012 BundleMaker Project Team
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Nils Hartmann - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.analysis.tinkerpop.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.ExceptionFactory;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerEdge implements Edge {

  private final BundleMakerBlueprintsGraph _bundleMakerBlueprintsGraph;

  private final IDependency                _dependency;

  private final Object                     _id;

  BundleMakerEdge(BundleMakerBlueprintsGraph bundleMakerBlueprintsGraph, IDependency dependency) {

    this._bundleMakerBlueprintsGraph = checkNotNull(bundleMakerBlueprintsGraph);
    this._dependency = checkNotNull(dependency);

    this._id = dependency.getFrom().getQualifiedName() + "_" + dependency.getTo().getQualifiedName();
    this._dependency.setProperty("type", dependency.getDependencyKind().name().toLowerCase());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getId()
   */
  @Override
  public Object getId() {
    return _id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Edge#getVertex(com.tinkerpop.blueprints.Direction)
   */
  @Override
  public Vertex getVertex(Direction direction) throws IllegalArgumentException {

    if (direction == Direction.BOTH) {
      throw ExceptionFactory.bothIsNotSupported();
    }

    IBundleMakerArtifact artifact = null;
    // same "wrong" mapping as in com.tinkerpop.blueprints.impls.neo4j.Neo4jEdge.getVertex(Direction)
    if (direction == Direction.IN) {
      artifact = _dependency.getTo();
    } else if (direction == Direction.OUT) {
      artifact = _dependency.getFrom();
    }

    return _bundleMakerBlueprintsGraph.getVertexForArtifact(artifact);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Edge#getLabel()
   */
  @Override
  public String getLabel() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getProperty(java.lang.String)
   */
  @Override
  public Object getProperty(String key) {
    return _dependency.getProperty(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getPropertyKeys()
   */
  @Override
  public Set<String> getPropertyKeys() {
    return _dependency.getPropertyKeys();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#setProperty(java.lang.String, java.lang.Object)
   */
  @Override
  public void setProperty(String key, Object value) {
    _dependency.setProperty(key, value);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#removeProperty(java.lang.String)
   */
  @Override
  public Object removeProperty(String key) {
    return _dependency.removeProperty(key);
  }

}
