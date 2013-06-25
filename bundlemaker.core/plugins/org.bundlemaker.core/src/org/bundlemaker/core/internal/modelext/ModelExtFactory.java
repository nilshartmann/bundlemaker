package org.bundlemaker.core.internal.modelext;

import org.bundlemaker.core._type.internal.adapter.ModelExtension;
import org.bundlemaker.core.spi.modext.IModelExtension;

public class ModelExtFactory {

  private static IModelExtension _modelExtension;

  public static IModelExtension getModelExtension() {

    if (_modelExtension == null) {
      _modelExtension = new ModelExtension();
      _modelExtension.initialize();
    }
    //
    return _modelExtension;
  }
}
