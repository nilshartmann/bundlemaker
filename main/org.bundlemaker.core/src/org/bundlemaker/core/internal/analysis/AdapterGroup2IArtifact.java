package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.core.analysis.IArtifactTreeVisitor;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupArtifact;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.modules.Group;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

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

  /** - */
  private Group                                 _group;

  /**
   * <p>
   * Creates a new instance of type {@link AdapterGroup2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterGroup2IArtifact(Group group, IBundleMakerArtifact parent) {
    super(group.getPath().lastSegment());

    Assert.isNotNull(parent);

    //
    _group = group;

    // set parent/children dependency
    setParent(parent);
    ((AbstractBundleMakerArtifactContainer) parent).getModifiableChildren().add(this);

    //
    _groupAndModuleContainerDelegate = new GroupAndModuleContainerDelegate(this);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Group getAssociatedGroup() {
    return _group;
  }

  @Override
  public String getName() {
    return _group.getPath().lastSegment();
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
  @Override
  public IGroupArtifact getOrCreateGroup(String path) {
    return _groupAndModuleContainerDelegate.getOrCreateGroup(path);
  }

  /**
   * {@inheritDoc}
   */
  public IGroupArtifact getOrCreateGroup(IPath path) {
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

    //
    AdapterUtils.changeGroupName(this, name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getQualifiedName() {

    //
    List<String> groupNames = new LinkedList<String>();

    //
    IBundleMakerArtifact groupArtifact = this;
    while (groupArtifact != null && groupArtifact.isInstanceOf(IGroupArtifact.class)) {
      groupNames.add(groupArtifact.getName());
      groupArtifact = groupArtifact.getParent();
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
  public String handleCanAdd(IBundleMakerArtifact artifactToAdd) {

    //
    if (!(artifactToAdd.isInstanceOf(IGroupArtifact.class) || artifactToAdd instanceof AdapterModule2IArtifact)) {
      return "Only groups and modules are addable to groups";
    }

    // prevent entries with duplicate names entries
    if (getChild(artifactToAdd.getName()) != null) {
      return String.format("The group '%s' already contains a child with the name '%s'.", this.getQualifiedName(),
          artifactToAdd.getName());
    }

    //
    IBundleMakerArtifact parent = this.getParent();
    while (parent != null) {
      if (parent.equals(artifactToAdd)) {
        return String.format("Cannot add '%s' already since it is a parent of '%s'.", artifactToAdd.getQualifiedName(),
            this.getQualifiedName());
      }
      parent = parent.getParent();
    }

    //
    return null;
  }

  @Override
  protected void onAddArtifact(IBundleMakerArtifact artifact) {

    // CHANGE THE UNDERLYING MODEL
    if (artifact instanceof IModuleArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IModuleArtifact) artifact, getQualifiedName()
          .replace('|', '/'));

    } else if (artifact instanceof IGroupArtifact) {
      AdapterUtils.addModulesIfNecessaryAndResetClassification((IGroupArtifact) artifact, this);
    }
  }

  @Override
  protected void onRemoveArtifact(IBundleMakerArtifact artifact) {

    // CHANGE THE UNDERLYING MODEL
    if (!AdapterUtils.removeResourceModuleFromModularizedSystem(artifact)) {

      // we have to support the case that an empty group has been removed
      internalRemoveArtifact(artifact);
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
      for (IBundleMakerArtifact artifact : getChildren()) {
        ((IBundleMakerArtifact) artifact).accept(visitor);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void accept(IArtifactTreeVisitor... visitors) {
    DispatchingArtifactTreeVisitor artifactTreeVisitor = new DispatchingArtifactTreeVisitor(visitors);
    accept(artifactTreeVisitor);
  }
}
