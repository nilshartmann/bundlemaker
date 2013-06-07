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

import java.io.File;

import org.bundlemaker.core._type.internal.Reference;
import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.osgi.Db4oService;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractPersistentDependencyStore implements IPersistentDependencyStore {

  /** the db4o service */
  private Db4oService     _db4oService;

  /** the (absolute) file name of the database file */
  private String          _fileName;

  /** the db4o based database */
  private ObjectContainer _database;

  /** indicates if the store is initialized */
  private boolean         _isInitialized = false;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractPersistentDependencyStore}.
   * </p>
   * 
   * @param db4oService
   *          the db4o service
   * @param fileName
   *          the file name
   */
  public AbstractPersistentDependencyStore(Db4oService db4oService, String fileName) {

    Assert.isNotNull(db4oService);
    Assert.isNotNull(fileName);

    _db4oService = db4oService;
    _fileName = fileName;
  }

  /**
   * <p>
   * Initializes the {@link IPersistentDependencyStore}.
   * </p>
   */
  public final void init() {

    // create a new configuration
    Configuration configuration = Db4o.newConfiguration();

    // set cascade on update
    configuration.objectClass(Resource.class).cascadeOnUpdate(true);
    configuration.objectClass(Reference.class).cascadeOnUpdate(true);
    configuration.objectClass(DefaultProjectContentResource.class).cascadeOnUpdate(true);

    // set cascade on activation
    configuration.objectClass(Resource.class).cascadeOnActivate(true);
    configuration.objectClass(Reference.class).cascadeOnActivate(true);
    configuration.objectClass(DefaultProjectContentResource.class).cascadeOnActivate(true);

    // note: cascadeOnDelete is not possible, otherwise we clean up FlyWeightCache instances!
    // configuration.objectClass(Resource.class).cascadeOnDelete(false);
    // configuration.objectClass(Reference.class).cascadeOnDelete(false);
    // configuration.objectClass(ResourceKey.class).cascadeOnDelete(false);
    //
    // configuration.detectSchemaChanges(false);
    // configuration.callbacks(false);

    // set the activation depth
    // since we really want the whole db in memory, set the activation
    // depth very high
    configuration.activationDepth(Integer.MAX_VALUE);

    // open file
    File file = new File(_fileName);

    file.getParentFile().mkdirs();
    _database = _db4oService.openFile(configuration, _fileName);

    // set initialized
    _isInitialized = true;
  }

  /**
   * <p>
   * Returns <code>true</code> if this store is initialized.
   * </p>
   * 
   * @return <code>true</code> if this store is initialized.
   */
  public final boolean isInitialized() {
    return _isInitialized;
  }

  /**
   * <p>
   * Disposes the dependency store.
   * </p>
   */
  public final void dispose() {

    try {
      // commit all changes
      _database.commit();

      // close the database
      _database.close();
    } catch (Db4oIOException e) {
    } catch (DatabaseClosedException e) {
    } catch (DatabaseReadOnlyException e) {
    }

    // set initialized to false
    _isInitialized = false;
  }

  /**
   * {@inheritDoc}
   */
  public final void commit() {
    Assert.isTrue(isInitialized(), "The persistent dependency store has not been initialized.");

    // commits the database
    _database.commit();
  }

  /**
   * <p>
   * Returns the database.
   * </p>
   * 
   * @return the database.
   */
  protected final ObjectContainer getDatabase() {
    return _database;
  }
}
