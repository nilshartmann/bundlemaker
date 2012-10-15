/*******************************************************************************
 * Copyright (c) 2012 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.ui.transformation.handlers;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;

import org.osgi.framework.Bundle;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class TransformationScriptClassLoader extends URLClassLoader {

  /** use degradable logger */
  // private static final Log log = LogUtils.createLogger(BundleDelegatingClassLoader.class);

  private final Bundle backingBundle;

  /**
   * Factory method for creating a class loader over the given bundle and with a given class loader as fall-back. In
   * case the bundle cannot find a class or locate a resource, the given class loader will be used as fall back.
   * 
   * @param bundle
   *          bundle used for class loading and resource acquisition
   * @param bridge
   *          class loader used as fall back in case the bundle cannot load a class or find a resource. Can be
   *          <code>null</code>
   * @return class loader adapter over the given bundle and class loader
   */
  public static TransformationScriptClassLoader createBundleClassLoaderFor(final Bundle bundle, final URL[] urls) {
    return AccessController.doPrivileged(new PrivilegedAction<TransformationScriptClassLoader>() {

      @Override
      public TransformationScriptClassLoader run() {
        return new TransformationScriptClassLoader(urls, bundle);
      }
    });
  }

  /**
   * Private constructor.
   * 
   * Constructs a new <code>BundleDelegatingClassLoader</code> instance.
   * 
   * @param bundle
   * @param bridgeLoader
   */
  protected TransformationScriptClassLoader(URL[] urls, Bundle bundle) {
    super(urls);
    // Assert.notNull(bundle, "bundle should be non-null");
    this.backingBundle = bundle;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    try {
      System.out.println("findClass: " + name);
      return this.backingBundle.loadClass(name);
    } catch (ClassNotFoundException cnfe) {
      System.out.println("findClass " + name + " failed: " + cnfe);
      cnfe.printStackTrace();
      return super.findClass(name);
      // DebugUtils.debugClassLoading(backingBundle, name, null);
    } catch (NoClassDefFoundError ncdfe) {
      // This is almost always an error
      // This is caused by a dependent class failure,
      // so make sure we search for the right one.
      String cname = ncdfe.getMessage().replace('/', '.');
      // DebugUtils.debugClassLoading(backingBundle, cname, name);
      NoClassDefFoundError e = new NoClassDefFoundError(name + " not found from bundle ["
          + OsgiStringUtils.nullSafeNameAndSymName(backingBundle) + "]");
      e.initCause(ncdfe);
      throw e;
    }
  }

  @Override
  public URL findResource(String name) {
    // boolean trace = log.isTraceEnabled();
    //
    // if (trace)
    // log.trace("Looking for resource " + name);
    URL url = this.backingBundle.getResource(name);

    // if (trace && url != null)
    // log.trace("Found resource " + name + " at " + url);
    return url;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Enumeration<URL> findResources(String name) throws IOException {
    // boolean trace = log.isTraceEnabled();

    // if (trace)
    // log.trace("Looking for resources " + name);

    Enumeration<URL> enm = this.backingBundle.getResources(name);

    // if (trace && enm != null && enm.hasMoreElements())
    // log.trace("Found resource " + name + " at " + this.backingBundle.getLocation());

    return enm;
  }

  @Override
  public URL getResource(String name) {
    URL resource = findResource(name);
    if (resource == null) {
      resource = backingBundle.getResource(name);
    }
    return resource;
  }

  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    Class<?> clazz = null;
    System.out.println("loadClass: " + name);

    try {
      clazz = findClass(name);
    } catch (ClassNotFoundException cnfe) {
      if (backingBundle != null) {
        System.out.println("loadClass: " + name + " from bridge loader...");
        try {
          clazz = backingBundle.loadClass(name);
        } catch (ClassNotFoundException e) {
          System.out.println("loadClass: " + name + " failed: " + e);
          e.printStackTrace();
          throw e;
        }
      } else
        throw cnfe;
    }
    if (resolve) {
      resolveClass(clazz);
    }
    return clazz;
  }

  // public String toString() {
  // return "BundleDelegatingClassLoader for [" + OsgiStringUtils.nullSafeNameAndSymName(backingBundle) + "]";
  // }

  /**
   * Returns the bundle to which this class loader delegates calls to.
   * 
   * @return the backing bundle
   */
  public Bundle getBundle() {
    return backingBundle;
  }

}
