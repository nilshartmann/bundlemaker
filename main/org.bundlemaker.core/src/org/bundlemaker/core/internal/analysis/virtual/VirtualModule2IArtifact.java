package org.bundlemaker.core.internal.analysis.virtual;

import org.bundlemaker.core.analysis.IAnalysisModelVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.spi.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.DispatchingArtifactTreeVisitor;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VirtualModule2IArtifact extends AbstractArtifactContainer implements IModuleArtifact {

  /** - */
  private String _fullyQualifiedName;

  /**
   * <p>
   * Creates a new instance of type {@link VirtualModule2IArtifact}.
   * </p>
   * 
   * @param name
   * @param fullyQualifiedName
   * @param parent
   */
  public VirtualModule2IArtifact(String name, String fullyQualifiedName, IBundleMakerArtifact parent) {
    super(name);

    _fullyQualifiedName = fullyQualifiedName;

    // set parent/children dependency
    setParent(parent);
    ((AbstractArtifactContainer) parent).getModifiableChildrenCollection().add(this);
  }

  @Override
  public boolean isVirtual() {
    return true;
  }

  @Override
  public void setNameAndVersion(String name, String version) {
    super.setName(name);
  }

  @Override
  public boolean isMovable() {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModule getAssociatedModule() {
    return null;
  }

  @Override
  public boolean isResourceModule() {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getQualifiedName() {
    return _fullyQualifiedName;
  }

  @Override
  public String getModuleName() {
    return _fullyQualifiedName;
  }

  @Override
  public String getModuleVersion() {
    return "0.0.0";
  }

  @Override
  public String handleCanAdd(IBundleMakerArtifact artifact) {
    return "Can not artifacts to virtual modules.";
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onRemoveArtifact");

  }

  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {
    throw new UnsupportedOperationException("onAddArtifact");
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
}
