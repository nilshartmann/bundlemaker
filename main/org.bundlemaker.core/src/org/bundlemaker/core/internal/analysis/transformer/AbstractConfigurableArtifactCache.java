package org.bundlemaker.core.internal.analysis.transformer;

import org.bundlemaker.core.analysis.ArtifactModelConfiguration;
import org.bundlemaker.core.internal.analysis.AbstractAdvancedContainer;
import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractConfigurableArtifactCache extends AbstractCacheAwareArtifactCache {

  /** - */
  private ArtifactModelConfiguration _modelConfiguration;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractConfigurableArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   */
  public AbstractConfigurableArtifactCache(IModifiableModularizedSystem modularizedSystem,
      ArtifactModelConfiguration modelConfiguration) {
    super(modularizedSystem);

    Assert.isNotNull(modelConfiguration);

    //
    _modelConfiguration = modelConfiguration;
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractConfigurableArtifactCache}.
   * </p>
   * 
   * @param modularizedSystem
   * @param rootArtifact
   */
  public AbstractConfigurableArtifactCache(IModularizedSystem modularizedSystem,
      AbstractAdvancedContainer rootArtifact, ArtifactModelConfiguration modelConfiguration) {
    super(modularizedSystem, rootArtifact);

    Assert.isNotNull(modelConfiguration);

    //
    _modelConfiguration = modelConfiguration;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public final ArtifactModelConfiguration getConfiguration() {
    return _modelConfiguration;
  }
}
