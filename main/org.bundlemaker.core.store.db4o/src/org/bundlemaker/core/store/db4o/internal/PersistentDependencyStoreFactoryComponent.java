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

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.IBundleMakerProject;
import org.bundlemaker.core.internal.store.IPersistentDependencyStore;
import org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import com.db4o.osgi.Db4oService;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreFactoryComponent implements IPersistentDependencyStoreFactory {

  /** the cache */
  private Map<IBundleMakerProject, PersistentDependencyStoreImpl> _cache;

  /** the db4o service */
  private Db4oService                                             _db4oService;

  /**
   * <p>
   * Creates a new instance of type {@link PersistentDependencyStoreFactoryComponent}.
   * </p>
   */
  public PersistentDependencyStoreFactoryComponent() {

    // create the cache
    _cache = new HashMap<IBundleMakerProject, PersistentDependencyStoreImpl>();
  }

  /**
   * @see org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory#resetPersistentDependencyStore(org.bundlemaker.core.IBundleMakerProject)
   */
  public void resetPersistentDependencyStore(IBundleMakerProject project) throws CoreException {

    // step 1: dispose the cache if necessary
    if (_cache.containsKey(project)) {

      // get the store
      PersistentDependencyStoreImpl infoStore = _cache.get(project);

      // dispose the store if necessary
      if (infoStore.isInitialized()) {
        infoStore.dispose();
      }
    }

    // step 2: delete the existing '.bundlemaker/db4o.store' file
    IFile file = project.getProject().getFile(".bundlemaker/db4o.store");
    file.delete(true, null);

    // step 3: re-init the dependency store
    if (_cache.containsKey(project)) {

      // get the store
      PersistentDependencyStoreImpl dependencyStore = _cache.get(project);

      // initialize the dependency store
      dependencyStore.init();
    }
  }

  
  
  public void releasePersistentDependencyStore(IBundleMakerProject project) {
    // step 1: dispose the cache if necessary
    if (_cache.containsKey(project)) {

      // get the store
      PersistentDependencyStoreImpl storeImpl = _cache.get(project);

      // dispose the store if necessary
      if (storeImpl.isInitialized()) {
        storeImpl.dispose();
      }
      
      //
      _cache.remove(project);
    }
    
  }

  /**
   * @see org.bundlemaker.core.internal.store.IPersistentDependencyStoreFactory#getPersistentDependencyStore(org.bundlemaker.core.IBundleMakerProject)
   */
  public IPersistentDependencyStore getPersistentDependencyStore(IBundleMakerProject project) {

    // step 1: return the cached version if one exists
    if (_cache.containsKey(project)) {

      //
      IPersistentDependencyStore dependencyStore = _cache.get(project);

      //
      if (!dependencyStore.isInitialized()) {
        dependencyStore.init();
      }

      //
      return dependencyStore;
    }

    // step 2: create a new store
    IFile file = project.getProject().getFile(".bundlemaker/db4o.store");
    PersistentDependencyStoreImpl store = new PersistentDependencyStoreImpl(_db4oService, file.getRawLocation()
        .toOSString());

    // step 3: initialize the store
    store.init();

    // step 4: cache it
    _cache.put(project, store);

    // return the store
    return store;
  }

  /**
   * <p>
   * </p>
   * 
   * @param db4oService
   */
  public void setDb4oService(Db4oService db4oService) {
    _db4oService = db4oService;
  }

  /**
   * <p>
   * </p>
   * 
   * @param db4oService
   */
  public void unsetDb4oService(Db4oService db4oService) {
    _db4oService = null;
  }
}
