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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IDependency;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.util.collections.GenericCache;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerBlueprintsGraph implements Graph {

  private final IRootArtifact                                         _rootArtifact;

  private final GenericCache<IBundleMakerArtifact, BundleMakerVertex> _vertexCache                       = new GenericCache<IBundleMakerArtifact, BundleMakerVertex>() {

                                                                                                           private static final long serialVersionUID = 1L;

                                                                                                           @Override
                                                                                                           protected BundleMakerVertex create(
                                                                                                               IBundleMakerArtifact key) {
                                                                                                             return new BundleMakerVertex(
                                                                                                                 BundleMakerBlueprintsGraph.this,
                                                                                                                 key);
                                                                                                           }

                                                                                                         };

  private final GenericCache<IDependency, BundleMakerEdge>            _edgeCache                         = new GenericCache<IDependency, BundleMakerEdge>() {

                                                                                                           @Override
                                                                                                           protected BundleMakerEdge create(
                                                                                                               IDependency key) {
                                                                                                             return new BundleMakerEdge(
                                                                                                                 BundleMakerBlueprintsGraph.this,
                                                                                                                 key);
                                                                                                           }

                                                                                                         };

  private final Function<IDependency, Edge>                           _dependencyToEdgeConverterFunction = new Function<IDependency, Edge>() {
                                                                                                           @Override
                                                                                                           public Edge apply(
                                                                                                               IDependency dependency) {
                                                                                                             return _edgeCache
                                                                                                                 .getOrCreate(dependency);
                                                                                                           }
                                                                                                         };

  public BundleMakerBlueprintsGraph(IRootArtifact rootArtifact) {
    this._rootArtifact = checkNotNull(rootArtifact);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getFeatures()
   */
  @Override
  public Features getFeatures() {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#addVertex(java.lang.Object)
   */
  @Override
  public Vertex addVertex(Object id) {
    throw new UnsupportedOperationException("addVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertex(java.lang.Object)
   */
  @Override
  public Vertex getVertex(Object id) {
    throw new UnsupportedOperationException("getVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeVertex(com.tinkerpop.blueprints.Vertex)
   */
  @Override
  public void removeVertex(Vertex vertex) {
    throw new UnsupportedOperationException("removeVertex");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertices()
   */
  @Override
  public Iterable<Vertex> getVertices() {

    final List<Vertex> result = new LinkedList<Vertex>();

    _rootArtifact.accept(new IAnalysisModelVisitor.Adapter() {

      /*
       * (non-Javadoc)
       * 
       * @see org.bundlemaker.core.analysis.IAnalysisModelVisitor.Adapter#onVisit(org.bundlemaker.core.analysis.
       * IBundleMakerArtifact)
       */
      @Override
      public boolean onVisit(IBundleMakerArtifact artifact) {
        BundleMakerVertex bundleMakerVertex = getVertexForArtifact(artifact);
        result.add(bundleMakerVertex);
        return true;
      }
    });

    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertices(java.lang.String, java.lang.Object)
   */
  @Override
  public Iterable<Vertex> getVertices(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#addEdge(java.lang.Object, com.tinkerpop.blueprints.Vertex,
   * com.tinkerpop.blueprints.Vertex, java.lang.String)
   */
  @Override
  public Edge addEdge(Object id, Vertex outVertex, Vertex inVertex, String label) {
    throw new UnsupportedOperationException("addEdge");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdge(java.lang.Object)
   */
  @Override
  public Edge getEdge(Object id) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeEdge(com.tinkerpop.blueprints.Edge)
   */
  @Override
  public void removeEdge(Edge edge) {
    throw new UnsupportedOperationException("removeEdge");

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges()
   */
  @Override
  public Iterable<Edge> getEdges() {
    Collection<IDependency> dependenciesTo = _rootArtifact.getDependenciesTo();

    return toEdgeIterable(dependenciesTo);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges(java.lang.String, java.lang.Object)
   */
  @Override
  public Iterable<Edge> getEdges(String key, Object value) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#shutdown()
   */
  @Override
  public void shutdown() {
    _vertexCache.clear();
  }

  /**
   * @param artifact
   * @return
   */
  public BundleMakerVertex getVertexForArtifact(IBundleMakerArtifact artifact) {
    checkNotNull(artifact);

    return _vertexCache.getOrCreate(artifact);
  }

  /**
   * @param iterable
   * @return
   */
  public Iterable<Edge> toEdgeIterable(Iterable<IDependency> iterable) {
    return Iterables.transform(iterable, _dependencyToEdgeConverterFunction);
  }

}
