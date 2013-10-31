package org.bundlemaker.core.common.classloading;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * The {@link CompoundClassLoader} contains a list of {@link BundleDelegatingClassLoader BundleDelegatingClassLoaders}
 * that are traversed to find a requested class.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CompoundClassLoader extends ClassLoader {

  /** the list of class loaders */
  private List<BundleDelegatingClassLoader> _classLoaders = new LinkedList<BundleDelegatingClassLoader>();

  /**
   * <p>
   * Adds the given class loader to this compound class loader.
   * </p>
   * 
   * @param classLoader
   *          the class loader
   * @return <code>true</code> if this collection changed as a result of the call
   */
  public boolean add(BundleDelegatingClassLoader classLoader) {

    Assert.isNotNull(classLoader);

    return _classLoaders.add(classLoader);
  }

  /**
   * <p>
   * </p>
   * 
   */
  public void clear() {
    _classLoaders.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {

    //
    for (BundleDelegatingClassLoader delegatingClassLoader : _classLoaders) {
      try {
        return delegatingClassLoader.findClass(name);
      } catch (Exception e) {
        // do nothing
      }
    }

    // delegate to super.findClass()
    return super.findClass(name);
  }
}
