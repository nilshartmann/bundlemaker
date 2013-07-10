package org.bundlemaker.core.internal.transformation.add;

import org.bundlemaker.core.internal.api.resource.IModifiableModularizedSystem;
import org.bundlemaker.core.resource.IModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class ModelNotificationSuppressor {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param runnable
   */
  public static void performWithoutNotification(IModularizedSystem modularizedSystem, Runnable runnable) {
    Assert.isNotNull(modularizedSystem);
    Assert.isNotNull(runnable);

    try {

      // we have to set the model modification handling to 'false'
      ((IModifiableModularizedSystem) modularizedSystem).getListenerList().setHandleModelModification(false);

      //
      runnable.run();

    } finally {

      // we have to set the model modification handling to 'true'
      ((IModifiableModularizedSystem) modularizedSystem).getListenerList().setHandleModelModification(true);

    }
  }
}
