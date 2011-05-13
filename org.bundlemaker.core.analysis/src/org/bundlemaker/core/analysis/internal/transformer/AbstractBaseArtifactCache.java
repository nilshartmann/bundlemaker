package org.bundlemaker.core.analysis.internal.transformer;

import org.bundlemaker.core.analysis.IAdvancedArtifact;
import org.bundlemaker.core.analysis.internal.AdapterGroup2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterModule2IArtifact;
import org.bundlemaker.core.analysis.internal.AdapterResourceModule2IArtifact;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
import org.bundlemaker.core.modules.IResourceModule;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.bundlemaker.core.util.GenericCache;
import org.bundlemaker.dependencyanalysis.base.model.IArtifact;
import org.bundlemaker.dependencyanalysis.base.model.impl.AbstractArtifactContainer;
import org.eclipse.core.runtime.IPath;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBaseArtifactCache extends AbstractArtifactCache {

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBaseArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AbstractBaseArtifactCache(IModifiableModularizedSystem modularizedSystem) {
    super(modularizedSystem);
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractBaseArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param artifact
   */
  protected AbstractBaseArtifactCache(IModularizedSystem modularizedSystem, IAdvancedArtifact artifact) {
    super(modularizedSystem, artifact);
  }

  /**
   * {@inheritDoc}
   */
  protected GenericCache<IPath, AbstractArtifactContainer> createGroupCache() {

    // return the group cache
    return new GenericCache<IPath, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IPath classification) {

        //
        if (classification == null || classification.isEmpty()) {
          return (AbstractArtifactContainer) getRootArtifact();
        }

        //
        return new AdapterGroup2IArtifact(classification.lastSegment(), getGroupCache().getOrCreate(
            classification.removeLastSegments(1)));
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  protected GenericCache<IModule, AbstractArtifactContainer> createModuleCache() {

    return new GenericCache<IModule, AbstractArtifactContainer>() {

      @Override
      protected AbstractArtifactContainer create(IModule module) {

        // get the parent
        IArtifact parent = module.hasClassification() ? getGroupCache().getOrCreate(module.getClassification())
            : getRootArtifact();

        //
        return module instanceof IResourceModule ? new AdapterResourceModule2IArtifact((IResourceModule) module, parent)
            : new AdapterModule2IArtifact(module, parent);
      }
    };
  }
}
