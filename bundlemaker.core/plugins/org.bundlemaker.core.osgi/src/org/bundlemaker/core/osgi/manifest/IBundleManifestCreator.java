package org.bundlemaker.core.osgi.manifest;

import org.bundlemaker.core.modules.IModularizedSystem;
import org.bundlemaker.core.modules.IModule;
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
  public ManifestContents createManifest(IModularizedSystem modularizedSystem, IModule resourceModule,
      ManifestContents manifestTemplate, ManifestContents originalManifest, IManifestPreferences manifestPreferences);
}
