/*******************************************************************************
 * Copyright (c) 2011 Gerd Wuetherich (gerd@gerd-wuetherich.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Wuetherich (gerd@gerd-wuetherich.de) - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.store.db4o.internal;

import java.util.List;

import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.parser.IParsableResource;

import com.db4o.osgi.Db4oService;
import com.db4o.query.Query;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreImpl extends AbstractPersistentDependencyStore {

  /**
   * <p>
   * Creates a new instance of type {@link PersistentDependencyStoreImpl}.
   * </p>
   * 
   * @param db4oService
   * @param fileName
   */
  public PersistentDependencyStoreImpl(Db4oService db4oService, String fileName) {
    super(db4oService, fileName);
  }

  /**
   * {@inheritDoc}
   */
  public List<IParsableResource> getResources() {
    Query query = getDatabase().query();
    query.constrain(Resource.class);
    return query.execute();
  }

  /**
   * {@inheritDoc}
   */
  public void updateResource(IParsableResource bundleElement) {
    getDatabase().store(bundleElement);
  }

  /**
   * {@inheritDoc}
   */
  public void delete(IParsableResource resource) {
    getDatabase().delete(resource);
  }
}
