package org.bundlemaker.core.internal.analysis.virtual;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.analysis.AbstractAdvancedContainer;
import org.bundlemaker.core.modules.IModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VirtualModule2IArtifact extends AbstractAdvancedContainer implements IModuleArtifact {

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
  public VirtualModule2IArtifact(String name, String fullyQualifiedName, IArtifact parent) {
    super(ArtifactType.Module, name);

    _fullyQualifiedName = fullyQualifiedName;

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getQualifiedName() {
    return _fullyQualifiedName;
  }

  @Override
  public String handleCanAdd(IArtifact artifact) {
    return "Can not artifacts to virtual modules.";
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
}
