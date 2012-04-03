package org.bundlemaker.core.modules.modifiable;

import org.eclipse.core.runtime.IPath;

public interface IModifiableModule {

  /**
   * <p>
   * </p>
   * 
   * @param classification
   */
  void setClassification(IPath classification);
}
