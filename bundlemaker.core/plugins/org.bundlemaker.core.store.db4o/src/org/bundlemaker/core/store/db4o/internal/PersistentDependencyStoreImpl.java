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
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.bundlemaker.core.BundleMakerCore;
import org.bundlemaker.core.common.classloading.BundleDelegatingClassLoader;
import org.bundlemaker.core.common.classloading.CompoundClassLoader;
import org.bundlemaker.core.internal.modelext.ModelExtFactory;
import org.bundlemaker.core.internal.resource.DefaultProjectContentResource;
import org.bundlemaker.core.internal.resource.Resource;
import org.bundlemaker.core.spi.parser.IParsableResource;
import org.bundlemaker.core.spi.store.IPersistentDependencyStore;
import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.config.Configuration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Query;
import com.db4o.reflect.jdk.JdkReflector;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class PersistentDependencyStoreImpl implements IPersistentDependencyStore {

  /** the (absolute) file name of the database file */
  private String          _fileName;

  /** the db4o based database */
  private ObjectContainer _database;

  /** indicates if the store is initialized */
  private boolean         _isInitialized = false;

  /** - */
  private BundleContext   _bundleContext;

  /**
   * <p>
   * Creates a new instance of type {@link PersistentDependencyStoreImpl}.
   * </p>
   * 
   * @param fileName
   */
  public PersistentDependencyStoreImpl(String fileName, BundleContext bundleContext) {

    Assert.isNotNull(fileName);
    Assert.isNotNull(bundleContext);

    //
    _fileName = fileName;
    _bundleContext = bundleContext;
  }

  /**
   * <p>
   * Creates a new instance of type {@link PersistentDependencyStoreImpl}.
   * </p>
   * 
   * @param fileName
   *          the file name
   */
  public PersistentDependencyStoreImpl(String fileName) {
    Assert.isNotNull(fileName);

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

    ClassLoader classLoader = getReflectorClassLoader();
    configuration.reflectWith(new JdkReflector(classLoader));

    try {

      // set cascade on update
      configuration.objectClass(Resource.class).cascadeOnUpdate(true);
      configuration.objectClass(DefaultProjectContentResource.class).cascadeOnUpdate(true);

      // set cascade on activation
      configuration.objectClass(Resource.class).cascadeOnActivate(true);
      configuration.objectClass(DefaultProjectContentResource.class).cascadeOnActivate(true);

      // configure db4o with additional persistent classes
      String[][] classes = getAdditionalPersistentClasses();

      for (String clazz : classes[0]) {
        configuration.objectClass(classLoader.loadClass(clazz)).cascadeOnUpdate(true);
      }

      for (String clazz : classes[1]) {
        configuration.objectClass(classLoader.loadClass(clazz)).cascadeOnActivate(true);
      }

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    // note: cascadeOnDelete is not possible, otherwise we clean up
    // FlyWeightCache instances!

    // set the activation depth
    // since we really want the whole db in memory, set the activation
    // depth very high
    configuration.activationDepth(Integer.MAX_VALUE);

    // open file
    File file = new File(_fileName);
    file.getParentFile().mkdirs();

    // we have to use the Db4o methods here as the Db4o OSGi service does
    // handle the class loading right ,-/
    _database = Db4o.openFile(configuration, _fileName);

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

  /**
   * {@inheritDoc}
   */
  public List<IParsableResource> getResources() {
    Query query = getDatabase().query();
    query.constrain(Resource.class);
    List<IParsableResource> result = query.execute();

    //
    return result;
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

  /**
   * <p>
   * Creates the reflector class loader.
   * </p>
   * 
   * @return the reflector class loader.
   */
  private ClassLoader getReflectorClassLoader() {

    //
    CompoundClassLoader compoundClassLoader = new CompoundClassLoader();

    //
    for (Bundle bundle : getExtensionBundles()) {
      compoundClassLoader.add(new BundleDelegatingClassLoader(bundle));
    }

    //
    return compoundClassLoader;
  }

  /**
   * <p>
   * Returns a list with all the bundles that define model extensions.
   * </p>
   * 
   * @return the reflector class loader.
   */
  private List<Bundle> getExtensionBundles() {

    // get the namespace list
    List<String> namespaces = ModelExtFactory.getModelExtensionFactory().getExtensionBundleNamespaces();

    // create the result
    List<Bundle> result = new LinkedList<Bundle>();

    // add the bundles
    for (Bundle bundle : _bundleContext.getBundles()) {
      if (namespaces.contains(bundle.getSymbolicName()) || BundleMakerCore.BUNDLE_ID_BUNDLEMAKER_CORE.equals(bundle.getSymbolicName())) {
        System.out.println(" - " + bundle.getSymbolicName());
        result.add(bundle);
      }
    }

    // return the result
    return result;
  }

  /**
   * <p>
   * Returns a two dimensional array that contains the additional persistent classes.
   * </p>
   * 
   * @return
   */
  private String[][] getAdditionalPersistentClasses() {

    //
    List<String> activateClasses = new LinkedList<String>();
    List<String> updateClasses = new LinkedList<String>();

    //
    for (Bundle bundle : getExtensionBundles()) {

      URL url = bundle.getResource("store-db4o.properties");

      if (url != null) {
        try {

          Properties properties = new Properties();
          InputStream inputStream = url.openStream();
          properties.load(inputStream);
          inputStream.close();

          if (properties.containsKey("cascadeOnUpdate")) {

            String prop = properties.getProperty("cascadeOnUpdate");
            String[] values = prop.split(",");
            try {
              for (String value : values) {
                updateClasses.add(value.trim());
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

          if (properties.containsKey("cascadeOnActivate")) {
            String prop = properties.getProperty("cascadeOnActivate");
            String[] values = prop.split(",");
            try {
              for (String value : values) {
                activateClasses.add(value.trim());
              }
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

        } catch (IOException e) {
          //
        }
      }
    }

    // create the result
    String[][] result = new String[2][];
    result[0] = updateClasses.toArray(new String[0]);
    result[1] = activateClasses.toArray(new String[0]);
    return result;
  }
}
