package org.bundlemaker.core.modules.modifiable;

import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IModifiableModule {

  /**
   * <p>
   * </p>
   * 
   * @param classification
   */
  void setClassification(IPath classification);
}
