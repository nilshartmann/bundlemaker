package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterGroup2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GroupCache extends AbstractArtifactCacheAwareGenericCache<IPath, AbstractArtifactContainer> {

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
  protected AbstractArtifactContainer create(IPath classification) {

    //
    if (classification == null || classification.isEmpty()) {
      return (AbstractArtifactContainer) getArtifactCache().getRootArtifact();
    }

    //
    return new AdapterGroup2IArtifact(classification.lastSegment(), getArtifactCache().getGroupCache().getOrCreate(
        classification.removeLastSegments(1)));
  }
}