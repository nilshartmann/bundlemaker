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

import java.util.HashMap;
import java.util.Set;

import com.tinkerpop.blueprints.Element;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 *
 */
public abstract class BundleMakerElement implements Element {

  private final Object _id;
  private final BundleMakerGraph _bundleMakerGraph;
  private final HashMap<String, Object> _properties = new HashMap<String, Object>();
  
  /**
   * 
   */
   BundleMakerElement(
       BundleMakerGraph graph,
       Object id) {
     this._bundleMakerGraph = graph;
    this._id = id;
  }
   
   protected BundleMakerGraph getBundleMakerGraph() {
     return this._bundleMakerGraph;
   }
  
  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Element#getProperty(java.lang.String)
   */
  @Override
  public Object getProperty(String key) {
    return _properties.get(key);
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Element#getPropertyKeys()
   */
  @Override
  public Set<String> getPropertyKeys() {
    return _properties.keySet();
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Element#setProperty(java.lang.String, java.lang.Object)
   */
  @Override
  public void setProperty(String key, Object value) {
    _properties.put(key, value);
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Element#removeProperty(java.lang.String)
   */
  @Override
  public Object removeProperty(String key) {
    return _properties.remove(key);
  }

  /* (non-Javadoc)
   * @see com.tinkerpop.blueprints.Element#getId()
   */
  @Override
  public Object getId() {
    return _id;
  }

}
