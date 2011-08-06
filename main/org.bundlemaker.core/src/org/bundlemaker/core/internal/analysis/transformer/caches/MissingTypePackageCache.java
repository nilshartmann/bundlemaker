package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.VirtualArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MissingTypePackageCache extends AbstractArtifactCacheAwareGenericCache<String, AbstractArtifactContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link MissingTypePackageCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public MissingTypePackageCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractArtifactContainer create(String packageName) {
    return new VirtualArtifact(ArtifactType.Package, packageName, getArtifactCache().getMissingTypesVirtualModule());
  }
}