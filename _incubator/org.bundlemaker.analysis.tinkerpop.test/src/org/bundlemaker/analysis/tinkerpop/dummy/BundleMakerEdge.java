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

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public class BundleMakerEdge extends BundleMakerElement implements Edge {
  
  private final String _label;
  
  private BundleMakerVertex _incoming;
  private BundleMakerVertex _outgoing;

   /**
   * 
   */
  public BundleMakerEdge(BundleMakerGraph graph, Object id, String label, BundleMakerVertex incoming, BundleMakerVertex outgoing) {
    super(graph, id);
    
    this._label = label;
    this._incoming = incoming;
    this._outgoing = outgoing;

    
  }
  
  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Edge#getVertex(com.tinkerpop.blueprints.Direction)
   */
  @Override
  public Vertex getVertex(Direction direction) throws IllegalArgumentException {
    
    // same "wrong" mapping as in com.tinkerpop.blueprints.impls.neo4j.Neo4jEdge.getVertex(Direction)
    if (direction == Direction.IN) {
      return _outgoing;
    } else if (direction == Direction.OUT) {
      return _incoming;
    }
    
    throw new IllegalArgumentException("direction invalid");
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Edge#getLabel()
   */
  @Override
  public String getLabel() {
    return _label;
  }

}
