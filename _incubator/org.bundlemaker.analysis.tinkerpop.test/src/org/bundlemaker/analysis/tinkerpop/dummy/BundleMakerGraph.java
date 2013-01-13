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
package org.bundlemaker.analysis.tinkerpop.dummy;

import java.util.Hashtable;
import java.util.Map;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Features;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class BundleMakerGraph implements Graph {

  private final Map<Object, Vertex> _vertices  = new Hashtable<Object, Vertex>();

  private final Map<Object, Edge>   _edges     = new Hashtable<Object, Edge>();

  private int                       _idCounter = 0;

  public BundleMakerGraph() {

    _vertices.put(0, new BundleMakerVertex(this, 0));

  }

  public BundleMakerVertex createNode() {

    BundleMakerVertex newVertex = new BundleMakerVertex(this, newId());
    _vertices.put(newVertex.getId(), newVertex);

    return newVertex;
  }

  private Object newId() {
    return ++_idCounter;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getFeatures()
   */
  @Override
  public Features getFeatures() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#addVertex(java.lang.Object)
   */
  @Override
  public Vertex addVertex(Object id) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertex(java.lang.Object)
   */
  @Override
  public Vertex getVertex(Object id) {
    throw new UnsupportedOperationException();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeVertex(com.tinkerpop.blueprints.Vertex)
   */
  @Override
  public void removeVertex(Vertex vertex) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getVertices()
   */
  @Override
  public Iterable<Vertex> getVertices() {
    throw new UnsupportedOperationException();
    // return _vertices.values();
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
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdge(java.lang.Object)
   */
  @Override
  public Edge getEdge(Object id) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#removeEdge(com.tinkerpop.blueprints.Edge)
   */
  @Override
  public void removeEdge(Edge edge) {
    // TODO Auto-generated method stub

  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges()
   */
  @Override
  public Iterable<Edge> getEdges() {
    return _edges.values();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#getEdges(java.lang.String, java.lang.Object)
   */
  @Override
  public Iterable<Edge> getEdges(String key, Object value) {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.tinkerpop.blueprints.Graph#shutdown()
   */
  @Override
  public void shutdown() {
    // TODO Auto-generated method stub

  }

  /**
   * @param bundleMakerVertex
   * @param target
   * @param type
   * @return
   */
  public BundleMakerEdge createRelationShip(BundleMakerVertex from, BundleMakerVertex to, RelType type) {
    BundleMakerEdge edge = new BundleMakerEdge(this, newId(), type.name(), from, to);

    from.addOutgoing(edge);
    to.addIncoming(edge);

    _edges.put(edge.getId(), edge);
    return edge;
  }

}