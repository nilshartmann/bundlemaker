package org.bundlemaker.core.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.modules.AmbiguousDependencyException;
import org.eclipse.core.runtime.Assert;

/**
 * 
 * @param <K>
 * @param <V>
 */
public abstract class GenericCache<K, V> {

  /** - */
  private Map<K, V> _cacheMap;

  /**
   * <p>
   * Creates a new instance of type {@link GenericCache}.
   * </p>
   */
  public GenericCache() {

    // create the type to artifact map
    _cacheMap = new HashMap<K, V>();

  }

  public int size() {
    return _cacheMap.size();
  }

  public void clear() {
    _cacheMap.clear();
  }

  public boolean isEmpty() {
    return _cacheMap.isEmpty();
  }

  public boolean containsKey(Object key) {
    return _cacheMap.containsKey(key);
  }

  public V get(Object key) {
    return _cacheMap.get(key);
  }

  public Map<K, V> getMap() {
    return _cacheMap;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<V> values() {

    //
    return _cacheMap.values();
  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   * 
   * @throws AmbiguousDependencyException
   */
  public final V getOrCreate(K key) {

    //
    Assert.isNotNull(key);

    //
    if (!_cacheMap.containsKey(key)) {

      V value = create(key);

      _cacheMap.put(key, value);
    }

    //
    return _cacheMap.get(key);

  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  protected abstract V create(K key);
}
