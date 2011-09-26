package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.core.internal.analysis.AbstractAdvancedContainer;
import org.bundlemaker.core.internal.analysis.AdapterGroup2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupCache extends AbstractArtifactCacheAwareGenericCache<IPath, AbstractAdvancedContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link GroupCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public GroupCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractAdvancedContainer create(IPath classification) {

    System.out.println("Create " + classification);

    //
    if (classification == null || classification.isEmpty()) {
      return (AbstractAdvancedContainer) getArtifactCache().getRootArtifact();
    }

    IArtifact parent = getParent(classification);

    //
    return new AdapterGroup2IArtifact(classification.lastSegment(), parent);
  }

  public AbstractAdvancedContainer getOrCreate(IPath key) {
    return super.getOrCreate(key);
  }

  public IArtifact getParent(IPath classification) {
    IArtifact parent = getArtifactCache().getGroupCache().getOrCreate(classification.removeLastSegments(1));
    return parent;
  }
}