package org.bundlemaker.core.transformation;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractUndoableTransformation implements IUndoableTransformation {

  /** - */
  private IModifiableModularizedSystem _modularizedSystem;

  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor progressMonitor) {
    _modularizedSystem = modularizedSystem;
  }

  public final IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }
}
