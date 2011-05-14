package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.dependencyanalysis.base.model.ArtifactType;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModule2IArtifact extends AbstractAdvancedContainer {

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
    ((AbstractArtifactContainer) parent).getChildren().add(this);
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
    AdapterUtils.getModularizedSystem(artifact).initializeResourceModules();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);

    // get the result
    boolean result = super.removeArtifact(artifact);

    // TODO: TYPE CHECK??
    AdapterUtils.removePackageFromModule(artifact, this);
    AdapterUtils.getModularizedSystem(artifact).initializeResourceModules();

    // return the result
    return result;
  }

  @Override
  public boolean canAdd(IArtifact artifact) {
    return artifact != null && artifact.getType().equals(ArtifactType.Package);
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
