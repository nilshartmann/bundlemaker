package org.bundlemaker.analysis.testkit;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DeleteTransformation implements ITransformation {

  /** - */
  private IModuleIdentifier _moduleIdentifier;

  /**
   * <p>
   * Creates a new instance of type {@link DeleteTransformation}.
   * </p>
   * 
   * @param moduleIdentifier
   */
  public DeleteTransformation(IModuleIdentifier moduleIdentifier) {
    super();
    Assert.isNotNull(moduleIdentifier);

    //
    _moduleIdentifier = moduleIdentifier;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {
    modularizedSystem.removeModule(_moduleIdentifier);
  }
}
