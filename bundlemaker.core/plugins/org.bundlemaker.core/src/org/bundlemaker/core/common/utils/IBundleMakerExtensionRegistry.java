package org.bundlemaker.core.common.utils;

import java.util.List;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <T>
 */
public interface IBundleMakerExtensionRegistry<T> {

  /**
   * <p>
   * </p>
   */
  void initialize();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean isInitalized();

  /**
   * <p>
   * </p>
   */
  void dispose();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  List<T> getExtensionInstances();
}
