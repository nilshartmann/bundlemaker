package org.bundlemaker.core.internal.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.IModifiableArtifact;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AdapterGroup2IArtifact extends AbstractAdvancedContainer {

  private static final char DELIMITER = '/';

  /**
   * <p>
   * Creates a new instance of type {@link AdapterGroup2IArtifact}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AdapterGroup2IArtifact(String name, IArtifact parent) {
    super(ArtifactType.Group, name);

    // set parent/children dependency
    setParent(parent);
    ((AbstractAdvancedContainer) parent).getModifiableChildren().add(this);
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
  public boolean canAdd(IArtifact artifact) {
    return artifact.getType().equals(ArtifactType.Group) || artifact.getType().equals(ArtifactType.Module);
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
    // TODO!!!
    ((IModifiableArtifact) artifact).setParent(this);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.addResourceModuleToModularizedSystem(artifact);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeArtifact(IArtifact artifact) {

    Assert.isNotNull(artifact);

    boolean result = super.removeArtifact(artifact);

    // CHANGE THE UNDERLYING MODEL
    AdapterUtils.removeResourceModuleFromModularizedSystem(artifact);

    // TODO!!!
    ((IModifiableArtifact) artifact).setParent(null);

    //
    return result;
  }
}
