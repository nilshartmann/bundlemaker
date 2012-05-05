package org.bundlemaker.core.osgi.manifest;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IResourceModule;
import org.eclipse.virgo.util.parser.manifest.ManifestContents;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface IBundleManifestCreator {

  /**
   * <p>
   * </p>
   * 
   * @param modularizedSystem
   * @param resourceModule
   * @param manifestTemplate
   * @param exportPackagePreferences
   * @param packageWiringPreferences
   * @return
   */
  public ManifestContents createManifest(IModularizedSystem modularizedSystem, IResourceModule resourceModule,
      ManifestContents manifestTemplate, ManifestContents originalManifest, IManifestPreferences manifestPreferences);
}
