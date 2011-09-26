package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.modules.AbstractModule;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.ModuleIdentifier;
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

  @Override
  public boolean isVirtual() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {
    return true;
  }

  @Override
  public void setNameAndVersion(String name, String version) {

    ((AbstractModule<?, ?>) _module).setModuleIdentifier(new ModuleIdentifier(name, version));

    super.setName(name);
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

  @Override
  public String handleCanAdd(IArtifact artifact) {
    return "Can not add artifacts to non-resource modules.";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IArtifactTreeVisitor visitor) {

    //
    if (visitor.visit(this)) {
      //
      for (IArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
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
