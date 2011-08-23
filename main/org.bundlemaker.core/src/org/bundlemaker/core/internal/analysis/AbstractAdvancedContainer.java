package org.bundlemaker.core.internal.analysis;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractAdvancedContainer extends AbstractArtifactContainer implements IAdvancedArtifact {

  /**
   * <p>
   * Creates a new instance of type {@link AbstractAdvancedContainer}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractAdvancedContainer(ArtifactType type, String name) {
    super(type, name);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected void assertCanAdd(IArtifact artifact) {

    //
    if (artifact == null) {
      throw new RuntimeException("Can not add 'null' to " + this);
    }

    //
    String canAddMessage = handleCanAdd(artifact);

    //
    if (canAddMessage != null) {
      throw new RuntimeException("Can not add " + artifact + " to " + this + ":\n" + canAddMessage);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean canAdd(IArtifact artifact) {

    if (artifact == null) {
      return false;
    }

    return handleCanAdd(artifact) == null;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   * @return
   */
  protected String handleCanAdd(IArtifact artifact) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IModularizedSystem getModularizedSystem() {
    return AdapterUtils.getModularizedSystem(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependencyModel getDependencyModel() {
    return ((AbstractAdvancedContainer) getParent(ArtifactType.Root)).getDependencyModel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {
    return (IRootArtifact) getParent(ArtifactType.Root);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // if the artifact has a parent, it has to be removed
    if (artifact.getParent() != null) {
      artifact.getParent().removeArtifact(artifact);
    }

    // call super
    super.addArtifact(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // set parent to null
    if (artifact.getParent() != null) {
      ((AbstractArtifact) artifact).setParent(null);
    }

    // call super
    return super.removeArtifact(artifact);
  }

  @Override
  public boolean containsTypesOrResources() {

    //
    for (IArtifact artifact : getChildren()) {
      if (((IAdvancedArtifact) artifact).containsTypesOrResources()) {
        return true;
      }
    }

    //
    return false;
  }
}
