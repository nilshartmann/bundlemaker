package org.bundlemaker.core.spi.modext;

import org.bundlemaker.core.resource.IModularizedSystem;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ICacheAwareModularizedSystem extends IModularizedSystem {

  /**
   * <p>
   * </p>
   * 
   * @param cacheCallback
   */
  void registerCacheCallback(ICacheCallback cacheCallback);

  /**
   * <p>
   * </p>
   * 
   * @param cacheCallback
   */
  void unregisterCacheCallback(ICacheCallback cacheCallback);
}
