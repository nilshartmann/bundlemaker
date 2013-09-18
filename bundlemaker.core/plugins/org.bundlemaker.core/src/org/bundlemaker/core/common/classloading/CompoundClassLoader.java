package org.bundlemaker.core.common.classloading;

import java.util.LinkedList;
import java.util.List;


/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CompoundClassLoader extends ClassLoader {

  /** - */
  private List<BundleDelegatingClassLoader> _classLoaders = new LinkedList<BundleDelegatingClassLoader>();

  public boolean add(BundleDelegatingClassLoader e) {
    return _classLoaders.add(e);
  }

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
      }
    }

    System.err.println("NOT FOUND " + name);

    return super.findClass(name);
  }
}
