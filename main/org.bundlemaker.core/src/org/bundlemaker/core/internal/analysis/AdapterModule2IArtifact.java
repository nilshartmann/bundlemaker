package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.modules.IModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModule2IArtifact extends AbstractAdvancedContainer implements IModuleArtifact {

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
    super(ArtifactType.Module, module.getModuleIdentifier().toString());

    Assert.isNotNull(module);
    Assert.isTrue(parent instanceof AbstractArtifactContainer);

    // set the resource module
    _module = module;

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getAssociatedModule() {
    return _module;
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
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    //
    super.addArtifact(artifact);

    // TODO: TYPE CHECK??
    AdapterUtils.addPackageToModule(artifact, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    boolean result = false;

    // package type
    if (artifact.getType().equals(ArtifactType.Package)) {

      //
      if (getChildren().contains(artifact)) {
        result = super.removeArtifact(artifact);
        AdapterUtils.removePackageFromModule(artifact, this);
      }
    }

    // package type
    else if (artifact.getType().equals(ArtifactType.Type)) {
      IArtifact packageArtifact = artifact.getParent();
      packageArtifact.removeArtifact(packageArtifact);
    }

    // return the result
    return result;
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return artifact != null
        && (artifact.getType().equals(ArtifactType.Package) || artifact.getType().equals(ArtifactType.Type));
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
