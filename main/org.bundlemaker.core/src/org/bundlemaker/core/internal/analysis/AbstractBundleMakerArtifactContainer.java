package org.bundlemaker.core.internal.analysis;

import java.util.Collection;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.analysis.model.impl.AbstractArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.analysis.IArtifactModelConfiguration;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.ChangeAction;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleMakerArtifactContainer extends AbstractArtifactContainer implements
    IBundleMakerArtifact {

  /** - */
  private IRootArtifact _root;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBundleMakerArtifactContainer}.
   * </p>
   * 
   * @param type
   * @param name
   */
  public AbstractBundleMakerArtifactContainer(ArtifactType type, String name) {
    super(type, name);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected abstract void onRemoveArtifact(IArtifact artifact);

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  protected abstract void onAddArtifact(IArtifact artifact);

  /**
   * {@inheritDoc}
   */
  @Override
  public IArtifactModelConfiguration getConfiguration() {
    return getRoot().getConfiguration();
  }

  /**
   * {@inheritDoc}
   */
  public void removeFromParent() {
    if (this.getParent() != null) {
      this.getParent().removeArtifact(this);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasParent() {
    return getParent() != null;
  }

  @Override
  public void setParent(IArtifact parent) {
    super.setParent(parent);
    getRoot();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IRootArtifact getRoot() {

    //
    if (_root == null) {
      _root = (IRootArtifact) getParent(ArtifactType.Root);
    }

    //
    return _root;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean containsTypesOrResources() {

    // return result
    return containsTypes() || containsResources();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsTypes() {

    // return 'true' if any child contains (or is) a type
    for (IArtifact artifact : getChildren()) {
      if (((IBundleMakerArtifact) artifact).containsTypes()) {
        return true;
      }
    }

    // otherwise return false
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsResources() {

    // return 'true' if any child contains (or is) a resource
    for (IArtifact artifact : getChildren()) {
      if (((IBundleMakerArtifact) artifact).containsResources()) {
        return true;
      }
    }

    // otherwise return false
    return false;
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
    return ((AbstractBundleMakerArtifactContainer) getParent(ArtifactType.Root)).getDependencyModel();
  }

  /**
   * {@inheritDoc}
   */
  public IBundleMakerArtifact getChild(String path) {
    return (IBundleMakerArtifact) super.getChild(path);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public Collection<IBundleMakerArtifact> getChildren() {
    return (Collection<IBundleMakerArtifact>) super.getChildren();
  }

  public IBundleMakerArtifact getParent() {
    return (IBundleMakerArtifact) super.getParent();
  }

  public IBundleMakerArtifact getParent(ArtifactType type) {
    return (IBundleMakerArtifact) super.getParent(type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    // set the change action
    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(new CurrentAction(this,
        (IBundleMakerArtifact) artifact, ChangeAction.ADDED));

    onAddArtifact(artifact);

    // set change action to null
    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeArtifact(IArtifact artifact) {
    Assert.isNotNull(artifact);

    // set the change action
    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(new CurrentAction(this,
        (IBundleMakerArtifact) artifact, ChangeAction.REMOVED));

    onRemoveArtifact(artifact);

    // set change action to null
    ((AdapterModularizedSystem2IArtifact) getRoot()).setCurrentAction(null);

    //
    return true;
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public final void internalAddArtifact(IArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // if the artifact has a parent, it has to be removed
    if (artifact.getParent() != null) {
      ((AbstractBundleMakerArtifactContainer) artifact.getParent()).internalRemoveArtifact(artifact);
    }

    // call super
    super.addArtifact(artifact);
  }

  /**
   * <p>
   * </p>
   * 
   * @param artifact
   */
  public final void internalRemoveArtifact(IArtifact artifact) {

    // assert not null
    Assert.isNotNull(artifact);

    // set parent to null
    if (artifact.getParent() != null) {
      ((AbstractArtifact) artifact).setParent(null);
    }

    // call super
    super.removeArtifact(artifact);
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
}
