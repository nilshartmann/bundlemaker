package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.IModifiableArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModularizedSystem2IArtifact extends AbstractAdvancedContainer {

  private IModifiableModularizedSystem _modularizedSystem;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModularizedSystem2IArtifact(IModifiableModularizedSystem modularizedSystem) {
    super(ArtifactType.Root, null);

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

  public IModifiableModularizedSystem getModularizedSystem() {
    return _modularizedSystem;
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return artifact.getType().equals(ArtifactType.Group) || artifact.getType().equals(ArtifactType.Module);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    // call the super method
    super.addArtifact(artifact);
    // TODO!!!
    ((IModifiableArtifact)artifact).setParent(this);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.addResourceModuleToModularizedSystem(artifact);
    AdapterUtils.getModularizedSystem(artifact).initializeResourceModules();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    Assert.isNotNull(artifact);

    boolean result = super.removeArtifact(artifact);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.removeResourceModuleFromModularizedSystem(artifact);
    AdapterUtils.getModularizedSystem(this).initializeResourceModules();

    // TODO!!!
    ((IModifiableArtifact)artifact).setParent(null);

    //
    return result;
  }
}
