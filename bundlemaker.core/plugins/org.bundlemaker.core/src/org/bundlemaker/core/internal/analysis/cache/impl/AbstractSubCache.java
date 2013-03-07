package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.util.collections.SymetricGenericCache;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Abstract {@link SymetricGenericCache} that contains a {@link DefaultArtifactCache} instance. Subclasses of this class
 * can access the {@link DefaultArtifactCache} to access other cache instances. This is necessary to construct the
 * parent dependencies properly.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <K>
 * @param <V>
 */
public abstract class AbstractSubCache<K, V> extends SymetricGenericCache<K, V> {

  /** serialVersionUID */
  private static final long                     serialVersionUID = 1L;

  /** the default artifact cache */
  private final ArtifactCache _artifactCache;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public AbstractSubCache(ArtifactCache artifactCache) {
    Assert.isNotNull(artifactCache);

    // set the artifact cache
    _artifactCache = artifactCache;
  }

  /**
   * <p>
   * Returns the {@link DefaultArtifactCache}.
   * </p>
   * 
   * @return
   */
  public final ArtifactCache getArtifactCache() {
    return _artifactCache;
  }
}