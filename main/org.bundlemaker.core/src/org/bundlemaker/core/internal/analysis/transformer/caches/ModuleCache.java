package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterModule2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleCache extends AbstractArtifactCacheAwareGenericCache<IModule, AbstractArtifactContainer> {

  /**
   * <p>
   * Creates a new instance of type {@link ModuleCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public ModuleCache(DefaultArtifactCache artifactCache) {
    super(artifactCache);
  }

  @Override
  protected AbstractArtifactContainer create(IModule module) {

    // get the parent
    IArtifact parent = module.hasClassification() ? getArtifactCache().getGroupCache().getOrCreate(
        module.getClassification()) : getArtifactCache().getRootArtifact();

    //
    return module instanceof IResourceModule ? new AdapterResourceModule2IArtifact((IResourceModule) module, parent)
        : new AdapterModule2IArtifact(module, parent);
  }
}