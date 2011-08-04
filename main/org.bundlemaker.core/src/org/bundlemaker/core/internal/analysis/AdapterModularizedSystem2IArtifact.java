package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.IModifiableArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
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
    super(ArtifactType.Root, name(modularizedSystem));

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
    ((IModifiableArtifact) artifact).setParent(this);

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {
      AdapterUtils.addResourceModuleToModularizedSystem(artifact);
    }
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

    // TODO!!!
    ((IModifiableArtifact) artifact).setParent(null);

    //
    return result;
  }

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  private static String name(IModifiableModularizedSystem modularizedSystem) {
    Assert.isNotNull(modularizedSystem);
    return modularizedSystem.getName();
  }
}
