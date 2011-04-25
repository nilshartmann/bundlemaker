package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.model.ArtifactType;
import org.bundlemaker.core.analysis.model.IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

public class AdapterModularizedSystem2IArtifact extends AbstractArtifactContainer implements IArtifact {

  private IModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModularizedSystem2IArtifact(IModularizedSystem modularizedSystem) {
    super(ArtifactType.Module, null);

    Assert.isNotNull(modularizedSystem);

    // set the resource module
    _modularizedSystem = modularizedSystem;
  }

  @Override
  public final String getName() {
    return _modularizedSystem.getName();
  }

  @Override
  public final String getQualifiedName() {
    return getName();
  }
}
