package org.bundlemaker.core.ui.editor.transformation;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.transformation.ITransformation;

public class TransformationExecutorContext {

  private final IModularizedSystem _system;

  public TransformationExecutorContext(IModularizedSystem system) {
    _system = system;
  }

  public void addTransformation(ITransformation transformation) {
    _system.getTransformations().add(transformation);
  }

}
