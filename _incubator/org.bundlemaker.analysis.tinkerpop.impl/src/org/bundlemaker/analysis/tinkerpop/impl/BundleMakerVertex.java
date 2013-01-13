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

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.DefaultQuery;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerVertex implements Vertex {

  private final BundleMakerBlueprintsGraph _bundleMakerBlueprintsGraph;

  private final IBundleMakerArtifact       _bundleMakerArtifact;

  BundleMakerVertex(BundleMakerBlueprintsGraph bundleMakerBlueprintsGraph, IBundleMakerArtifact artifact) {
    _bundleMakerBlueprintsGraph = checkNotNull(bundleMakerBlueprintsGraph);
    _bundleMakerArtifact = checkNotNull(artifact);
    _bundleMakerArtifact.setProperty("qname", artifact.getQualifiedName());
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getProperty(java.lang.String)
   */
  @Override
  public Object getProperty(String key) {
    return _bundleMakerArtifact.getProperty(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getPropertyKeys()
   */
  @Override
  public Set<String> getPropertyKeys() {

    return Sets.newHashSet(Iterables.transform(_bundleMakerArtifact.getPropertyKeys(), new Function<Object, String>() {
      @Override
      public String apply(Object o) {
        return String.valueOf(o);
      }
    }));

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#setProperty(java.lang.String, java.lang.Object)
   */
  @Override
  public void setProperty(String key, Object value) {
    _bundleMakerArtifact.setProperty(key, value);

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#removeProperty(java.lang.String)
   */
  @Override
  public Object removeProperty(String key) {

    return _bundleMakerArtifact.getProperty(key);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Element#getId()
   */
  @Override
  public Object getId() {
    return _bundleMakerArtifact.getQualifiedName();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Vertex#getEdges(com.tinkerpop.blueprints.Direction, java.lang.String[])
   */
  @Override
  public Iterable<Edge> getEdges(Direction direction, String... labels) {
    Iterable<IDependency> iterable = null;

    switch (direction) {
    case IN:
      iterable = _bundleMakerArtifact.getDependenciesFrom();
      break;
    case OUT:
      iterable = _bundleMakerArtifact.getDependenciesTo();
      break;
    case BOTH:
      iterable = Iterables.concat(_bundleMakerArtifact.getDependenciesFrom(), _bundleMakerArtifact.getDependenciesTo());
      break;
    }

    if (labels == null || labels.length == 0) {
      return _bundleMakerBlueprintsGraph.toEdgeIterable(iterable);
    }

    throw new UnsupportedOperationException("getEdges with labels (" + Joiner.on(",").join(labels).toString()
        + ") not supported");

    // @SuppressWarnings("unchecked")
    // Iterable<Edge> result =(Iterable<Edge>) Iterables.filter(iterable, new Predicate<Edge>() {
    // public boolean apply(Edge e) {
    // for (String label : labels) {
    // if (label.equals(e.getLabel())){
    // return true;
    // }
    // }
    // return false;
    // }
    //
    // });
    //
    // return result;

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Vertex#getVertices(com.tinkerpop.blueprints.Direction, java.lang.String[])
   */
  @Override
  public Iterable<Vertex> getVertices(Direction direction, String... labels) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Vertex#query()
   */
  @Override
  public Query query() {
    return new DefaultQuery(this);
  }
}
