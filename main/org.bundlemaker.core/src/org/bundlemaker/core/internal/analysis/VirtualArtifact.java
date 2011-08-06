package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IVirtualContainer;

/**
 * <p>
 * Artifact implementation for 'virtual' artifacts, e.g. for missing types.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class VirtualArtifact extends AbstractAdvancedContainer implements IVirtualContainer {

  /**
   * <p>
   * Creates a new instance of type {@link VirtualArtifact}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public VirtualArtifact(ArtifactType type, String name, IArtifact parent) {
    super(type, name);

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canAdd(IArtifact artifact) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {
    return getName();
  }
}
