package org.bundlemaker.core.itestframework.internal;

import org.bundlemaker.core.modules.IModuleIdentifier;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableResourceModule;
import org.bundlemaker.core.modules.transformation.IUndoableTransformation;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupTransformation implements IUndoableTransformation {

  /** - */
  private IModuleIdentifier         _moduleIdentifier;

  /** - */
  private IPath                     _group;

  /** - */
  private IPath                     _oldGroup;

  /** - */
  private IModifiableResourceModule _modifiableResourceModule;

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
    
    //
    _modifiableResourceModule = modularizedSystem.getModifiableResourceModule(_moduleIdentifier);
    _oldGroup = _modifiableResourceModule.getClassification();
    _modifiableResourceModule.setClassification(_group);
  }

  @Override
  public void undo() {
    _modifiableResourceModule.setClassification(_oldGroup);
  }
}
