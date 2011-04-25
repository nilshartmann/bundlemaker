package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

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

  @Override
  public final String getName() {
    return _module.getModuleIdentifier().toString();
  }

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
   * </p>
   * 
   * @return
   */
  protected IModule getModule() {
    return _module;
  }
}
