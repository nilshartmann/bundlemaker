package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.util.GenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract {@link GenericCache} that contains a {@link DefaultArtifactCache}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <K>
 * @param <V>
 */
public abstract class AbstractArtifactCacheAwareGenericCache<K, V> extends GenericCache<K, V> {

  /** the default artifact cache */
  private final DefaultArtifactCache _artifactCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractArtifactCacheAwareGenericCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public AbstractArtifactCacheAwareGenericCache(DefaultArtifactCache artifactCache) {
    Assert.isNotNull(artifactCache);

    _artifactCache = artifactCache;
  }

  /**
   * <p>
   * Returns the {@link DefaultArtifactCache}.
   * </p>
   * 
   * @return
   */
  public DefaultArtifactCache getArtifactCache() {
    return _artifactCache;
  }
}