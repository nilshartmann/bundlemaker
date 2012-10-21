package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
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
public class AdapterModule2IArtifact extends AbstractArtifactContainer implements IModuleArtifact {

  /** the resource module */
  private IModule _module;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterModule2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterModule2IArtifact(IModule module, IBundleMakerArtifact parent) {
    super(module.getModuleIdentifier().toString());

    Assert.isNotNull(module);
    Assert.isTrue(parent instanceof AbstractArtifactContainer);

    // set the resource module
    _module = module;

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onRemoveArtifact");

  }

  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onAddArtifact");
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

    ModuleIdentifier newModuleIdentifier = new ModuleIdentifier(name, version);
    ((AbstractModule<?, ?>) _module).setModuleIdentifier(newModuleIdentifier);

    super.setName(newModuleIdentifier.toString());
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
  public String getModuleName() {
    return _module.getModuleIdentifier().getName();
  }

  @Override
  public String getModuleVersion() {
    return _module.getModuleIdentifier().getVersion();
  }

  @Override
  public String handleCanAdd(IBundleMakerArtifact artifact) {
    return "Can not add artifacts to non-resource modules.";
  }

  @Override
  public boolean isResourceModule() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void accept(IAnalysisModelVisitor visitor) {

    //
    if (visitor.visit(this)) {
      //
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  public void accept(IAnalysisModelVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
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
