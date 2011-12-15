package org.bundlemaker.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class CompoundClassLoader extends ClassLoader {

  /** - */
  private Map<String, BundleDelegatingClassLoader> _map = new HashMap<String, BundleDelegatingClassLoader>();

  public Map<String, BundleDelegatingClassLoader> getMap() {
    return _map;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {

    // String packageName = name.substring(0, name.lastIndexOf("."));
    //
    // if (name.contains(".") && _map.containsKey(packageName)) {
    // return _map.get(packageName).findClass(name);
    // }

    for (BundleDelegatingClassLoader delegatingClassLoader : _map.values()) {
      try {
        return delegatingClassLoader.findClass(name);
      } catch (Exception e) {
        // // // TODO Auto-generated catch block
        // e.printStackTrace();
      }
    }

    System.err.println("NOT FOUND " + name);

    return super.findClass(name);
  }
}
