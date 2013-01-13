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

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Query;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class BundleMakerVertex extends BundleMakerElement implements Vertex {
  private List<BundleMakerEdge> _outgoingEdges = new LinkedList<BundleMakerEdge>();
  private List<BundleMakerEdge> _incomingEdges = new LinkedList<BundleMakerEdge>();
  /**
   * @param id
   */
  public BundleMakerVertex(
      BundleMakerGraph bundleMakerGraph, 
      Object id) {
    super(bundleMakerGraph, id);
  }
  
  public BundleMakerEdge createRelationshipTo(BundleMakerVertex target, RelType type) {
    BundleMakerEdge newEdge = getBundleMakerGraph().createRelationShip(
        this, target, type);
    return newEdge;
  }


  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Vertex#getEdges(com.tinkerpop.blueprints.Direction, java.lang.String[])
   */
  @Override
  public Iterable<Edge> getEdges(Direction direction, final String... labels) {
    
    
    Iterable<? extends Edge> iterable = null;
    
    switch (direction) {
    case IN:
      iterable = _incomingEdges;
      break;
    case OUT:
      iterable = _outgoingEdges;
      break;
    case BOTH:
      iterable = Iterables.concat(_incomingEdges, _outgoingEdges);
      break;
    }
    
    if (labels == null || labels.length==0) {
      return (Iterable<Edge>) iterable;
    }
        
    @SuppressWarnings("unchecked")
    Iterable<Edge> result =(Iterable<Edge>) Iterables.filter(iterable, new Predicate<Edge>() {
      public boolean apply(Edge e) {
        for (String label : labels) {
          if (label.equals(e.getLabel())){
            return true;
          }
        }
        return false;
      }
     
    });
    
    return result;
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Vertex#getVertices(com.tinkerpop.blueprints.Direction, java.lang.String[])
   */
  @Override
  public Iterable<Vertex> getVertices(Direction direction, String... labels) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Vertex#query()
   */
  @Override
  public Query query() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param edge
   */
  public void addOutgoing(BundleMakerEdge edge) {
    _outgoingEdges.add(edge);
  }
  
  public void addIncoming(BundleMakerEdge edge) {
    _incomingEdges.add(edge);
  }


}
