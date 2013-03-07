package org.bundlemaker.core.internal.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.eclipse.core.runtime.Assert;
import org.osgi.framework.Bundle;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleDelegatingClassLoader extends ClassLoader {

  /** - */
  private final Bundle _bundle;

  /**
   * <p>
   * Creates a new instance of type {@link BundleDelegatingClassLoader}.
   * </p>
   * 
   * @param bundle
   */
  public BundleDelegatingClassLoader(Bundle bundle) {
    Assert.isNotNull(bundle);

    _bundle = bundle;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Bundle getBundle() {
    return _bundle;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    return _bundle.loadClass(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected URL findResource(String name) {
    URL resource = _bundle.getResource(name);
    return resource;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Enumeration<URL> findResources(String name) throws IOException {
    return _bundle.getResources(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

    // the result
    Class<?> clazz;

    // try to load the class
    try {
      clazz = findClass(name);
    } catch (ClassNotFoundException cnfe) {
      throw new ClassNotFoundException(name + " from bundle " + _bundle.getBundleId() + " ("
          + _bundle.getSymbolicName() + ")", cnfe);
    }

    // resolve if requested
    if (resolve) {
      resolveClass(clazz);
    }

    // return the result
    return clazz;
  }
}