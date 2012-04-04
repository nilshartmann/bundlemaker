package org.bundlemaker.core.internal.analysis.cache.impl;

import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.analysis.IGroupAndModuleContainer;
import org.bundlemaker.core.analysis.IModuleArtifact;
import org.bundlemaker.core.internal.analysis.AdapterModule2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.internal.analysis.cache.ModuleKey;
import org.bundlemaker.core.internal.analysis.virtual.VirtualModule2IArtifact;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * Implementation of an {@link AbstractSubCache} that holds all module artifacts.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleSubCache extends AbstractSubCache<ModuleKey, IModuleArtifact> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link ModuleSubCache}.
   * </p>
   * 
   * @param artifactCache
   */
  public ModuleSubCache(ArtifactCache artifactCache) {
    super(artifactCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IModuleArtifact create(ModuleKey moduleKey) {

    Assert.isNotNull(moduleKey);

    // step 1: if the module key has a module, we have to create a 'real' AdapterModule2IArtifact...
    if (moduleKey.hasModule()) {

      // get the module
      IModule module = moduleKey.getModule();

      // get the parent
      IBundleMakerArtifact parent = getModuleParent(module);

      // return the module adapter
      return module instanceof IResourceModule ? new AdapterResourceModule2IArtifact((IResourceModule) module, parent,
          getArtifactCache()) : new AdapterModule2IArtifact(module, parent);

    }

    // step 2: ...otherwise we have to create a VirtualModule2IArtifact
    else {

      // return VirtualModule2IArtifact
      return new VirtualModule2IArtifact(moduleKey.getModuleName(), moduleKey.getModuleName(), getArtifactCache()
          .getRootArtifact());
    }
  }

  /**
   * <p>
   * Returns the
   * </p>
   * 
   * @param module
   * @return
   */
  public IGroupAndModuleContainer getModuleParent(IModule module) {

    Assert.isNotNull(module);

    // step 1: if module has no classification, we have to return the root artifact...
    if (!module.hasClassification()) {
      return getArtifactCache().getRootArtifact();
    }

    // step 2: ... otherwise we have to return the group artifact
    else {
      return getArtifactCache().getGroupCache().getOrCreate(module.getClassification());
    }
  }
}