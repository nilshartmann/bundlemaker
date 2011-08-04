package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.ArtifactType;
import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.VirtualArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class MissingTypeCache extends AbstractArtifactCacheAwareGenericCache<String, AbstractArtifactContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link MissingTypeCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public MissingTypeCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AbstractArtifactContainer create(String typeName) {

    IArtifact parent = getArtifactCache().getMissingTypesVirtualModule();

    //
    int index = typeName.lastIndexOf('.');

    if (index != -1) {
      parent = getArtifactCache().getMissingTypePackageCache().getOrCreate(typeName.substring(0, index));
      typeName = typeName.substring(index + 1);
    }

    //
    return new VirtualArtifact(ArtifactType.Type, typeName, parent);
  }
}