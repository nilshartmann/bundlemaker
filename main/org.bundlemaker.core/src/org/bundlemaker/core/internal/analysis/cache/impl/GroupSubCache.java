package org.bundlemaker.core.internal.analysis.cache.impl;

import java.util.Map;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.internal.analysis.AdapterGroup2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.modules.Group;
import org.bundlemaker.core.internal.modules.modularizedsystem.AbstractTransformationAwareModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all group artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupSubCache extends AbstractSubCache<Group, IGroupAndModuleContainer> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link GroupSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public GroupSubCache(ArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IGroupAndModuleContainer create(Group classification) {

    // step 1: if the classification is 'null' or empty, we have to return the 'root' artifact
    if (classification == null || classification.getPath().isEmpty()) {
      return getArtifactCache().getRootArtifact();
    }

    for (Map.Entry<Group, IGroupAndModuleContainer> entry : this.entrySet()) {
      System.out.println(entry.getKey() + " : " + entry.getValue());
    }

    // step 2: compute the parent
    IBundleMakerArtifact parent = getGroupParent(classification);

    // step 3: create the new instance
    return new AdapterGroup2IArtifact(classification, parent);
  }

  /**
   * <p>
   * Returns the correct parent for the given classification.
   * </p>
   * 
   * @param group
   * @return
   */
  private IBundleMakerArtifact getGroupParent(Group group) {

    //
    Assert.isNotNull(group);

    //
    IPath parentPath = group.getPath().removeLastSegments(1);

    // if the parent path is 'null' or empty, we have to return the 'root' artifact
    if (parentPath.isEmpty()) {
      return getArtifactCache().getRootArtifact();
    } else {
      Group parentGroup = ((AbstractTransformationAwareModularizedSystem) getArtifactCache().getModularizedSystem())
          .getOrCreateGroup(parentPath);
      return getArtifactCache().getGroupCache().getOrCreate(parentGroup);
    }
  }
}