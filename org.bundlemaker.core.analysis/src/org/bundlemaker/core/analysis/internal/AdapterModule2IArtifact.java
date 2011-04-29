package org.bundlemaker.core.analysis.internal;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModule2IArtifact extends AbstractArtifactContainer implements IArtifact {

  /** the resource module */
  private IModule _module;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModule2IArtifact(IModule module, IArtifact parent) {
    super(ArtifactType.Module, parent);

    Assert.isNotNull(module);

    // set the resource module
    _module = module;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getName() {
    return _module.getModuleIdentifier().toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getQualifiedName() {

    String classification = "";

    if (_module.hasClassification()) {
      classification = _module.getClassification().toString() + "/";
    }

    return classification + _module.getModuleIdentifier().toString();
  }

  /**
   * <p>
   * Returns the module.
   * </p>
   * 
   * @return the module.
   */
  protected IModule getModule() {
    return _module;
  }
}
