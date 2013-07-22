package org.bundlemaker.core.internal.gson;

import org.bundlemaker.core.analysis.IArtifactSelector;
import org.bundlemaker.core.analysis.IBundleMakerArtifact;
import org.bundlemaker.core.common.collections.GenericCache;
import org.bundlemaker.core.resource.IModularizedSystem;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class GsonHelper {

  /** - */
  @SuppressWarnings("serial")
  private static GenericCache<IModularizedSystem, Gson> _gsonMap = new GenericCache<IModularizedSystem, Gson>() {

                                                                   @Override
                                                                   protected Gson create(
                                                                       IModularizedSystem modularizedSystem) {

                                                                     return new GsonBuilder()
                                                                         .registerTypeHierarchyAdapter(
                                                                             IBundleMakerArtifact.class,
                                                                             new BundleMakerArtifactJsonAdapter(
                                                                                 modularizedSystem)
                                                                         )
                                                                         .registerTypeAdapter(IArtifactSelector.class,
                                                                             new ArtifactSelectorJsonAdapter())
                                                                         .registerTypeAdapter(IArtifactSelector.class,
                                                                             new ArtifactSelectorJsonAdapter()).
                                                                         excludeFieldsWithoutExposeAnnotation()
                                                                         .create();
                                                                   }
                                                                 };

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @return
   */
  public static Gson gson(IModularizedSystem modularizedSystem) {
    return _gsonMap.getOrCreate(modularizedSystem);
  }
}
