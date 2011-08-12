package org.bundlemaker.core.internal.analysis.transformer.caches;

import org.bundlemaker.analysis.model.IArtifact;
import org.bundlemaker.analysis.model.impl.AbstractArtifactContainer;
import org.bundlemaker.core.internal.analysis.AdapterModule2IArtifact;
import org.bundlemaker.core.internal.analysis.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.internal.analysis.transformer.DefaultArtifactCache;
import org.bundlemaker.core.internal.analysis.transformer.caches.ModuleCache.ModuleKey;
import org.bundlemaker.core.internal.analysis.virtual.VirtualModule2IArtifact;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ModuleCache extends AbstractArtifactCacheAwareGenericCache<ModuleKey, AbstractArtifactContainer> {

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
  protected AbstractArtifactContainer create(ModuleKey moduleKey) {

    if (moduleKey.hasModule()) {

      IModule module = moduleKey.getModule();

      // get the parent
      IArtifact parent = module.hasClassification() ? getArtifactCache().getGroupCache().getOrCreate(
          module.getClassification()) : getArtifactCache().getRootArtifact();
      //
      return module instanceof IResourceModule ? new AdapterResourceModule2IArtifact((IResourceModule) module, parent)
          : new AdapterModule2IArtifact(module, parent);

    } else {

      //
      return new VirtualModule2IArtifact(moduleKey.getModuleName(), moduleKey.getModuleName(), getArtifactCache()
          .getRootArtifact());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class ModuleKey {

    /** - */
    private String  _moduleName;

    /** - */
    private IModule _module;

    /**
     * <p>
     * Creates a new instance of type {@link ModuleKey}.
     * </p>
     * 
     * @param moduleName
     */
    public ModuleKey(String moduleName) {
      Assert.isNotNull(moduleName);

      _moduleName = moduleName;
    }

    /**
     * <p>
     * Creates a new instance of type {@link ModuleKey}.
     * </p>
     * 
     * @param module
     */
    public ModuleKey(IModule module) {
      Assert.isNotNull(module);

      _module = module;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public final String getModuleName() {
      return _moduleName;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public final IModule getModule() {
      return _module;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public boolean hasModule() {
      return _module != null;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_module == null) ? 0 : _module.hashCode());
      result = prime * result + ((_moduleName == null) ? 0 : _moduleName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ModuleKey other = (ModuleKey) obj;
      if (_module == null) {
        if (other._module != null)
          return false;
      } else if (!_module.equals(other._module))
        return false;
      if (_moduleName == null) {
        if (other._moduleName != null)
          return false;
      } else if (!_moduleName.equals(other._moduleName))
        return false;
      return true;
    }
  }
}