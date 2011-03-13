package org.bundlemaker.core.modules;

import java.util.Map;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModule extends ITypeContainer {

  /**
   * <p>
   * Returns the module identifier of this module.
   * </p>
   * 
   * @return the module identifier of this module.
   */
  IModuleIdentifier getModuleIdentifier();

  /**
   * <p>
   * Returns the classification of this module.
   * </p>
   * 
   * @return the classification
   */
  IPath getClassification();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  boolean hasClassification();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  Map<String, Object> getUserAttributes();
}
