package org.bundlemaker.core.common.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <K>
 * @param <V>
 */
public abstract class SymetricGenericCache<K, V> extends GenericCache<K, V> {

  /** - */
  private static final long serialVersionUID = 1L;

  /** - */
  private Map<V, K>         _valueToKeyMap;

  /**
   * <p>
   * Creates a new instance of type {@link SymetricGenericCache}.
   * </p>
   */
  public SymetricGenericCache() {
    super();

    //
    _valueToKeyMap = new HashMap<V, K>();
  }

  /**
   * <p>
   * Creates a new instance of type {@link SymetricGenericCache}.
   * </p>
   * 
   * @param initialCapacity
   * @param loadFactor
   */
  public SymetricGenericCache(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);

    //
    _valueToKeyMap = new HashMap<V, K>();
  }

  /**
   * <p>
   * Creates a new instance of type {@link SymetricGenericCache}.
   * </p>
   * 
   * @param initialCapacity
   */
  public SymetricGenericCache(int initialCapacity) {
    super(initialCapacity);

    //
    _valueToKeyMap = new HashMap<V, K>();
  }

  /**
   * <p>
   * Creates a new instance of type {@link SymetricGenericCache}.
   * </p>
   * 
   * @param m
   */
  public SymetricGenericCache(Map<? extends K, ? extends V> m) {
    super(m);

    //
    _valueToKeyMap = new HashMap<V, K>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param key
   * @return
   */
  protected abstract V create(K key);

  /**
   * {@inheritDoc}
   */
  @Override
  public V put(K key, V value) {

    //
    V result = super.put(key, value);

    // TODO: CHECK if unique
    _valueToKeyMap.put(value, key);

    //
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    super.putAll(m);

    //
    for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
      _valueToKeyMap.put(entry.getValue(), entry.getKey());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public V remove(Object key) {

    V result = super.remove(key);

    _valueToKeyMap.remove(result);

    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param value
   * @return
   */
  public K getKeyForValue(V value) {
    return _valueToKeyMap.get(value);
  }
}
