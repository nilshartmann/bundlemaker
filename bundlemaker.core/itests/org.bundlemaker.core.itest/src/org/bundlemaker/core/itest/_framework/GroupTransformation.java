package org.bundlemaker.core.itest._framework;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.transformation.ITransformation;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupTransformation implements ITransformation {

  /** - */
  private IModuleIdentifier _moduleIdentifier;

  /** - */
  private IPath             _group;

  /**
   * <p>
   * Creates a new instance of type {@link GroupTransformation}.
   * </p>
   * 
   * @param moduleIdentifier
   * @param group
   */
  public GroupTransformation(IModuleIdentifier moduleIdentifier, IPath group) {
    super();

    _moduleIdentifier = moduleIdentifier;
    _group = group;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(IModifiableModularizedSystem modularizedSystem, IProgressMonitor monitor) {
    modularizedSystem.getModifiableResourceModule(_moduleIdentifier).setClassification(_group);
  }
}
