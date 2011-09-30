package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public final class AdapterGroup2IArtifact extends AbstractBundleMakerArtifactContainer implements IGroupArtifact {

  // the group qualified name delimiter
  private static final char                     DELIMITER = '/';

  /** - */
  private final GroupAndModuleContainerDelegate _groupAndModuleContainerDelegate;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterGroup2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterGroup2IArtifact(String name, IArtifact parent) {
    super(ArtifactType.Group, name);

    Assert.isNotNull(parent);

    // set parent/children dependency
    setParent(parent);
    ((AbstractBundleMakerArtifactContainer) parent).getModifiableChildren().add(this);

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);
  }

  /**
   * {@inheritDoc}
   */
  public IModuleArtifact getOrCreateModule(String qualifiedModuleName, String moduleVersion) {
    return _groupAndModuleContainerDelegate.getOrCreateModule(qualifiedModuleName, moduleVersion);
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(String path) {
    return _groupAndModuleContainerDelegate.getOrCreateGroup(path);
  }

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    super.setName(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {

    //
    List<String> groupNames = new LinkedList<String>();

    //
    IArtifact group = this;
    while (group != null && ArtifactType.Group.equals(group.getType())) {
      groupNames.add(group.getName());
      group = group.getParent();
    }

    //
    Collections.reverse(groupNames);

    //
    StringBuilder builder = new StringBuilder();

    //
    for (Iterator<String> iterator = groupNames.iterator(); iterator.hasNext();) {
      builder.append(iterator.next());
      if (iterator.hasNext()) {
        builder.append(DELIMITER);
      }
    }

    //
    return builder.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String handleCanAdd(IArtifact artifact) {

    //
    if (!(artifact.getType().equals(ArtifactType.Group) || artifact instanceof AdapterModule2IArtifact)) {
      return "Only groups and modules are addable to groups";
    }

    //
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addArtifact(IArtifact artifact) {

    // asserts
    Assert.isNotNull(artifact);
    assertCanAdd(artifact);

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.addModuleToModularizedSystem(artifact, getQualifiedName().replace('|', '/'))) {

      // we have to support the case that an empty group is added
      internalAddArtifact(artifact);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    Assert.isNotNull(artifact);

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.removeResourceModuleFromModularizedSystem(artifact)) {
      internalRemoveArtifact(artifact);
    }

    return true;
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
