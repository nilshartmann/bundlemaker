package org.bundlemaker.core;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.transformation.ITransformation;
import org.eclipse.core.runtime.IPath;

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
  public void apply(IModifiableModularizedSystem modularizedSystem) {
    modularizedSystem.getModifiableResourceModule(_moduleIdentifier).setClassification(_group);
  }
}
