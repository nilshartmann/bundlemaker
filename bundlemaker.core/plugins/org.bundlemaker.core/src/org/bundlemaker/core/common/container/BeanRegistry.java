package org.bundlemaker.core.common.container;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

public class BeanRegistry {

  /** - */
  private static BeanRegistry   _registry;

  /** - */
  private Map<Class<?>, Object> _instances = new HashMap<Class<?>, Object>();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public static BeanRegistry instance() {

    //
    if (_registry == null) {
      _registry = new BeanRegistry();
      _registry.initialize();
    }

    //
    return _registry;
  }

  /**
   * <p>
   * </p>
   * 
   * @param clazz
   * @return
   */
  public <T> T getBean(Class<T> clazz) {
    Assert.isNotNull(clazz);

    return (T) _instances.get(clazz);
  }

  /**
   * <p>
   * Creates a new instance of type {@link BeanRegistry}.
   * </p>
   */
  private BeanRegistry() {

    //
    _instances = new HashMap<Class<?>, Object>();
  }

  /**
   * <p>
   * </p>
   */
  private void initialize() {

    //
    // _instances.put(key, value);
  }
}
