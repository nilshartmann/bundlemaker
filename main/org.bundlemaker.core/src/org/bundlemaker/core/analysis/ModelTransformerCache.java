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
package org.bundlemaker.core.analysis;

import java.util.HashMap;
import java.util.Map;

import org.bundlemaker.core.internal.analysis.cache.ArtifactCache;
import org.bundlemaker.core.modules.modifiable.IModifiableModularizedSystem;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;

/**
 * <p>
 * Helper class to transform {@link IModifiableModularizedSystem IModifiableModularizedSystems}.
 * </p>
 */
public class ModelTransformerCache {

  //
  private static Map<CacheKey, IRootArtifact> _cache = new HashMap<CacheKey, IRootArtifact>();

  /**
   * <p>
   * </p>
   */
  public static void invalidateCache() {
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
  public static IRootArtifact getArtifactModel(IModifiableModularizedSystem modifiableModularizedSystem,
      IArtifactModelConfiguration configuration) {

    // assert not null
    Assert.isNotNull(modifiableModularizedSystem);

    // set the default configuration if no configuration is set
    configuration = configuration == null ? new ArtifactModelConfiguration() : configuration;

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
      System.out.println(" --> Error in ModelTransformer.transformWithAggregatedTypes: " + e);
      e.printStackTrace();
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
    private IArtifactModelConfiguration  _configuration;

    /**
     * <p>
     * Creates a new instance of type {@link CacheKey}.
     * </p>
     * 
     * @param modifiableModularizedSystem
     * @param configuration
     */
    public CacheKey(IModifiableModularizedSystem modifiableModularizedSystem, IArtifactModelConfiguration configuration) {

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
