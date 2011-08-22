package org.bundlemaker.core.internal.analysis;

import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.IDependencyModel;
import org.bundlemaker.core.analysis.ArtifactTreeChangedEvent;
import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeChangedListener;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterModularizedSystem2IArtifact extends AbstractAdvancedContainer implements IRootArtifact {

  /** - */
  private IModifiableModularizedSystem       _modularizedSystem;

  /** - */
  private DependencyModel                    _dependencyModel;

  /** - */
  private List<IArtifactTreeChangedListener> _artifactTreeChangedListeners;

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

    //
    _artifactTreeChangedListeners = new LinkedList<IArtifactTreeChangedListener>();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public void invalidateAll() {
    if (getRoot() != null) {
      getRoot().accept(new InvalidateAggregatedDependencies());
    }
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
    return false;
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
  public String handleCanAdd(IArtifact artifact) {

    //
    if (!(artifact.getType().equals(ArtifactType.Group) || artifact instanceof AdapterModule2IArtifact)) {
      return "Only groups and modules are addable to root";
    }

    //
    return null;
  }

  @Override
  public IRootArtifact getRoot() {
    return this;
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

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact || artifact instanceof IGroupArtifact) {
      AdapterUtils.addModuleToModularizedSystem(artifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    Assert.isNotNull(artifact);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.removeResourceModuleFromModularizedSystem(artifact);

    return super.removeArtifact(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IDependencyModel getDependencyModel() {
    return _dependencyModel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifactTreeChangedListener(IArtifactTreeChangedListener listener) {
    if (!_artifactTreeChangedListeners.contains(listener)) {
      _artifactTreeChangedListeners.add(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeArtifactTreeChangedListener(IArtifactTreeChangedListener listener) {
    if (_artifactTreeChangedListeners.contains(listener)) {
      _artifactTreeChangedListeners.remove(listener);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void fireArtifactTreeChangedEvent(ArtifactTreeChangedEvent event) {

    //
    IArtifactTreeChangedListener[] listeners = _artifactTreeChangedListeners
        .toArray(new IArtifactTreeChangedListener[0]);

    //
    for (IArtifactTreeChangedListener changedListener : listeners) {
      changedListener.artifactTreeChanged(event);
    }
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
        ((IAdvancedArtifact) artifact).accept(visitor);
      }
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param dependencyModel
   */
  void setDependencyModel(DependencyModel dependencyModel) {
    _dependencyModel = dependencyModel;
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
