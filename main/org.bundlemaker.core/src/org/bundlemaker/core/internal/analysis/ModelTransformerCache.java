/*******************************************************************************
 * Copyright (c) 2011 Bundlemaker project team.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Bundlemaker project team - initial API and implementation
 ******************************************************************************/
package org.bundlemaker.core.internal.analysis;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.analysis.AnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IAnalysisModelConfiguration;
import org.bundlemaker.core.analysis.IRootArtifact;
import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Helper class to transform {@link IModifiableModularizedSystem IModifiableModularizedSystems}.
 * </p>
 */
// TODO: move to internal
public class ModelTransformerCache {

  //
  private static Map<CacheKey, IRootArtifact> _cache = new HashMap<CacheKey, IRootArtifact>();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Collection<IRootArtifact> getAllArtifactModels() {
    return _cache.values();
  }

  /**
   * <p>
   * </p>
   */
  public void invalidateCache() {
    _cache.clear();
  }

  /**
   * <p>
   * Transforms the given {@link IModifiableModularizedSystem} in a {@link IDependencyModel}.
   * </p>
   * 
   * @param modifiableModularizedSystem
   *          the modularized system
   * @return the {@link IDependencyModel}
   */
  public IRootArtifact getArtifactModel(IModifiableModularizedSystem modifiableModularizedSystem,
      IAnalysisModelConfiguration configuration) {

    // assert not null
    Assert.isNotNull(modifiableModularizedSystem);

    // set the default configuration if no configuration is set
    configuration = configuration == null ? new AnalysisModelConfiguration() : configuration;

    //
    CacheKey cacheKey = new CacheKey(modifiableModularizedSystem, configuration);

    //
    if (_cache.containsKey(cacheKey)) {
      return _cache.get(cacheKey);
    }

    try {

      // create the artifact cache
      ArtifactCache artifactCache = new ArtifactCache(modifiableModularizedSystem, configuration);

      // create the dependency model
      IRootArtifact artifactModel = artifactCache.transform();

      _cache.put(cacheKey, artifactModel);
      return artifactModel;

    } catch (CoreException e) {
      throw new RuntimeException("Error in ModelTransformer.transformWithAggregatedTypes: " + e.getMessage(), e);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private static class CacheKey {

    /* - */
    private IModifiableModularizedSystem _modifiableModularizedSystem;

    /* - */
    private IAnalysisModelConfiguration  _configuration;

    /**
     * <p>
     * Creates a new instance of type {@link CacheKey}.
     * </p>
     * 
     * @param modifiableModularizedSystem
     * @param configuration
     */
    public CacheKey(IModifiableModularizedSystem modifiableModularizedSystem, IAnalysisModelConfiguration configuration) {

      Assert.isNotNull(modifiableModularizedSystem);
      Assert.isNotNull(configuration);

      this._modifiableModularizedSystem = modifiableModularizedSystem;
      this._configuration = configuration;
    }

    // /**
    // * <p>
    // * </p>
    // *
    // * @return
    // */
    // public final IModifiableModularizedSystem getModifiableModularizedSystem() {
    // return _modifiableModularizedSystem;
    // }
    //
    // /**
    // * <p>
    // * </p>
    // *
    // * @return
    // */
    // public final IArtifactModelConfiguration getConfiguration() {
    // return _configuration;
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((_configuration == null) ? 0 : _configuration.hashCode());
      result = prime * result + ((_modifiableModularizedSystem == null) ? 0 : _modifiableModularizedSystem.hashCode());
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      CacheKey other = (CacheKey) obj;
      if (_configuration == null) {
        if (other._configuration != null)
          return false;
      } else if (!_configuration.equals(other._configuration))
        return false;
      if (_modifiableModularizedSystem == null) {
        if (other._modifiableModularizedSystem != null)
          return false;
      } else if (!_modifiableModularizedSystem.equals(other._modifiableModularizedSystem))
        return false;
      return true;
    }
  }
}
